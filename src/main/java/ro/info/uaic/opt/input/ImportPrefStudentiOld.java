package ro.info.uaic.opt.input;

///*
// * Copyright (C) 2016 Faculty of Computer Science Iasi, Romania
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package ro.info.uaic.opt;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author Cristian Frăsinaru
// */
//public class ImportPrefStudentiOld extends ImportFile {
//
//    private static final int COD_STUDENT_COL = 0;
//    private static final int COD_PACHET_COL = 1;
//    private static final int COD_DISCIPLINE_COL = 2;
//
//    /**
//     *
//     * @param repartizare
//     */
//    public ImportPrefStudentiOld(Repartizare repartizare) {
//        super(repartizare, Repartizare.PREF_STUDENTI_FILE);
//    }
//
//    /**
//     *
//     * @param line
//     */
//    @Override
//    protected void parseLine(String line) {
//        if (line == null || line.isEmpty()) {
//            return;
//        }
//        String tokens[] = line.split(CSV_SPLITTER);
//        trim(tokens);
//
//        String codStudent = tokens[COD_STUDENT_COL];
//        if (codStudent.isEmpty()) {
//            raiseError(line, "Lipseste codul studentului");
//        }
//        Student student = repartizare.getStudent(codStudent);
//        if (student == null) {
//            raiseError(line, "Cod student inexistent: " + codStudent);
//        }
//
//        String codPachet = tokens[COD_PACHET_COL];
//        if (codPachet.isEmpty()) {
//            raiseError(line, "Lipseste codul pachetului");
//        }
//        Pachet pachet = repartizare.getPachet(codPachet);
//        if (pachet == null) {
//            raiseError(line, "Cod pachet inexistent: " + codPachet);
//        }
//
//        int n = pachet.getNrDiscipline();
//        if (tokens.length < 2 + n) {
//            raiseError(line, "Sunt specificate doar " + tokens.length + " coloane");
//        }
//        List<Disciplina> discipline = new ArrayList<>(n);
//        for (int i = 0; i < n; i++) {
//            String codDisc = tokens[COD_DISCIPLINE_COL + i];
//            Disciplina disc = repartizare.getDisciplina(codDisc);
//            if (disc == null) {
//                raiseError(line, "Cod disciplina inexistent " + codDisc);
//            }
//            if (!disc.isOptional()) {
//                raiseError(line, "Disciplina nu este optionala (nu are definit pachetul): " + codDisc);
//            }
//            if (disc.getAnStudiu() != pachet.getAnStudiu()) {
//                raiseError(line, "Disciplina si pachetul sunt definite in ani de studiu diferiti");
//            } else if (disc.getSemestru() != pachet.getSemestru()) {
//                raiseError(line, "Disciplina si pachetul sunt definite in semestre diferite");
//            }
//            discipline.add(disc);
//        }
//        student.setListaPref(pachet, discipline);
//    }
//
//}
