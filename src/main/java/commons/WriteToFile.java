package commons;

import forms.FavDiscipline;
import forms.Subject;

import javax.servlet.Filter;
import java.io.*;
import java.util.Date;
import java.util.List;

import static commons.Constants.*;

/**
 * Created by Iulia-PC on 6/16/2016.
 */
public class WriteToFile {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static int packageCounter = 1;


    private static void writeOptionale(BufferedWriter bw, int packageNumber, List<List<Subject>> sem) {
        try {
            for (int i = 0; i < packageNumber; i++) {
                for (int j = 0; j < sem.get(i).size(); j++) {
                    bw.write(sem.get(i).get(j).getTitle());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getLink());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getProf());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getD1().getCod());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getD2().getCod());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getD3().getCod());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getD4().getCod());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* Scrie in fisier optionalele salvate de admin pentru a fi afisate studentilor */
    public static void writeOptionaleToFile(String filename, int p1, List<Integer> subNoP1, List<List<Subject>> sem1, int p2, List<Integer> subNoP2, List<List<Subject>> sem2, String indications) {
        File file = new File(filename);

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(indications);
            bw.newLine();
            bw.write(Integer.toString(p1));
            bw.newLine();
            for (int i = 0; i < p1; i++) {
                bw.write(String.valueOf(subNoP1.get(i)));
                bw.newLine();
            }

            writeOptionale(bw, p1, sem1);

            bw.write(Integer.toString(p2));
            bw.newLine();
            for (int i = 0; i < p2; i++) {
                bw.write(String.valueOf(subNoP2.get(i)));
                bw.newLine();
            }

            writeOptionale(bw, p2, sem2);

            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


    private static void writeOptions(FileWriter fw, List<List<String>> options){
        try{
            for(int i = 0; i < options.size(); i++){
                for(int j = 0; j < options.get(i).size(); j++){
                    fw.append(COMMA_DELIMITER);
                    fw.append(options.get(i).get(j));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /* Scrie in fisier de tip csv preferintele studentilor */
    public static void writePreferinteStudentiToCsv(String fileName, String timestamp, String nrMatricol, String nume, List<List<String>> sem1, List<List<String>> sem2) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName, true);
            fileWriter.append(timestamp);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(nrMatricol);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(nume);

            writeOptions(fileWriter, sem1);
            writeOptions(fileWriter, sem2);
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writePacheteToCsv(String fileName, String year, List<Integer> sem1, List<Integer> sem2) {
        FileWriter fileWriter = null;
        String[] yearNumber = year.split("\\s+");
        try {
            if (year.equals(AN_2)) {
                fileWriter = new FileWriter(fileName);
            } else {
                fileWriter = new FileWriter(fileName, true);
            }

            for (int i = 0; i < sem1.size(); i++) {
                fileWriter.append(CO + (packageCounter++));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(yearNumber[1]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("1");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(sem1.get(i)));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            for (int i = 0; i < sem2.size(); i++) {
                fileWriter.append(CO + (packageCounter++));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(yearNumber[1]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("2");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(sem2.get(i)));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String makeCodAndPond(FavDiscipline discipline){
        String response = new String();

        if(discipline.getCod().equals("")){
            response = "";
        }else{
            response =  COMMA_DELIMITER + "\"" + discipline.getCod() + "\"" + COMMA_DELIMITER + discipline.getPondere();
        }
        return response;
    }

    private static void parseSemesterAndWriteDisciplines(FileWriter fw, List<List<Subject>> semester){
        try{
            for(int i = 0; i < semester.size(); i++){
                for(int j = 0; j < semester.get(i).size(); j++){
                    fw.append(semester.get(i).get(j).getTitle());
                    fw.append(makeCodAndPond(semester.get(i).get(j).getD1()));
                    fw.append(makeCodAndPond(semester.get(i).get(j).getD2()));
                    fw.append(makeCodAndPond(semester.get(i).get(j).getD3()));
                    fw.append(makeCodAndPond(semester.get(i).get(j).getD4()));
                    fw.append(NEW_LINE_SEPARATOR);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writePrefDisciplineToCsv(String year, List<List<Subject>> sem1, List<List<Subject>> sem2) {
        FileWriter fileWriter = null;
        try{
            if(year.equals(AN_2)){
                fileWriter = new FileWriter(PREF_DISCIPLINE);
            }
            if(year.equals(AN_3)){
                fileWriter = new FileWriter(PREF_DISCIPLINE, true);
            }


            parseSemesterAndWriteDisciplines(fileWriter, sem1);
            parseSemesterAndWriteDisciplines(fileWriter, sem2);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

}
