package com.example.darooyar2.feature.tracker.data.database.prescription;

import org.json.JSONArray;
import org.json.JSONObject;

public class PrescriptionModel {

    private String doctorName;
    private String date;

    public PrescriptionModel(String doctorName, String date) {
        this.doctorName = doctorName;
        this.date = date;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctorName", doctorName);
            jsonObject.put("date", date);
            return jsonObject;
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    public static PrescriptionModel toPrescriptionModel(JSONObject prescriptionJSON) {
        String doctorName = prescriptionJSON.optString("doctorName");
        String date = prescriptionJSON.optString("date");
        return new PrescriptionModel(doctorName, date);
    }

    public static PrescriptionModel[] toPrescriptionModel(JSONArray jsonArray) {
        PrescriptionModel[] prescriptionModels = new PrescriptionModel[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++)
            prescriptionModels[i] = toPrescriptionModel(jsonArray.optJSONObject(i));
        return prescriptionModels;
    }
}
