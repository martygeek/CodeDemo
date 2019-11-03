package us.martypants.rightpointdemo.managers

import rx.Observable
import us.martypants.rightpointdemo.DataManagerAPI
import us.martypants.rightpointdemo.models.ImdbData


/**
 * Created by Martin Rehder on 2019-11-02.
 */
class DataManager(private val mApi: DataManagerAPI) {

    fun getImdbData(search: String) : Observable<ImdbData>? {
        return mApi.getImdbData(search)
    }


    fun getSpecifics(search: String, type: String) : Observable<ImdbData>? {
        return mApi.getSpecificType(search, type)
    }

}