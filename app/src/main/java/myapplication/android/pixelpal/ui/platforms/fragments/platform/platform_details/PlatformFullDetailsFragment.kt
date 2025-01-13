package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details

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
import myapplication.android.pixelpal.databinding.FragmentPlatformFullDetailsBinding
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
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsArgumentsModel
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsUi
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsEffect
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsIntent
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsStoreFactory
import javax.inject.Inject

class PlatformFullDetailsFragment : MviBaseFragment<
        PlatformDetailsPartialState,
        PlatformDetailsIntent,
        PlatformDetailsState,
        PlatformDetailsEffect>(R.layout.fragment_platform_details) {

    private var _binding: FragmentPlatformFullDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: PlatformDetailsArgumentsModel by lazy { getPlatformArguments()!! }

    private fun getPlatformArguments(): PlatformDetailsArgumentsModel? {
        var argsModel: PlatformDetailsArgumentsModel? = null
        activity?.let {
            with(it.intent){
                argsModel = PlatformDetailsArgumentsModel(
                    getIntExtra(Constants.PLATFORM_ID, 0),
                    getStringExtra(Constants.PLATFORM_NAME)!!,
                    getIntExtra(Constants.PLATFORM_PROJECTS, 0),
                    getIntExtra(Constants.PLATFORM_YEAR_START, 0),
                    getStringExtra(Constants.PLATFORM_BACKGROUND)!!
                )
            }
        }
        return argsModel
    }

    @Inject
    lateinit var localDI: PlatformDetailsLocalDI

    override val store: MviStore<PlatformDetailsPartialState, PlatformDetailsIntent, PlatformDetailsState, PlatformDetailsEffect>
            by viewModels { PlatformDetailsStoreFactory(localDI.reducer, localDI.actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.platformDetailsComponent().create().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlatformFullDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonBack()
        if (savedInstanceState == null) {
            store.sendIntent(PlatformDetailsIntent.Init)
        }
        store.sendIntent(PlatformDetailsIntent.GetPlatformDetails(args.id))
    }

    private fun initButtonBack() {
        binding.iconBack.setOnClickListener { store.sendEffect(PlatformDetailsEffect.NavigateBack) }
    }

    override fun resolveEffect(effect: PlatformDetailsEffect) {
        when (effect) {
            PlatformDetailsEffect.NavigateBack -> activity?.finish()
        }
    }

    override fun render(state: PlatformDetailsState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                initRecycler(state.ui.data)
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("PlatformDetailsException", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun initRecycler(data: PlatformDetailsUi) {
        val adapter = initAdapter()
        val items = mutableListOf(
            ImageViewDelegateItem(
                ImageViewModel(
                    1,
                    args.background
                )
            ),
            TitleTextViewDelegateItem(TitleTextViewModel(2, args.name)),

        )
        items.addAll(getYearsTitleDetails(args.startYear!!, data.endYear))
        items.add(
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    4, getString(R.string.famous_projects), args.gamesCount.toString()
                )
            )
        )
        items.add(SubtitleTextViewDelegateItem(
            SubtitleTextViewModel(
                5,
                getString(R.string.description)
            )
        ))
        items.add(DescriptionTextViewDelegateItem(DescriptionTextViewModel(6, data.description)))
        binding.recyclerView.adapter = adapter
        adapter.submitList(items)
    }

    private fun getYearsTitleDetails(startYear: Int, endYear: Int?): List<TitleDetailsTextViewDelegateItem> {
        val yearDelegates = mutableListOf<TitleDetailsTextViewDelegateItem>()
        yearDelegates.add(
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(1, getString(R.string.start_year), startYear.toString())
            )
        )
        if (endYear != null){
            yearDelegates.add(
                TitleDetailsTextViewDelegateItem(
                    TitleDetailsTextViewModel(2, getString(R.string.end_year), endYear.toString())
                )
            )
        }
        return yearDelegates
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