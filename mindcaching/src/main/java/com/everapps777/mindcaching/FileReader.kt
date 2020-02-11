package com.everapps777.mindcaching

import android.view.View
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*

object FileReader {


    val fileStatus = Collections.synchronizedMap(WeakHashMap<View, Boolean>())

    fun fileStream(inputStream: InputStream, outputSteam: OutputStream, view: View): Boolean {
        val bufferSize = 1024

        return try {
            val byteArray = ByteArray(bufferSize)
            while (fileStatus[view]!!) {
                val count = inputStream.read(byteArray, 0, bufferSize)
                if (count == -1) break
                outputSteam.write(byteArray, 0, count)
            }
            fileStatus[view]!!
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        finally {
            inputStream.close()
            outputSteam.close()
        }
    }

}