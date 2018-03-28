package com.naziksoft.wethearapp.db;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DBHelperFactory {
    private static DBHelper dbHelper;

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper(Context context) {
        if (dbHelper == null) {
            Context appContext = context.getApplicationContext();
            dbHelper = OpenHelperManager.getHelper(appContext, DBHelper.class);
        }
    }

    public static void releaseHelper() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }
}
