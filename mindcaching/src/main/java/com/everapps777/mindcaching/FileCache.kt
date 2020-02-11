package com.everapps777.mindcaching

import android.content.Context
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import java.io.File

class FileCache(context: Context) {
    private val cacheDir: File = context.cacheDir

    fun getFile(url : String) : File {
        // file extension
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(url)
        // completeFileName
        val imageName = URLUtil.guessFileName(url, null, fileExtension)
        return File(cacheDir,imageName)
    }

    fun clear(){
        val files = cacheDir.listFiles()
        files?.forEach { it.delete() }
    }
}