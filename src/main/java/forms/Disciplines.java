package forms;

/**
 * Created by Iulia-PC on 6/15/2016.
 */
public class Disciplines {

    private String discipline1;
    private String discipline2;
    private String discipline3;
    private String discipline4;

    public Disciplines(String d1, String d2, String d3, String d4){
        this.discipline1 = d1;
        this.discipline2 = d2;
        this.discipline3 = d3;
        this.discipline4 = d4;
    }

    public Disciplines(){

    }

    public String getDiscipline1() {
        return discipline1;
    }

    public String getDiscipline4() {
        return discipline4;
    }

    public String getDiscipline2() {
        return discipline2;
    }

    public String getDiscipline3() {
        return discipline3;
    }

    public void setDiscipline1(String discipline1) {
        this.discipline1 = discipline1;
    }

    public void setDiscipline2(String discipline2) {
        this.discipline2 = discipline2;
    }

    public void setDiscipline3(String discipline3) {
        this.discipline3 = discipline3;
    }

    public void setDiscipline4(String discipline4) {
        this.discipline4 = discipline4;
    }
}
