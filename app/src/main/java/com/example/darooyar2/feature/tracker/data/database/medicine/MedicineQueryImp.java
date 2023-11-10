package com.example.darooyar2.feature.tracker.data.database.medicine;

import android.content.Context;
import com.example.darooyar2.common.QueryDatabase;

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
        put(model, MEDICINE_DATABASE);
    }

    public void deleteMedicine(MedicineModel model) throws JSONException {
        delete(model, MEDICINE_DATABASE);
    }

    public ArrayList<MedicineModel> getMedicines(){
        ensureCacheData();
        return MedicineModel.toMedicineModels(cacheData);
    }
    public ArrayList<MedicineModel> getMedicines(long prescriptionId){
        ensureCacheData();
        ArrayList<MedicineModel> medicines=getMedicines();
        ArrayList<MedicineModel> result=new ArrayList<>();
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getPrescriptionId()==prescriptionId){
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
