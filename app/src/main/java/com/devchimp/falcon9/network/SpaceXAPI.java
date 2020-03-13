package com.devchimp.falcon9.network;

import com.devchimp.falcon9.objects.Launch;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface SpaceXAPI {
    @GET("/v3/launches")
    Observable<List<Launch>> getLaunches(@Query("rocket_id") String rocketID);
}
