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

        val data = getSettingsInfo()

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
    private fun updateSoundValue(progress: Int){
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()){
            putInt("sound_progress", progress)
            apply()
        }
    }
    private fun getSettingsInfo(): Int{
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)){
            val soundProgress = getInt("soundProgress", 0)
            return soundProgress
        }

    }
}