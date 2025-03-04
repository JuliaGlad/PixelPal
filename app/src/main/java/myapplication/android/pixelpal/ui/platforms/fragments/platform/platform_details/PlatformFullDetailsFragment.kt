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
import myapplication.android.pixelpal.databinding.FragmentPlatformFullDetailsBinding
import myapplication.android.pixelpal.di.DaggerAppComponent
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataModel
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
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.di.DaggerPlatformDetailsComponent
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsArgumentsModel
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsResult
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
        PlatformDetailsEffect>(R.layout.fragment_platform_full_details) {

    private var _binding: FragmentPlatformFullDetailsBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { initAdapter() }
    private val recyclerItems = mutableListOf<DelegateItem>()
    private var isUpdated = false
    private val args: PlatformDetailsArgumentsModel by lazy { getPlatformArguments()!! }

    private fun getPlatformArguments(): PlatformDetailsArgumentsModel? {
        var argsModel: PlatformDetailsArgumentsModel? = null
        activity?.let {
            with(it.intent) {
                argsModel = PlatformDetailsArgumentsModel(
                    getLongExtra(PLATFORM_ID, 0L),
                    getStringExtra(PLATFORM_NAME)!!,
                    getIntExtra(PLATFORM_PROJECTS, 0),
                    getIntExtra(PLATFORM_YEAR_START, 0),
                    getStringExtra(PLATFORM_BACKGROUND)!!
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
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerPlatformDetailsComponent.factory().create(appComponent).inject(this)
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
            is PlatformDetailsEffect.OpenGameDetails -> {
                with(effect) {
                    (activity as PlatformDetailsActivity).openGameDetailsActivity(
                        gameId, name, genres, releaseDate, image
                    )
                }
            }
        }
    }

    override fun render(state: PlatformDetailsState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                if (!isUpdated) {
                    initRecycler(state.ui.data)
                } else {
                    updateRecycler(state.ui.data.newItems)
                }
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("PlatformDetailsException", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun updateRecycler(newItems: GamesMainInfoListUi?) {
        isUpdated = false
        val new = getPlatformGames(newItems!!)
        for (i in recyclerItems){
            if (i is GameDetailsShortDataDelegateItem){
                adapter.notifyItemChanged(recyclerItems.indexOf(i), new)
            }
        }
    }

    private fun initRecycler(data: PlatformDetailsResult) {
        val games = getPlatformGames(data.games)
        recyclerItems.addAll(
            listOf(
                ImageViewDelegateItem(
                    ImageViewModel(
                        1,
                        args.background
                    )
                ),
                TitleTextViewDelegateItem(TitleTextViewModel(2, args.name)),

                )
        )
        recyclerItems.addAll(getYearsTitleDetails(args.startYear!!, data.platformDetailsUi.endYear))
        recyclerItems.add(
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    4, getString(R.string.famous_projects), args.gamesCount.toString()
                )
            )
        )
        recyclerItems.add(
            SubtitleTextViewDelegateItem(
                SubtitleTextViewModel(
                    5,
                    getString(R.string.description)
                )
            )
        )
        recyclerItems.add(
            DescriptionTextViewDelegateItem(
                DescriptionTextViewModel(
                    6,
                    data.platformDetailsUi.description
                )
            )
        )
        recyclerItems.add(GameDetailsShortDataDelegateItem(
            GameDetailsShortDataModel(
                7,
                getString(R.string.famous_projects),
                getString(R.string.unknown),
                games,
                { (activity as PlatformDetailsActivity).openAllGamesActivity(args.id) },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        isUpdated = true
                        store.sendIntent(
                            PlatformDetailsIntent.GetGames(
                                args.id
                            )
                        )
                    }
                }
            )
        ))
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
    }

    private fun getYearsTitleDetails(
        startYear: Int,
        endYear: Int?
    ): List<TitleDetailsTextViewDelegateItem> {
        val yearDelegates = mutableListOf<TitleDetailsTextViewDelegateItem>()
        yearDelegates.add(
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(1, getString(R.string.start_year), startYear.toString())
            )
        )
        if (endYear != null) {
            yearDelegates.add(
                TitleDetailsTextViewDelegateItem(
                    TitleDetailsTextViewModel(2, getString(R.string.end_year), endYear.toString())
                )
            )
        }
        return yearDelegates
    }

    private fun getPlatformGames(items: GamesMainInfoListUi): MutableList<GamesShortModel> {
        val list = mutableListOf<GamesShortModel>()
        for (i in items.games) {
            with(i) {
                list.add(
                    GamesShortModel(
                        gameId,
                        name,
                        rating?.toInt(),
                        releaseDate,
                        playTime,
                        uri,
                        {
                            store.sendEffect(
                                PlatformDetailsEffect.OpenGameDetails(
                                    gameId,
                                    genre,
                                    name,
                                    releaseDate!!,
                                    uri!!
                                )
                            )
                        }
                    )
                )
            }
        }
        return list
    }

    private fun initAdapter(): MainAdapter {
        return MainAdapter().apply {
            addDelegate(ImageViewDelegate())
            addDelegate(TitleTextViewDelegate())
            addDelegate(TitleDetailsTextViewDelegate())
            addDelegate(SubtitleTextViewDelegate())
            addDelegate(DescriptionTextViewDelegate())
            addDelegate(GameDetailsShortDataDelegate())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val PLATFORM_ID = "PlatformId"
        const val PLATFORM_NAME = "PlatformName"
        const val PLATFORM_BACKGROUND = "PlatformBackground"
        const val PLATFORM_YEAR_START = "PlatformYearStart"
        const val PLATFORM_PROJECTS = "PlatformProjects"
    }
}