package us.martypants.rightpointdemo

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import us.martypants.rightpointdemo.databinding.ActivityMainBinding
import us.martypants.rightpointdemo.models.Search
import us.martypants.rightpointdemo.repository.ImdbRepository
import javax.inject.Inject


/**
 * Created by Martin Rehder on 2019-11-02.
 */
public class ImdbViewmodel (app: App) : AndroidViewModel(app) {

    init {
        app.userComponent?.inject(this)
    }


    @Inject
    lateinit var repo: ImdbRepository

    var imdbSearchList = MutableLiveData<Pair<List<Search>?, Error?>>()

    fun getImdbData(searchString: String, searchType: String?) {

        if (searchType != null) {
            repo.getImdbTypeData(searchString, searchType) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    imdbSearchList.value = it
                }
            }
        } else {
            repo.getImdbData(searchString) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    imdbSearchList.value = it
                }
            }
        }

    }


    fun searchType(binding: ActivityMainBinding) : String? {
        val typeOfSearch = binding.types.checkedRadioButtonId
        return when (typeOfSearch) {
            R.id.movies -> "movie"
            R.id.series -> "series"
            else -> null
        }
    }

}