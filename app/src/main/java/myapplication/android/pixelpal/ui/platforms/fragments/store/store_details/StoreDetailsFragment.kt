package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.FragmentStoreDetailsBinding
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.image_view.ImageViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.image_view.ImageViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.image_view.ImageViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview.SubtitleTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview.SubtitleTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview.SubtitleTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.title_details_text_view.TitleDetailsTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.title_details_text_view.TitleDetailsTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.title_details_text_view.TitleDetailsTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.title_textview.TitleTextViewModel
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreArgumentModel
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsUi
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsEffect
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsIntent
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsState
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsStoreFactory
import javax.inject.Inject

class StoreDetailsFragment : MviBaseFragment<
        StoreDetailsPartialState,
        StoreDetailsIntent,
        StoreDetailsState,
        StoreDetailsEffect>(R.layout.fragment_store_details) {

    private var _binding: FragmentStoreDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var localDI: StoreDetailsLocalDI

    private val args: StoreArgumentModel by lazy { getArgument()!! }

    private fun getArgument(): StoreArgumentModel? {
        var storeArgumentModel: StoreArgumentModel? = null
        activity?.let {
            with(it.intent) {
                storeArgumentModel = StoreArgumentModel(
                    getIntExtra(Constants.STORE_ID, 0),
                    getStringExtra(Constants.STORE_NAME)!!,
                    getStringExtra(Constants.STORE_IMAGE)!!,
                    getStringExtra(Constants.STORE_DOMAIN),
                    getIntExtra(Constants.STORE_PROJECTS, 0)
                )
            }
        }
        return storeArgumentModel
    }

    override val store: MviStore<StoreDetailsPartialState, StoreDetailsIntent, StoreDetailsState, StoreDetailsEffect>
            by viewModels { StoreDetailsStoreFactory(localDI.reducer, localDI.actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.storeDetailsComponent().create().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonBack()
        if (savedInstanceState == null) {
            store.sendIntent(StoreDetailsIntent.Init)
        }
        store.sendIntent(StoreDetailsIntent.GetStoreDetails(args.id))
    }

    private fun initButtonBack() {
        binding.iconBack.setOnClickListener { store.sendEffect(StoreDetailsEffect.NavigateBack) }
    }

    override fun resolveEffect(effect: StoreDetailsEffect) {
        when(effect){
            StoreDetailsEffect.NavigateBack -> activity?.finish()
        }
    }

    override fun render(state: StoreDetailsState) {
        when(state.ui){
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                initRecycler(state.ui.data)
            }
            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("Error store details", state.ui.throwable.message.toString())
            }
            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun initRecycler(data: StoreDetailsUi) {
        val adapter = initAdapter()
        val items = mutableListOf(
            ImageViewDelegateItem(
                ImageViewModel(
                    1,
                    args.image
                )
            ),
            TitleTextViewDelegateItem(TitleTextViewModel(2, args.name)),
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    3, getString(R.string.domain), args.domain.toString()
                )
            ),
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    4, getString(R.string.famous_projects), args.projects.toString()
                )
            ),
            SubtitleTextViewDelegateItem(
                SubtitleTextViewModel(
                    5,
                    getString(R.string.description)
                )
            ),
            DescriptionTextViewDelegateItem(DescriptionTextViewModel(6, data.description))
        )
        binding.recyclerView.adapter = adapter
        adapter.submitList(items)
    }

    private fun initAdapter(): MainAdapter {
        return MainAdapter().apply {
            addDelegate(ImageViewDelegate())
            addDelegate(TitleTextViewDelegate())
            addDelegate(TitleDetailsTextViewDelegate())
            addDelegate(SubtitleTextViewDelegate())
            addDelegate(DescriptionTextViewDelegate())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}