package myapplication.android.pixelpal.ui.mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

abstract class MviBaseFragment<
        PartialState : MviPartialState,
        Intent : MviIntent,
        State : MviState,
        Effect : MviEffect>(@LayoutRes layoutId: Int) :
    Fragment(layoutId) {

    protected abstract val store: MviStore<PartialState, Intent, State, Effect>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            store.uiState.collect(::render)
        }

        lifecycleScope.launch {
            store.effect.collect(::resolveEffect)
        }
    }

    protected abstract fun render(state: State)

    protected abstract fun resolveEffect(effect: Effect)
}