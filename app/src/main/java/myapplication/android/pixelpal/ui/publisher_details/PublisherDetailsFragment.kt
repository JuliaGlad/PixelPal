package myapplication.android.pixelpal.ui.publisher_details

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
import myapplication.android.pixelpal.databinding.FragmentPublisherDetailsBinding
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
import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import myapplication.android.pixelpal.ui.publisher_details.model.PublisherArgumentsModel
import myapplication.android.pixelpal.ui.publisher_details.model.PublisherDetailsResult
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsEffect
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsIntent
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsLocalDI
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsPartialState
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsState
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsStoreFactory
import javax.inject.Inject

class PublisherDetailsFragment : MviBaseFragment<
        PublisherDetailsPartialState,
        PublisherDetailsIntent,
        PublisherDetailsState,
        PublisherDetailsEffect>(R.layout.fragment_publisher_details) {

    @Inject
    lateinit var localDi: PublisherDetailsLocalDI

    private var isUpdated = false
    private val args: PublisherArgumentsModel by lazy { getPublisherArguments()!! }
    private val recyclerItems = mutableListOf<DelegateItem>()
    val adapter by lazy { initAdapter() }

    private fun getPublisherArguments(): PublisherArgumentsModel? {
        var args: PublisherArgumentsModel? = null
        activity?.let {
            with(it.intent) {
                args = PublisherArgumentsModel(
                    getLongExtra(Constants.PUBLISHER_ID, 0),
                    getStringExtra(Constants.PUBLISHER_NAME)!!,
                    getIntExtra(Constants.PUBLISHER_GAME_COUNT, 0),
                    getStringExtra(Constants.PUBLISHER_BACKGROUND)
                )
            }
        }
        return args
    }

    private var _binding: FragmentPublisherDetailsBinding? = null
    private val binding get() = _binding!!

    override val store: MviStore<PublisherDetailsPartialState, PublisherDetailsIntent, PublisherDetailsState, PublisherDetailsEffect>
            by viewModels { PublisherDetailsStoreFactory(localDi.actor, localDi.reducer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.publisherDetailsComponent().create().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublisherDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonBack()
        if (savedInstanceState == null) {
            store.sendIntent(PublisherDetailsIntent.Init)
        }
        store.sendIntent(PublisherDetailsIntent.GetPublisher(args.id))
    }

    private fun initButtonBack() {
        binding.iconBack.setOnClickListener { store.sendEffect(PublisherDetailsEffect.NavigateBack) }
    }

    override fun resolveEffect(effect: PublisherDetailsEffect) {
        when (effect) {
            PublisherDetailsEffect.NavigateBack -> activity?.finish()
            is PublisherDetailsEffect.OpenGameDetails -> {
                with(effect) {
                    (activity as PublisherDetailsActivity).openGameDetailsActivity(
                        gameId, name, genres, releaseDate, image
                    )
                }
            }
        }
    }

    override fun render(state: PublisherDetailsState) {
        when (state.ui) {
            is LceState.Content -> {
                if (!isUpdated) {
                    binding.loading.root.visibility = GONE
                    initRecycler(state.ui.data)
                } else {
                    updateRecycler(state.ui.data.newItems)
                }
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("PublisherDetailsException", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun updateRecycler(newItems: GamesNewsListUi?) {
        isUpdated = false
        val new = getPublisherGames(newItems!!)
        for (i in recyclerItems){
            if (i is GameDetailsShortDataDelegateItem){
                adapter.notifyItemChanged(recyclerItems.indexOf(i), new)
            }
        }
    }

    private fun initRecycler(data: PublisherDetailsResult) {
        val games = getPublisherGames(data.games)
        recyclerItems.addAll(
            listOf(
                ImageViewDelegateItem(
                    ImageViewModel(
                        1,
                        args.background!!
                    )
                ),
                TitleTextViewDelegateItem(TitleTextViewModel(2, args.name)),
                TitleDetailsTextViewDelegateItem(
                    TitleDetailsTextViewModel(
                        4,
                        getString(R.string.famous_projects),
                        args.gamesCount.toString()
                    )
                ),
                SubtitleTextViewDelegateItem(
                    SubtitleTextViewModel(
                        5,
                        getString(R.string.description)
                    )
                ),
                DescriptionTextViewDelegateItem(DescriptionTextViewModel(6, data.publisherDetails.description)),
                GameDetailsShortDataDelegateItem(
                    GameDetailsShortDataModel(
                        7,
                        getString(R.string.famous_projects),
                        getString(R.string.unknown),
                        games,
                        { (activity as PublisherDetailsActivity).openAllPublisherActivity(args.id) },
                        object : RecyclerEndListener {
                            override fun onEndReached() {
                                isUpdated = true
                                store.sendIntent(
                                    PublisherDetailsIntent.GetGames(
                                        args.id
                                    )
                                )
                            }
                        }
                    )
                )
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
    }

    private fun getPublisherGames(items: GamesNewsListUi): MutableList<GamesShortModel> {
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
                                PublisherDetailsEffect.OpenGameDetails(
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

}