package us.martypants.rightpointdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import us.martypants.rightpointdemo.databinding.ActivityMainBinding
import us.martypants.rightpointdemo.databinding.ItemImdbBinding
import us.martypants.rightpointdemo.models.Search
import us.martypants.rightpointdemo.view.BindingAdapter


class MainActivity : RxAppCompatActivity() {

    lateinit var viewModel: ImdbViewmodel
    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (application as App).userComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory(application as App))
                .get(ImdbViewmodel::class.java)


        val search = "terminator"
        viewModel.getImdbData(search)

        viewModel.imdbSearchList
            .observe(this, Observer {
                setupRecycler()
                mBinding.progressCircular.visibility = View.GONE
            })
    }

    fun setupRecycler() {
            Log.d("MJR", viewModel.imdbSearchList.value?.first.toString())
        mBinding.progressCircular.visibility = View.VISIBLE
        val titles: List<Search>? = viewModel.imdbSearchList.value?.first
        mBinding.recycler.setHasFixedSize(true)
        val layoutMgr = LinearLayoutManager(this)
        mBinding.recycler.layoutManager = layoutMgr

        if (titles != null) {
            if (titles.isNotEmpty()) {
               val adapter = object: BindingAdapter<ItemImdbBinding>(R.layout.item_imdb) {
                   override fun getItemCount(): Int {
                       return titles.size
                   }

                   override fun updateBinding(binding: ItemImdbBinding?, position: Int) {
                       val item = titles[position]
                       binding?.model = item
                       Picasso.with(this@MainActivity)
                           .load(item.poster)
                           .into(binding?.image)
                   }

               }
                mBinding.recycler.adapter = adapter

            }
        }
    }
}
