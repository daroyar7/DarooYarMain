package com.example.darooyar2.feature.tracker.data.database.prescription;

import android.content.Context;

import com.example.darooyar2.common.QueryDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class PrescriptionQueryImp extends QueryDatabase {

    private static final String prescriptionDatabase = "/Prescription.txt";

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

//    public void addPrescription() {
//        ensureCacheData();
//        for (int i = 0; i < cacheData.length(); i++) {
//            JSONObject lessonJSON = cacheData.optJSONObject(i);
//            if (lessonJSON.optString("level").equals(level) && lessonJSON.optInt("lesson") == lessonNumber) {
//                cacheData.optJSONObject(i).optJSONArray("skills").optJSONObject(skill).put("key", 1);
//                DatabaseFileManager.getInstance().writeFile(cacheData.toString(), LESSON_FILE);
//                break;
//            }
//        }
//    }

}
