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
import us.martypants.rightpointdemo.view.EndlessRecyclerViewScrollListener

const val PAGE = 10

class MainActivity : RxAppCompatActivity() {

    private lateinit var viewModel: ImdbViewmodel
    private lateinit var mBinding: ActivityMainBinding

    var titles: MutableList<Search>? = null
    val layoutMgr = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.editText.imeOptions = EditorInfo.IME_ACTION_DONE

        (application as App).userComponent?.inject(this)
        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory(application as App))
                .get(ImdbViewmodel::class.java)
        viewModel.setActivityBinding(mBinding)
        mBinding.viewmodel = viewModel
        mBinding.recycler.setHasFixedSize(true)
        mBinding.recycler.layoutManager = layoutMgr
        mBinding.recycler.addOnScrollListener(scrollListener)
        mBinding.editText.afterTextChanged { viewModel.currentPage = 1 }
        mBinding.types.setOnCheckedChangeListener { _, _ -> viewModel.currentPage = 1 }

        viewModel.imdbSearchList
            .observe(this, Observer {
                setupRecycler()
                mBinding.progressCircular.visibility = View.GONE
            })

    }

    private val scrollListener = object : EndlessRecyclerViewScrollListener(layoutMgr) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            //  Triggered only when new data needs to be appended to the list
            viewModel.currentPage++
            viewModel.search()
        }

    }

    private fun setupRecycler() {
        Log.d("MJR", viewModel.imdbSearchList.value?.first.toString())
        mBinding.progressCircular.visibility = View.VISIBLE
        if (viewModel.currentPage > 1) {
            titles?.addAll(viewModel.imdbSearchList.value?.first!!)
            mBinding.recycler.adapter?.notifyItemRangeInserted(
                (viewModel.currentPage * PAGE) - PAGE,
                PAGE
            )
        } else {
            titles = viewModel.imdbSearchList.value?.first
            scrollListener.resetState()

            val adapter: RecyclerView.Adapter<BindingViewHolder<ItemImdbBinding>>?
            if (titles != null) {
                mBinding.recycler.visibility = View.VISIBLE

                adapter = object : BindingAdapter<ItemImdbBinding>(R.layout.item_imdb) {
                    override fun getItemCount(): Int {
                        return titles?.size!!
                    }

                    override fun updateBinding(binding: ItemImdbBinding?, position: Int) {
                        val item = titles?.get(position)
                        binding?.assetname = item?.title
                        binding?.image?.tag = item?.imdbID
                        binding?.handler = clickListener
                        Picasso.with(this@MainActivity)
                            .load(item?.poster)
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

    private val clickListener = View.OnClickListener { v ->
        val id = v.tag as String
        loadFragment(id)
    }

    override fun onBackPressed() {
        mBinding.recycler.visibility = View.VISIBLE
        mBinding.container.visibility = View.GONE
        super.onBackPressed()
    }

    private fun loadFragment(id: String) {

        val item = titles?.firstOrNull { it.imdbID == id }
        val frag = DetailFragment.newInstance(item)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, frag)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        mBinding.recycler.visibility = View.GONE
        mBinding.container.visibility = View.VISIBLE
    }
}
