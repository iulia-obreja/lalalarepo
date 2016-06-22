/*
 * Copyright (C) 2016 Faculty of Computer Science Iasi, Romania
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ro.info.uaic.opt.report;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Locale;
import ro.info.uaic.opt.Application;
import ro.info.uaic.opt.model.Repartizare;
import static ro.info.uaic.opt.Application.ANI_STUDIU;

/**
 *
 * @author Cristian Frasinaru
 */
public class ReportManager {

    private static Configuration cfg;
    private static boolean local = false;
    public static final String TEMPLATES_FOLDER = "templates";

    /**
     * Creates a new instance of ReportManager
     */
    private ReportManager() {
    }

    public static void init() {
        try {
            makeDirectory(Application.OUTPUT_FOLDER);
            Path html = makeDirectory(Application.OUTPUT_FOLDER + "/html");
            Files.copy(Paths.get("style.css"), html.resolve("style.css"), REPLACE_EXISTING);
        } catch (IOException ex) {
            System.err.println(ex);
            return;
        }

        try {
            cfg = new Configuration();
            cfg.setEncoding(Locale.ENGLISH, "utf-8");
            String dir = "./templates";
            if (!dir.equals("")) {
                try {
                    cfg.setDirectoryForTemplateLoading(new File(dir));
                    local = true;
                } catch (IOException ex) {
                    System.err.println(ex);
                    cfg.setClassForTemplateLoading(ReportManager.class, "/");
                }
            } else {
                cfg.setClassForTemplateLoading(ReportManager.class, "/");
            }
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setSharedVariable("appName", Application.NAME + " " + Application.VERSION);

        } catch (TemplateModelException e) {
            System.err.println("FreeMarker shared variable error\n" + e);
        }
    }

    private static Path makeDirectory(String dirName) throws IOException {
        Path dir = Paths.get(dirName);
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }
        return dir;
    }

    public static Template getTemplate(String template) throws IOException {
        if (!local) {
            template = TEMPLATES_FOLDER + "/" + template;
        }
        return cfg.getTemplate(template);
    }

    public static Report createReports() {
        ReportManager.init();
        Report index = new IndexReport();
        index.create();

        new IndexOptionaleReport().create();
        for (int i = 0; i < ANI_STUDIU.length; i++) {
            new StudentiReport(ANI_STUDIU[i] - 1).create();
        }
        Repartizare.getInstance().getOptionale().stream().forEach((opt) -> {
            new OptionalReport(opt).create();
        });
        new RepartizareCSV().create();
        return index;
    }
}
