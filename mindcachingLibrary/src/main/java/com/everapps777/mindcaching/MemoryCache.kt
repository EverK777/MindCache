package com.everapps777.mindcaching

import android.graphics.Bitmap
import java.util.*
import kotlin.collections.LinkedHashMap

class MemoryCache {

    companion object{
        var limitMemorySize = 1000000L
    }

    private val cacheFile  =
        Collections.synchronizedMap(LinkedHashMap<String, Bitmap>(10, 1.5f, true))


    private var size = 0L

    init {
        initMemorySize(Runtime.getRuntime().maxMemory() / 4)
    }

    private fun initMemorySize(newLimit : Long){
        limitMemorySize = newLimit
    }

    fun getBitmap (id : String): Bitmap?{
        try {
            if(!(cacheFile.containsKey(id))) return null
            return cacheFile[id]
        }catch (e : NullPointerException){
            e.printStackTrace()
            return null
        }
    }

    fun putBitmapIntoCache(id: String, bitmap: Bitmap){
        try{
            if(cacheFile.containsKey(id)){
                size -= getSizeInBytes(bitmap)
            }
            cacheFile[id]  = bitmap
            size += getSizeInBytes(bitmap)
            checkSize()
        }catch (e: Throwable){
            e.printStackTrace()
        }
    }



    private fun checkSize(){
        if(size > limitMemorySize){
          val iterator :  MutableIterator<Map.Entry<String, Bitmap>>  =  cacheFile.entries.iterator()
            while (iterator.hasNext()){
                val entry : Map.Entry<String, Bitmap> = iterator.next()
                size -=getSizeInBytes(entry.value)
                iterator.remove()
                if(size <= limitMemorySize){
                    break
                }
            }
        }
    }

    fun clear(){
        try {
            cacheFile.clear()
            size = 0
        }catch (e : java.lang.NullPointerException){
            e.printStackTrace()
        }
    }


    private fun getSizeInBytes(bitmap: Bitmap?): Long {
        if(bitmap == null){
            return 0
        }
        return bitmap.rowBytes * bitmap.height.toLong()
    }
}