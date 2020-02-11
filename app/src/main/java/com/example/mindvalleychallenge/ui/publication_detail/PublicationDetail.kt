package com.example.mindvalleychallenge.ui.publication_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.everapps777.mindcaching.loadImage
import com.example.mindvalleychallenge.R
import com.example.mindvalleychallenge.utils.AppConstants
import kotlinx.android.synthetic.main.activity_publication_detail.*


class PublicationDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_publication_detail)

        val extras = intent.extras
        val id = extras?.getString(AppConstants.ID_PHOTO)
        val url = extras?.getString(AppConstants.ITEM_EXTRA_DETAIL)

        fullScreenImageView.transitionName = id
        url?.let { fullScreenImageView.loadImage(it) }


    }
}
