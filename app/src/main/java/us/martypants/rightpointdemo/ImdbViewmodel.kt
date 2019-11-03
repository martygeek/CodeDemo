package us.martypants.rightpointdemo

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import us.martypants.rightpointdemo.models.Search
import javax.inject.Inject


/**
 * Created by Martin Rehder on 2019-11-02.
 */
public class ImdbViewmodel (app: App) : AndroidViewModel(app) {

    init {
        app.userComponent.inject(this)
    }


    @Inject
    lateinit var repo: ImdbRepository

    var imdbSearchList = MutableLiveData<Pair<List<Search>?, Error?>>()

    fun getImdbData(searchString: String) {

        repo.getImdbData(searchString) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                imdbSearchList.value = it
            }
        }

    }
}