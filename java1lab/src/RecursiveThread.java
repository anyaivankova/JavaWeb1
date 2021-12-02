import java.io.File;
import java.util.concurrent.ExecutorService;

public class RecursiveThread implements Runnable{

    private final File activeFolder;
    private final File resultFolder;
    private final ExecutorService executorService;

    public RecursiveThread(File activeFolder, File resultFolder, ExecutorService executorService) {
        this.activeFolder = activeFolder;
        this.resultFolder = resultFolder;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        System.out.println("Поток "+Thread.currentThread().getName()+" начинает работу (R)");
        if(resultFolder.mkdir()){
            System.out.println("Создал "+resultFolder.getName()+"("+Thread.currentThread().getName()+")");
        }

        File[] files = activeFolder.listFiles();
        String newResultFolderName;
        File newResultFolder;

        for (File iterFile : files) {
            if(iterFile.isDirectory()){
                newResultFolderName = resultFolder.getAbsoluteFile() + "/" + iterFile.getName();
                newResultFolder = new File(newResultFolderName);
                executorService.submit(new RecursiveThread(iterFile, newResultFolder, executorService));
            }else if(iterFile.getName().matches(".*\\.txt$")){
                executorService.submit(new FileThread(iterFile, resultFolder));
            }
        }
        System.out.println("Поток "+Thread.currentThread().getName()+" завершен");
    }
}
