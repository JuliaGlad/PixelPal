package myapplication.android.pixelpal.ui.platforms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentPlatformsBinding
import myapplication.android.pixelpal.ui.platforms.fragments.platform.PlatformDetailsFragment
import myapplication.android.pixelpal.ui.platforms.fragments.store.StoresFragment
import myapplication.android.pixelpal.ui.platforms.pager.PagerAdapter

class PlatformsFragment: Fragment() {
    private lateinit var pagerAdapter: PagerAdapter
    private var _binding: FragmentPlatformsBinding? = null
    private val binding get() = _binding!!
    private var query: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlatformsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
        initTabs()
        initTabClickListener()
        initSearch()
    }

    private fun initSearch() {
        binding.searchItem.messageEditText.addTextChangedListener(afterTextChanged = { text ->
            query = text.toString()
            val position = binding.tabs.selectedTabPosition
            val fragment = pagerAdapter.getFragment(position)
            if (fragment is PlatformPager) fragment.updateQuery(query)
        })
    }

    private fun initTabClickListener() {
       binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = tab?.position?.let { pagerAdapter.getFragment(it) }
                if (fragment is PlatformPager) fragment.updateQuery(query)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.i("TabUnselected", tab?.id.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.i("TabReselected", tab?.id.toString())
            }
        })
    }

    private fun initTabs() {
        val tabs = listOf(
            getString(R.string.platforms),
            getString(R.string.where_to_buy)
        )
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    private fun initPager() {
        pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = pagerAdapter
        pagerAdapter.update(
            listOf(
                PlatformDetailsFragment.getInstance(),
                StoresFragment.getInstance()
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}