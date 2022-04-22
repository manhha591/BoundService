package com.example.boundservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.boundservice.MusicBoundService.MyBinder


class MainActivity : AppCompatActivity() {

    private var musicBoundService: MusicBoundService? = null
    private var isServiceCOnnected = false
    private var mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val myBinder = iBinder as MyBinder
            musicBoundService = myBinder.getMusicBoundService()
            musicBoundService?.startMusic()
            isServiceCOnnected = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            isServiceCOnnected = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStartServive = findViewById<Button>(R.id.btn_start_service)
        val btnStopService = findViewById<Button>(R.id.btn_stop_service)
        btnStartServive.setOnClickListener { onClickStartService() }
        btnStopService.setOnClickListener { onClickStopService() }
    }

    private fun onClickStopService() {
        if (isServiceCOnnected) {
            unbindService(mServiceConnection)
            isServiceCOnnected = false
        }
    }

    private fun onClickStartService() {
        val intent = Intent(this, MusicBoundService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }
}