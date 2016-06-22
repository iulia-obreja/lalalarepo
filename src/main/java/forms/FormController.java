package forms;

import commons.CommonDataForm;
import login.LoginController;

import javax.faces.bean.*;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static commons.WriteToFile.*;
import static commons.Constants.*;

/**
 * Created by Iulia-PC on 6/7/2016.
 */
@ManagedBean(name = "formCtrl", eager = true)
@ApplicationScoped
public class FormController {

    private static List<List<Subject>> semester1;
    private static List<List<Subject>> semester2;
    private String indications;
    private boolean disableVeziOptionaleButton = true;
    private boolean disableOkButtonOptionale = true;
    private boolean disablePosteazaButton = true;


    private static int getSubjectsArraySize(Object item) {
        int response = Integer.parseInt(item.toString());
        return response;
    }

    private static List<List<Subject>> mapSubjectsToPackages(List<Integer> subjectsNumbers) {
        List<List<Subject>> listResponse = new ArrayList<List<Subject>>();
        int countSubjects;
        List<Subject> subjects;
        for (int i = 0; i < subjectsNumbers.size(); i++) {
            countSubjects = getSubjectsArraySize(subjectsNumbers.get(i));
            subjects = new ArrayList<Subject>();
            for (int j = 0; j < countSubjects; j++) {
                subjects.add(new Subject());
            }
            listResponse.add(subjects);
        }
        return listResponse;
    }

    public static void init() {
        if (CommonDataForm.getSubjectsNumberMappedToPackagesSem1() == null) {
            semester1 = new ArrayList<List<Subject>>();
        } else {
            semester1 = new ArrayList<List<Subject>>(mapSubjectsToPackages(CommonDataForm.getSubjectsNumberMappedToPackagesSem1()));
        }


        if (CommonDataForm.getSubjectsNumberMappedToPackagesSem2() == null) {
            semester2 = new ArrayList<List<Subject>>();
        } else {
            semester2 = new ArrayList<List<Subject>>(mapSubjectsToPackages(CommonDataForm.getSubjectsNumberMappedToPackagesSem2()));
        }
    }

    public int getSubjectsPerPackageSem1(int index) {
        if (semester1 == null || semester1.size() == 0) {
            return 0;
        }
        return semester1.get(index).size();
    }

    ;

    public int getSubjectsPerPackageSem2(int index) {
        if (semester2 == null || semester2.size() == 0) {
            return 0;
        }
        return semester2.get(index).size();
    }

    public void onOkButtonOptionalePressed() {
        disableVeziOptionaleButton = false;
    }

    public List<List<Subject>> getSemester1() {
        return semester1;
    }

    public List<List<Subject>> getSemester2() {
        return semester2;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indicatons) {
        this.indications = indicatons;
    }

    public boolean isDisableVeziOptionaleButton() {
        return disableVeziOptionaleButton;
    }

    public void setDisableVeziOptionaleButton(boolean disableVeziOptionaleButton) {
        this.disableVeziOptionaleButton = disableVeziOptionaleButton;
    }

    public boolean isDisableOkButtonOptionale() {
        return disableOkButtonOptionale;
    }

    public boolean isDisablePosteazaButton() {
        return disablePosteazaButton;
    }

    public void setDisablePosteazaButton(boolean disablePosteazaButton) {
        this.disablePosteazaButton = disablePosteazaButton;
    }

    public String getLinkMessage() {
        return "Link catre pagina materiei";
    }

    public void onSaveButtonClick() {
        String yearSelected = CommonDataForm.getYearSelected();
        int numberOfPackages1 = CommonDataForm.getSubjectsNumberMappedToPackagesSem1().size();
        int numberOfPackages2 = CommonDataForm.getSubjectsNumberMappedToPackagesSem2().size();

        if (yearSelected.equals(AN_2)) {
            writeOptionaleToFile(AN_2_FILE,
                    numberOfPackages1,
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem1(),
                    semester1,
                    numberOfPackages2,
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem2(),
                    semester2,
                    indications);

            writePacheteToCsv(PACHETE,
                    AN_2,
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem1(),
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem2());
            writePrefDisciplineToCsv(AN_2, semester1, semester2);
        } else {
            writeOptionaleToFile(AN_3_FILE,
                    numberOfPackages1,
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem1(),
                    semester1,
                    numberOfPackages2,
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem2(),
                    semester2,
                    indications);
            writePacheteToCsv(PACHETE,
                    AN_3,
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem1(),
                    CommonDataForm.getSubjectsNumberMappedToPackagesSem2());
            writePrefDisciplineToCsv(AN_3, semester1, semester2);
        }
    }
}
