package com.example.darooyar2.feature.tracker.data.database.prescription;

import org.json.JSONArray;
import org.json.JSONObject;

public class PrescriptionModel {

    private String doctorName;
    private String seeingDoctorDate;

    public PrescriptionModel(String doctorName, String seeingDoctorDate) {
        this.doctorName = doctorName;
        this.seeingDoctorDate = seeingDoctorDate;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctorName", doctorName);
            jsonObject.put("seeingDoctorDate", seeingDoctorDate);
            return jsonObject;
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    public static PrescriptionModel toPrescriptionModel(JSONObject prescriptionJSON) {
        String doctorName = prescriptionJSON.optString("doctorName");
        String seeingDoctorDate = prescriptionJSON.optString("seeingDoctorDate");
        return new PrescriptionModel(doctorName, seeingDoctorDate);
    }

    public static PrescriptionModel[] toPrescriptionModel(JSONArray jsonArray) {
        PrescriptionModel[] prescriptionModels = new PrescriptionModel[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++)
            prescriptionModels[i] = toPrescriptionModel(jsonArray.optJSONObject(i));
        return prescriptionModels;
    }
}
