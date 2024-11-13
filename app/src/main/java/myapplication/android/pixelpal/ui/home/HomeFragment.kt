package myapplication.android.pixelpal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import myapplication.android.pixelpal.app.App
import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.databinding.FragmentHomeBinding
import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesModel

class HomeFragment : Fragment() {
    private lateinit var games: List<ReleasesModel>
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}