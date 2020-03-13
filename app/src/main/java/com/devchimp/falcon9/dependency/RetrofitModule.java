package com.devchimp.falcon9.dependency;

import com.devchimp.falcon9.network.SpaceXAPI;
import com.devchimp.falcon9.objects.Launch;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {
    public static final String FALCON9 = "falcon9";
    private static final String BASE_URL = "https://api.spacexdata.com";

    @Provides @Singleton
    Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    SpaceXAPI provideApi(Retrofit retrofit) {
        return retrofit.create(SpaceXAPI.class);
    }
}
