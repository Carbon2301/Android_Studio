package com.example.listexamples

import android.os.Bundle
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.TypedValueCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImageGridActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_grid)

        val thumbs = mutableListOf<Int>()
        repeat(28) {
            thumbs.add(resources.getIdentifier(
                "thumb${it + 1}", "drawable", packageName
            ))
        }

        val space = TypedValueCompat.dpToPx(8f, resources.displayMetrics)
        val imageSize = (resources.displayMetrics.widthPixels - space) / 3

        val adapter = ImageAdapter(thumbs, imageSize.toInt())
        val gridImages = findViewById<GridView>(R.id.grid_images)
        gridImages.adapter = adapter
    }
}