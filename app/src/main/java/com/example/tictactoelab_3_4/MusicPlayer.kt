package com.example.tictactoelab_3_4

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {

    private var mediaPlayer: MediaPlayer? = null
    private var volume: Float = 0.5f
    fun init(player: MediaPlayer, context: Context) {
        mediaPlayer = player
        loadVolume(context)
        mediaPlayer?.setVolume(volume, volume)
    }

    fun setVolume(value: Float, context: Context? = null) {
        volume = value
        mediaPlayer?.setVolume(volume, volume)
        context?.let { saveVolume(it) }
    }

    fun getVolume(): Float = volume

    fun start() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private fun saveVolume(context: Context) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putFloat("volume", volume).apply()
    }
    private fun loadVolume(context: Context) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        volume = prefs.getFloat("volume", 0.5f)
    }
    fun isInitialized(): Boolean = mediaPlayer != null

}