import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter folder: ");
        String activeFolderName = sc.next();//"D:\\TestFiles";
        File activeFolder = new File(activeFolderName);
        checkFolderName(activeFolderName, activeFolder);

        System.out.println("Enter result folder: ");
        File resultFolder = new File(sc.next());// new File("D:\\Test");

        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable runnable = new RecursiveThread(activeFolder, resultFolder, executorService);
        executorService.submit(runnable);

    }

    private static void checkFolderName(String dName, File dir) {
        if (!dir.exists()){
            System.err.printf("Folder %s does not exist", dName);
            exit(0);
        }
        if (!dir.isDirectory()){
            System.err.printf("File %s is not a folder", dName);
            exit(0);
        }
    }
}
