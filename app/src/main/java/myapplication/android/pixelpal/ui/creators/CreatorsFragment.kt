package myapplication.android.pixelpal.ui.creators

import android.os.Bundle
import android.util.AttributeSet
import android.util.Xml
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentCreatorsBinding
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.custom_view.flexBox.CreatorView
import myapplication.android.pixelpal.ui.custom_view.flexBox.FlexBoxLayout
import myapplication.android.pixelpal.ui.listener.ClickIntegerListener


class CreatorsFragment : Fragment() {
    private val viewModel: CreatorsViewModel by viewModels()
    private val roles: MutableList<RolesUi> = mutableListOf()
    private var _binding: FragmentCreatorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserves()
    }

    private fun setupObserves() {
        lifecycleScope.launch {
            viewModel.roles.collect { items ->
                items.forEach {
                    roles.add(it)
                    binding.flexBox.addCreators(it.title, items.indexOf(it))
                }
                (binding.flexBox.children.first() as CreatorView).isChosen = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun FlexBoxLayout.addCreators(title: String, id: Int) {
        val view = CreatorView(ContextThemeWrapper(requireContext(), R.style.CreatorItemStyle))
        val params = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        with(view) {
            viewId = id
            creator = title
            background =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.creator_bg_unselected,
                    context.theme
                )
            foreground =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.round_ripple,
                    context.theme
                )
            clickListener =
                object : ClickIntegerListener {
                    override fun onClick(int: Int) {
                        binding.flexBox.forEach {
                            (it as CreatorView).isChosen = it.viewId == int
                        }
                    }
                }
            onViewClicked()
        }
        addView(view, childCount, params)
    }
}