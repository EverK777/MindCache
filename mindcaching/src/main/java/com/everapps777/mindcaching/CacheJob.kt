package com.everapps777.mindcaching

import kotlinx.coroutines.Job

class CacheJob(private val job: Job) {
    fun cancelLoading(){
        job.cancel()
    }
}