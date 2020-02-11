package com.everapps777.mindcaching

import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object FileLoader {

    private lateinit var fileCache: FileCache
    private val views = Collections.synchronizedMap(WeakHashMap<View, String>())

   suspend fun getFile(view: View,url : String):File?{
       views[view] = url
       if(!FileReader.fileStatus.containsKey(view)) FileReader.fileStatus[view] = true
       fileCache = FileCache(view.context)
       val fileCache: File?

       fileCache = FileLoader.fileCache.getFile(url)

       if(fileCache.exists()) return fileCache

       return downloadAndGetFile(view, url,fileCache)
   }

    private suspend fun downloadAndGetFile(view: View, url: String,file: File?) : File? {
        if(file != null){
            try {
                val imageUrl = URL(url)
                val conn: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
                conn.useCaches = true
                conn.connectTimeout = 30000
                conn.readTimeout = 30000
                conn.instanceFollowRedirects = true
                val inputStream = conn.inputStream
                val outputStream = FileOutputStream(file)

                val status = suspend { FileReader.fileStream(inputStream, outputStream,view) }

                if(!status()) return null

                return file

            }catch (e : Exception){
                e.printStackTrace()
                return null
            }
        }
        return null
    }


    fun cancelFileDownload(view: View){
        FileReader.fileStatus[view] = false
    }

    fun clearCache() {
        if (::fileCache.isInitialized) fileCache.clear()
    }
}