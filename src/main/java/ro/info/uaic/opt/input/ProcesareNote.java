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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cristian Frăsinaru
 */
public class ProcesareNote extends ImportFile {

    private final BufferedWriter writer, mapper;
    private final static String EOL = System.getProperty("line.separator");
    private final Map<String, Disciplina> map = new HashMap<>();
    private static final Disciplina EMPTY = new Disciplina();

    /**
     *
     * @throws IOException
     */
    public ProcesareNote() throws IOException {
        super("note_temp.csv");
        writer = new BufferedWriter(new FileWriter("note.csv"));
        mapper = new BufferedWriter(new FileWriter("note_map.csv"));
    }

    @Override
    public void parseFile() throws IOException {
        super.parseFile();
        writer.close();
        mapper.close();
    }

    @Override
    protected void parseLine(String line) {
        if (line == null || line.isEmpty()) {
            return;
        }
        String tokens[] = line.split(CSV_SPLITTER);
        trim(tokens);

        String codStudent = tokens[0].trim();
        String idDisc = tokens[1].trim();
        String denDisc = tokens[2].trim();
        String nota = tokens[3].trim();
        String codDisc = "";
        //
        String obs = "";
        Disciplina disc = map.get(idDisc);
        boolean mapped = false;
        if (disc == null) {
            disc = repartizare.findDisciplinaById(idDisc);
            if (disc == null) {
                disc = repartizare.findDisciplinaByDenumire(denDisc);
                obs = disc != null ? codDisc + "\t" + disc.getObs() + "\t" + disc.getDenumire() : "???";
            }
        } else {
            mapped = true;
        }
        if (disc != null) {
            map.put(idDisc, disc);
            codDisc = disc.getCod();
        } else {
            map.put(idDisc, EMPTY);
        }

        try {
            if (!mapped) {
                mapper.write(idDisc + "\t" + denDisc + "\t" + obs + EOL);
            }
            if (disc != null && disc != EMPTY) {
                writer.write("\"" + codStudent + "\"" + ","
                        + "\"" + disc.getCod() + "\"" + ","
                        + nota + EOL);
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
