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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ro.info.uaic.opt.Application;
import ro.info.uaic.opt.model.Repartizare;

/**
 *
 * @author Cristian Frăsinaru
 */
public abstract class ImportFile {

    protected Repartizare repartizare;
    protected String filename;
    protected static final String CSV_SPLITTER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    /**
     *
     * @param filename
     */
    public ImportFile(String filename) {
        this.repartizare = Repartizare.getInstance();
        this.filename = Application.INPUT_FOLDER + filename;
    }

    /**
     *
     * @throws IOException
     */
    public void parseFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    parseLine(line);
                } catch (InvalidLineException e) {
                    repartizare.log(e.getMessage());
                }
            }
        }
    }

    /**
     *
     * @param line
     */
    protected abstract void parseLine(String line);

    /**
     *
     * @param tokens
     */
    protected void trim(String tokens[]) {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].replaceAll("\"", "").trim();
        }
    }

    /**
     *
     * @param line
     * @param message
     */
    protected void log(String line, String message) throws InvalidLineException {
        //throw new InvalidLineException(filename, line, message);
        repartizare.log(filename + "\n\t" + "Linie: " + line + "\n\tEroare: " + message);

    }

}
