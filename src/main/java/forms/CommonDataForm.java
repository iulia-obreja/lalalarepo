package forms;

import java.util.List;

/**
 * Created by Iulia-PC on 6/10/2016.
 */
public class CommonDataForm {
    private static List<Integer> subjectsNumberMappedToPackagesSem1;
    private static List<Integer> subjectsNumberMappedToPackagesSem2;

    public static List<Integer> getSubjectsNumberMappedToPackagesSem1() {
        return subjectsNumberMappedToPackagesSem1;
    }

    public static List<Integer> getSubjectsNumberMappedToPackagesSem2() {
        return subjectsNumberMappedToPackagesSem2;
    }

    public static void setSubjectsNumberMappedToPackagesSem1(List<Integer> subjectsNumberMappedToPackagesSem1) {
        CommonDataForm.subjectsNumberMappedToPackagesSem1 = subjectsNumberMappedToPackagesSem1;
    }

    public static void setSubjectsNumberMappedToPackagesSem2(List<Integer> subjectsNumberMappedToPackagesSem2) {
        CommonDataForm.subjectsNumberMappedToPackagesSem2 = subjectsNumberMappedToPackagesSem2;
    }
}
