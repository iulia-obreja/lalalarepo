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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Cristian Frăsinaru
 */
public class Optional extends Disciplina {

    private Pachet pachet;
    private int grupe;
    private Map<Disciplina, Double> listaPref;
    private final List<Student> studenti = new ArrayList<>();
    private final int counter[];

    public Optional(String id, String cod, String denumire, int anStudiu, int semestru, String titular, String descriere, Pachet pachet, int grupe) {
        super(id, cod, denumire, anStudiu, semestru, titular, descriere);
        this.pachet = pachet;
        this.grupe = grupe;
        this.pachet.addDisciplina(this);
        this.counter = new int[pachet.getNrDiscipline()];
        Arrays.fill(counter, 0);
    }

    /**
     * @return the pachet
     */
    public Pachet getPachet() {
        return pachet;
    }

    /**
     * @param pachet the pachet to set
     */
    public void setPachet(Pachet pachet) {
        this.pachet = pachet;
    }

    /**
     * @return the grupe
     */
    public int getGrupe() {
        return grupe;
    }

    /**
     * @param grupe the grupe to set
     */
    public void setGrupe(int grupe) {
        this.grupe = grupe;
    }

    /**
     * @param listaPref the listaPref to set
     */
    public void setListaPref(Map<Disciplina, Double> listaPref) {
        this.listaPref = listaPref;
    }

    /**
     * @return the listaPref
     */
    public Map<Disciplina, Double> getListaPref() {
        return listaPref;
    }

    /**
     *
     * @param disc
     * @return
     */
    public double getPondere(Disciplina disc) {
        return listaPref.get(disc);
    }

    /**
     *
     * @param student
     */
    void addStudent(Student student) {
        studenti.add(student);
    }

    /**
     * @return the studenti
     */
    public List<Student> getStudenti() {
        return studenti;
    }

    /**
     *
     * @return
     */
    public int getNrStudenti() {
        return studenti.size();
    }

    /**
     *
     * @param pos
     * @param value
     */
    public void addCounter(int pos, int value) {
        counter[pos] += value;
    }

    /**
     *
     * @param pos
     * @return
     */
    public int getCounter(int pos) {
        return counter[pos];
    }

    /**
     *
     * @param s1
     * @param s2
     * @return
     */
    public int compareByPunctaj(Student s1, Student s2) {
        double p1 = s1.getPunctaj(this);
        double p2 = s2.getPunctaj(this);
        if (p1 != p2) {
            return (int) Math.signum(p2 - p1);
        }
        return (int) Math.signum(s2.getMediaGenerala() - s1.getMediaGenerala());
    }

}
