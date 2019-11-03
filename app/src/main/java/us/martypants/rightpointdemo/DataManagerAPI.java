package us.martypants.rightpointdemo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import us.martypants.rightpointdemo.models.ImdbData;

/**
 * Created by marty on 7/28/16.
 */


public interface DataManagerAPI {


    @GET("/?apikey=2ec4b519")
    Observable<ImdbData> getImdbData(@Query("s") String search);

    @GET("/?apikey=2ec4b519")
    Observable<ImdbData> getSpecificType(@Query("s") String search, @Query("type") String type);



}

