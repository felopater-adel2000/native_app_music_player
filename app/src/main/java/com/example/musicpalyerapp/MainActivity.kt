package com.example.musicpalyerapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val song: Song = intent.getSerializableExtra("song") as Song
        txt_artist.text = song.artist
        txt_title.text = song.title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(song.path)
        mediaPlayer.prepare()
        //mediaPlayer.isLooping = true
        mediaPlayer.seekTo(0)
        mediaPlayer.setVolume(0.5f, 0.5f)

        var duration = milliSecondToString(mediaPlayer.duration)
        txt_duration.text = duration

        sb_time.max = mediaPlayer.duration


        btn_onOf.setOnClickListener{
            if(mediaPlayer.isPlaying)
            {
                mediaPlayer.pause()
                //btn_onOf.setImageResource(R.drawable.ic_play)
                btn_onOf.setBackgroundResource(R.drawable.ic_play)
            }
            else
            {
                mediaPlayer.start()
                btn_onOf.setBackgroundResource(R.drawable.ic_pause)
            }
        }

        sb_volum.progress = 50

        //sb_volum.setOnSeekBarChangeListener()
        sb_volum.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean)
            {
                var volume = progress / 100f
                mediaPlayer.setVolume(volume, volume)

            }
            override fun onStartTrackingTouch(seek: SeekBar)
            {
            }
            override fun onStopTrackingTouch(seek: SeekBar)
            {
            }

        })

        sb_time.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seek: SeekBar)
            {
            }
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean)
            {
                if(fromUser)
                {
                    mediaPlayer.seekTo(progress)
                    sb_time.progress = progress
                }
            }
            override fun onStopTrackingTouch(seek: SeekBar)
            {
            }
        })

        Thread {
            while (true) {
                if (mediaPlayer.isPlaying) {
                    try {
                        val current = mediaPlayer.currentPosition
                        var duration = mediaPlayer.duration
                        val position = (100.0 / duration) * current
                        val elapsedTime = milliSecondToString(current)
                        runOnUiThread{
                            txt_time.text = elapsedTime
                            sb_time.progress = current
                        }
                        Thread.sleep(1000)
                    } catch (e: Exception) {
                    }
                }
            }
        }.start()
    }


    fun milliSecondToString(time: Int): String
    {
        var duration = ""
        val minutes = time / 1000 / 60
        val seconds = time / 1000 % 60
        if(seconds < 10)
        {
            duration = "$minutes:0$seconds"
        }
        else
        {
            duration = "$minutes:$seconds"
        }
        return duration
    }

    override fun onDestroy()
    {
        super.onDestroy()
        if(mediaPlayer.isPlaying) mediaPlayer.stop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId == android.R.id.home)
        {
            finish()
            if(mediaPlayer.isPlaying)
            {
                mediaPlayer.stop()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}