package com.lego.myadvanceapplication.ui.news.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.utils.GPUImageFilterTools
import com.lego.myadvanceapplication.ui.utils.GPUImageFilterTools.FilterAdjuster
import com.lego.myadvanceapplication.ui.utils.loadImage
import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import kotlinx.android.synthetic.main.activity_reddit_details.*


class RedditDetailsActivity : AppCompatActivity() {

    private val noImageFilter = GPUImageFilter()
    private var currentImageFilter = noImageFilter
    private var filterAdjuster: FilterAdjuster? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_details)
        intent.getStringExtra(EXTRA_IMG_DETAILS)?.let { imgUrl ->

            photoView.loadImage(imgUrl)
            photoView.drawable
            modifySwitch.setOnCheckedChangeListener { _: View, isActive: Boolean ->
                if (isActive) {
                    ivGpu.visibility = View.VISIBLE
                    photoView.visibility = View.GONE
                    ivGpu.filter = noImageFilter
                    ivGpu.setRenderMode(GPUImageView.RENDERMODE_CONTINUOUSLY)
                    ivGpu.setImage(photoView.drawToBitmap())
                } else {
                    photoView.visibility = View.VISIBLE
                    ivGpu.visibility = View.GONE
                }
            }


            ivGpu.setOnClickListener {
                GPUImageFilterTools.showDialog(this) { filter ->
                    if (currentImageFilter.javaClass != filter.javaClass) {
                        currentImageFilter = filter
                        ivGpu.filter = currentImageFilter
                        filterAdjuster = FilterAdjuster(currentImageFilter)
                    }
                }
            }

            // Later when image should be saved saved:
//        gpuImage.saveToPictures("GPUImage", "ImageWithFilter.jpg", null)
        } ?: finish()
    }

    companion object {
        const val EXTRA_IMG_DETAILS = "EXTRA_IMG_DETAILS"
    }

}