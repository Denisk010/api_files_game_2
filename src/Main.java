import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static String pathToSave = "C:\\Apps\\Games\\savegames\\";

    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(90, 5, 1, 200.56);
        GameProgress progress2 = new GameProgress(80, 3, 2, 150.12);
        GameProgress progress3 = new GameProgress(40, 2, 3, 100.77);
        saveGame(pathToSave + "save1.dat", progress1);
        saveGame(pathToSave + "save2.dat", progress2);
        saveGame(pathToSave + "save3.dat", progress3);
        zipFiles(pathToSave + "zippedsave.zip", "save1.dat", "save2.dat", "save3.dat");
    }

    public static void saveGame(String fileTosave, GameProgress gameProgress) {
        System.out.println("Saving game to file " + fileTosave);
        try (FileOutputStream fileStream = new FileOutputStream(fileTosave);
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void zipFiles(String nameOfZip, String... filesToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(
                new FileOutputStream(nameOfZip))) {
            for (int i = 0; i < filesToZip.length; i++) {
                FileInputStream fis = new FileInputStream(pathToSave + filesToZip[i]);
                ZipEntry entry = new ZipEntry(filesToZip[i]);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
            for (int i = 0; i < filesToZip.length; i++) {
                File fileToDelete = new File(pathToSave, filesToZip[i]);
                fileToDelete.delete();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}