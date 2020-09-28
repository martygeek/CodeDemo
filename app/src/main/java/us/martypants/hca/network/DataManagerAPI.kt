package us.martypants.hca.network

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import us.martypants.hca.models.SoData

interface DataManagerAPI {

    @GET("/2.2/questions?order=desc&sort=activity&site=stackoverflow&pagesize=100")
    fun getSoData(@Query("page") page: Int): Observable<SoData>?
}