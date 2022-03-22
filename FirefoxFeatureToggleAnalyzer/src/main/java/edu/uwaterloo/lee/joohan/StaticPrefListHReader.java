package edu.uwaterloo.lee.joohan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticPrefListHReader {


    public int getNumOfToggles(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.h", buildId);
        Pattern pattern = Pattern.compile("PREF\\(");
        int count = 0;

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    count++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return count;
    }
}
