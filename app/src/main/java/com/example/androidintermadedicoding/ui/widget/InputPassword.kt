package com.example.androidintermadedicoding.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener


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

        addTextChangedListener(onTextChanged = { s,_,_,_ ->
            if (s.toString().isNotEmpty()) {
                if (s.toString().length < 6) {
                    isLengthCharacterMin = true
                    errorMessage = "Password anda kurang"
                }else{
                    isLengthCharacterMin = false
                }

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