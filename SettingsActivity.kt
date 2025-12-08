package com.example.tictactoelab_3_4

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoelab_3_4.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        val savedLang = getSavedLang()
        updateCheckboxes(savedLang)

        binding.cbRu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbEng.isChecked = false
                changeLanguage("ru")
            }
        }

        binding.cbEng.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbRu.isChecked = false
                changeLanguage("en")
            }
        }

        binding.soundBar.progress = (MusicPlayer.getVolume() * 100).toInt()

        binding.soundBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                MusicPlayer.setVolume(progress / 100f, this@SettingsActivity)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setContentView(binding.root)

    }
    private fun changeLanguage(lang: String) {
        saveLang(lang)

        val locales = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(locales)
    }

    private fun updateCheckboxes(lang: String) {
        when (lang) {
            "ru" -> {
                binding.cbRu.isChecked = true
                binding.cbEng.isChecked = false
            }
            "en" -> {
                binding.cbRu.isChecked = false
                binding.cbEng.isChecked = true
            }
        }
    }

    private fun saveLang(lang: String) {
        getSharedPreferences("settings", MODE_PRIVATE)
            .edit()
            .putString("lang", lang)
            .apply()
    }

    private fun getSavedLang(): String {
        return getSharedPreferences("settings", MODE_PRIVATE)
            .getString("lang", "en") ?: "en"
    }
}
