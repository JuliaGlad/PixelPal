package myapplication.android.pixelpal.ui.profile.signing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.databinding.ActivityCreateAccountBinding

class AccountCreationActivity : AppCompatActivity() {

    private var _binding: ActivityCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}