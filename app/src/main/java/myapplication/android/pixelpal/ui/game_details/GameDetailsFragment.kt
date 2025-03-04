package myapplication.android.pixelpal.ui.game_details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentGameDetailsBinding
import myapplication.android.pixelpal.di.DaggerAppComponent
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.delegates.delegates.creator_game_details.CreatorGameDetailsDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.creator_game_details.CreatorGameDetailsDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.creator_game_details.CreatorGameDetailsModel
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsModel
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataModel
import myapplication.android.pixelpal.ui.delegates.delegates.image_view.ImageViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.image_view.ImageViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.image_view.ImageViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.imagesCarousel.ImageCarouselDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.imagesCarousel.ImagesCarouselDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.imagesCarousel.ImagesCarouselModel
import myapplication.android.pixelpal.ui.delegates.delegates.stores_game_details.StoreGameDetailsDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.stores_game_details.StoreGameDetailsDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.stores_game_details.StoreGameDetailsModel
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
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity
import myapplication.android.pixelpal.ui.game_details.di.DaggerGameDetailsComponent
import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.game_details.model.ScreenshotsUiList
import myapplication.android.pixelpal.ui.game_details.model.StoresSellingGameUiList
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsContentResult
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsEffect
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsIntent
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsLceState
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsLocalDI
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsPartialState
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsState
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsStore
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsStoreFactory
import myapplication.android.pixelpal.ui.game_details.recycler_view.stores_details.StoreLinkModels
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import java.util.stream.Collectors
import javax.inject.Inject

class GameDetailsFragment @Inject constructor() : MviBaseFragment<
        GameDetailsPartialState,
        GameDetailsIntent,
        GameDetailsState,
        GameDetailsEffect>(R.layout.fragment_game_details) {

    @Inject
    lateinit var gameDetailsLocalDI: GameDetailsLocalDI

    private val recyclerItems = mutableListOf<DelegateItem>()

    private var _binding: FragmentGameDetailsBinding? = null
    val binding get() = _binding!!
    private val adapter = MainAdapter()
    private var gameId: Long? = null
    private var releaseDate: String? = null
    private var genres: String? = null
    private var gameName: String? = null
    private var gameImage: String? = null
    private var isNotFavorite: Boolean = false
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerGameDetailsComponent.factory().create(appComponent).inject(this)
        if (activity?.intent != null) getActivityArguments()
    }

    private fun getActivityArguments() {
        with(activity?.intent) {
            gameId = this?.getLongExtra(GAME_ID_ARG, 0)
            releaseDate = this?.getStringExtra(GAME_RELEASE_ARG)
            genres = this?.getStringExtra(GAME_GENRES_ARG)
            gameName = this?.getStringExtra(GAME_NAME_ARG)
            gameImage = this?.getStringExtra(GAME_IMAGE_ARG)
        }
    }

    override val store: GameDetailsStore by viewModels {
        GameDetailsStoreFactory(
            gameDetailsLocalDI.reducer,
            gameDetailsLocalDI.actor
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(GameDetailsIntent.Init)
        }
        store.sendIntent(GameDetailsIntent.GetGameMainData(gameId.toString()))
    }

    private fun initBackButton() {
        binding.iconBack.setOnClickListener {
            store.sendEffect(GameDetailsEffect.NavigateBack)
        }
    }

    override fun resolveEffect(effect: GameDetailsEffect) {
        when (effect) {
            GameDetailsEffect.NavigateBack -> {
                if (!isNotFavorite) activity?.finish()
                else {
                    activity?.apply {
                        setResult(Activity.RESULT_OK, Intent().putExtra(GAME_ID_ARG, gameId))
                        finish()
                    }
                }
            }

            is GameDetailsEffect.OpenGameDetails -> {
                TODO("Change game")
            }

            is GameDetailsEffect.OpenAllSameSeries ->
                with(effect) {
                    (activity as GameDetailsActivity).openAllGamesActivity(
                        SAME_SERIES_ID, gameId, genres!!
                    )
                }

            is GameDetailsEffect.OpenAllAdditionsAndParentGames -> {
                with(effect) {
                    (activity as GameDetailsActivity).openAllGamesActivity(
                       ADDITIONS_AND_PARENT_ID, gameId, genres!!
                    )
                }
            }

            is GameDetailsEffect.OpenCreatorDetails -> {
                with(effect) {
                    (activity as GameDetailsActivity).openCreatorDetailsActivity(
                        creatorId,
                        name,
                        role,
                        famousProjects,
                        image
                    )
                }
            }

            is GameDetailsEffect.OpenAllCreators -> {
                with(effect) {
                    (activity as GameDetailsActivity).openAllCreatorsActivity(gameId)
                }
            }
        }
    }

    override fun render(state: GameDetailsState) {
        when (state.ui) {
            is GameDetailsLceState.Content -> {
                binding.loading.root.visibility = GONE
                val data = state.ui.data as GameDetailsContentResult
                isFavorite = data.gameDetails.isFavorite
                initRecycler(data)
                checkBackButtonPressed()
                initBackButton()
                initFavoriteButton()
            }

            is GameDetailsLceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("GameDetailsException", state.ui.throwable.message.toString())
            }

            GameDetailsLceState.Loading -> {
                binding.loading.root.visibility = VISIBLE
            }

            is GameDetailsLceState.UpdateCreators ->
                updateCreatorsRecycler(state.ui.data as CreatorsGameUiList)

            is GameDetailsLceState.UpdateParentGamesAndAdditions ->
                updateParentAndAdditionsRecycler(state.ui.data as GamesShortDataUiList)

            is GameDetailsLceState.UpdateSameSeries ->
                updateSameSeriesRecycler(state.ui.data as GamesShortDataUiList)

            is GameDetailsLceState.UpdateStoresSellingGame ->
                updateStoresRecycler(state.ui.data as StoresSellingGameUiList)

            GameDetailsLceState.GameAddedToFavorites -> {
                Log.i("Added To Favorites", "game with id $gameId added to userFavs")
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_favorite,
                    activity?.theme
                )?.let { binding.iconFav.setIcon(it) }
            }

            GameDetailsLceState.GameRemovedFromFavorites -> {
                Log.i("Removed From Favorites", "game with id $gameId removed from userFavs")
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_favorite_outline,
                    activity?.theme
                )?.let { binding.iconFav.setIcon(it) }
            }
        }
    }

    private fun initFavoriteButton() {
        with(binding) {
            val icon =
                if (isFavorite) ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_favorite,
                    activity?.theme
                )
                else ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_favorite_outline,
                    activity?.theme
                )

            iconFav.setIcon(icon!!)
            iconFav.setOnClickListener {
                gameId?.let { id ->
                    if (isFavorite) {
                        isNotFavorite = true
                        isFavorite = false
                        store.sendIntent(GameDetailsIntent.RemoveGameFromFavorites(id))
                    } else {
                        isNotFavorite = false
                        isFavorite = true
                        store.sendIntent(GameDetailsIntent.AddGameToFavorites(id))
                    }
                }

            }
        }
    }

    private fun updateStoresRecycler(items: StoresSellingGameUiList) {
        this.recyclerItems.forEach {
            if (it is StoreGameDetailsDelegateItem) {
                with((it.content() as StoreGameDetailsModel)) {
                    isUpdated = true
                    val models = getStoresModels(items)
                    adapter.notifyItemChanged(recyclerItems.indexOf(it), models)
                }
            }
        }
    }

    private fun updateParentAndAdditionsRecycler(
        games: GamesShortDataUiList,
    ) {
        this.recyclerItems.forEach {
            if (it is GameDetailsShortDataDelegateItem) {
                if (ADDITION == it.model.id) {
                    with((it.content() as GameDetailsShortDataModel)) {
                        isUpdated = true
                        val models = mutableListOf<GamesShortModel>()
                        val newItems = mutableListOf<GamesShortModel>()
                        games.items.forEach { gamesShortDataUi ->
                            with(gamesShortDataUi) {
                                newItems.add(
                                    GamesShortModel(
                                        gameId,
                                        name,
                                        rating,
                                        releaseDate,
                                        playtime,
                                        image,
                                        {
                                            store.sendEffect(
                                                GameDetailsEffect.OpenGameDetails(
                                                    gameId,
                                                    genres!!,
                                                    name,
                                                    releaseDate!!,
                                                    image!!
                                                )
                                            )
                                        }
                                    )
                                )
                            }
                        }
                        adapter.notifyItemChanged(recyclerItems.indexOf(it), models)
                    }
                }
            }
        }
    }

    private fun updateSameSeriesRecycler(
        items: GamesShortDataUiList,
    ) {
        this.recyclerItems.forEach {
            if (it is CreatorGameDetailsDelegateItem) {
                if (SAME_SERIES == it.model.id) {
                    with((it.content() as CreatorGameDetailsModel)) {
                        isUpdated = true
                        val models = getGameShortModels(items)
                        adapter.notifyItemChanged(recyclerItems.indexOf(it), models)
                    }
                }
            }
        }
    }

    private fun updateCreatorsRecycler(items: CreatorsGameUiList) {
        this.recyclerItems.forEach {
            if (it is CreatorGameDetailsDelegateItem) {
                with((it.content() as CreatorGameDetailsModel)) {
                    isUpdated = true
                    val models = getCreatorModels(creators = items)
                    adapter.notifyItemChanged(recyclerItems.indexOf(it), models)
                }
            }
        }
    }

    private fun initRecycler(items: GameDetailsContentResult) {
        initAdapter()
        addMainInfo(items.gameDetails.description)
        addScreenshots(items.screenshotsUiList)
        addCreators(items.creators)
        addSameSeries(items.sameSeries)
        addParentAndAddition(items.parentGames, items.additions)
        addStores(items.storeLinks)
        adapter.submitList(this.recyclerItems)
    }

    private fun addSameSeries(sameSeries: GamesShortDataUiList) {
        val newItems = getGameShortModels(sameSeries)
        recyclerItems.add(GameDetailsShortDataDelegateItem(
            GameDetailsShortDataModel(
                SAME_SERIES,
                getString(R.string.same_series),
                getString(R.string.unknown),
                newItems,
                { store.sendEffect(GameDetailsEffect.OpenAllSameSeries(gameId!!)) },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (!binding.recyclerView.isComputingLayout) {
                            store.sendIntent(GameDetailsIntent.GetSameSeries(gameId.toString()))
                        }
                    }
                }
            )
        ))
    }

    private fun addParentAndAddition(
        parentGames: GamesShortDataUiList,
        additions: GamesShortDataUiList
    ) {
        val newItems = getAdditionAndParentModels(parentGames, additions)
        recyclerItems.add(GameDetailsShortDataDelegateItem(
            GameDetailsShortDataModel(
                ADDITION,
                getString(R.string.dlcs_ultimate_edition_goty),
                getString(R.string.unknown),
                newItems,
                { store.sendEffect(GameDetailsEffect.OpenAllAdditionsAndParentGames(gameId!!)) },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (!binding.recyclerView.isComputingLayout) {
                            store.sendIntent(GameDetailsIntent.GetParentGamesAndAdditions(gameId.toString()))
                        }
                    }
                }
            )
        ))
    }

    private fun getAdditionAndParentModels(
        parentGames: GamesShortDataUiList,
        additions: GamesShortDataUiList
    ): MutableList<GamesShortModel> {
        val newItems = mutableListOf<GamesShortModel>()
        newItems.addAll(getGameShortModels(additions))
        newItems.addAll(getGameShortModels(parentGames))
        return newItems
    }

    private fun getGameShortModels(items: GamesShortDataUiList): MutableList<GamesShortModel> {
        val newItems = mutableListOf<GamesShortModel>()
        items.items.forEach { gamesShortDataUi ->
            with(gamesShortDataUi) {
                newItems.add(
                    GamesShortModel(
                        gameId,
                        name,
                        rating,
                        releaseDate,
                        playtime,
                        image,
                        {
                            store.sendEffect(
                                GameDetailsEffect.OpenGameDetails(
                                    gameId,
                                    genres!!,
                                    name,
                                    releaseDate!!,
                                    image!!
                                )
                            )
                        }
                    )
                )
            }
        }
        return newItems
    }

    private fun addStores(storeLinks: StoresSellingGameUiList) {
        val stores = getStoresModels(storeLinks)
        recyclerItems.add(StoreGameDetailsDelegateItem(
            StoreGameDetailsModel(
                recyclerItems.size + 1,
                getString(R.string.where_to_buy),
                getString(R.string.unknown),
                stores,
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (!binding.recyclerView.isComputingLayout) {
                            store.sendIntent(GameDetailsIntent.GetStoresSellingGame(gameId.toString()))
                        }
                    }
                }
            )
        ))
    }

    private fun getStoresModels(items: StoresSellingGameUiList): MutableList<StoreLinkModels> {
        val stores = mutableListOf<StoreLinkModels>()
        items.items.forEachIndexed { index, link ->
            stores.add(
                with(link) {
                    StoreLinkModels(
                        index,
                        id,
                        url
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        startActivity(intent)
                    }
                }
            )
        }
        return stores
    }

    private fun getCreatorModels(creators: CreatorsGameUiList): MutableList<CreatorsModel> {

        val newItems = mutableListOf<CreatorsModel>()
        creators.items.forEachIndexed { index, creatorUi ->
            with(creatorUi) {
                newItems.add(
                    CreatorsModel(
                        index, creatorId, name, famousProjects,
                        role.stream().map { it.title }.collect(Collectors.toList()), image
                    ) {
                        store.sendEffect(
                            GameDetailsEffect.OpenCreatorDetails(
                                creatorId,
                                name,
                                role.toStringArray(),
                                famousProjects, image
                            )
                        )
                    }
                )
            }
        }
        return newItems
    }

    private fun List<RolesUi>.toStringArray(): Array<String?> {
        val array = arrayOfNulls<String>(size)
        for (i in array.indices) {
            array[i] = get(i).title
        }
        return array
    }

    private fun addCreators(creators: CreatorsGameUiList) {
        val newItems = getCreatorModels(creators)
        recyclerItems.add(
            CreatorGameDetailsDelegateItem(
                CreatorGameDetailsModel(
                    recyclerItems.size + 1,
                    getString(R.string.creators),
                    getString(R.string.unknown),
                    newItems,
                    { store.sendEffect(GameDetailsEffect.OpenAllCreators(gameId!!)) },
                    object : RecyclerEndListener {
                        override fun onEndReached() {
                            if (!binding.recyclerView.isComputingLayout) {
                                store.sendIntent(GameDetailsIntent.GetCreators(gameId.toString()))
                            }
                        }
                    }
                )
            )
        )
    }

    private fun addScreenshots(screenshotsUiList: ScreenshotsUiList) {
        val screenshots = mutableListOf<String>()
        screenshotsUiList.items.forEach {
            screenshots.add(it.image)
        }
        recyclerItems.add(
            ImagesCarouselDelegateItem(
                ImagesCarouselModel(
                    recyclerItems.size + 1,
                    getString(R.string.screenshot),
                    screenshots
                )
            )
        )
    }

    private fun addMainInfo(description: String) {
        val newItems = listOf(
            ImageViewDelegateItem(ImageViewModel(0, gameImage!!)),
            TitleTextViewDelegateItem(TitleTextViewModel(1, gameName!!)),
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    2,
                    getString(R.string.release_date),
                    releaseDate!!
                )
            ),
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    3,
                    getString(R.string.genre),
                    genres!!
                )
            ),
            SubtitleTextViewDelegateItem(
                SubtitleTextViewModel(
                    4,
                    getString(R.string.description)
                )
            ),
            DescriptionTextViewDelegateItem(DescriptionTextViewModel(5, description))
        )
        recyclerItems.addAll(newItems)
    }

    private fun initAdapter() {
        adapter.apply {
            addDelegate(StoreGameDetailsDelegate())
            addDelegate(ImageViewDelegate())
            addDelegate(ImageCarouselDelegate())
            addDelegate(DescriptionTextViewDelegate())
            addDelegate(GameDetailsShortDataDelegate())
            addDelegate(CreatorGameDetailsDelegate())
            addDelegate(SubtitleTextViewDelegate())
            addDelegate(TitleDetailsTextViewDelegate())
            addDelegate(TitleTextViewDelegate())
        }
        binding.recyclerView.adapter = adapter
    }

    private fun checkBackButtonPressed() {
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                store.sendEffect(GameDetailsEffect.NavigateBack)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ADDITIONS_AND_PARENT_ID = "AdditionsAndParentId"
        const val SAME_SERIES_ID = "SameSeriesId"
        const val GAME_ID_ARG = "gameIdArg"
        const val GAME_NAME_ARG = "gameNameArg"
        const val GAME_RELEASE_ARG = "gameReleaseArg"
        const val GAME_GENRES_ARG = "gameGenresArg"
        const val GAME_IMAGE_ARG = "gameImageArg"
        const val SAME_SERIES = 66
        const val ADDITION = 33

        fun getInstance(
            id: Long = 0,
            name: String = "",
            genres: String = "",
            released: String = "",
            image: String = ""
        ): GameDetailsFragment {
            val fragment = GameDetailsFragment()
            fragment.apply {
                gameName = name
                gameId = id
                this.genres = genres
                releaseDate = released
                gameImage = image
            }
            return fragment
        }
    }
}