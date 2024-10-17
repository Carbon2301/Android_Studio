package com.example.listexamples

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter(val thumbs: List<Int>, val imageSize: Int): BaseAdapter() {
    override fun getCount(): Int = thumbs.size

    override fun getItem(p0: Int): Any = thumbs[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val imageView = ImageView(p2?.context)
        imageView.layoutParams = ViewGroup.LayoutParams(imageSize, imageSize)
        imageView.setImageResource(thumbs[p0])
        return imageView
    }
}