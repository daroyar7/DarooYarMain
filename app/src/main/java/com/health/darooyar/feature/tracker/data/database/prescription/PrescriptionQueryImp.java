package com.health.darooyar.feature.tracker.data.database.prescription;

import android.content.Context;

import com.health.darooyar.common.QueryDatabase;

import org.json.JSONArray;
import org.json.JSONException;

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

    public void putPrescription(PrescriptionModel prescriptionModel) throws JSONException {
        ensureCacheData();
        cacheData = put(prescriptionModel, PRESCRIPTION_DATABASE);
    }

    public void deletePrescription(PrescriptionModel prescriptionModel) throws JSONException {
        ensureCacheData();
        cacheData = delete(prescriptionModel, PRESCRIPTION_DATABASE);
    }

    public ArrayList<PrescriptionModel> getPrescriptions() {
        ensureCacheData();
        return PrescriptionModel.toPrescriptionModels(cacheData);
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
