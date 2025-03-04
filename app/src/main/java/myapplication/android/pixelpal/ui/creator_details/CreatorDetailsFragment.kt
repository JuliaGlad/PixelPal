package myapplication.android.pixelpal.ui.creator_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentCreatorDetailsBinding
import myapplication.android.pixelpal.di.DaggerAppComponent
import myapplication.android.pixelpal.ui.creator_details.di.DaggerCreatorsDetailsComponent
import myapplication.android.pixelpal.ui.creator_details.model.CreatorArgumentsModel
import myapplication.android.pixelpal.ui.creator_details.model.CreatorDetailsResultUi
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsEffect
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsIntent
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsLocalDI
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsPartialState
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsState
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsStoreFactory
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewModel
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data.GameDetailsShortDataModel
import myapplication.android.pixelpal.ui.delegates.delegates.rating_image.RatingImageDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.rating_image.RatingImageDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.rating_image.RatingImageModel
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
import javax.inject.Inject

class CreatorDetailsFragment : MviBaseFragment<
        CreatorDetailsPartialState,
        CreatorDetailsIntent,
        CreatorDetailsState,
        CreatorDetailsEffect>(R.layout.fragment_creator_details) {

    private var _binding: FragmentCreatorDetailsBinding? = null
    private val binding get() = _binding!!
    private val itemsRecycler = mutableListOf<DelegateItem>()
    private val adapter by lazy { initAdapter() }
    private var isUpdated = false
    private val creatorArgumentsModel: CreatorArgumentsModel by lazy { getActivityArguments()!! }

    private fun getActivityArguments(): CreatorArgumentsModel? {
        var model: CreatorArgumentsModel? = null
        with(activity?.intent) {
            this?.let {
                model = CreatorArgumentsModel(
                    getLongExtra(CREATOR_ID, 0L),
                    getStringExtra(CREATOR_NAME)!!,
                    getStringArrayExtra(CREATOR_ROLE)!!,
                    getIntExtra(CREATOR_FAMOUS_PROJECTS, 0),
                    getStringExtra(CREATOR_IMAGE)!!,
                )
            }
        }
        return model
    }

    @Inject
    lateinit var localDI: CreatorDetailsLocalDI

    override val store: MviStore<
            CreatorDetailsPartialState,
            CreatorDetailsIntent,
            CreatorDetailsState,
            CreatorDetailsEffect
            > by viewModels { CreatorDetailsStoreFactory(localDI.actor, localDI.reducer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerCreatorsDetailsComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    private fun initButtonBack() {
        binding.iconBack.setOnClickListener { store.sendEffect(CreatorDetailsEffect.NavigateBack) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorDetailsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonBack()
        if (savedInstanceState == null) {
            store.sendIntent(CreatorDetailsIntent.Init)
        }
        store.sendIntent(CreatorDetailsIntent.GetCreatorDetails(creatorArgumentsModel.creatorId))
    }

    override fun resolveEffect(effect: CreatorDetailsEffect) {
        when (effect) {
            CreatorDetailsEffect.NavigateBack -> activity?.finish()
            is CreatorDetailsEffect.OpenGameDetails -> {
                with(effect) {
                    (activity as CreatorDetailsActivity).openGameDetailsActivity(
                        gameId, name, genres, releaseDate, image
                    )
                }
            }
        }
    }

    override fun render(state: CreatorDetailsState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                if (!isUpdated) {
                    initRecycler(state.ui.data)
                } else {
                    updateRecycler(state.ui.data.newItems!!)
                }
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("CreatorDetailsException", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun updateRecycler(data: GamesMainInfoListUi) {
        isUpdated = false
        val newItems = getCreatorGames(data)
        for (i in itemsRecycler) {
            if (i is GameDetailsShortDataDelegateItem) {
                adapter.notifyItemChanged(itemsRecycler.indexOf(i), newItems)
            }
        }
    }

    private fun initRecycler(data: CreatorDetailsResultUi) {
        val roles = getRoles()
        val games: MutableList<GamesShortModel> = getCreatorGames(data.games)
        itemsRecycler.addAll(
            listOf(
                RatingImageDelegateItem(
                    RatingImageModel(
                        1,
                        creatorArgumentsModel.image!!,
                        data.creatorDetails.rating
                    )
                ),
                TitleTextViewDelegateItem(TitleTextViewModel(2, creatorArgumentsModel.name)),
                TitleDetailsTextViewDelegateItem(
                    TitleDetailsTextViewModel(
                        3, getString(R.string.roles), roles
                    )
                ),
                SubtitleTextViewDelegateItem(
                    SubtitleTextViewModel(
                        5,
                        getString(R.string.description)
                    )
                ),
                DescriptionTextViewDelegateItem(
                    DescriptionTextViewModel(
                        6,
                        data.creatorDetails.description
                    )
                ),
                GameDetailsShortDataDelegateItem(
                    GameDetailsShortDataModel(
                        7,
                        getString(R.string.famous_projects),
                        getString(R.string.unknown),
                        games,
                        {
                            (activity as CreatorDetailsActivity).openAllGamesActivity(
                                creatorArgumentsModel.creatorId
                            )
                        },
                        object : RecyclerEndListener {
                            override fun onEndReached() {
                                isUpdated = true
                                store.sendIntent(
                                    CreatorDetailsIntent.GetGamesByCreators(
                                        creatorArgumentsModel.creatorId
                                    )
                                )
                            }
                        }
                    )
                )
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.submitList(itemsRecycler)
    }

    private fun getCreatorGames(items: GamesMainInfoListUi): MutableList<GamesShortModel> {
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
                                CreatorDetailsEffect.OpenGameDetails(
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

    private fun getRoles(): String {
        var roles = ""
        for (i in creatorArgumentsModel.role) {
            roles = "$i, $roles "
        }
        return roles
    }

    private fun initAdapter(): MainAdapter =
        MainAdapter().apply {
            addDelegate(RatingImageDelegate())
            addDelegate(TitleTextViewDelegate())
            addDelegate(TitleDetailsTextViewDelegate())
            addDelegate(SubtitleTextViewDelegate())
            addDelegate(DescriptionTextViewDelegate())
            addDelegate(GameDetailsShortDataDelegate())
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val CREATOR_ID = "CreatorId"
        const val CREATOR_NAME = "CreatorName"
        const val CREATOR_ROLE = "CreatorRoles"
        const val CREATOR_FAMOUS_PROJECTS = "CreatorFamousProjects"
        const val CREATOR_IMAGE = "CreatorImage"
    }

}