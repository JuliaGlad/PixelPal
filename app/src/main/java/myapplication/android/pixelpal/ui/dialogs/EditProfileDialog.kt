package myapplication.android.pixelpal.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.view.isNotEmpty
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.android.pixelpal.databinding.DialogEditProfileBinding
import myapplication.android.pixelpal.ui.listener.DialogDismissedListener

class EditProfileDialog : DialogFragment() {

    private var dialogDismissedListener: DialogDismissedListener? = null
    private var previousName: String = ""
    private var name: String? = null

    fun setDialogDismissedListener(dialogDismissedListener: DialogDismissedListener){
        this.dialogDismissedListener = dialogDismissedListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val binding = DialogEditProfileBinding.inflate(layoutInflater)

        with(binding) {
            textInputEdit.setText(previousName)
            buttonCancel.setOnClickListener { dismiss() }
            buttonLogout.setOnClickListener {
                if (textInputLayout.isNotEmpty() ){
                    if (previousName != name) name = textInputEdit.text.toString()
                    dismiss()
                }
            }
        }

        return builder.setView(binding.root).create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (name != null){
            dialogDismissedListener?.handleDialogClose(Bundle().apply {
                putString(NAME_ARG, name)
            })
        }
    }

    companion object{
        fun getInstance(name: String) : EditProfileDialog = EditProfileDialog().apply {
            previousName = name
        }
        const val NAME_ARG = "NameArg"
    }

}