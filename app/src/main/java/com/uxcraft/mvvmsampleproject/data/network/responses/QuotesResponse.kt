package com.uxcraft.mvvmsampleproject.data.network.responses

import com.uxcraft.mvvmsampleproject.data.db.entities.Quote

data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)
