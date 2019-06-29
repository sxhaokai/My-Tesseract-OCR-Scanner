package com.zl.tesseract.scanner.tess;

import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.zl.tesseract.scanner.MyApplication;

/**
 * Created by Fadi on 6/11/2014.
 */
public class TessEngine {

    static final String TAG = "DBG_" + TessEngine.class.getName();
    private static TessEngine mInstance;
    private TessBaseAPI tessBaseAPI;

    private TessEngine() {
    }

    public static TessEngine Generate() {
        if (mInstance == null) {
            mInstance = new TessEngine();
        }
        return mInstance;
    }

    public String detectText(Bitmap bitmap) {
        if (tessBaseAPI == null) {
            Log.d(TAG, "Initialization of TessBaseApi");
            TessDataManager.initTessTrainedData(MyApplication.sAppContext);
            tessBaseAPI = new TessBaseAPI();
            String path = TessDataManager.getTesseractFolder();
            Log.d(TAG, "Tess folder: " + path);
            tessBaseAPI.setDebug(true);
            tessBaseAPI.init(path, "chi_sim");
//        tessBaseAPI.init(path, "chi_sim+eng");
            // 白名单
//        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
            // 黑名单
//        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?");
            tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
            Log.d(TAG, "Ended initialization of TessEngine");
            Log.d(TAG, "Running inspection on bitmap");
        }
        tessBaseAPI.setImage(bitmap);
        String inspection = tessBaseAPI.getUTF8Text();

        Log.d(TAG, "Confidence values: " + tessBaseAPI.meanConfidence());
        Log.d(TAG, inspection);
        return inspection;
//        return Tools.getTelNum(inspection);
    }

    public void release() {
        if (tessBaseAPI != null) {
            tessBaseAPI.end();
            tessBaseAPI = null;
        }
        System.gc();
    }


}
