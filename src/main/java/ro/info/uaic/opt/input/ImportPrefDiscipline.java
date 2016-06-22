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

import ro.info.uaic.opt.model.Disciplina;
import ro.info.uaic.opt.model.Optional;
import java.util.HashMap;
import java.util.Map;
import ro.info.uaic.opt.model.Repartizare;

/**
 *
 * @author Cristian Frăsinaru
 */
public class ImportPrefDiscipline extends ImportFile {

    private static final int COD_OPTIONAL_COL = 0;
    private static final int COD_DISCIPLINE_COL = 1;

    /**
     *
     */
    public ImportPrefDiscipline() {
        super(Repartizare.PREF_DISCIPLINE_FILE);
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

        String codOptional = tokens[COD_OPTIONAL_COL];
        if (codOptional.isEmpty()) {
            log(line, "Lipseste codul disciplinei optionale");
            return;
        }
        Disciplina disc = repartizare.getDisciplina(codOptional);
        if (disc == null) {
            log(line, "Cod disciplina inexistent: " + codOptional);
            return;
        }
        if (!(disc instanceof Optional)) {
            log(line, "Disciplina nu este optionala (nu are definit pachetul): " + codOptional);
            return;
        }
        Optional optional = (Optional) disc;
        int n = tokens.length - 1;
        int i = COD_DISCIPLINE_COL;
        Map<Disciplina, Double> discipline = new HashMap<>();
        while (i < n) {
            String codDisc = tokens[i++];
            Disciplina discPref = repartizare.getDisciplina(codDisc);
            if (discPref == null) {
                log(line, "Cod disciplina inexistent " + codDisc);
                return;
            }
            if (optional.getAnStudiu() < discPref.getAnStudiu()
                    || (optional.getAnStudiu() == discPref.getAnStudiu() && optional.getSemestru() <= discPref.getSemestru())) {
                log(line, "Disciplinele preferate trebuie sa preceada strict optionalul: " + codDisc);
                return;
            }
            double pondere = 0;
            try {
                pondere = Double.parseDouble(tokens[i++]);
                if (pondere < 0 || pondere > 1) {
                    log(line, "Pondere disciplina invalida: " + pondere);
                    return;
                }
            } catch (NumberFormatException e) {
                log(line, "Pondere disciplina invalida: " + e.getMessage());
                return;
            }

            discipline.put(discPref, pondere);
        }
        optional.setListaPref(discipline);
    }

}
