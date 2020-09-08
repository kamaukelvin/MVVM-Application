package com.uxcraft.mvvmsampleproject.ui.home.quotes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxcraft.mvvmsampleproject.R
import com.uxcraft.mvvmsampleproject.data.db.entities.Quote
import com.uxcraft.mvvmsampleproject.ui.home.profile.ProfileViewModelFactory
import com.uxcraft.mvvmsampleproject.utils.Coroutines
import com.uxcraft.mvvmsampleproject.utils.hide
import com.uxcraft.mvvmsampleproject.utils.show
import com.uxcraft.mvvmsampleproject.utils.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory : QuotesViewModelFactory by instance()

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(QuotesViewModel::class.java)
        bindUI()
    }

    private fun bindUI()= Coroutines.main {
        progress_bar.show()
//        fetch the quotes to display from the view model
       viewModel.quotes.await().observe(this, Observer{
           progress_bar.hide()
//           FUNCTION TO DISPLAY THE QUOTES IN RECYCLER VIEW
           initRecyclerView(it.toQuoteItem())
       })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {

//        display our quotes in the recycler view
//        create our adapter first (GroupAdapter since we are using Groupie)

        val groupAdapter = GroupAdapter<ViewHolder>().apply{
            addAll(quoteItem)
        }
//        set our adapter to the recycler view
        recyclerview.apply{
          layoutManager= LinearLayoutManager(context)
            setHasFixedSize(true)
//            set groupie adapter to the recycler view adapter
            adapter= groupAdapter
        }


    }

    /*
our viewModel.quotes.await contains live data that can be observed, but note that the
observer is a list<Quote> and we need a list of QuoteItem hence we have to convert and then
map them to get a single quote item
*/
    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
    return this.map {
        QuoteItem(it)
    }
}


}