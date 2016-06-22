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
import ro.info.uaic.opt.model.Pachet;

/**
 * cod, nume, an, email
 *
 * @author Cristian Frăsinaru
 */
public class ImportPachete extends ImportFile {

    private static final int COD_COL = 0;
    private static final int AN_COL = 1;
    private static final int SEMESTRU_COL = 2;
    private static final int DISCIPLINE_COL = 3;

    /**
     *
     */
    public ImportPachete() {
        super(Repartizare.PACHETE_FILE);
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
        if (tokens.length < 4) {
            log(line, "Sunt specificate doar " + tokens.length + " coloane");
        }
        trim(tokens);

        String cod = tokens[COD_COL];
        if (cod.isEmpty()) {
            log(line, "Lipseste codul pachetului");
            return;
        }

        int an = 0;
        try {
            an = Integer.parseInt(tokens[AN_COL]);
            if (an != 2 && an != 3) {
                log(line, "Anul de studiu trebuie sa fie 2 sau 3");
                return;
            }
        } catch (NumberFormatException e) {
            log(line, "An de studiu invalid " + e.getMessage());
            return;
        }

        int semestru = 0;
        try {
            semestru = Integer.parseInt(tokens[SEMESTRU_COL]);
            if (semestru != 1 && semestru != 2) {
                log(line, "Semestrul trebuie sa fie 1 sau 2");
                return;
            }
        } catch (NumberFormatException e) {
            log(line, "Semestru invalid: " + e.getMessage());
            return;
        }

        int disc = 0;
        try {
            disc = Integer.parseInt(tokens[DISCIPLINE_COL]);
            if (disc <= 0) {
                log(line, "Numar invalid de discipline: " + disc);
                return;
            }
        } catch (NumberFormatException e) {
            log(line, "Numar invalid de discipline: " + e.getMessage());
            return;
        }

        if (repartizare.getPachet(cod) != null) {
            log(line, "Cod pachet duplicat " + cod);
            return;
        }
        Pachet pachet = new Pachet(cod, an, semestru, disc);
        repartizare.addPachet(pachet);
    }

}
