package commons;

import java.util.*;

/**
 * Created by Iulia-PC on 6/10/2016.
 */
public class CommonDataForm {
    private static Map<String, List<List<Integer>>> year = new HashMap<String, List<List<Integer>>>();
    private static List<Integer> subjectsNumberMappedToPackagesSem1;
    private static List<Integer> subjectsNumberMappedToPackagesSem2;
    private static String yearSelected;

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

    public static Map<String, List<List<Integer>>> getYear() {
        return year;
    }

    public static void setYear(Map<String, List<List<Integer>>> year) {
        CommonDataForm.year = year;
    }

    public static String getYearSelected() {
        return yearSelected;
    }

    public static void setYearSelected(String yearSelected) {
        CommonDataForm.yearSelected = yearSelected;
    }
}
