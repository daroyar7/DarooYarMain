package com.example.darooyar2.common;

import android.content.Context;

import com.example.darooyar2.feature.tracker.data.database.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    protected void put(Model model ,String CACHE_PATH) throws JSONException {
        JSONArray cacheData = new JSONArray(readFile(CACHE_PATH));
        if (model.getId() == 0) {
            model.createId();
            JSONObject jsonObject = model.toJSON();
            cacheData.put(jsonObject);
            writeFile(cacheData.toString(), CACHE_PATH);
        }else {
            for (int i = 0; i < cacheData.length(); i++) {
                if (cacheData.optJSONObject(i).optLong("id") == model.getId()){
                    try {
                        cacheData.remove(i);
                        cacheData.put(i, model.toJSON());
                        writeFile(cacheData.toString(), CACHE_PATH);
                    } catch (Exception ignored) {
                    }
                    return;
                }
            }
        }
    }
    protected void delete(Model model ,String CACHE_PATH) throws JSONException {
        JSONArray cacheData = new JSONArray(readFile(CACHE_PATH));
        for (int i = 0; i < cacheData.length(); i++) {
            if (cacheData.optJSONObject(i).optLong("id") == model.getId()){
                cacheData.remove(i);
                return;
            }
        }
    }
}
