package us.martypants.rightpointdemo

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.trello.rxlifecycle.components.support.RxAppCompatActivity




class MainActivity : RxAppCompatActivity() {

    lateinit var viewModel: ImdbViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).userComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory(application as App))
                .get(ImdbViewmodel::class.java)


        val search = "terminator"
        viewModel.getImdbData(search)

        viewModel.imdbSearchList
            .observe(this, Observer {
                setupRecycler()
            })
    }

    fun setupRecycler() {
            Log.d("MJR", viewModel.imdbSearchList.value?.first.toString())

    }
}
