package com.health.darooyar.feature.tracker.data.database.medicine;

import android.content.Context;
import android.util.Log;

import com.health.darooyar.common.QueryDatabase;
import com.health.darooyar.container.AppLoader;
import com.health.darooyar.feature.alarm.AlarmSetter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MedicineQueryImp extends QueryDatabase {
    private static final String MEDICINE_DATABASE = "/Medicine.txt";
    private volatile static MedicineQueryImp medicineQueryImp;
    private static JSONArray cacheData = new JSONArray();

    public MedicineQueryImp(Context context) {
        super(context);
    }

    public static MedicineQueryImp getInstance(Context context) {
        MedicineQueryImp localInstance = medicineQueryImp;
        if (localInstance == null) {
            synchronized (MedicineQueryImp.class) {
                localInstance = medicineQueryImp;
                if (localInstance == null) {
                    localInstance = medicineQueryImp = new MedicineQueryImp(context);
                }
            }
        }
        return localInstance;
    }

    public void putMedicine(MedicineModel model) throws JSONException {
        ensureCacheData();
        Log.i("TAG", "setAlarm1: ");
        cacheData = put(model, MEDICINE_DATABASE);
        AlarmSetter.startingAlarm(AppLoader.getAppContext());
    }

    public void deleteMedicine(MedicineModel model) throws JSONException {
        ensureCacheData();
        AlarmSetter.removeAlarm(AppLoader.getAppContext(), model);
        cacheData = delete(model, MEDICINE_DATABASE);
        AlarmSetter.startingAlarm(AppLoader.getAppContext());
    }

    public ArrayList<MedicineModel> getMedicines() {
        ensureCacheData();
        return MedicineModel.toMedicineModels(cacheData);
    }

    public ArrayList<MedicineModel> getMedicinesToday() {
        ensureCacheData();
        ArrayList<MedicineModel> medicineModels = getMedicines();
        ArrayList<MedicineModel> medicineModelsToday = new ArrayList<>();
        for (int i = 0; i < medicineModels.size(); i++)
            medicineModelsToday.addAll(medicineModels.get(i).shouldTakingToday());
        return medicineModelsToday;
    }

    public ArrayList<MedicineModel> getMedicines(long prescriptionId) {
        ensureCacheData();
        ArrayList<MedicineModel> medicines = getMedicines();
        ArrayList<MedicineModel> result = new ArrayList<>();
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getPrescriptionId() == prescriptionId) {
                result.add(medicines.get(i));
            }
        }
        return result;
    }

    private void ensureCacheData() {
        if (cacheData == null || cacheData.length() == 0) {
            try {
                cacheData = new JSONArray(readFile(MEDICINE_DATABASE));
            } catch (JSONException e) {
                cacheData = new JSONArray();
            }
        }
    }
}
