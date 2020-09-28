package us.martypants.hca.repository

import android.util.Log
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.martypants.hca.App
import us.martypants.hca.managers.DataManager
import us.martypants.hca.models.Item
import javax.inject.Inject

class SoRepository(app: App) {

    init {
        app.userComponent?.inject(this)
    }

    @Inject
    lateinit var dataManager: DataManager


    fun getSoData( page: Int, completion: (result: Pair<List<Item>, Error?>) -> Unit) {

        dataManager.getSoData(page)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                println(it.toString())
                completion(Pair(it.items, null))
            },
                { error -> Log.d("App","Error: " + error.localizedMessage) })
    }

}