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
package ro.info.uaic.opt.input;

import ro.info.uaic.opt.model.Optional;
import ro.info.uaic.opt.model.Pachet;
import ro.info.uaic.opt.model.Student;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristian Frăsinaru
 */
public class ImportPrefStudenti extends ImportFile {

    private static final int TIMESTAMP_COL = 0;
    private static final int COD_STUDENT_COL = 1;
    private static final int NUME_STUDENT_COL = 2;

    private final int anStudiu;

    /**
     *
     * @param fileName
     * @param anStudiu
     */
    public ImportPrefStudenti(String fileName, int anStudiu) {
        super(fileName);
        this.anStudiu = anStudiu;
    }

    /**
     *
     * @param line
     */
    @Override
    protected void parseLine(String line) {
        if (line == null || line.isEmpty()) {
            return;
        }
        String tokens[] = line.split(CSV_SPLITTER);
        trim(tokens);

        String timestamp = tokens[TIMESTAMP_COL];
        String codStudent = tokens[COD_STUDENT_COL].toUpperCase();
        String numeStudent = tokens[NUME_STUDENT_COL];
        if (codStudent.isEmpty()) {
            log(line, "Lipseste codul studentului");
            return;
        }
        Student student = repartizare.getStudent(codStudent);
        if (student == null) {
            student = repartizare.findStudentByNume(numeStudent, anStudiu);
            if (student == null) {
                log(line, "Student inexistent: " + codStudent + " -- " + numeStudent);
                return;
            } else {
                log(line, "Student fuzzy: " + codStudent + ", " + numeStudent
                        + " --> " + student.getCod() + ", " + student.getNume());
            }
        }

        if (student.isListaPrefSet()) {
            //log(line, "Preferinte duplicat: " + codStudent + " -- " + numeStudent);
            return;
        }
        int i = NUME_STUDENT_COL + 1;
        for (Pachet pachet : repartizare.getPachete(anStudiu)) {
            int n = pachet.getNrDiscipline();
            List<Optional> discipline = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                String codDisc = tokens[i++];
                Optional disc = (Optional) repartizare.getDisciplina(codDisc);
                if (disc == null) {
                    log(line, "Cod disciplina inexistent " + codDisc);
                    return;
                }
                if (discipline.contains(disc)) {
                    //log(line, "Disciplina duplicat in preferinte: " + codDisc);
                    continue;
                }
                discipline.add(disc);
            }
            student.setListaPref(pachet, discipline);
        }
    }

}
