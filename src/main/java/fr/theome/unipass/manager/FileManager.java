package fr.theome.unipass.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    public String getFirstLine(String filePath){
        try {
            FileReader     file = new FileReader(filePath);
            BufferedReader br   = new BufferedReader(file);
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
