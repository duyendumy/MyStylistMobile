package com.example.mystylistmobile.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.mystylistmobile.retrofit.AuthenticationInterceptor;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private static SessionManager instance;
    private static final String PREF_NAME = "register_login";
    private static final String USER_TOKEN  = "user_token";

    private static final String REFRESH_TOKEN  = "user_refresh_token";

    private static final String NAME  = "user_name";

    private static final String EMAIL  = "user_email";

    private static final String PASSWORD  = "user_password";

    private static final String SAVE_LOGIN  = "save_login";

    private static final String SEASONAL_COLOR_NAME = "user_seasonal_color_name";

    private static final String SEASONAL_COLOR_ID = "user_seasonal_color_id";

    private static final String STYLE_TYPE_NAME = "user_style_type_name";

    private static final String STYLE_TYPE_ID = "user_style_type_id";

    private static final String BODY_SHAPE_NAME = "user_body_shape_name";

    private static final String BODY_SHAPE_ID = "user_body_shape_id";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    private SessionManager() {
        // Private constructor to enforce singleton pattern
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveUserInfo(String token,String refreshToken,  String name, String nameSeasonalColor, Long idSeasonalColor, String nameStyleType, Long idStyleType, String nameBodyShape, Long idBodyShape) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.putString(NAME, name);
        editor.putString(SEASONAL_COLOR_NAME, nameSeasonalColor);
        editor.putLong(SEASONAL_COLOR_ID, idSeasonalColor);
        editor.putString(STYLE_TYPE_NAME, nameStyleType);
        editor.putLong(STYLE_TYPE_ID, idStyleType);
        editor.putString(BODY_SHAPE_NAME, nameBodyShape);
        editor.putLong(BODY_SHAPE_ID, idBodyShape);
        editor.apply();
    }

    public void setSaveLogin(Boolean saveLogin){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SAVE_LOGIN, saveLogin);
        editor.apply();
    }

    public Boolean getSaveLogin(){
        return sharedPreferences.getBoolean(SAVE_LOGIN, false);
    }

    public String getUserToken() {
        return sharedPreferences.getString(USER_TOKEN, null);
    }

    public void setUserToken(String authToken){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, authToken);
        editor.apply();
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(REFRESH_TOKEN, null);
    }

    public void setRefreshToken(String refreshToken){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public Long getSeasonalColorId(){
        return sharedPreferences.getLong(SEASONAL_COLOR_ID, 0);
    }

    public String getSeasonalColorName(){
        return sharedPreferences.getString(SEASONAL_COLOR_NAME, null);
    }
    public Long getStyleTypeId(){
        return sharedPreferences.getLong(STYLE_TYPE_ID, 0);
    }
    public String getStyleTypeName(){
        return sharedPreferences.getString(STYLE_TYPE_NAME, null);
    }
    public Long getBodyShapeId(){
        return sharedPreferences.getLong(BODY_SHAPE_ID, 0);
    }
    public String getBodyShapeName(){
        return sharedPreferences.getString(BODY_SHAPE_NAME, null);
    }

    public String getName(){ return sharedPreferences.getString(NAME, null); }
    public String getEmail(){ return sharedPreferences.getString(EMAIL, null); }

    public void setEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public void setPassword(String password){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public String getPassword(){ return sharedPreferences.getString(PASSWORD, null);}

    public void clearAuthToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_TOKEN);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(EMAIL);
        editor.remove(PASSWORD);
        editor.remove(USER_TOKEN);
        editor.remove(NAME);
        editor.remove(SEASONAL_COLOR_NAME);
        editor.remove(SEASONAL_COLOR_ID);
        editor.remove(STYLE_TYPE_NAME);
        editor.remove(STYLE_TYPE_NAME);
        editor.remove(STYLE_TYPE_NAME);
        editor.remove(BODY_SHAPE_ID);
        editor.remove(SAVE_LOGIN);
        editor.apply();
    }
    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
