package us.martypants.rightpointdemo.managers

import rx.Observable
import us.martypants.rightpointdemo.models.ImdbData
import us.martypants.rightpointdemo.network.DataManagerAPI


/**
 * Created by Martin Rehder on 2019-11-02.
 */
class DataManager(private val mApi: DataManagerAPI) {

    fun getImdbData(search: String, page: Int) : Observable<ImdbData>? {
        return mApi.getImdbData(search, page)
    }


    fun getSpecifics(search: String, type: String, page: Int) : Observable<ImdbData>? {
        return mApi.getSpecificType(search, type, page)
    }

}