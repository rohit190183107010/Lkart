package com.example.rohit.lkart2.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LKART";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String USERID = "id";
    private static final String NAME = "name";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }


    public void setId(String id) {
        editor.putString(USERID, id);
        editor.commit();
    }
    public void setName(String name) {
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getId(){
        return pref.getString(USERID, "0");
    }
    public String getName(){
        return pref.getString(NAME, "Guest");
    }
}