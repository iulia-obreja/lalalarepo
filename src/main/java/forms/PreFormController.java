package forms;

import commons.CommonDataForm;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static commons.Constants.*;

/**
 * Created by Iulia-PC on 6/9/2016.
 */
@ManagedBean(name = "preFormCtrl", eager = true)
@ApplicationScoped
public class PreFormController {


    private boolean isButtonDisabled;
    private String year;
    private List<String> years;
    private String numberPackagesSem1;
    private String numberPackagesSem2;

    private List<Integer> subjectsSem1;
    private List<Integer> subjectsSem2;


    public PreFormController(){
        this.isButtonDisabled = true;
        years = new ArrayList<String>();
        years.add(AN_2);
        years.add(AN_3);
    }

    public String getYear() {
        return year;
    }
    public String getNumberPackagesSem1() {
        return numberPackagesSem1;
    }
    public String getNumberPackagesSem2() {
        return numberPackagesSem2;
    }
    public List<Integer> getSubjectsSem1() {
        return subjectsSem1;
    }
    public List<Integer> getSubjectsSem2() {
        return subjectsSem2;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setNumberPackagesSem1(String numberPackagesSem1) {
        this.numberPackagesSem1 = numberPackagesSem1;
    }
    public void setNumberPackagesSem2(String numberPackagesSem2) {
        this.numberPackagesSem2 = numberPackagesSem2;
    }
    public void setSubjectsSem1(List<Integer> subjectsSem1) {
        this.subjectsSem1 = subjectsSem1;
    }
    public boolean isButtonDisabled() {
        return isButtonDisabled;
    }
    public void setButtonDisabled(boolean buttonDisabled) {
        this.isButtonDisabled = buttonDisabled;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public int getNumberPSem1() {
        if (!verifyString(numberPackagesSem1)) {
            return 1;
        }
        return Integer.parseInt(numberPackagesSem1);
    }

    public void onKeyUpNumberPSem1() {
        subjectsSem1 = new ArrayList<Integer>(getNumberPSem1());
        for (int i = 0; i < getNumberPSem1(); i++) {
            subjectsSem1.add(0);
        }
        CommonDataForm.setSubjectsNumberMappedToPackagesSem1(mapData(subjectsSem1));
        FormController.init();
    }

    public boolean renderPanelS1() {
        if (!verifyString(numberPackagesSem1)) {
            return false;
        }
        return true;
    }

    public int getNumberPSem2(){
        if(!verifyString(numberPackagesSem2)){
            return 1;
        }
        return Integer.parseInt(numberPackagesSem2);
    }

    public void onKeyUpNumberPSem2(){
        subjectsSem2 = new ArrayList<Integer>(getNumberPSem2());
        for (int i = 0; i < getNumberPSem2(); i++) {
            subjectsSem2.add(0);
        }
        CommonDataForm.setSubjectsNumberMappedToPackagesSem2(mapData(subjectsSem2));
        FormController.init();
    }

    public boolean renderPanelS2(){
        if(!verifyString(numberPackagesSem2)){
            return false;
        }
        return true;
    }

    public List<Integer> mapData(List<Integer> subjects){
        List<Integer> listResponse = subjects;
        return listResponse;
    }

    public void onOkButtonClick(ActionEvent actionEvent){
        setButtonDisabled(false);
        CommonDataForm.setSubjectsNumberMappedToPackagesSem1(mapData(subjectsSem1));
        CommonDataForm.setSubjectsNumberMappedToPackagesSem2(mapData(subjectsSem2));
        List<List<Integer>> year = new ArrayList<List<Integer>>();
        year.add(CommonDataForm.getSubjectsNumberMappedToPackagesSem1());
        year.add(CommonDataForm.getSubjectsNumberMappedToPackagesSem2());
        CommonDataForm.getYear().put(this.year, year);
        CommonDataForm.setYearSelected(this.year);
        FormController.init();
    }

    public boolean isStringDigit(String str){
        final Pattern pattern = Pattern.compile("^[1-9]$");
        if (!pattern.matcher(str).matches()) {
            return false;
        }
        return true;
    }

    public boolean verifyString(String str){
        if(str == null || !isStringDigit(str)){
            return false;
        }
        return true;
    }

    public boolean isOkButtonDisabled(){
        if(verifyString(numberPackagesSem1) && verifyString(numberPackagesSem2)){
            return false;
        }
        return true;
    }


}
