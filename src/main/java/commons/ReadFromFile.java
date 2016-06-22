package commons;

import forms.FavDiscipline;
import forms.Subject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Iulia-PC on 6/16/2016.
 */
public class ReadFromFile {

    private static String indications;
    private static Map<Integer, List<Subject>> sem1;
    private static Map<Integer, List<Subject>> sem2;

    private static String[] makeDisciplines(String d1, String d2, String d3, String d4){
        String[] response = new String[4];
        String dd1, dd2, dd3;

        if(!d1.equals("")){
            if(d2.equals("") && d3.equals("") && d4.equals("")){
                dd1 = d1;
            }else{
                dd1 = d1 + ", ";
            }
        }else{
            dd1 = d1;
        }

        if(!d2.equals("")){
            if(d3.equals("") && d4.equals("")){
                dd2 = d2;
            }else{
                dd2 = d2 + ", ";
            }
        }else{
            dd2 = d2;
        }

        if(!d3.equals("")){
            if(d4.equals("")){
                dd3 = d3;
            }else{
                dd3 = d3 + ", ";
            }
        }else{
            dd3 = d3;
        }

        response[0] = dd1;
        response[1] = dd2;
        response[2] = dd3;
        response[3] = d4;

        return response;
    }

    private static Map<Integer, List<Subject>> addSubjectsToSemester(BufferedReader br, List<Integer> numberOfSubjects) {
        Map<Integer, List<Subject>> resp = new HashMap<Integer, List<Subject>>();
        List<Subject> subjects;
        Subject subject;
        FavDiscipline discipline;
        String[] disciplines;

        try {
            for (int i = 0; i < numberOfSubjects.size(); i++) {
                subjects = new ArrayList<Subject>();
                for (int j = 0; j < numberOfSubjects.get(i); j++) {
                    String titlu = br.readLine();
                    String link = br.readLine();
                    String prof = br.readLine();
                    String d1 = br.readLine();
                    String d2 = br.readLine();
                    String d3 = br.readLine();
                    String d4 = br.readLine();

                    disciplines = makeDisciplines(d1, d2, d3, d4);

                    subject = new Subject();
                    subject.setTitle(titlu);
                    subject.setLink(link);
                    subject.setProf(prof);

                    discipline = new FavDiscipline();
                    discipline.setCod(disciplines[0]);
                    subject.setD1(discipline);

                    discipline = new FavDiscipline();
                    discipline.setCod(disciplines[1]);
                    subject.setD2(discipline);

                    discipline = new FavDiscipline();
                    discipline.setCod(disciplines[2]);
                    subject.setD3(discipline);

                    discipline = new FavDiscipline();
                    discipline.setCod(disciplines[3]);
                    subject.setD4(discipline);
                    subjects.add(subject);
                }
                resp.put(i + 1, subjects);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return resp;
    }


    public static void readFromFile(String filePath) {
        List<Integer> numberOfSubjects1 = new ArrayList<Integer>();
        List<Integer> numberOfSubjects2 = new ArrayList<Integer>();
        int numberOfPackages1;
        int numberOfPackages2;
        List<Subject> subjects;
        Subject subject;

        String line = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            indications = br.readLine();
            numberOfPackages1 = Integer.parseInt(br.readLine());

            for (int i = 0; i < numberOfPackages1; i++) {
                line = br.readLine();
                numberOfSubjects1.add(Integer.valueOf(line));
            }

            sem1 = addSubjectsToSemester(br, numberOfSubjects1);

            numberOfPackages2 = Integer.parseInt(br.readLine());
            for(int i = 0; i < numberOfPackages2; i++){
                line = br.readLine();
                numberOfSubjects2.add(Integer.valueOf(line));
            }

            sem2 = addSubjectsToSemester(br, numberOfSubjects2);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIndications() {
        return indications;
    }

    public static Map<Integer, List<Subject>> getSem1() {
        return sem1;
    }

    public static Map<Integer, List<Subject>> getSem2() {
        return sem2;
    }

}
