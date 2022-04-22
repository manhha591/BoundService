package com.example.boundservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MusicBoundService: Service() {
    private val myBinder = MyBinder()
    private var mMediaPlayer: MediaPlayer? = null

    inner class MyBinder : Binder() {

            fun getMusicBoundService() : MusicBoundService = this@MusicBoundService
            // BoundService getBoundService (){
            // return BoundService.this;

        }

    override fun onCreate() {
        super.onCreate()
        Log.e("MusicBoundService", "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e("MusicBoundService", "onBind")
        return myBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("MusicBoundService", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MusicBoundService", "onDestroy")
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    fun startMusic() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.music)
        }
        mMediaPlayer!!.start()
    }
}