package com.example.mindvalleychallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everapps777.mindcaching.configureRecycler
import com.everapps777.mindcaching.loadAndSaveToCache
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_recycler_test.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listUrl: ArrayList<String> = ArrayList()
        for (i in 0 until 10) {
            listUrl.add("https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=128\\u0026w=128\\u0026s=622a88097cf6661f84cd8942d851d9a2")
        }
        val adapter = DynamicAdapter(
            entries = listUrl,
            layout = R.layout.item_recycler_test,
            action = fun(_, view, item, _) {
                view.imageCover.loadAndSaveToCache(item)
            }
        )
        recyclerTest.setHasFixedSize(true)
        recyclerTest.configureRecycler(true)
        recyclerTest.adapter = adapter
    }
}
