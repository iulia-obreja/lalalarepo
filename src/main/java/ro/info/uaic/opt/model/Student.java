package ro.info.uaic.opt.model;

import java.text.Collator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 *
 * @author Cristian Frăsinaru
 */
public class Student implements Comparable<Student> {

    private String cod;
    private String nume;
    private int anStudiu;
    private String email;
    private final Map<Disciplina, Double> note = new HashMap<>();
    private final Map<Pachet, List<Optional>> pref = new HashMap<>();
    private final Map<Optional, Double> punctaje = new HashMap<>();
    private final Map<Pachet, Optional> optionale = new HashMap<>();
    private double mediaGenerala = 0;

    public Student(String cod, String nume, int anStudiu, String email) {
        this.cod = cod;
        this.nume = nume;
        this.anStudiu = anStudiu;
        this.email = email;
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
     * @return the nume
     */
    public String getNume() {
        return nume;
    }

    /**
     * @param nume the nume to set
     */
    public void setNume(String nume) {
        this.nume = nume;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param disc
     * @param nota
     */
    public void addNota(Disciplina disc, double nota) {
        note.put(disc, nota);
        calcMediaGenerala();
    }

    private double calcMediaGenerala() {
        double suma = 0;
        for (Disciplina disc : note.keySet()) {
            suma += note.get(disc);
        }
        mediaGenerala = suma / note.size();
        return mediaGenerala;
    }

    /**
     *
     * @param disc
     * @return
     */
    public double getNota(Disciplina disc) {
        return note.get(disc);
    }

    /**
     * @return the note
     */
    public Map<Disciplina, Double> getNote() {
        return note;
    }

    /**
     *
     * @return
     */
    public String getNoteAsString() {
        StringBuilder sb = new StringBuilder();
        for (Disciplina disc : note.keySet()) {
            if (sb.length() == 0) {
                sb.append("(");
            } else {
                sb.append(", ");
            }
            sb.append(disc.getCod()).append(":").append(note.get(disc));
        }
        if (sb.length() > 0) {
            NumberFormat fmt = NumberFormat.getNumberInstance();
            fmt.setMaximumFractionDigits(2);
            sb.append(")=").append(fmt.format(mediaGenerala));
        }
        return sb.toString();
    }

    /**
     *
     * @param pachet
     * @return
     */
    public String getPrefAsString(Pachet pachet) {
        StringBuilder sb = new StringBuilder();
        for (Disciplina disc : pref.get(pachet)) {
            if (sb.length() == 0) {
                sb.append("(");
            } else {
                sb.append(", ");
            }
            sb.append(disc.getCod());
        }
        if (sb.length() > 0) {
            sb.append(")");
        }
        return sb.toString();
    }

    /**
     * Calculeaza media ponderata corespunzatoare unui optional.
     *
     * @param opt
     * @return
     */
    public double getPunctaj(Optional opt) {
        if (opt.getListaPref() == null || opt.getListaPref().isEmpty()) {
            return mediaGenerala;
        }
        Double punctaj = punctaje.get(opt);
        if (punctaj == null) {
            punctaj = 0.0;
            for (Disciplina disc : opt.getListaPref().keySet()) {
                double pondere = opt.getListaPref().get(disc);
                Double nota = note.get(disc);
                punctaj += (nota == null ? 0.0 : nota * pondere);
            }
            punctaj = Math.round(punctaj * 100) / 100.0;
            punctaje.put(opt, punctaj);
        }
        return punctaj;
    }

    /**
     *
     * @return
     */
    public double getMediaGenerala() {
        return this.mediaGenerala;
    }

    /**
     *
     * @param pachet
     * @param discipline
     */
    public void setListaPref(Pachet pachet, List<Optional> discipline) {
        List<Optional> old = pref.get(pachet);
        if (old != null) {
            for (int i = 0; i < old.size(); i++) {
                Optional opt = old.get(i);
                opt.addCounter(i, -1);
            }
        }
        pref.put(pachet, discipline);
        for (int i = 0; i < discipline.size(); i++) {
            Optional opt = discipline.get(i);
            opt.addCounter(i, 1);
        }
    }

    /**
     *
     * @param pachet
     * @return
     */
    public List<Optional> getListaPref(Pachet pachet) {
        return pref.get(pachet);
    }

    /**
     *
     * @return
     */
    public boolean isListaPrefSet() {
        return !pref.isEmpty();
    }

    /**
     *
     * @param pachet
     * @param opt
     */
    public void addOptional(Pachet pachet, Optional opt) {
        optionale.put(pachet, opt);
        opt.addStudent(this);
    }

    /**
     * @return the optionale
     */
    public Map<Pachet, Optional> getOptionale() {
        return optionale;
    }

    /**
     *
     * @param pachet
     * @return
     */
    public Optional getOptional(Pachet pachet) {
        return optionale.get(pachet);
    }

    /**
     *
     * @param opt
     * @return
     */
    public int getOptionalPos(Optional opt) {
        Pachet pachet = opt.getPachet();
        return pref.get(pachet).indexOf(opt);
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
        final Student other = (Student) obj;
        return Objects.equals(this.cod, other.cod);
    }

    /**
     * Dupa an de studiu si apoi dupa num
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Student o) {
        if (this.anStudiu != o.anStudiu) {
            return this.anStudiu - o.anStudiu;
        }
        Collator collator = Collator.getInstance(Locale.forLanguageTag("ro"));
        return collator.compare(this.nume, o.nume);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        return joiner.add(nume).add(cod).add("anul " + anStudiu).toString();
    }

}
