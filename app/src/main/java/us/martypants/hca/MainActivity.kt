package us.martypants.hca

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import us.martypants.hca.databinding.ActivityMainBinding
import us.martypants.hca.databinding.SoItemBinding
import us.martypants.hca.models.Item
import us.martypants.hca.models.Search
import us.martypants.hca.view.BindingAdapter
import us.martypants.hca.view.BindingViewHolder
import us.martypants.hca.view.EndlessRecyclerViewScrollListener

const val PAGE = 10

class MainActivity : RxAppCompatActivity() {

    private lateinit var viewModel: SoViewmodel
    private lateinit var mBinding: ActivityMainBinding
    var adapter: RecyclerView.Adapter<BindingViewHolder<SoItemBinding>>? = null

    var titles: MutableList<Search>? = null
    val layoutMgr = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as App).userComponent?.inject(this)
        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory(application as App))
                .get(SoViewmodel::class.java)
        mBinding.viewmodel = viewModel
        mBinding.recycler.setHasFixedSize(true)
        mBinding.recycler.layoutManager = layoutMgr
        mBinding.recycler.addOnScrollListener(scrollListener)


        viewModel.getSoData()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.soSearch.observe(this, Observer {
            if (it.first != null && !it.first.isEmpty()) {
                setupRecycler(it.first)
            }
            if (it.second != null) {
                errorDialog(this, getString(R.string.no_results))
                mBinding.recycler.visibility = View.GONE
            }
        })
    }


    private val scrollListener = object : EndlessRecyclerViewScrollListener(layoutMgr) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            //  Triggered only when new data needs to be appended to the list
            viewModel.currentPage++
            // TODO: merge pageed data with existing list
//            viewModel.getSoData()
        }

    }

    private fun setupRecycler(list: List<Item>) {
        mBinding.recycler.visibility = View.VISIBLE

        adapter = object : BindingAdapter<SoItemBinding>(R.layout.so_item) {
            override fun getItemCount(): Int {
                return list.size
            }

            override fun updateBinding(binding: SoItemBinding?, position: Int) {
                val item = list.get(position)
                binding?.title?.text = item.title
                binding?.count?.text = item.answer_count.toString()
                item.accepted_answer_id?.let {
                    binding?.answer?.text = it.toString()
                }

            }
        }

        mBinding.recycler.adapter = adapter
    }

}
