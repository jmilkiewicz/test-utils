package pl.softmil.test.utils.waituntil.blueprint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyFileOperation {
    private String fileName;

    public ModifyFileOperation(String fileName) {
        this.fileName = fileName;
    }

    public void doWork(int numberOfLinesToWrite, int perWriteSleep)
            throws IOException, InterruptedException {
        File file = new File(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < numberOfLinesToWrite; i++) {
            Thread.sleep(perWriteSleep);
            bufferedWriter.write("to jest linia nr" + i + "\n");
            bufferedWriter.flush();
        }
        bufferedWriter.close();
    }

}
