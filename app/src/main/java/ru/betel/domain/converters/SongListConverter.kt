package ru.betel.domain.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.betel.domain.model.entity.SongEntity

class SongListConverter {
    @TypeConverter
    fun fromSongList(songList: List<SongEntity>): String {
        val gson = Gson()
        return gson.toJson(songList)
    }
    @TypeConverter
    fun toSongList(songListString: String): List<SongEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<SongEntity>>() {}.type
        return gson.fromJson(songListString, type)
    }
}
