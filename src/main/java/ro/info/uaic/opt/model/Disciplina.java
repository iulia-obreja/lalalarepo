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

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 *
 * @author Cristian Frăsinaru
 */
public class Disciplina {

    protected String id; //din planul de invatamant CS101, CS102, etc.
    protected String cod; //abreviere: MATE, SGBD, etc.
    protected String denumire;
    protected int anStudiu;
    protected int semestru;
    protected String titular;
    protected String descriere;
    protected String obs;

    public Disciplina() {
        id = "???";
        cod = "???";
        denumire = "???";
    }

    public Disciplina(String id, String cod, String denumire, int anStudiu, int semestru, String titular, String descriere) {
        this.id = id;
        this.cod = cod;
        this.denumire = denumire;
        this.anStudiu = anStudiu;
        this.semestru = semestru;
        this.titular = titular;
        this.descriere = descriere;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the denumire
     */
    public String getDenumire() {
        return denumire;
    }

    /**
     * @param denumire the denumire to set
     */
    public void setDenumire(String denumire) {
        this.denumire = denumire;
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
     * @return the titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * @param titular the titular to set
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * @return the descriere
     */
    public String getDescriere() {
        return descriere;
    }

    /**
     * @param descriere the descriere to set
     */
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    
    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @param obs the obs to set
     */
    public void setObs(String obs) {
        this.obs = obs;
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
        final Disciplina other = (Disciplina) obj;
        return Objects.equals(this.cod, other.cod);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        return joiner.add(denumire).add(cod).add(id).add("anul " + anStudiu).add("sem. " + semestru).toString();
    }

}
