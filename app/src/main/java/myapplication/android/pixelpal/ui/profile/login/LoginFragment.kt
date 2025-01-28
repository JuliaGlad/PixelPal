package myapplication.android.pixelpal.ui.profile.login

import android.app.Activity
import android.content.Intent
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
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.material.snackbar.Snackbar
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.databinding.FragmentLoginBinding
import myapplication.android.pixelpal.domain.usecase.user.CheckUserUseCase
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextInputLayoutDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextInputLayoutDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextInputLayoutModel
import myapplication.android.pixelpal.ui.delegates.delegates.text_input.TextWatcherImpl
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewModel
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import myapplication.android.pixelpal.ui.profile.main.ProfileMainFragment
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginEffect
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginIntent
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginLceState
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginLocalDI
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginPartialState
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginState
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginStoreFactory
import javax.inject.Inject

class LoginFragment : MviBaseFragment<
        LoginPartialState,
        LoginIntent,
        LoginState,
        LoginEffect>(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { initAdapter() }

    private var email: String = ""
    private var password: String = ""
    private var launcher : ActivityResultLauncher<Intent>? = null

    @Inject
    lateinit var localDI: LoginLocalDI

    override val store: MviStore<LoginPartialState, LoginIntent, LoginState, LoginEffect>
            by viewModels { LoginStoreFactory(localDI.reducer, localDI.actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.loginComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        launcher = setLauncher()
    }

    private fun setLauncher() =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                store.sendEffect(LoginEffect.NavigateToProfile)
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (CheckUserUseCase().checkAuth()) store.sendEffect(LoginEffect.NavigateToProfile)
        if (savedInstanceState == null) {
            store.sendIntent(LoginIntent.Init)
        }
    }

    override fun resolveEffect(effect: LoginEffect) {
        when (effect) {
            LoginEffect.OpenCreateAccountActivity -> (activity as MainActivity).launchCreateAccountActivity(launcher!!)
            LoginEffect.NavigateToProfile -> (activity as MainActivity).presenter.navigateTo(
                FragmentScreen { ProfileMainFragment() }
            )

            LoginEffect.ShowSnackBar ->  {
                binding.nextLoading.root.visibility = GONE
                Snackbar.make(
                    requireView(),
                    getString(R.string.empty_fields_or_incorrect_data),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun render(state: LoginState) {
        when (state.ui) {
            is LoginLceState.Error -> {
                binding.nextLoading.root.visibility = GONE
                binding.loading.root.visibility = GONE
            }
            LoginLceState.Init -> initUi()
            LoginLceState.Loading -> binding.loading.root.visibility = VISIBLE
            LoginLceState.LoggedIn -> store.sendEffect(LoginEffect.NavigateToProfile)
        }
    }

    private fun initUi() {
        initButton()
        initActionText()
        initRecycler()
    }

    private fun initButton() {

        binding.buttonNext.setOnClickListener {
            binding.nextLoading.root.visibility = VISIBLE
            if (email.isEmpty() or password.isEmpty()) store.sendEffect(LoginEffect.ShowSnackBar)
            else store.sendIntent(LoginIntent.SignInWithEmailAndPassword(email, password))
        }
    }

    private fun initActionText() {
        binding.actionText.setOnClickListener { store.sendEffect(LoginEffect.OpenCreateAccountActivity) }
    }

    private fun initRecycler() {
        val items = listOf(
            TitleTextViewDelegateItem(
                TitleTextViewModel(
                    1,
                    getString(R.string.welcome_to_pixel_pal)
                )
            ),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    EMAIL_ID,
                    getString(R.string.email_hint),
                    getString(R.string.email),
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                    TextWatcherImpl("Email"){ value ->
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
                    TextWatcherImpl("Password"){value ->
                        password = value
                    }
                )
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.submitList(items)
    }

    private fun initAdapter(): MainAdapter =
        MainAdapter().apply {
            addDelegate(TitleTextViewDelegate())
            addDelegate(TextInputLayoutDelegate())
        }

    companion object {
        const val EMAIL_ID = 1
        const val PASSWORD_ID = 2
    }

}