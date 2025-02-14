package myapplication.android.pixelpal.ui.profile.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.FragmentProfileMainBinding
import myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon.ButtonWithIconDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon.ButtonWithIconDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon.ButtonWithIconModel
import myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon_warning.ButtonWithIconWarningDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon_warning.ButtonWithIconWarningDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon_warning.ButtonWithIconWarningModel
import myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview.SubtitleTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview.SubtitleTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview.SubtitleTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.user_avatar.AvatarDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.user_avatar.AvatarDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.user_avatar.AvatarModel
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.dialogs.DeleteAccountDialog
import myapplication.android.pixelpal.ui.dialogs.EditProfileDialog
import myapplication.android.pixelpal.ui.dialogs.LogoutWarningDialog
import myapplication.android.pixelpal.ui.listener.DialogDismissedListener
import myapplication.android.pixelpal.ui.main.BottomScreen
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainEffect
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainIntent
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainLceState
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainLocalDI
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainPartialState
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainState
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainStoreFactory
import javax.inject.Inject

class ProfileMainFragment : MviBaseFragment<
        ProfileMainPartialState,
        ProfileMainIntent,
        ProfileMainState,
        ProfileMainEffect>(R.layout.fragment_profile_main) {

    private var _binding: FragmentProfileMainBinding? = null
    private val binding get() = _binding!!
    private var uri: Uri? = null
    private var notFirst = false
    private val adapter by lazy { initAdapter() }
    private var recyclerItems: List<DelegateItem>? = null
    private var launcher: ActivityResultLauncher<Intent>? = null

    @Inject
    lateinit var localDI: ProfileMainLocalDI

    override val store: MviStore<ProfileMainPartialState, ProfileMainIntent, ProfileMainState, ProfileMainEffect>
            by viewModels { ProfileMainStoreFactory(localDI.reducer, localDI.actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.profileMainComponent().create().inject(this)
        launcher = setActivityResultLauncher()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(ProfileMainIntent.Init)
        }
        store.sendIntent(ProfileMainIntent.GetUserData)
    }

    override fun resolveEffect(effect: ProfileMainEffect) {
        when (effect) {
            is ProfileMainEffect.ShowEditDialog -> {
                val dialogFragment = EditProfileDialog.getInstance(effect.name)
                dialogFragment.show(activity?.supportFragmentManager!!, "EDIT_DIALOG")
                dialogFragment.setDialogDismissedListener(object : DialogDismissedListener {
                    override fun handleDialogClose(args: Bundle?) {
                        binding.loading.root.visibility = VISIBLE
                        store.sendIntent(
                            ProfileMainIntent.EditUser(
                                args?.getString(Constants.NAME_ARG).toString()
                            )
                        )
                    }
                })
            }

            ProfileMainEffect.OpenFavoritesActivity -> (activity as MainActivity).openFavoritesActivity()
            ProfileMainEffect.ShowDeleteDialog -> {
                val dialogFragment = DeleteAccountDialog()
                dialogFragment.show(activity?.supportFragmentManager!!, "DELETE_DIALOG")
                dialogFragment.setDialogDismissedListener(object : DialogDismissedListener {
                    override fun handleDialogClose(args: Bundle?) {
                        binding.loading.root.visibility = VISIBLE
                        store.sendIntent(
                            ProfileMainIntent.DeleteUser(
                                args?.getString(Constants.PASSWORD_ARG).orEmpty()
                            )
                        )
                    }
                })
            }

            ProfileMainEffect.ShowLogoutDialog -> {
                val dialogFragment = LogoutWarningDialog()
                dialogFragment.show(activity?.supportFragmentManager!!, "LOGOUT_DIALOG")
                dialogFragment.setDialogDismissedListener(object : DialogDismissedListener {
                    override fun handleDialogClose(args: Bundle?) {
                        binding.loading.root.visibility = VISIBLE
                        store.sendIntent(ProfileMainIntent.Logout)
                    }
                })
            }

            ProfileMainEffect.NavigateToLogin ->
                app.router.navigateTo(BottomScreen.profile())
        }
    }

    override fun render(state: ProfileMainState) {
        when (state.ui) {
            is ProfileMainLceState.DataLoaded -> {
                binding.loading.root.visibility = GONE
                if (!notFirst) {
                    with(state.ui.content!!) {
                        initRecycler(
                            name, email, uri
                        )
                    }
                    notFirst = true
                } else {
                    for (i in recyclerItems!!) {
                        if (i is SubtitleTextViewDelegateItem) {
                            val item = (i.content() as SubtitleTextViewModel)
                            if (item.id == NAME_ID) {
                                item.text = state.ui.content?.name.toString()
                                adapter.notifyItemChanged(recyclerItems!!.indexOf(i))
                            }
                        }
                    }
                }
            }

            is ProfileMainLceState.Error -> binding.loading.root.visibility = GONE
            ProfileMainLceState.Loading -> binding.loading.root.visibility = VISIBLE
            ProfileMainLceState.LoggedOut -> store.sendEffect(ProfileMainEffect.NavigateToLogin)
        }
    }

    fun initRecycler(name: String?, email: String?, image: String?) {
        uri = image?.toUri()
        recyclerItems = initRecyclerItems(image, name, email)
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
    }

    private fun initRecyclerItems(
        image: String?,
        name: String?,
        email: String?
    ): List<DelegateItem> {
        val items = listOf(
            AvatarDelegateItem(AvatarModel(1, image) {
                initImagePicker()
            }),
            SubtitleTextViewDelegateItem(
                SubtitleTextViewModel(
                    NAME_ID,
                    name!!,
                    alignment = View.TEXT_ALIGNMENT_CENTER
                )
            ),
            SubtitleTextViewDelegateItem(
                SubtitleTextViewModel(
                    EMAIL_ID,
                    email!!,
                    8,
                    View.TEXT_ALIGNMENT_CENTER
                )
            ),
            ButtonWithIconDelegateItem(ButtonWithIconModel(
                3,
                getString(R.string.edit_profile),
                R.drawable.ic_edit
            ) { store.sendEffect(ProfileMainEffect.ShowEditDialog(name)) }),
            ButtonWithIconDelegateItem(ButtonWithIconModel(
                4,
                getString(R.string.favorites),
                R.drawable.ic_favorite
            ) { store.sendEffect(ProfileMainEffect.OpenFavoritesActivity) }),
            ButtonWithIconDelegateItem(ButtonWithIconModel(
                5,
                getString(R.string.log_out),
                R.drawable.ic_log_out,
            ) { store.sendEffect(ProfileMainEffect.ShowLogoutDialog) }),
            ButtonWithIconWarningDelegateItem(ButtonWithIconWarningModel(
                6,
                getString(R.string.delete_account),
                R.drawable.ic_delete,
            ) { store.sendEffect(ProfileMainEffect.ShowDeleteDialog) })
        )
        return items
    }

    private fun initImagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .compress(512)
            .maxResultSize(512, 512)
            .createIntent { intent ->
                launcher?.launch(intent)
            }
    }

    private fun setActivityResultLauncher() = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                uri = data.data
                for (i in recyclerItems!!) {
                    if (i is AvatarDelegateItem) {
                        (i.content() as AvatarModel).uri = uri.toString()
                        adapter.notifyItemChanged(recyclerItems!!.indexOf(i))
                    }
                }
            }
        }
    }


    private fun initAdapter(): MainAdapter =
        MainAdapter().apply {
            addDelegate(AvatarDelegate())
            addDelegate(SubtitleTextViewDelegate())
            addDelegate(ButtonWithIconDelegate())
            addDelegate(ButtonWithIconWarningDelegate())
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NAME_ID = 2
        const val EMAIL_ID = 3
    }

}