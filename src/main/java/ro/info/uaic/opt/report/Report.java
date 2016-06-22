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

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import ro.info.uaic.opt.Application;
import ro.info.uaic.opt.model.Repartizare;

/**
 *
 * @author Cristian Frăsinaru
 */
public class Report {

    protected String template;
    protected String output;
    protected Map<String, Object> data;

    /**
     *
     * @param template
     * @param output
     */
    public Report(String template, String output) {
        this.template = template;
        this.output = Application.OUTPUT_FOLDER + "/" + output;
        this.data = new HashMap<>();
    }

    protected Map<String, Object> getData() {
        return data;
    }

    /**
     * Returneaza false -> abort
     *
     * @return
     */
    protected boolean prepareData() {
        data.put("debug", Application.DEBUG);
        data.put("app", Application.NAME + " " + Application.VERSION);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");        
        data.put("publishDate", fmt.format(LocalDate.now()));
        data.put("repartizare", Repartizare.getInstance());
        data.put("aniStudiu", Application.ANI_STUDIU);
        return true;
    }

    /**
     *
     * @return
     */
    public boolean create() {
        try {
            if (!prepareData()) {
                return false;
            }
            Template temp = ReportManager.getTemplate(template);
            String filename = getFilename();

            try (Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), temp.getEncoding()))) {
                temp.process(data, out);
            }
            return true;
        } catch (TemplateException e) {
            System.err.println("Template exception in report " + template + "\n" + e);
            return false;
        } catch (IOException e) {
            System.err.println("IO exception in report " + template + "\n" + e);
            return false;
        }
    }

    /**
     *
     */
    public void show() {
        show(true);
    }

    /**
     *
     * @param create
     */
    public void show(boolean create) {
        if (create) {
            boolean ok = create();
            if (!ok) {
                return;
            }
        }
        try {
            Desktop.getDesktop().open(new File(getFilename()));
        } catch (IOException e) {
            System.err.println("Exception showing the report " + output + "\n" + e);
        }
    }

    private String getFilename() throws IOException {
        String folder = ".";
        String fileName = folder + "/" + output;
        fileName = fileName.replace(' ', '_');
        return new File(fileName).getCanonicalPath();
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(String output) {
        this.output = output;
    }
}
