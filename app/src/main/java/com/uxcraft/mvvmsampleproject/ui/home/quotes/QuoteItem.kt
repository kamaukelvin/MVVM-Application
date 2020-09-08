package com.uxcraft.mvvmsampleproject.ui.home.quotes

import com.uxcraft.mvvmsampleproject.R
import com.uxcraft.mvvmsampleproject.data.db.entities.Quote
import com.uxcraft.mvvmsampleproject.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

/*
when working with groupie library we have to create an Item class for our entities to be
displayed in the recycler view
*/

//BindableItem need s to be inherited for this Item class (as per the rules of groupie library)
class QuoteItem(
    private val quote: Quote

):BindableItem<ItemQuoteBinding> (){

// this function sets the quote to our binding instance
    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
     viewBinding.setQuote(quote)
    }

//    this function returns the layout
    override fun getLayout() = R.layout.item_quote
}