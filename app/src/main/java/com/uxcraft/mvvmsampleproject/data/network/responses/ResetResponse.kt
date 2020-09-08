package com.uxcraft.mvvmsampleproject.data.network.responses

data class ResetResponse (
    var success: Boolean?,
    val message: String?,
    val errors : List<String>
)