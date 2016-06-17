package forms;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.Map;
import static commons.ReadFromFile.*;
import static commons.Constants.*;

/**
 * Created by Iulia-PC on 6/16/2016.
 */
@ManagedBean(name = "studentViewCtrl")
@SessionScoped
public class StudentViewController {

    private static Map<Integer, List<Subject>> year2Sem1;
    private static Map<Integer, List<Subject>> year2Sem2;

    private static Map<Integer, List<Subject>> year3Sem1;
    private static Map<Integer, List<Subject>> year3Sem2;
    private String indicationsYear2;
    private String indicationsYear3;
    private String title;


    public StudentViewController(){
        readFromFile(AN_2_FILE);
        indicationsYear2 = getIndications();
        year2Sem1 = getSem1();
        year2Sem2 = getSem2();

        readFromFile(AN_3_FILE);
        indicationsYear3 = getIndications();
        year3Sem1 = getSem1();
        year3Sem2 = getSem2();
    }


    public String getIndicationsYear2() {
        return indicationsYear2;
    }

    public String getIndicationsYear3() {
        return indicationsYear3;
    }

    public int getPackagesSem1Year3(){
        return year3Sem1.size();
    }

    public int getPackagesSem2Year3(){
        return year3Sem2.size();
    }

    public int getPackagesSem1Year2(){
        return year2Sem1.size();
    }

    public int getPackagesSem2Year2(){
        return year2Sem2.size();
    }

    public static Map<Integer, List<Subject>> getYear2Sem1() {
        return year2Sem1;
    }

    public static Map<Integer, List<Subject>> getYear2Sem2() {
        return year2Sem2;
    }

    public static Map<Integer, List<Subject>> getYear3Sem1() {
        return year3Sem1;
    }

    public static Map<Integer, List<Subject>> getYear3Sem2() {
        return year3Sem2;
    }

    public static int getSize(Map<Integer, List<Subject>> map, Integer key){
        if(map == null || map.get(key) == null){
            return 1;
        }
        return map.get(key).size();
    }

    public static int getYear3Sem1Size(Integer key){
        return getSize(year3Sem1, key);
    }

    public static int getYear3Sem2Size(Integer key){
        return getSize(year3Sem2, key);
    }

    public static int getYear2Sem1Size(Integer key){
        return getSize(year2Sem1, key);
    }

    public static int getYear2Sem2Size(Integer key){
        return getSize(year2Sem2, key);
    }

    public List<Subject> getListOfSubjectsYear3Sem1(Integer key){
        return year3Sem1.get(key);
    }

    public List<Subject> getListOfSubjectsYear3Sem2(Integer key){
        return year3Sem2.get(key);
    }

    public List<Subject> getListOfSubjectsYear2Sem2(Integer key){
        return year2Sem2.get(key);
    }

    public List<Subject> getListOfSubjectsYear2Sem1(Integer key){
        return year2Sem1.get(key);
    }

    public String getSubject() {
        return title;
    }

    public void setSubject(String title) {
        this.title = title;
    }
}
