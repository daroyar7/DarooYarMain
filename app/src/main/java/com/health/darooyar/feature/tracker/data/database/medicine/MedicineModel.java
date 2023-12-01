package com.health.darooyar.feature.tracker.data.database.medicine;

import com.health.darooyar.common.solarDate.NewPersianDate;
import com.health.darooyar.feature.tracker.data.database.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MedicineModel extends Model {
    private String name;
    private String detail = "";
    private long timeMustUsed = 0;
    private int durationNumber;
    private String durationUnit;
    private String startDate;
    private String startTime;
    private long prescriptionId;

    public MedicineModel(String name, int durationNumber, String durationUnit, String startDate, String startTime, long prescriptionId) {
        this.name = name;
        this.durationNumber = durationNumber;
        this.durationUnit = durationUnit;
        this.startDate = startDate;
        this.startTime = startTime;
        this.prescriptionId = prescriptionId;
    }

    public long getTimeMustUsed() {
        return timeMustUsed;
    }

    public int getNotificationId() {
        return ((int) (nearestTaking() / 1000)) + hashCode();
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
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

    public ArrayList<MedicineModel> shouldTakingToday() {
        ArrayList<MedicineModel> medicineModels = new ArrayList<>();

        LocalDate persianDate = NewPersianDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy/M/d")).toGregorian();
        int yearStart = persianDate.getYear();
        int monthStart = persianDate.getMonthValue();
        int dayStart = persianDate.getDayOfMonth();
        int hourStart = Integer.parseInt(startTime.replace(" ", "").split(":")[0]);
        int minStart = Integer.parseInt(startTime.replace(" ", "").split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearStart, monthStart, dayStart, hourStart, minStart, 0);

        long startTimeStamp = calendar.getTimeInMillis() / 1000 * 1000 - (24 * 60 * 60 * 1000);
        long nowTimeStamp = System.currentTimeMillis();
        int periodUnit;
        if (durationUnit.equals("ساعت")) {
            periodUnit = 60 * 60;
        } else if (durationUnit.equals("روز")) {
            periodUnit = 24 * 60 * 60;
        } else {
            periodUnit = 7 * 24 * 60 * 60;
        }
        int periodTime = periodUnit * durationNumber * 1000;

        while (startTimeStamp / 60 / 60 / 24 / 1000 <= (nowTimeStamp / 60 / 60 / 24 / 1000 + 1)) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(startTimeStamp);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(nowTimeStamp);
            if (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH) && startTimeStamp >  calendar.getTimeInMillis()) {
                MedicineModel medicineModel = new MedicineModel(name, durationNumber, durationUnit, startDate, startTime, prescriptionId);
                medicineModel.timeMustUsed = startTimeStamp;
                medicineModel.detail = detail;
                medicineModels.add(medicineModel);
            }
            startTimeStamp += periodTime;
        }
        return medicineModels;
    }

    public static MedicineModel toMedicineModel(JSONObject prescriptionJSON) {
        long id = prescriptionJSON.optLong("id");
        String name = prescriptionJSON.optString("name");
        String detail = prescriptionJSON.optString("detail");
        int durationNumber = prescriptionJSON.optInt("durationNumber");
        String durationUnit = prescriptionJSON.optString("durationUnit");
        String startDate = prescriptionJSON.optString("startDate");
        String startTime = prescriptionJSON.optString("startTime");
        long prescriptionId = prescriptionJSON.optLong("prescriptionId");

        MedicineModel model = new MedicineModel(name, durationNumber, durationUnit, startDate, startTime, prescriptionId);
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
            jsonObject.put("prescriptionId", prescriptionId);
            return jsonObject;
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    public long nearestTaking() {
        LocalDate persianDate = NewPersianDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy/M/d")).toGregorian();
        int yearStart = persianDate.getYear();
        int monthStart = persianDate.getMonthValue();
        int dayStart = persianDate.getDayOfMonth();
        int hourStart = Integer.parseInt(startTime.replace(" ", "").split(":")[0]);
        int minStart = Integer.parseInt(startTime.replace(" ", "").split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yearStart);
        calendar.set(Calendar.MONTH, monthStart);
        calendar.set(Calendar.DAY_OF_MONTH, dayStart);
        calendar.set(Calendar.HOUR_OF_DAY, hourStart);
        calendar.set(Calendar.MINUTE, minStart);

        long startTimeStamp = calendar.getTimeInMillis() * 1000 / 1000 - (24 * 60 * 60 * 1000);
        long nowTimeStamp = System.currentTimeMillis();
        int periodUnit;
        if (durationUnit.equals("ساعت")) {
            periodUnit = 60 * 60;
        } else if (durationUnit.equals("روز")) {
            periodUnit = 24 * 60 * 60;
        } else {
            periodUnit = 7 * 24 * 60 * 60;
        }
        int periodTime = periodUnit * durationNumber * 1000;

        while (startTimeStamp / 60 / 60 / 24 / 1000 <= (nowTimeStamp / 60 / 60 / 24 / 1000 + 1)) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(startTimeStamp);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(nowTimeStamp);
            if (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)  && startTimeStamp > nowTimeStamp) {
                return startTimeStamp;
            }
            startTimeStamp += periodTime;
        }
        return -1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicineModel)) return false;
        MedicineModel that = (MedicineModel) o;
        return getDurationNumber() == that.getDurationNumber() && getPrescriptionId() == that.getPrescriptionId() && getName().equals(that.getName()) && getDetail().equals(that.getDetail()) && getDurationUnit().equals(that.getDurationUnit()) && getStartDate().equals(that.getStartDate()) && getStartTime().equals(that.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDetail(), getDurationNumber(), getDurationUnit(), getStartDate(), getStartTime(), getPrescriptionId());
    }
}
