package com.example.musicpalyerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.*

class SongsAdapter : ArrayAdapter<Song>
{


    constructor(context: Context, resource: Int, data: LinkedList<Song>) : super(context, resource, data)
    {}


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_song, null)
        val lbl_title: TextView = view.findViewById(R.id.lbl_title)
        val lbl_aritst: TextView = view.findViewById(R.id.lbl_artist)

        var song: Song = getItem(position)!!
        lbl_aritst.text = song.artist
        lbl_title.text = song.title


        return view
    }
}