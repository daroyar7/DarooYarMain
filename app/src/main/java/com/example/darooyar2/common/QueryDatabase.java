package com.example.darooyar2.common;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QueryDatabase {

    private String cachePath;

    public QueryDatabase(Context context){
        cachePath = context.getCacheDir().getAbsolutePath() + "/database";
        new File(cachePath).mkdirs();
    }

    protected void writeFile(String data, String path){
        try {
            File file = new File(cachePath + path);
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String readFile(String path){
        String applicationText;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(cachePath + path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null)
                stringBuilder.append(receiveString);
            applicationText = stringBuilder.toString();

            inputStream.close();

            return applicationText;

        } catch (Exception ignored) {
            return "";
        }
    }
}
