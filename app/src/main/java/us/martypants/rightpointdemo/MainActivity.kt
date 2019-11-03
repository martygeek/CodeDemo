package us.martypants.rightpointdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import us.martypants.rightpointdemo.databinding.ActivityMainBinding
import us.martypants.rightpointdemo.databinding.ItemImdbBinding
import us.martypants.rightpointdemo.models.Search
import us.martypants.rightpointdemo.view.BindingAdapter
import us.martypants.rightpointdemo.view.BindingViewHolder


class MainActivity : RxAppCompatActivity() {

    private lateinit var viewModel: ImdbViewmodel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.handler = clickListener
        mBinding.editText.imeOptions = EditorInfo.IME_ACTION_DONE;

        (application as App).userComponent?.inject(this)
        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory(application as App))
                .get(ImdbViewmodel::class.java)
        mBinding.recycler.setHasFixedSize(true)
        val layoutMgr = GridLayoutManager(this, 2)
        mBinding.recycler.layoutManager = layoutMgr

        viewModel.imdbSearchList
            .observe(this, Observer {
                setupRecycler()
                mBinding.progressCircular.visibility = View.GONE
            })

    }

    private val clickListener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.search -> {
                val searchText = mBinding.editText.text.toString()
                if (searchText.isEmpty()) {
                    mBinding.editText.error = "Required"
                } else {
                    if (isConnectedToNetwork(this)) {
                        mBinding.editText.error = null
                        viewModel.getImdbData(searchText, viewModel.searchType(mBinding))
                        closeKeyboard(this)

                    } else {
                        errorDialog( this,getString(R.string.offline))
                    }
                }
            }
        }
    }


    private fun setupRecycler() {
        Log.d("MJR", viewModel.imdbSearchList.value?.first.toString())
        mBinding.progressCircular.visibility = View.VISIBLE
        val titles: List<Search>? = viewModel.imdbSearchList.value?.first
        val adapter: RecyclerView.Adapter<BindingViewHolder<ItemImdbBinding>>?
        if (titles != null) {
            mBinding.recycler.visibility = View.VISIBLE

            adapter = object: BindingAdapter<ItemImdbBinding>(R.layout.item_imdb) {
                   override fun getItemCount(): Int {
                       return titles.size
                   }

                   override fun updateBinding(binding: ItemImdbBinding?, position: Int) {
                       val item = titles[position]
                       binding?.model = item
                       Picasso.with(this@MainActivity)
                           .load(item.poster)
                           .error(R.drawable.notfound)
                           .into(binding?.image)
                   }
               }
                mBinding.recycler.adapter = adapter

        } else {
            errorDialog(this, getString(R.string.no_results))
            mBinding.recycler.visibility = View.GONE
        }
    }

}
