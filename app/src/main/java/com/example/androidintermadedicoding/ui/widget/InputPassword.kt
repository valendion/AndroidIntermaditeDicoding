package com.example.androidintermadedicoding.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText


class InputPassword:AppCompatEditText {
    private  var isLengthCharacterMin : Boolean = false
    private  var errorMessage: String? = null
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {


        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    if (s.toString().length < 6) {
                        isLengthCharacterMin = true
                        errorMessage = "Password anda kurang"
                    }else{
                        isLengthCharacterMin = false
                    }

                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        hint = "Masukkan Password Anda"

        textAlignment = View.TEXT_ALIGNMENT_TEXT_START

        error = if (isLengthCharacterMin){
            errorMessage
        }else{
            null
        }

    }





}