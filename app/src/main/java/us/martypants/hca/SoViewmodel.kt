package us.martypants.hca

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import us.martypants.hca.models.Item
import us.martypants.hca.repository.SoRepository
import javax.inject.Inject

class SoViewmodel (app: App) : AndroidViewModel(app) {

    init {
        app.userComponent?.inject(this)
    }

    private val context = app.applicationContext


    @Inject
    lateinit var repo: SoRepository
    var currentPage = 1

    var soSearch = MutableLiveData<Pair<List<Item>, Error?>>()



    fun getSoData() {

        repo.getSoData(currentPage) {
            val filteredList = it.first.filter { item ->
                item.is_answered && item.accepted_answer_id != 0 && item.answer_count > 1
            }
            soSearch.postValue(Pair(filteredList, null))

        }
    }


}