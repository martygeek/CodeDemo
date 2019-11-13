package us.martypants.rightpointdemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import us.martypants.rightpointdemo.databinding.DetailFragmentBinding
import us.martypants.rightpointdemo.models.Search


private const val ID = "imdbId"

class DetailFragment : Fragment() {

    private var item: Search? = null
    lateinit var binding: DetailFragmentBinding
    lateinit var viewModel: ImdbViewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getSerializable(ID) as Search?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.applicationContext as App).userComponent?.inject(this)

        viewModel = activity?.let { ViewModelProviders.of(it).get(ImdbViewmodel::class.java) }!!

        binding = DataBindingUtil.inflate<DetailFragmentBinding>(
            inflater,
            R.layout.detail_fragment,
            container,
            false
        )
        loadData()
        return binding.root
    }


    fun loadData() {
        Log.d("MJR", "Loading + " + item?.imdbID + " " + item?.title + " " + item?.poster)
        binding.model = item
        Picasso.with(activity)
            .load(item?.poster)
            .error(R.drawable.notfound)
            .into(binding.image)

    }

    companion object {
        @JvmStatic
        fun newInstance(item: Search?) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ID, item)
                }
            }
    }
}
