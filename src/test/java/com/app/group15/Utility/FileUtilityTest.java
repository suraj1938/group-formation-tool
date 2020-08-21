package com.app.group15.Utility;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FileUtilityTest {

    @Test
    public void checkFileExistTest() {
        String propertyFilePath = "src/main/resources/application.properties";
        assertTrue(FileUtility.checkFileExist(propertyFilePath));
    }

    @Test
    public void readCSVTest() {
        String sampleCSVStudentListPath = "src/main/resources/static/student_list.csv";
        String[] cols = {"name", "email", "banner_id"};
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        StringBuilder fileData = new StringBuilder();
        try {
            File myObj = new File(sampleCSVStudentListPath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileData.append(myReader.nextLine()).append("\n");
            }
            String[] rows = fileData.toString().split("\n");
            String[] fileCols = rows[0].toLowerCase().split(",");
            for (int i = 0; i < fileCols.length; i++) {
                fileCols[i] = fileCols[i].trim();
            }
            assertArrayEquals(fileCols, cols);
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
