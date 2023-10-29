package com.example.darooyar2.feature.tracker.prescription.data.database;

import android.content.Context;

import com.example.darooyar2.common.QueryDatabase;

import org.json.JSONObject;

public class PrescriptionQueryImp extends QueryDatabase {

    private static final String prescriptionDatabase = "/LanguageMedal.txt";

    private volatile static PrescriptionQueryImp prescriptionQueryImp;
    private static JSONObject cacheData = new JSONObject();

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

}
