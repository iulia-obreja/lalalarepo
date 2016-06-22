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
import ro.info.uaic.opt.model.Student;

/**
 *
 * @author Cristian Frăsinaru
 */
public class ImportNote extends ImportFile {

    private static final int COD_STUDENT_COL = 0;
    private static final int COD_DISCIPLINA_COL = 1;
    private static final int NOTA_COL = 2;

    /**
     *
     */
    public ImportNote() {
        super(Repartizare.NOTE_FILE);
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

        String codStudent = tokens[COD_STUDENT_COL];
        if (codStudent.isEmpty()) {
            log(line, "Lipseste codul studentului");
            return;
        }
        Student student = repartizare.getStudent(codStudent);
        if (student == null) {
            return;
            //raiseError(line, "Cod student inexistent: " + codStudent);
        }

        String codDisciplina = tokens[COD_DISCIPLINA_COL];
        if (codDisciplina.isEmpty()) {
            log(line, "Lipseste codul disciplinei");
            return;
        }
        Disciplina disc = repartizare.getDisciplina(codDisciplina);
        if (disc == null) {
            log(line, "Cod disciplina inexistent: " + codDisciplina);
            return;
        }

        double nota = 0;
        try {
            nota = Double.parseDouble(tokens[NOTA_COL]);
            if (nota < 0 || nota > 10) {
                log(line, "Nota invalida: " + nota);
                return;
            }
        } catch (NumberFormatException e) {
            log(line, "Nota invalida: " + e.getMessage());
            return;
        }
        if (nota > 0) {
            student.addNota(disc, nota);
        }
    }

}
