package edu.bu.androiddev.model

import androidx.compose.runtime.Composable

interface Node {
    var id: Long

    fun generateJsonMap():MutableMap<String, *>

    @Composable
    open fun render(index:Int):Unit

    fun getDbId():Long

}