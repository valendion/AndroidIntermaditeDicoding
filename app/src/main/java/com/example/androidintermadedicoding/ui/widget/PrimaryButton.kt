package com.example.androidintermadedicoding.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.androidintermadedicoding.R


class PrimaryButton: AppCompatButton {
    private lateinit var enableBackground: Drawable
    private lateinit var disableBackground: Drawable

    private var txtColor=0

    constructor(context: Context): super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs){

        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //Background Button
        background = if (isEnabled) enableBackground else disableBackground

        // Mengubah warna text pada button
        setTextColor(txtColor)

        // Mengubah ukuran text pada button
        textSize = 12f

        // Menjadikan object pada button menjadi center
        gravity = Gravity.CENTER
    }

    private fun init(){
        txtColor = ContextCompat.getColor(context, R.color.primaryWhite)

        enableBackground = ContextCompat.getDrawable(context, R.drawable.rounded_corner_enable) as Drawable
        disableBackground = ContextCompat.getDrawable(context, R.drawable.rounded_corner_disable) as Drawable
    }
}