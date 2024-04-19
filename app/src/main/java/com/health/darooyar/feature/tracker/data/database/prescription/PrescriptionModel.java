package com.health.darooyar.feature.tracker.data.database.prescription;

import android.util.Log;

import com.health.darooyar.feature.tracker.data.database.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrescriptionModel extends Model {
    private String doctorName;
    private String prescriptionName;
    private String date;

    public PrescriptionModel(String prescriptionName, String doctorName, String date) {
        this.prescriptionName = prescriptionName;
        this.doctorName = doctorName;
        this.date = date;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
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
            jsonObject.put("prescriptionName", prescriptionName);
            jsonObject.put("date", date);
            return jsonObject;
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    public static PrescriptionModel toPrescriptionModel(JSONObject prescriptionJSON) {
        long id = prescriptionJSON.optLong("id");
        String prescriptionName = prescriptionJSON.optString("prescriptionName");
        String doctorName = prescriptionJSON.optString("doctorName");
        String date = prescriptionJSON.optString("date");
        PrescriptionModel prescriptionModel = new PrescriptionModel(prescriptionName, doctorName, date);
        prescriptionModel.setId(id);
        return prescriptionModel;
    }

    public static ArrayList<PrescriptionModel> toPrescriptionModels(JSONArray jsonArray) {
        Log.i("TAG", "toPrescriptionModel: " + jsonArray);
        ArrayList<PrescriptionModel> prescriptionModels = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
            prescriptionModels.add(toPrescriptionModel(jsonArray.optJSONObject(i)));
        return prescriptionModels;
    }
}
