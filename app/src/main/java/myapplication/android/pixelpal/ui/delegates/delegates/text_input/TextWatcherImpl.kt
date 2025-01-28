package myapplication.android.pixelpal.ui.delegates.delegates.text_input

import android.text.Editable
import android.text.TextWatcher
import android.util.Log

class TextWatcherImpl(private val logVal: String, private val onTextChanged: (value: String) -> Unit) :
    TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Log.i("Before text changed: $logVal", "$p0")
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChanged.invoke(p0.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
        Log.i("After text changed: $logVal", "$p0")
    }
}