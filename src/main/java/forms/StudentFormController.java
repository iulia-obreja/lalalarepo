package forms;

import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

import static commons.Constants.*;
import static forms.StudentViewController.*;
import static commons.WriteToFile.writePreferinteStudentiToCsv;


/**
 * Created by Iulia-PC on 6/14/2016.
 */
@ManagedBean(name = "studentCtrl", eager = true)
@SessionScoped
public class StudentFormController {

    private List<String> years;
    private String year;
    private boolean year2Selected = false;
    private boolean year3Selected = false;
    private Integer numberOfPackagesSem1;
    private Integer numberOfPackagesSem2;
    private List<Integer> numberOfSubjectsSem1;
    private List<Integer> numberOfSubjectsSem2;
    private Integer numberOfSubjectsPerPackageSem1;
    private Integer numberOfSubjectsPerPackageSem2;
    private String titleSem1;
    private String titleSem2;
    private List<List<String>> optionsSem1;
    private List<List<String>> optionsSem2;


    public StudentFormController(){
        years = new ArrayList<String>();
        years.add(AN_2);
        years.add(AN_3);

    }


    private void makeOptions(String year){
        optionsSem1 = new ArrayList<List<String>>();
        optionsSem2 = new ArrayList<List<String>>();

        if(year.equals(AN_2)){
            for(int i = 0; i < getYear2Sem1().size(); i++){
                List<String> subjects = new ArrayList<String>();
                for(int j = 0; j < getYear2Sem1().get(i + 1).size(); j++){
                    subjects.add(new String());
                }
                optionsSem1.add(subjects);
            }

            for(int i = 0; i < getYear2Sem2().size(); i++){
                List<String> subjects = new ArrayList<String>();
                for(int j = 0; j < getYear2Sem2().get(i + 1).size(); j++){
                    subjects.add(new String());
                }
                optionsSem2.add(subjects);
            }
        }

        if(year.equals(AN_3)){
            for(int i = 0; i < getYear3Sem1().size(); i++){
                List<String> subjects = new ArrayList<String>();
                for(int j = 0; j < getYear3Sem1().get(i + 1).size(); j++){
                    subjects.add(new String());
                }
                optionsSem1.add(subjects);
            }

            for(int i = 0; i < getYear3Sem2().size(); i++){
                List<String> subjects = new ArrayList<String>();
                for(int j = 0; j < getYear3Sem2().get(i + 1).size(); j++){
                    subjects.add(new String());
                }
                optionsSem2.add(subjects);
            }
        }


    }

    public void onYearChanged(){
        if(year.equals(AN_2)){
            year2Selected = true;
            year3Selected = false;
            numberOfPackagesSem1 = getYear2Sem1().size();
            numberOfPackagesSem2 = getYear2Sem2().size();
            makeOptions(AN_2);
        }else if(year.equals(AN_3)){
            year3Selected = true;
            year2Selected = false;
            numberOfPackagesSem1 = getYear3Sem1().size();
            numberOfPackagesSem2 = getYear3Sem2().size();
            makeOptions(AN_3);
        }
    }



    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isYear2Selected() {
        return year2Selected;
    }

    public boolean isYear3Selected() {
        return year3Selected;
    }

    public Integer getNumberOfPackagesSem1() {
        if(numberOfPackagesSem1 == null){
            return 1;
        }
        return numberOfPackagesSem1;
    }

    public Integer getNumberOfPackagesSem2() {
        if(numberOfPackagesSem2 == null){
            return 1;
        }
        return numberOfPackagesSem2;
    }

    public List<Integer> getNumberOfSubjectsSem1() {
        return numberOfSubjectsSem1;
    }

    public List<Integer> getNumberOfSubjectsSem2() {
        return numberOfSubjectsSem2;
    }

    public List<String> formateTitleList(Map<Integer, List<Subject>> map, int pIndex){
        List<String> titles = new ArrayList<String>();

        for(int i = 0; i < map.get(pIndex).size(); i++){
            titles.add(map.get(pIndex).get(i).getTitle());
        }
        return titles;
    }

    public List<String> getListOfSubjectTitles(int packageIndex, String yearAndSemester){

        if(yearAndSemester.equals("21")){
             return formateTitleList(getYear2Sem1(), packageIndex);
        }

        if(yearAndSemester.equals("22")){
            return formateTitleList(getYear2Sem2(), packageIndex);
        }

        if(yearAndSemester.equals("31")){
            return formateTitleList(getYear3Sem1(), packageIndex);
        }

        if(yearAndSemester.equals("32")){
            return formateTitleList(getYear3Sem2(), packageIndex);
        }

        return new ArrayList<String>();
    }



    public String getTitleSem2() {
        return titleSem2;
    }

    public String getTitleSem1() {
        return titleSem1;
    }

    public void setTitleSem1(String titleSem1) {
        this.titleSem1 = titleSem1;
    }

    public void setTitleSem2(String titleSem2) {
        this.titleSem2 = titleSem2;
    }

    public List<List<String>> getOptionsSem1() {
        return optionsSem1;
    }

    public List<List<String>> getOptionsSem2() {
        return optionsSem2;
    }

    public void setOptionsSem1(List<List<String>> optionsSem1) {
        this.optionsSem1 = optionsSem1;
    }

    public void setOptionsSem2(List<List<String>> optionsSem2) {
        this.optionsSem2 = optionsSem2;
    }

    public boolean duplicatesExist(List<String> list){
        Set<String> set = new HashSet<String>(list);
        if(set.size() < list.size()){
            return true;
        }
        return false;
    }

    public boolean duplicatesExist(List<List<String>> sem1, List<List<String>> sem2){
        Set<String> set;

        for(int i = 0; i < sem1.size(); i++){
            set = new HashSet<String>(sem1.get(i));
            if(set.size() < sem1.get(i).size()){
                return true;
            }
        }

        for(int i = 0; i < sem2.size(); i++){
            set = new HashSet<String>(sem2.get(i));
            if(set.size() < sem2.get(i).size()){
                return true;
            }
        }

        return false;
    }

    public void onTrimiteButtonClick(){
        if(duplicatesExist(optionsSem1, optionsSem2)){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Eroare", "Trebuie completate toate optiunile, fara duplicate.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }else{
            if(year.equals(AN_2)){
               // writePreferinteStudentiToCsv(PREFERINTE_AN2);

            }
            if(year.equals(AN_3)){
              // writePreferinteStudentiToCsv(PREFERINTE_AN3);
            }
        }
    }
}
