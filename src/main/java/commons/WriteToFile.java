package commons;

import forms.Subject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Iulia-PC on 6/16/2016.
 */
public class WriteToFile {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";


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
                    bw.write(sem.get(i).get(j).getFavoriteDisciplines().getDiscipline1());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getFavoriteDisciplines().getDiscipline2());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getFavoriteDisciplines().getDiscipline3());
                    bw.newLine();
                    bw.write(sem.get(i).get(j).getFavoriteDisciplines().getDiscipline4());
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


    /* Scrie in fisier de tip csv preferintele studentilor */
    public static void writePreferinteStudentiToCsv(String fileName, Date timestamp, String nrMatricol, String nume, List<String> sem1, List<String> sem2){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.append("lalala");

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

}
