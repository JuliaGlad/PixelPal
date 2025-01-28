package myapplication.android.pixelpal.ui.delegates.delegates.text_input

import android.text.InputType
import android.text.TextWatcher

data class TextInputLayoutModel(
    val id: Int,
    val hint: String,
    val title: String,
    val inputType: Int,
    val textWatcher: TextWatcher
)