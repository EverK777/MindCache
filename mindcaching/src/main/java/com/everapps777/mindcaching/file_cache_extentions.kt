package com.everapps777.mindcaching

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.coroutines.*
import java.io.File


fun ImageView.loadImage(url: String, placeholder: Int? = null) {

    GlobalScope.launch(Dispatchers.IO) {
        ImageLoader.displayImage(
            url,
            this@loadImage,
            placeholder
        )
    }
}

fun ImageView.cancelLoading() {
    FileLoader.cancelFileDownload(this)
}

suspend fun View.downloadAndGetFile(url: String): File? {
    return FileLoader.getFile(this@downloadAndGetFile, url)
}


fun Context.getScreenDimens(): Pair<Int, Int> {
    val display = (this as Activity).windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val maxWidth = size.x + 200
    val maxHeight = size.y + 200

    return Pair(maxHeight, maxWidth)
}