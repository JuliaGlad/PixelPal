package myapplication.android.pixelpal.ui.profile.signing.create_account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentCreateAccountBinding
import myapplication.android.pixelpal.di.DaggerAppComponent
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextInputLayoutDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextInputLayoutDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextInputLayoutModel
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextWatcherImpl
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.user_avatar.AvatarDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.user_avatar.AvatarDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.user_avatar.AvatarModel
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import myapplication.android.pixelpal.ui.profile.signing.create_account.di.DaggerCreateAccountComponent
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountEffect
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountIntent
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountLceState
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountLocalDI
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountPartialState
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountState
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountStoreFactory
import javax.inject.Inject

class CreateAccountMainFragment : MviBaseFragment<
        CreateAccountPartialState,
        CreateAccountIntent,
        CreateAccountState,
        CreateAccountEffect>(R.layout.fragment_create_account) {

    @Inject
    lateinit var localDI: CreateAccountLocalDI

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!
    private var recyclerItems: List<DelegateItem>? = null
    val adapter by lazy { initAdapter() }
    private var launcher: ActivityResultLauncher<Intent>? = null
    var email: String = ""
    var password: String = ""
    var name: String = ""
    var uri: Uri? = null

    override val store: MviStore<CreateAccountPartialState, CreateAccountIntent, CreateAccountState, CreateAccountEffect>
            by viewModels { CreateAccountStoreFactory(localDI.reducer, localDI.actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerCreateAccountComponent.factory().create(appComponent).inject(this)
        launcher = setActivityResultLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(CreateAccountIntent.Init)
        }
    }

    override fun resolveEffect(effect: CreateAccountEffect) {
        when (effect) {
            CreateAccountEffect.NavigateBack -> activity?.finish()
            CreateAccountEffect.ShowSnackBar -> {
                binding.nextLoading.root.visibility = GONE
                Snackbar.make(
                    requireView(),
                    getString(R.string.empty_fields_or_incorrect_data),
                    Snackbar.LENGTH_LONG
                ).show()
            }

            CreateAccountEffect.OpenProfile -> {
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }
        }
    }

    override fun render(state: CreateAccountState) {
        when (state.ui) {
            CreateAccountLceState.AccountCreated -> {
                binding.loading.root.visibility = GONE
                store.sendEffect(CreateAccountEffect.OpenProfile)
            }

            is CreateAccountLceState.Error -> {
                binding.nextLoading.root.visibility = VISIBLE
                binding.loading.root.visibility = GONE
            }

            CreateAccountLceState.Init -> initUi()
            CreateAccountLceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun initUi() {
        initButtonBack()
        initNextButton()
        initRecycler()
    }

    private fun initButtonBack() {
        binding.iconBack.setOnClickListener {
            store.sendEffect(CreateAccountEffect.NavigateBack)
        }
    }

    private fun initNextButton() {
        binding.buttonNext.setOnClickListener {
            binding.nextLoading.root.visibility = VISIBLE
            store.sendIntent(
                CreateAccountIntent.CreateAccount(
                    email, password, name, uri
                )
            )
        }
    }

    private fun initRecycler() {
        recyclerItems = listOf(
            AvatarDelegateItem(AvatarModel(1, null) {
                initImagePicker()
            }),
            TitleTextViewDelegateItem(
                TitleTextViewModel(
                    1,
                    getString(R.string.account_creation)
                )
            ),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    EMAIL_ID,
                    getString(R.string.email_hint),
                    getString(R.string.email),
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                    TextWatcherImpl("Email") { value ->
                        email = value
                    }
                )
            ),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    PASSWORD_ID,
                    getString(R.string.password_hint),
                    getString(R.string.password),
                    InputType.TYPE_TEXT_VARIATION_PASSWORD,
                    TextWatcherImpl("Password") { value ->
                        password = value
                    }
                )
            ),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    NAME_ID,
                    getString(R.string.name_hint),
                    getString(R.string.name),
                    InputType.TYPE_CLASS_TEXT,
                    TextWatcherImpl("Name") { value ->
                        name = value
                    }
                )
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
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
            addDelegate(TextInputLayoutDelegate())
            addDelegate(AvatarDelegate())
            addDelegate(TitleTextViewDelegate())
        }

    companion object {
        const val EMAIL_ID = 1
        const val PASSWORD_ID = 2
        const val NAME_ID = 3
    }

}