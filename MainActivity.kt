package com.example.tictactoelab_3_4

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoelab_3_4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnStartNewGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity((intent))
        }

        binding.btnSettingsMainMenu.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity((intent))
        }

        setContentView(binding.root)

        if (!MusicPlayer.isInitialized()) {
            val player = MediaPlayer.create(this, R.raw.radiohead)
            player.isLooping = true
            MusicPlayer.init(player, this)
            MusicPlayer.start()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        MusicPlayer.stop()
    }
}
