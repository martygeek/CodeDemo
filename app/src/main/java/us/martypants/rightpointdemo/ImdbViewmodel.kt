package us.martypants.rightpointdemo

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import us.martypants.rightpointdemo.databinding.ActivityMainBinding
import us.martypants.rightpointdemo.models.Search
import us.martypants.rightpointdemo.repository.ImdbRepository
import javax.inject.Inject


/**
 * Created by Martin Rehder on 2019-11-02.
 */
class ImdbViewmodel (app: App) : AndroidViewModel(app) {

    init {
        app.userComponent?.inject(this)
    }

    private val context = app.applicationContext


    @Inject
    lateinit var repo: ImdbRepository

    private lateinit var binding: ActivityMainBinding

    var imdbSearchList = MutableLiveData<Pair<MutableList<Search>?, Error?>>()
    var currentPage = 1

    fun getImdbData(searchString: String, searchType: String?) {

        if (searchType != null) {
            repo.getImdbTypeData(searchString, searchType, currentPage) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    imdbSearchList.value = it
                }
            }
        } else {
            repo.getImdbData(searchString, currentPage) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    imdbSearchList.value = it
                }
            }
        }

    }

    fun setActivityBinding(newbinding: ActivityMainBinding) {
        this.binding = newbinding
    }

    fun search() {
        val searchText = binding.editText.text.toString()
        if (searchText.isEmpty()) {
            binding.editText.error = context.getString(R.string.reqd)
        } else {
            if (isConnectedToNetwork(context)) {
                binding.editText.error = null
                getImdbData(searchText, searchType())
                closeKeyboard(binding.search.context as Activity)

            } else {
                Toast.makeText( context, context.getString(R.string.offline), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearText() {
        binding.editText.text = null
    }

    private fun searchType() : String? {
        return when (binding.types.checkedRadioButtonId) {
            R.id.movies -> "movie"
            R.id.series -> "series"
            else -> null
        }
    }

}