package com.example.darooyar2.feature.tracker.data.database.prescription;

import android.content.Context;
import android.util.Log;

import com.example.darooyar2.common.QueryDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrescriptionQueryImp extends QueryDatabase {

    private static final String PRESCRIPTION_DATABASE = "/Prescription.txt";

    private volatile static PrescriptionQueryImp prescriptionQueryImp;
    private static JSONArray cacheData = new JSONArray();

    public static PrescriptionQueryImp getInstance(Context context) {
        PrescriptionQueryImp localInstance = prescriptionQueryImp;
        if (localInstance == null) {
            synchronized (PrescriptionQueryImp.class) {
                localInstance = prescriptionQueryImp;
                if (localInstance == null)
                    prescriptionQueryImp = localInstance = new PrescriptionQueryImp(context);
            }
        }
        return localInstance;
    }

    public PrescriptionQueryImp(Context context) {
        super(context);
    }

    public void putPrescription(PrescriptionModel prescriptionModel) {
        ensureCacheData();
        Log.i("TAG", "putPrescription: " + prescriptionModel.getId());
        if (prescriptionModel.getId() == 0) {
            prescriptionModel.createId();
            JSONObject jsonObject = prescriptionModel.toJSON();
            cacheData.put(jsonObject);
            writeFile(cacheData.toString(), PRESCRIPTION_DATABASE);
        }else {
            for (int i = 0; i < cacheData.length(); i++) {
                if (cacheData.optJSONObject(i).optLong("id") == prescriptionModel.getId()){
                    try {
                        cacheData.remove(i);
                        cacheData.put(i, prescriptionModel.toJSON());
                        writeFile(cacheData.toString(), PRESCRIPTION_DATABASE);
                    } catch (Exception ignored) {
                    }
                    return;
                }
            }
        }
    }

    public ArrayList<PrescriptionModel> getPrescriptions() {
        ensureCacheData();
        Log.i("TAG", "cacheData: "+ cacheData);
        return PrescriptionModel.toPrescriptionModel(cacheData);
    }

    public void removePrescription(PrescriptionModel prescriptionModel){
        ensureCacheData();
        for (int i = 0; i < cacheData.length(); i++) {
            if (cacheData.optJSONObject(i).optLong("id") == prescriptionModel.getId()){
                cacheData.remove(i);
                return;
            }
        }
    }

    private void ensureCacheData() {
        if (cacheData == null || cacheData.length() == 0) {
            try {
                cacheData = new JSONArray(readFile(PRESCRIPTION_DATABASE));
                Log.i("TAG", "ensureCacheData: "+cacheData);
            } catch (JSONException e) {
                cacheData = new JSONArray();
            }
        }
    }
}
