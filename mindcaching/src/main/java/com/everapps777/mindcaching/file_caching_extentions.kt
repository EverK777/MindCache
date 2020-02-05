package com.everapps777.mindcaching

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL


fun FragmentActivity.saveFileToCache(url: String): CacheJob {
    val job: Job = GlobalScope.launch(Dispatchers.IO) {

    }
    return CacheJob(job)
}

fun ImageView.loadAndSaveToCache(url: String): CacheJob {
    val job: Job = GlobalScope.launch {
        var inputStream: InputStream? = null
        val imageUrl = URL(url)
        var bitmap: Bitmap ?= null
        try {
            //get image form cache
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(url) // extension
            val imageName = URLUtil.guessFileName(url, null, fileExtension) // image name with extention
            val fileName = "${this@loadAndSaveToCache.context.cacheDir}/$imageName"
            val file = File(fileName)
           try {
               bitmap = BitmapFactory.decodeStream(FileInputStream(file))
               Log.d("CAHE_STATE","SAVED")
           }catch (e:java.lang.Exception){}
            // if the image is not in cache
            if (bitmap == null) {
                Log.d("CAHE_STATE","NOTSAVED")
                inputStream = imageUrl.content as InputStream
                val bitmapDecoded = BitmapFactory.decodeStream(inputStream)

                bitmap =  Bitmap.createScaledBitmap(bitmapDecoded, bitmapDecoded.width/2, bitmapDecoded.height/2, false)
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
            }
            GlobalScope.launch(Dispatchers.Main) {
                // DISPLAY INTO IMAGE VIEW
                this@loadAndSaveToCache.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
           Log.d("Error_Image_Library",e.toString())
        } finally {
            inputStream?.close()
        }

    }
    return CacheJob(job)
}







