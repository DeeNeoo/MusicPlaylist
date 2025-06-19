package za.ac.iie.musicplaylist

data class Song(
    val songName: String,
    val artistName: String,
    val rating: Int,
    val comment: String
)
class ArrayList{
    private val songs = ArrayList<Song>()

    fun addSong(songName: String, artistName: String, rating: Int, comment: String) {
        val song = Song(songName, artistName, rating, comment)
        songs.add(song)
    }

    fun getAllSongs(): String {
        val builder = StringBuilder()
        for (song in songs) {
            builder.append("Song: ${song.songName}\n")
            builder.append("Artist: ${song.artistName}\n")
            builder.append("Rating: ${song.rating}\n")
            builder.append("Comments: ${song.comment}\n\n")
        }
        return builder.toString()
    }

}


