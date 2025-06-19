package za.ac.iie.musicplaylist

class arrayListOf{
    private val songName = ArrayListOf<String>()
    private val artistName = ArrayListOf<String>()
    private val rating = ArrayListOf<Int>()
    private val comment = ArrayListOf<String>()


}
// Method to add a song
fun addSong(title: String, artist: String, rating: Int, comment: String) {
    songName.add(title)
    artistName.add(artist)
    songRating.add(rating)
    songComment.add(comment)
}
