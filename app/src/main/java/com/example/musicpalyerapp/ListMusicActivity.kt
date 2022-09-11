package com.example.musicpalyerapp

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_list_music.*
import java.util.*


class ListMusicActivity : AppCompatActivity()
{
    val requestCodePermission = 99
    val songs: LinkedList<Song> = LinkedList()

    lateinit var adapter: SongsAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_music)
        adapter = SongsAdapter(this, R.layout.item_song, songs)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCodePermission)
        }
        else
        {
            //reading from external storage
            getSongs()
        }


        lv_songs.adapter = adapter
        lv_songs.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent = Intent(this, MainActivity::class.java)
            val song: Song = songs[i]
            intent.putExtra("song", song)
            startActivity(intent)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == requestCodePermission)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getSongs()
            }
        }
    }

    fun getSongs()
    {
        var contentResolver: ContentResolver = contentResolver
        var songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        var songCursor: Cursor = contentResolver.query(songUri, null, null, null, null)!!
        if(songCursor != null && songCursor.moveToFirst())
        {
            var indexTitle: Int = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            var indexArtist: Int = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            var indexPath: Int = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            while (songCursor.moveToNext()) {
                var title = songCursor.getString(indexTitle)
                var artist = songCursor.getString(indexArtist)
                var path = songCursor.getString(indexPath)
                songs.add(Song(title, artist, path))
            }
            adapter.notifyDataSetChanged()

        }
    }
}