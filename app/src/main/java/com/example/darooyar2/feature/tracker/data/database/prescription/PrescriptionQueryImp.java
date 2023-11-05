package com.example.darooyar2.feature.tracker.data.database.prescription;

import android.content.Context;

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

    public void addPrescription(PrescriptionModel prescriptionModel) {
        ensureCacheData();
        prescriptionModel.createId();
        JSONObject jsonObject = prescriptionModel.toJSON();
        cacheData.put(jsonObject);
        writeFile(cacheData.toString(), PRESCRIPTION_DATABASE);
    }

    public ArrayList<PrescriptionModel> getPrescriptions() {
        ensureCacheData();
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
            } catch (JSONException e) {
                cacheData = new JSONArray();
            }
        }
    }
}
