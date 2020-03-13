package com.devchimp.falcon9;

import android.app.Application;
import android.content.Context;

import com.devchimp.falcon9.dependency.DaggerNetworkComponent;
import com.devchimp.falcon9.dependency.NetworkComponent;
import com.devchimp.falcon9.dependency.RetrofitModule;

public class Falcon9Application extends Application {
    private NetworkComponent networkComponent;

    @Override public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent
                .builder()
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public static NetworkComponent getNetComponent(Context context) {
        return ((Falcon9Application)(context.getApplicationContext())).networkComponent;
    }
}
