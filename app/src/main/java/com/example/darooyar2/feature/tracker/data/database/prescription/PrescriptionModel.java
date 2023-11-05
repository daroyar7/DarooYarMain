package com.example.darooyar2.feature.tracker.data.database.prescription;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrescriptionModel {

    private long id;
    private String doctorName;
    private String date;

    public PrescriptionModel(String doctorName, String date) {
        this.doctorName = doctorName;
        this.date = date;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void createId() {
        id = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDate() {
        return date;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("doctorName", doctorName);
            jsonObject.put("date", date);
            return jsonObject;
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    public static PrescriptionModel toPrescriptionModel(JSONObject prescriptionJSON) {
        long id = prescriptionJSON.optLong("id");
        String doctorName = prescriptionJSON.optString("doctorName");
        String date = prescriptionJSON.optString("date");
        PrescriptionModel prescriptionModel = new PrescriptionModel(doctorName, date);
        prescriptionModel.setId(id);
        return prescriptionModel;
    }

    public static ArrayList<PrescriptionModel> toPrescriptionModel(JSONArray jsonArray) {
        Log.i("TAG", "toPrescriptionModel: "+jsonArray);
        ArrayList<PrescriptionModel> prescriptionModels = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
            prescriptionModels.add(toPrescriptionModel(jsonArray.optJSONObject(i)));
        return prescriptionModels;
    }
}
