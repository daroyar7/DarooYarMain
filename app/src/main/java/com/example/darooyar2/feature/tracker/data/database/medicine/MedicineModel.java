package com.example.darooyar2.feature.tracker.data.database.medicine;

import com.example.darooyar2.feature.tracker.data.database.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedicineModel extends Model {
    private String name;
    private String detail = "";
    private int durationNumber;
    private String durationUnit;
    private String startDate;
    private String startTime;

    public MedicineModel(String name, int durationNumber, String durationUnit, String startDate, String startTime) {
        this.name = name;
        this.durationNumber = durationNumber;
        this.durationUnit = durationUnit;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getDurationNumber() {
        return durationNumber;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDurationNumber(int durationNumber) {
        this.durationNumber = durationNumber;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public static MedicineModel toMedicineModel(JSONObject prescriptionJSON) {
        long id = prescriptionJSON.optLong("id");
        String name = prescriptionJSON.optString("name");
        String detail = prescriptionJSON.optString("detail");
        int durationNumber = prescriptionJSON.optInt("durationNumber");
        String durationUnit = prescriptionJSON.optString("durationUnit");
        String startDate = prescriptionJSON.optString("startDate");
        String startTime = prescriptionJSON.optString("startTime");

        MedicineModel model = new MedicineModel(name, durationNumber, durationUnit, startDate, startTime);
        model.setDetail(detail);
        model.setId(id);
        return model;
    }



    public static ArrayList<MedicineModel> toMedicineModels(JSONArray jsonArray) {
        ArrayList<MedicineModel> prescriptionModels = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
            prescriptionModels.add(toMedicineModel(jsonArray.optJSONObject(i)));
        return prescriptionModels;
    }

    @Override
    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("detail", detail);
            jsonObject.put("durationNumber", durationNumber);
            jsonObject.put("durationUnit", durationUnit);
            jsonObject.put("startDate", startDate);
            jsonObject.put("startTime", startTime);
            return jsonObject;
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
