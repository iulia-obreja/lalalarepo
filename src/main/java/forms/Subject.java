package forms;

/**
 * Created by Iulia-PC on 6/8/2016.
 */
public class Subject {

    private String title;
    private String link;
    private String prof;
    private Disciplines favoriteDisciplines = new Disciplines();

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

    public Disciplines getFavoriteDisciplines() {
        return favoriteDisciplines;
    }

    public void setFavoriteDisciplines(Disciplines favoriteDisciplines) {
        this.favoriteDisciplines = favoriteDisciplines;
    }
}
