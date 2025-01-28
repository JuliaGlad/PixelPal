package myapplication.android.pixelpal.ui.listener

import android.os.Bundle

interface DialogDismissedListener {
    fun handleDialogClose(args: Bundle? = null)
}
