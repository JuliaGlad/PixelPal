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
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.FragmentCreatorDetailsBinding
import myapplication.android.pixelpal.ui.creator_details.model.CreatorArgumentsModel
import myapplication.android.pixelpal.ui.creator_details.model.CreatorDetailsUi
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsEffect
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsIntent
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsLocalDI
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsPartialState
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsState
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsStoreFactory
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.description_textview.DescriptionTextViewModel
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
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
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

    private val creatorArgumentsModel: CreatorArgumentsModel by lazy { getActivityArguments()!! }

    private fun getActivityArguments(): CreatorArgumentsModel? {
        var model: CreatorArgumentsModel? = null
        with(activity?.intent) {
            this?.let {
                model = CreatorArgumentsModel(
                    getLongExtra(Constants.CREATOR_ID, 0L),
                    getStringExtra(Constants.CREATOR_NAME)!!,
                    getStringArrayExtra(Constants.CREATOR_ROLE)!!,
                    getIntExtra(Constants.CREATOR_FAMOUS_PROJECTS, 0),
                    getStringExtra(Constants.CREATOR_IMAGE)!!,
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
        appComponent.creatorDetailsComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            store.sendIntent(CreatorDetailsIntent.Init)
        }
        store.sendIntent(CreatorDetailsIntent.GetCreatorDetails(creatorArgumentsModel.creatorId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun resolveEffect(effect: CreatorDetailsEffect) {
        when (effect) {
            CreatorDetailsEffect.NavigateBack -> activity?.finish()
        }
    }

    override fun render(state: CreatorDetailsState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                initRecycler(state.ui.data)
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("CreatorDetailsException", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun initRecycler(data: CreatorDetailsUi) {
        val adapter = initAdapter()

        var roles = ""
        for (i in creatorArgumentsModel.role) {
            roles = "$i, $roles "
        }
        val items = mutableListOf(
            RatingImageDelegateItem(
                RatingImageModel(
                    1,
                    creatorArgumentsModel.image!!,
                    data.rating
                )
            ),
            TitleTextViewDelegateItem(TitleTextViewModel(2, creatorArgumentsModel.name)),
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    3, getString(R.string.roles), roles
                )
            ),
            TitleDetailsTextViewDelegateItem(
                TitleDetailsTextViewModel(
                    4, getString(R.string.famous_projects), creatorArgumentsModel.famousProjects.toString()
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

    private fun initAdapter(): MainAdapter =
        MainAdapter().apply {
            addDelegate(RatingImageDelegate())
            addDelegate(TitleTextViewDelegate())
            addDelegate(TitleDetailsTextViewDelegate())
            addDelegate(SubtitleTextViewDelegate())
            addDelegate(DescriptionTextViewDelegate())
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}