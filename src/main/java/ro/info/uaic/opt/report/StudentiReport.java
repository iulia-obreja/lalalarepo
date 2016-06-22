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
package ro.info.uaic.opt.report;

/**
 *
 * @author Cristian Frăsinaru
 */
public class StudentiReport extends Report {

    private final int anStudiu;

    public StudentiReport(int anStudiu) {
        super("studenti.ftl", "html/studenti_an" + anStudiu + ".html");
        this.anStudiu = anStudiu;
    }

    @Override
    protected boolean prepareData() {
        if (!super.prepareData()) {
            return false;
        }
        data.put("anStudiu", anStudiu);
        return true;
    }
}
