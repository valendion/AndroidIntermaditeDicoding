package com.example.androidintermadedicoding.utils

import java.util.regex.Pattern

object Formatting {
    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9+.\\_%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isFormatEmail(input: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(input).matches()
    }


}