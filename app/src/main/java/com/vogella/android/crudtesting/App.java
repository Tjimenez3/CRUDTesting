package com.vogella.android.crudtesting;

import android.app.Application;

import com.vogella.android.crudtesting.api.Api;
import com.vogella.android.crudtesting.api.ApiRequest;
import com.vogella.android.crudtesting.constants.UrlConstant;

public class App extends Application {

    private static ApiRequest mainApi = null;

    @Override
    public void onCreate() {
        initAll();
        super.onCreate();
    }

    public void initAll() {
        initApi();
    }

    private void initApi() {
        mainApi = new Api(UrlConstant.BASE_URL).provideApiCall();

    }

    public static ApiRequest getApi() {
        if (mainApi == null) {
            mainApi = new Api(UrlConstant.BASE_URL).provideApiCall();
        }
        return mainApi;
    }

}