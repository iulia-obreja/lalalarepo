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
package ro.info.uaic.opt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Cristian Frăsinaru
 */
public class Pachet implements Comparable<Pachet> {

    private String cod;
    private int anStudiu;
    private int semestru;
    private int nrDiscipline;
    private final List<Optional> discipline = new ArrayList<>();

    /**
     * 
     * @param cod
     * @param anStudiu
     * @param semestru
     * @param discipline 
     */
    public Pachet(String cod, int anStudiu, int semestru, int discipline) {
        this.cod = cod;
        this.anStudiu = anStudiu;
        this.semestru = semestru;
        this.nrDiscipline = discipline;
    }

    /**
     * @return the cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * @return the anStudiu
     */
    public int getAnStudiu() {
        return anStudiu;
    }

    /**
     * @param anStudiu the anStudiu to set
     */
    public void setAnStudiu(int anStudiu) {
        this.anStudiu = anStudiu;
    }

    /**
     * @return the semestru
     */
    public int getSemestru() {
        return semestru;
    }

    /**
     * @param semestru the semestru to set
     */
    public void setSemestru(int semestru) {
        this.semestru = semestru;
    }

    /**
     * @return the nrDiscipline
     */
    public int getNrDiscipline() {
        return nrDiscipline;
    }

    /**
     * @param discipline the nrDiscipline to set
     */
    public void setNrDiscipline(int discipline) {
        this.nrDiscipline = discipline;
    }

    /**
     *
     * @param disc
     */
    public void addDisciplina(Optional disc) {
        discipline.add(disc);
    }

    /**
     * @return the discipline
     */
    public List<Optional> getDiscipline() {
        return discipline;
    }

    @Override
    public int hashCode() {
        return cod.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pachet other = (Pachet) obj;
        return Objects.equals(this.cod, other.cod);
    }

    @Override
    public int compareTo(Pachet o) {
        return cod.compareTo(o.cod);
    }

    @Override
    public String toString() {
        return cod;
    }

}
