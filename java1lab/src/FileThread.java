import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class FileThread implements Runnable{

    private File textFile;
    private String newFileName;

    public FileThread(File textFile/*D:/TestFiles/1.txt*/, File resultFolder/*D:/Test*/) {
        this.textFile = textFile;
        this.newFileName = resultFolder.getAbsolutePath()+"/"+textFile.getName(); // "D:/Test" + "/" + "1.txt"
    }

    @Override
    public void run() {
        System.out.println("Поток "+Thread.currentThread().getName()+" начинает работу (F)");

        System.out.println("Начинаю чтение "+textFile.getAbsolutePath()+"("+Thread.currentThread().getName()+")");
        String data = readFile(textFile);

        System.out.println("Начинаю сортировку "+textFile.getAbsolutePath()+"("+Thread.currentThread().getName()+")");
        data = sort(data);

        System.out.println("Начинаю запись "+newFileName +"("+Thread.currentThread().getName()+")");
        writeFile(data);

        System.out.println("Поток "+Thread.currentThread().getName()+" завершен");
    }

    private String readFile(File file) {
        FileReader reader = null;

        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(reader);

        StringBuilder s = new StringBuilder();
        while (scanner.hasNextLine()) {
            s.append(scanner.nextLine());
        }
        return s.toString();
    }

    private String sort(String data) {

        String[] sNums = data.split(" ");

        int[] ints = new int[sNums.length];

        for (int i = 0; i < sNums.length; i++) {
            ints[i] = Integer.parseInt(sNums[i].trim());
        }

        Arrays.sort(ints);

        for (int i = 0; i < sNums.length; i++) {
            sNums[i] = String.valueOf(ints[i]);
        }

        return String.join(" ", sNums);
    }

    private void writeFile(String data){
        File newTXTFile = new File(newFileName);
        try {
            if (newTXTFile.createNewFile()){
                System.out.println("Создал файл "+newTXTFile.getName()+"("+Thread.currentThread().getName()+")");
            }
            FileWriter writer = new FileWriter(newTXTFile);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
