package com.example.darooyar2.feature.tracker.data.database.prescription;

import android.util.Log;

import com.example.darooyar2.feature.tracker.data.database.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrescriptionModel extends Model {
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

    public String getDoctorName() {
        return doctorName;
    }

    public String getDate() {
        return date;
    }

    @Override
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

    public static ArrayList<PrescriptionModel> toPrescriptionModels(JSONArray jsonArray) {
        Log.i("TAG", "toPrescriptionModel: "+jsonArray);
        ArrayList<PrescriptionModel> prescriptionModels = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
            prescriptionModels.add(toPrescriptionModel(jsonArray.optJSONObject(i)));
        return prescriptionModels;
    }
}
