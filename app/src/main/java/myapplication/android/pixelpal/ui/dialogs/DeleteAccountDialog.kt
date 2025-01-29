package myapplication.android.pixelpal.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.view.isNotEmpty
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.DialogDeleteAccountBinding
import myapplication.android.pixelpal.ui.listener.DialogDismissedListener

class DeleteAccountDialog : DialogFragment() {

    private var dialogDismissedListener: DialogDismissedListener? = null
    private var delete: Boolean = false
    private var password: String? = null

    fun setDialogDismissedListener(dialogDismissedListener: DialogDismissedListener) {
        this.dialogDismissedListener = dialogDismissedListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val binding = DialogDeleteAccountBinding.inflate(layoutInflater)

        binding.buttonDelete.setOnClickListener {
            delete = true
            if (binding.textInputLayout.isNotEmpty()) {
                password = binding.textInputEdit.text.toString()
                dismiss()
            }
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
        return builder.setView(binding.root).create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (delete) dialogDismissedListener?.handleDialogClose(Bundle().apply {
            putString(Constants.PASSWORD_ARG, password)
        })
    }
}