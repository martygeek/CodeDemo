package us.martypants.rightpointdemo.repository

import android.util.Log
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.martypants.rightpointdemo.App
import us.martypants.rightpointdemo.managers.DataManager
import us.martypants.rightpointdemo.models.Search
import javax.inject.Inject

/**
 * Created by Martin Rehder on 2019-11-02.
 */
class ImdbRepository(app: App) {

    init {
        app.userComponent?.inject(this)
    }

    @Inject
    lateinit var dataManager: DataManager

    fun getImdbData(searchString: String, page: Int, completion: (result: Pair<MutableList<Search>?, Error?>) -> Unit) {

        dataManager.getImdbData(searchString, page)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                completion(Pair(it.search, null))
            },
                { error ->

                    Log.d(
                        "REPO","Error: " + error.localizedMessage
                    )
                })
    }

    fun getImdbTypeData(searchString: String, searchType: String, page: Int, completion: (result: Pair<MutableList<Search>?, Error?>) -> Unit) {

        dataManager.getSpecifics(searchString, searchType, page)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                completion(Pair(it.search, null))
            },
                { error ->

                    Log.d(
                        "REPO","Error: " + error.localizedMessage
                    )
                })
    }
}