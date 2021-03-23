package com.icheero.app.activity.feature

import android.os.Bundle
import com.icheero.app.R
import com.icheero.sdk.base.BaseActivity
import java.util.*

class KotlinActivity : BaseActivity() {

    private var count: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val language: String? = null
        if (language != null) {
            print(language.toUpperCase(Locale.getDefault()))
        }

        val answer: String = if (count == 1) {
            "s1"
        } else {
            "s2"
        }
        print(generateAnswerString(2))
        var s = stringLengthFunc("hello world")
        stringMapper(generateAnswerString(2), stringLengthFunc)
    }

    private fun generateAnswerString(countThreshold: Int): String = "1$countThreshold"

    val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    private fun stringMapper(str: String, mapper: (String) -> Int) : Int {
        return mapper(str)
    }
}