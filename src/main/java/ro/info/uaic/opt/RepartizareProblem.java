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

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import ro.info.uaic.opt.model.Optional;
import ro.info.uaic.opt.model.Pachet;
import ro.info.uaic.opt.model.Repartizare;
import ro.info.uaic.opt.model.Student;
import ro.infoiasi.sm.Element;
import ro.infoiasi.sm.Matching;
import ro.infoiasi.sm.Partition;
import ro.infoiasi.sm.Problem;
import ro.infoiasi.sm.XSMPWriter;
import ro.infoiasi.sm.solver.GaleShapleySolver;

/**
 *
 * @author Cristian Frăsinaru
 */
public class RepartizareProblem extends Problem {

    private final Repartizare repartizare;
    private final Pachet pachet;

    public RepartizareProblem(Pachet pachet) {
        setName("Repartizare Optionale");
        System.out.println(pachet);
        this.repartizare = Repartizare.getInstance();
        this.pachet = pachet;
        int an = pachet.getAnStudiu() - 1;
        List<Student> studenti = repartizare.getStudenti(an);

        //scoatem studentii care nu au preferinte
        for (ListIterator<Student> it = studenti.listIterator(); it.hasNext();) {
            Student stud = it.next();
            if (stud.getListaPref(pachet) == null) {
                it.remove();
            }
        }

        //create the partitions
        Partition studParty = createPartition("studenti");
        Partition discParty = createPartition("optionale");

        for (Student stud : studenti) {
            Element e = studParty.createElement(stud.getCod());
            e.setUserObject(stud);

        }

        for (Optional disc : pachet.getDiscipline()) {
            Element e = discParty.createElement(disc.getCod(), disc.getGrupe() * Application.MAX_STUDENTI_GRUPA);
            e.setUserObject(disc);
        }

        //Prefs studenti
        for (Element studElem : studParty.getElements()) {
            Student stud = (Student) studElem.getUserObject();
            for (Optional opt : stud.getListaPref(pachet)) {
                Element optElem = discParty.findElement(opt.getCod());
                studElem.addPreference(optElem);
            }
        }

        //Prefs disciplina
        for (Element optElem : discParty.getElements()) {
            Optional disc = (Optional) optElem.getUserObject();
            //Sortam studentii
            Collections.sort(studenti, disc::compareByPunctaj);
            for (Student stud : studenti) {
                Element studElem = studParty.findElement(stud.getCod());
                optElem.addPreference(studElem);
            }
        }
    }

    /**
     * 
     */
    public void solve() {
        try {
            XSMPWriter.write(this, new FileWriter("xsmp/repartizare_" + pachet.getCod() + ".xml"));
        } catch (IOException ex) {
            System.err.println(ex);
        }
        GaleShapleySolver gs = new GaleShapleySolver(this);
        Matching matching = gs.solve();
        for (Element studElem : getPartition(0).getElements()) {
            Student stud = (Student) studElem.getUserObject();
            if (matching.isEmpty(studElem)) {
                System.out.println(stud + " nerepartizat");
            } else {
                Element discElem = matching.get(studElem)[0];
                Optional disc = (Optional) discElem.getUserObject();
                stud.addOptional(pachet, disc);
            }
        }
    }
}
