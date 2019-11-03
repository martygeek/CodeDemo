package us.martypants.rightpointdemo

import android.util.Log
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.martypants.rightpointdemo.managers.DataManager
import us.martypants.rightpointdemo.models.Search
import javax.inject.Inject

/**
 * Created by Martin Rehder on 2019-11-02.
 */
class ImdbRepository(app: App) {

    init {
        app.userComponent.inject(this)
    }

    @Inject
    lateinit var dataManager: DataManager

    fun getImdbData(searchString: String, completion: (result: Pair<List<Search>?, Error?>) -> Unit) {

        dataManager.getImdbData(searchString)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                completion(Pair(it.search, null))
            },
                { error ->

                    Log.d(
                        "MJR","Error: " + error.localizedMessage
                    )
                })
    }
}