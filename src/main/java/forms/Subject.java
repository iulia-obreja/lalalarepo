package forms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iulia-PC on 6/8/2016.
 */
public class Subject {

    private String title;
    private String link;
    private String prof;
    private FavDiscipline d1 = new FavDiscipline();
    private FavDiscipline d2 = new FavDiscipline();
    private FavDiscipline d3 = new FavDiscipline();
    private FavDiscipline d4 = new FavDiscipline();

    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }

    public String getProf() {
        return prof;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public FavDiscipline getD1() {
        return d1;
    }

    public FavDiscipline getD3() {
        return d3;
    }

    public FavDiscipline getD2() {
        return d2;
    }

    public FavDiscipline getD4() {
        return d4;
    }

    public void setD1(FavDiscipline d1) {
        this.d1 = d1;
    }

    public void setD3(FavDiscipline d3) {
        this.d3 = d3;
    }

    public void setD2(FavDiscipline d2) {
        this.d2 = d2;
    }

    public void setD4(FavDiscipline d4) {
        this.d4 = d4;
    }
}
