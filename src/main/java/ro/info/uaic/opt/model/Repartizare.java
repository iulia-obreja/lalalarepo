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
package ro.info.uaic.opt.model;

import ro.info.uaic.opt.input.ImportPrefDiscipline;
import ro.info.uaic.opt.input.ImportPrefStudenti;
import ro.info.uaic.opt.input.ImportNote;
import ro.info.uaic.opt.input.ImportDiscipline;
import ro.info.uaic.opt.input.ImportPachete;
import ro.info.uaic.opt.input.ImportStudenti;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import ro.info.uaic.opt.Application;
import static ro.info.uaic.opt.Application.ANI_STUDIU;


/**
 *
 * @author Cristian Frăsinaru
 */
public class Repartizare {

    private static final Repartizare instance = new Repartizare();
    public static final String PACHETE_FILE = "pachete.csv";
    public static final String STUDENTI_FILE = "studenti.csv";
    public static final String DISCIPLINE_FILE = "discipline.csv";
    public static final String NOTE_FILE = "note.csv";
    public static final String PREF_STUDENTI_FILE = "pref_studenti.csv";
    public static final String PREF_DISCIPLINE_FILE = "pref_discipline.csv";
    public static final String ERR_FILE = "erori.txt";

    private final Map<String, Student> studenti = new HashMap<>();
    private final Map<String, Pachet> pachete = new HashMap<>();
    private final Map<String, Disciplina> discipline = new HashMap<>();
    private final List<Optional> optionale = new ArrayList<>();

    private PrintWriter errWriter = null;
    private int errorCount;

    private Repartizare() {
    }

    public void loadData() {
        try {
            errorCount = 0;
            errWriter = new PrintWriter(new FileWriter(Application.INPUT_FOLDER + ERR_FILE));
            //
            new ImportPachete().parseFile();
            new ImportStudenti().parseFile();
            new ImportDiscipline().parseFile();
            //new ProcesareNote() .parseFile();

            new ImportNote().parseFile();
            for (int i = 0; i < ANI_STUDIU.length; i++) {
                int an = ANI_STUDIU[i];
                new ImportPrefStudenti("pref_studenti" + (an - 1) + ".csv", an).parseFile();
            }
            new ImportPrefDiscipline().parseFile();
            //
            errWriter.close();
            if (errorCount > 0) {
                //Desktop.getDesktop().open(new File(ERR_FILE));
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (errWriter != null) {
                errWriter.close();
            }
        }
    }

    public static Repartizare getInstance() {
        return instance;
    }

    /**
     *
     * @param student
     */
    public void addStudent(Student student) {
        studenti.put(student.getCod(), student);
    }

    /**
     *
     * @param cod
     * @return
     */
    public Student getStudent(String cod) {
        return studenti.get(cod);
    }

    /**
     *
     * @param pachet
     */
    public void addPachet(Pachet pachet) {
        pachete.put(pachet.getCod(), pachet);
    }

    /**
     *
     * @param cod
     * @return
     */
    public Pachet getPachet(String cod) {
        return pachete.get(cod);
    }

    /**
     *
     * @param cod
     * @return
     */
    public Disciplina getDisciplina(String cod) {
        return discipline.get(cod);
    }

    /**
     *
     * @param id
     * @return
     */
    public Disciplina findDisciplinaById(String id) {
        for (Disciplina disc : discipline.values()) {
            if (disc.getId().trim().equalsIgnoreCase(id)) {
                return disc;
            }
        }
        return null;
    }

    /**
     *
     * @param denumire
     * @return
     */
    public Disciplina findDisciplinaByDenumire(String denumire) {
        Disciplina best = null;
        double maxDist = 0.9;
        for (Disciplina disc : discipline.values()) {
            String s1 = disc.getDenumire().toLowerCase();
            String s2 = denumire;
            double dist = StringUtils.getJaroWinklerDistance(s1, s2);
            if (dist > maxDist) {
                maxDist = dist;
                best = disc;
                if (maxDist == 1.0) {
                    break;
                }
            }
        }
        if (best != null) {
            best.setObs(String.valueOf(maxDist));
        }
        return best;
    }

    /**
     *
     * @param nume
     * @param anStudiu
     * @return
     */
    public Student findStudentByNume(String nume, int anStudiu) {
        /*
        for (Student student : studenti.values()) {
            if (student.getNume().equalsIgnoreCase(nume)) {
                return student;
            }
        }
        return null;
         */
        Student best = null;
        double maxDist = 0.8;
        for (Student student : studenti.values()) {
            if (student.getAnStudiu() != anStudiu) {
                continue;
            }
            String s1 = student.getNume().toLowerCase();
            String s2 = nume.toLowerCase();
            double dist = StringUtils.getJaroWinklerDistance(s1, s2);
            if (dist > maxDist) {
                maxDist = dist;
                best = student;
                if (maxDist == 1.0) {
                    break;
                }
            }
        }
        return best;

    }

    /**
     *
     * @param disc
     */
    public void addDisciplina(Disciplina disc) {
        discipline.put(disc.getCod(), disc);
        if (disc instanceof Optional) {
            optionale.add((Optional) disc);
        }
    }

    /**
     * @return the optionale
     */
    public List<Optional> getOptionale() {
        return optionale;
    }

    /**
     *
     * @return
     */
    public List<Pachet> getPachete() {
        return getPachete(0);
    }

    /**
     *
     * @param anStudiu
     * @return
     */
    public List<Pachet> getPachete(int anStudiu) {
        List<Pachet> list = new ArrayList<>();
        for (Pachet p : pachete.values()) {
            if (anStudiu == 0 || p.getAnStudiu() == anStudiu) {
                list.add(p);
            }
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Returneaza toti studentii (anul 1 si anul 2)
     *
     * @return
     */
    public List<Student> getStudenti() {
        return getStudenti(0);
    }

    /**
     * Returneaza doar studentii si-au manifestat preferinte
     *
     * @param anStudiu
     * @return
     */
    public List<Student> getStudenti(int anStudiu) {
        List<Student> list = new ArrayList<>();
        for (Student st : studenti.values()) {
            if ((anStudiu == 0 || st.getAnStudiu() == anStudiu) && st.isListaPrefSet()) {
                list.add(st);
            }
        }
        Collections.sort(list);
        return list;
    }

    /**
     *
     * @param message
     */
    public void log(String message) {
        errorCount++;
        System.err.println(message);
        errWriter.println(message);
    }

}
