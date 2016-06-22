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

import ro.info.uaic.opt.model.Optional;

/**
 *
 * @author Cristian Frăsinaru
 */
public class OptionalReport extends Report {

    private final Optional optional;

    public OptionalReport(Optional optional) {
        super("optional.ftl", "html/" + optional.getPachet().getCod() + "_" + optional.getCod() + ".html");
        this.optional = optional;
    }

    @Override
    protected boolean prepareData() {
        if (!super.prepareData()) {
            return false;
        }
        data.put("optional", optional);
        return true;
    }
}
