package myapplication.android.pixelpal.ui.games.games

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import myapplication.android.pixelpal.databinding.FragmentGamesBinding

class FragmentGames: Fragment() {
    private var id: Long = 0
    private var title: String = ""
    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance(id: Long, title: String) =
           FragmentGames().apply {
               this.id = id
               this.title = title
           }
    }
}