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
import ro.info.uaic.opt.model.Disciplina;
import ro.info.uaic.opt.model.Optional;
import ro.info.uaic.opt.model.Pachet;

/**
 * cod, denumire, an, semestru, titular, descriere, pachet, grupe
 *
 * @author Cristian Frăsinaru
 */
public class ImportDiscipline extends ImportFile {

    private static final int ID_COL = 0;
    private static final int COD_COL = 1;
    private static final int DENUMIRE_COL = 2;
    private static final int AN_COL = 3;
    private static final int SEMESTRU_COL = 4;
    private static final int TITLULAR_COL = 5;
    private static final int DESCRIERE_COL = 6;
    private static final int PACHET_COL = 7;
    private static final int GRUPE_COL = 8;

    /**
     *
     */
    public ImportDiscipline() {
        super(Repartizare.DISCIPLINE_FILE);
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
        if (tokens.length < 9) {
            log(line, "Sunt specificate doar " + tokens.length + " coloane");
            return;
        }
        trim(tokens);

        String id = tokens[ID_COL];
        if (id.isEmpty()) {
            log(line, "Lipseste ID-ul disciplinei");
            return;
        }

        String cod = tokens[COD_COL];
        if (cod.isEmpty()) {
            log(line, "Lipseste codul disciplinei");
            return;
        }

        String denumire = tokens[DENUMIRE_COL];
        if (denumire.isEmpty()) {
            log(line, "Lipseste denumirea disciplinei");
            return;
        }

        int an = 0;
        try {
            an = Integer.parseInt(tokens[AN_COL]);
            if (an != 1 && an != 2 && an != 3) {
                log(line, "Anul de studiu trebuie sa fie 1, 2 sau 3");
                return;
            }
        } catch (NumberFormatException e) {
            log(line, "An de studiu invalid: " + e.getMessage());
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

        String titular = tokens[TITLULAR_COL];
        String descriere = tokens[DESCRIERE_COL];

        String codPachet = tokens[PACHET_COL];
        Pachet pachet = null;
        if (!codPachet.isEmpty()) {
            pachet = repartizare.getPachet(codPachet);
            if (pachet == null) {
                log(line, "Cod pachet inexistent: " + codPachet);
                return;
            }
        }

        int grupe = 0;
        try {
            grupe = Integer.parseInt(tokens[GRUPE_COL]);
            if (grupe < 0) {
                log(line, "Numar grupe invalid: " + grupe);
                return;
            }
        } catch (NumberFormatException e) {
            if (pachet != null) {
                log(line, "Numar grupe invalid: " + e.getMessage());
                return;
            }
        }
        if (repartizare.getDisciplina(cod) != null) {
            log(line, "Cod disciplina duplicat: " + cod);
            return;
        }

        Disciplina disc = null;
        if (codPachet.isEmpty()) {
            disc = new Disciplina(id, cod, denumire, an, semestru, titular, descriere);
        } else {
            disc = new Optional(id, cod, denumire, an, semestru, titular, descriere, pachet, grupe);
        }
        repartizare.addDisciplina(disc);
    }

}
