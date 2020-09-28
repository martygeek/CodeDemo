package us.martypants.hca.managers

import rx.Observable
import us.martypants.hca.models.SoData
import us.martypants.hca.network.DataManagerAPI


class DataManager(private val mApi: DataManagerAPI) {

    fun getSoData(page: Int) : Observable<SoData>? {
        return mApi.getSoData(page)
    }

}