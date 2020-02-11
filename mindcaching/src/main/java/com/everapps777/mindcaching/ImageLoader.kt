package com.everapps777.mindcaching

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.everapps777.mindcaching.FileReader.fileStatus
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object ImageLoader {


    private val memoryCache = MemoryCache()
    private lateinit var fileCache: FileCache
    private val imageViews = Collections.synchronizedMap(WeakHashMap<ImageView, String>())



    suspend fun displayImage(
        url: String,
        imageView: ImageView,
        placeHolder: Int? = null
    ) {

        imageViews[imageView] = url
        if(!fileStatus.containsKey(imageView))fileStatus[imageView] = true

        val bitmap = suspend { memoryCache.getBitmap(url) }
        val newBitmap = bitmap()
        if (newBitmap != null ) {
            GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageBitmap(newBitmap)
            }
        } else {
            if (placeHolder != null) imageView.setImageResource(placeHolder)
            getBitmapToDisplay(
                url,
                imageView,
                placeHolder
            )
        }
    }

    private suspend fun getBitmap(url: String, imageView: ImageView): Bitmap? {
        val context = imageView.context
        fileCache = FileCache(context)
        val file = fileCache.getFile(url)
        var bitmap = decodeFile(file, context)
        if (bitmap != null) {
            return bitmap
        }
        try {
            val imageUrl = URL(url)
            val conn: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
            conn.useCaches = true
            conn.connectTimeout = 30000
            conn.readTimeout = 30000
            conn.instanceFollowRedirects = true
            val inputStream = conn.inputStream
            val outputStream = FileOutputStream(file)


            val status = FileReader.fileStream(inputStream, outputStream,imageView)
            if(!status) return null

            bitmap = decodeFile(file, context)

            return bitmap
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return null
    }

    private suspend fun decodeFile(file: File, context: Context): Bitmap? {
        try {
            val bitmapOptions = BitmapFactory.Options()
            bitmapOptions.inJustDecodeBounds = false
            val decodeBitmap = suspend { BitmapFactory.decodeStream(FileInputStream(file), null, bitmapOptions) }
            decodeBitmap()
            val heightTemp = bitmapOptions.outHeight
            val widthTemp = bitmapOptions.outWidth
            val screenDimens = context.getScreenDimens()
            var scale = 1

            if (heightTemp > screenDimens.first || widthTemp  > screenDimens.second) {
                scale = 3
            }


            val bitmapFactoryOption = BitmapFactory.Options()
            bitmapFactoryOption.inSampleSize = scale
            val decodeStream = suspend {
                BitmapFactory.decodeStream(
                    FileInputStream(file),
                    null,
                    bitmapFactoryOption
                )
            }
            return decodeStream()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private suspend fun getBitmapToDisplay(
        url: String,
        imageView: ImageView,
        placeHolder: Int?
    ) {
        if (imageViewReused(
                url,
                imageView
            )
        ) return

        val bitmapJob = suspend {
            getBitmap(
                url,imageView
            )
        }

        val bitmap = bitmapJob()

        if (bitmap != null) {
            memoryCache.putBitmapIntoCache(url, bitmap)
        }

        if (imageViewReused(
                url,
                imageView
            )
        ) return

        if (bitmap != null) {
            GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
            }


        } else if (placeHolder != null) {
            imageView.setImageResource(placeHolder)
        }

    }

    private fun imageViewReused(url: String, imageView: ImageView): Boolean {
        val tag = imageViews[imageView]
        if (tag == null || tag != url) {
            return true
        }
        return false
    }



}