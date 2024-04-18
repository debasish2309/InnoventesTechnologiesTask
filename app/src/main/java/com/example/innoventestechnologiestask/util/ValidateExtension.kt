package com.example.innoventestechnologiestask.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import java.util.regex.Pattern


fun String?.isValidPan(): Boolean {
    val regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
    val p = Pattern.compile(regex)
    if (this == null) {
        return false
    }
    val m = p.matcher(this)
    return m.matches()
}

fun EditText.afterTextChanged(afterTextChange: (String) -> Unit):TextWatcher {
    val watcher = object:TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            afterTextChange.invoke(p0.toString())
        }
    }
    this.addTextChangedListener(watcher)
    return watcher
}

fun View.drawable(resourcePath: Int) {
    this.background = ContextCompat.getDrawable(this.context,resourcePath)
}

