package com.example.mindvalleychallenge.extentions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun androidx.recyclerview.widget.RecyclerView.configureRecycler(isVertical: Boolean = true): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context,
        if (isVertical) androidx.recyclerview.widget.RecyclerView.VERTICAL else androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false)
    return this
}

fun RecyclerView.configureStaggedRecycler(numColumns:Int):  androidx.recyclerview.widget.RecyclerView{
     layoutManager = StaggeredGridLayoutManager(numColumns, StaggeredGridLayoutManager.VERTICAL)
    return this
}