package myapplication.android.pixelpal.ui.platforms.fragments.platform

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App
import myapplication.android.pixelpal.databinding.FragmentPlatformDetailsBinding
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformUiList
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformEffect
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformIntent
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformStore
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformStoreFactory
import myapplication.android.pixelpal.ui.platforms.fragments.platform.recycler_view.PlatformAdapter
import myapplication.android.pixelpal.ui.platforms.fragments.platform.recycler_view.PlatformModel
import javax.inject.Inject

class PlatformDetailsFragment : MviBaseFragment<
        PlatformPartialState,
        PlatformIntent,
        PlatformState,
        PlatformEffect>(R.layout.fragment_platforms) {
    private val platformComponent by lazy {
        (activity?.application as App).appComponent.platformComponent().create()
    }
    private var _binding: FragmentPlatformDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var platformLocalDI: PlatformLocalDI

    override val store: PlatformStore by viewModels {
        PlatformStoreFactory(platformLocalDI.actor, platformLocalDI.reducer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        platformComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlatformDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(PlatformIntent.Init)
        }
        store.sendIntent(PlatformIntent.GetPlatforms)
    }

    override fun resolveEffect(effect: PlatformEffect) {
        TODO("handel effects")
    }

    override fun render(state: PlatformState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loadingRecycler.root.visibility = GONE
                initRecycler(state.ui.data)
            }

            is LceState.Error -> {
                binding.loadingRecycler.root.visibility = GONE
                Log.e("Platform error", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loadingRecycler.root.visibility = VISIBLE
        }
    }

    private fun initRecycler(platforms: PlatformUiList) {
        val adapter = PlatformAdapter()
        binding.recyclerView.adapter = adapter

        val models = mutableListOf<PlatformModel>()
        for (i in platforms.list) {
            models.add(
                PlatformModel(
                    1,
                    i.id,
                    i.name,
                    i.startYear,
                    i.gamesCount,
                    i.background,
                    object : ClickListener {
                        override fun onClick() {
                            TODO("open platform details fragment")
                        }
                    }
                ))
        }
        adapter.submitList(models)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance() =
            PlatformDetailsFragment()
    }
}