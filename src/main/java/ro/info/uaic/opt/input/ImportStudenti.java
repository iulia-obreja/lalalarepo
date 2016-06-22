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

import ro.info.uaic.opt.model.Repartizare;
import ro.info.uaic.opt.model.Student;

/**
 * cod, nume, an, email
 *
 * @author Cristian Frăsinaru
 */
public class ImportStudenti extends ImportFile {

    private static final int AN_COL = 0;
    private static final int NUME_COL = 1;
    private static final int COD_COL = 2;
    private static final int EMAIL_COL = 3;

    /**
     *
     */
    public ImportStudenti() {
        super(Repartizare.STUDENTI_FILE);
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
        if (tokens.length < 3) {
            log(line, "Sunt specificate doar " + tokens.length + " coloane");
            return;
        }
        trim(tokens);

        int an = 1;
        try {
            an = Integer.parseInt(tokens[AN_COL]);
            if (an != 1 && an != 2) {
                log(line, "Anul de studiu trebuie sa fie 1 sau 2");
                return;
            }
        } catch (NumberFormatException e) {
            log(line, "An de studiu invalid: " + e.getMessage());
            return;
        }

        String cod = tokens[COD_COL];
        if (cod.isEmpty()) {
            log(line, "Lipseste codul studentului");
            return;
        }

        String nume = tokens[NUME_COL];
        if (nume.isEmpty()) {
            log(line, "Lipseste numele studentului");
            return;
        }

        String email = null;
        if (EMAIL_COL < tokens.length) {
            email = tokens[EMAIL_COL];
        }

        if (repartizare.getStudent(cod) != null) {
            log(line, "Cod student duplicat: " + cod);
            return;
        }
        Student student = new Student(cod, nume, an, email);
        repartizare.addStudent(student);
    }

}
