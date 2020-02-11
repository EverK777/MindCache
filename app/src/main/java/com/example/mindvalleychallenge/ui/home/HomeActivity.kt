package com.example.mindvalleychallenge.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.everapps777.mindcaching.cancelLoading
import com.everapps777.mindcaching.loadImage
import com.example.mindvalleychallenge.R
import com.example.mindvalleychallenge.extentions.configureStaggedRecycler
import com.example.mindvalleychallenge.models.Publication
import com.example.mindvalleychallenge.ui.publication_detail.PublicationDetail
import com.example.mindvalleychallenge.utils.AppConstants
import com.example.mindvalleychallenge.utils.DynamicAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_recycler_test.view.*
import org.koin.android.viewmodel.ext.android.viewModel



class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.requestPublications()
        viewModel.getPublications().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                loadingView.visibility = View.GONE
                initRecyclerView(it)
            }
        })


    }

    private fun initRecyclerView(publications: ArrayList<Publication>) {
        val adapter = DynamicAdapter(
            layout = R.layout.item_recycler_test,
            entries = publications,
            action = fun(_, view, item, _) {
                val bg: Int = Color.parseColor(item.color)
                view.imageCover.loadImage(item.urls.regular)
                view.likeText.text = item.likes.toString()
                view.backgroundView.setBackgroundColor(bg)
                //using library
                view.profile_image.loadImage(item.user.profile_image.large)
                ViewCompat.setTransitionName(view.imageCover, item.id)
                view.cardViewContainer.setOnClickListener {
                    goToPhotoPreview(item.id, item.urls.regular, view.imageCover)
                }
                view.setOnLongClickListener {
                    view.imageCover.cancelLoading()
                    return@setOnLongClickListener false
                }
            }
        )
        recyclerPublications.configureStaggedRecycler(2)
        recyclerPublications.adapter = adapter
    }

    private fun goToPhotoPreview(idPublication: String, url:String, imageView: ImageView){
        val intent = Intent(this, PublicationDetail::class.java)
        intent.putExtra(AppConstants.ITEM_EXTRA_DETAIL, url)
        intent.putExtra(AppConstants.ID_PHOTO, idPublication)
        intent.putExtra(idPublication, ViewCompat.getTransitionName(imageView))

        val options = ViewCompat.getTransitionName(imageView)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                it
            )
        }
        startActivity(intent, options?.toBundle())
    }


}
