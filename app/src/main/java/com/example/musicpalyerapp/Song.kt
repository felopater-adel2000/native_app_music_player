package com.example.musicpalyerapp

import java.io.Serializable

class Song : Serializable
{
    var title: String = ""
    get() = field
    set(value) {field = value}

    var artist: String = ""
    get() = field
    set(value) {field = value}

    var path: String = ""
    get() = field
    set(value) {field = value}

    constructor(title: String, artist: String, path: String)
    {
        this.artist = artist
        this.path = path
        this.title = title
    }
}