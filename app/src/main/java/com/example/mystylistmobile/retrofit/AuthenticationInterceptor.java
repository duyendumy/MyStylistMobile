package com.example.mystylistmobile.retrofit;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.mystylistmobile.dto.auth.TokenRequest;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.JwtResponse;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.service.AuthService;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class AuthenticationInterceptor implements Interceptor {
    private String authToken;
    private String refreshToken;
    private AuthService authService;

    private Context context;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    public AuthenticationInterceptor(String authToken, String refreshToken, AuthService authService, Context context) {
        this.authToken = authToken;
        this.refreshToken = refreshToken;
        this.authService = authService;
        this.context = context;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer " + authToken);

        Request request = builder.build();
        Response response = chain.proceed(request);

        if (response.code() == 401 && response.body() != null) {

            // Make the token refresh request
            TokenRequest tokenRequest = new TokenRequest(refreshToken);
            authService.refreshToken(tokenRequest).enqueue(new Callback<ResponseModel<JwtResponse, ErrorDTO>>() {
                @Override
                public void onResponse(Call<ResponseModel<JwtResponse, ErrorDTO>> call, retrofit2.Response<ResponseModel<JwtResponse, ErrorDTO>> response) {
                    if(response.body() != null) {
                        // Update access token and refresh token in session manager
                        JwtResponse newAuthToken = response.body().getResponse();
                        authToken = newAuthToken.getAccess().getToken();
                        refreshToken = newAuthToken.getRefresh().getToken();
                        SessionManager.getInstance(context).setRefreshToken(refreshToken);
                        SessionManager.getInstance(context).setUserToken(authToken);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel<JwtResponse, ErrorDTO>> call, Throwable t) {

                }
            });

            Request.Builder newBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + authToken);

            Request newRequest = newBuilder.build();
            return chain.proceed(newRequest);
        }


        return response;
    }


}
