package myapplication.android.pixelpal.ui.platforms.fragments.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmnetStoreBinding
import myapplication.android.pixelpal.di.DiContainer
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.platforms.fragments.store.model.StoresUiList
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresEffect
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresIntent
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresState
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresStore
import myapplication.android.pixelpal.ui.platforms.fragments.store.recycler_view.StoreAdapter
import myapplication.android.pixelpal.ui.platforms.fragments.store.recycler_view.StoreModel

class StoresFragment : MviBaseFragment<
        StoresPartialState,
        StoresIntent,
        StoresState,
        StoresEffect
        >(R.layout.fragmnet_store){

    private var _binding: FragmnetStoreBinding? = null
    private val binding get() = _binding!!

    override val store: StoresStore by viewModels { DiContainer.storesStoreFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmnetStoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null){
            store.sendIntent(StoresIntent.Init)
        }
        store.sendIntent(StoresIntent.GetStores)
    }

    override fun resolveEffect(effect: StoresEffect) {
        TODO("handle effects")
    }

    override fun render(state: StoresState) {
        when(state.ui){
            is LceState.Content -> {
                binding.loadingRecycler.root.visibility = GONE
                initRecycler(state.ui.data)
            }
            is LceState.Error -> {
                binding.loadingRecycler.root.visibility = GONE
                Log.e("Stores error", state.ui.throwable.message.toString())
            }
            LceState.Loading -> binding.loadingRecycler.root.visibility = VISIBLE
        }
    }

    private fun initRecycler(stores: StoresUiList){
        val adapter = StoreAdapter()
        binding.recyclerView.adapter = adapter

        val models = mutableListOf<StoreModel>()
        for (i in stores.stores){
            models.add(StoreModel(
                1,
                i.id,
                i.name,
                i.domain,
                i.projects,
                i.image,
                object : ClickListener{
                    override fun onClick() {
                        TODO("open stores details fragment")
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
        fun getInstance() = StoresFragment()
    }
}