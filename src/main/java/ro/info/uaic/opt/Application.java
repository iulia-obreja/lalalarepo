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
package ro.info.uaic.opt;

import java.io.IOException;
import ro.info.uaic.opt.model.Pachet;
import ro.info.uaic.opt.model.Repartizare;
import ro.info.uaic.opt.report.Report;
import ro.info.uaic.opt.report.ReportManager;

import javax.faces.bean.ManagedBean;

import static commons.Constants.INPUT_FILEPATH;
/**
 *
 * @author Cristian Frăsinaru
 */

public class Application {

    public static final boolean DEBUG = true;
    public static final String NAME = "FiiOpt";
    public static final String VERSION = "1.0";
    //
    public static final int MAX_STUDENTI_GRUPA = 30;
    public static final String INPUT_FOLDER = INPUT_FILEPATH;
    public static final String OUTPUT_FOLDER = "output";
    public static final int[] ANI_STUDIU = new int[]{2, 3};
    //
//    public static final int MAX_STUDENTI_GRUPA = 2;
//    public static final String INPUT_FOLDER = "demo";
//    public static final String OUTPUT_FOLDER = "demo/output";
//    public static final int[] ANI_STUDIU = new int[]{2};

    /**
     *
     * @throws IOException
     */
    public static void courseAllocation() throws IOException {
        //
        Repartizare rep = Repartizare.getInstance();
        rep.loadData();

        //
        for (Pachet pachet : rep.getPachete()) {
            RepartizareProblem problem = new RepartizareProblem(pachet);
            problem.solve();
        }


        Report index = ReportManager.createReports();
        index.show();

    }

}
