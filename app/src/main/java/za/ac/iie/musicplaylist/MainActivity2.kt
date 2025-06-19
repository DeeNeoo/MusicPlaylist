package za.ac.iie.musicplaylist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity() {

    private var songList = ArrayList<Song>()

    private val songName = ArrayList<String>()
    private val artistName = ArrayList<String>()
    private val rating = ArrayList<Int>()
    private val comment = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        // Retrieve the song list from MainActivity
        @Suppress("UNCHECKED_CAST")
        songList = (intent.getSerializableExtra("songList") as? ArrayList<Song> ?: ArrayList()) as ArrayList<Song>


        val btnDisplay = findViewById<Button>(R.id.btnDisplay)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnReturn = findViewById<Button>(R.id.btnReturn)

        val txtDisplayResults = findViewById<TextView>(R.id.txtDisplayResults)
        val txtAverage = findViewById<TextView>(R.id.txtAverage)


        // ✅ Return to MainActivity
        btnReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // ✅ Display Song List
        btnDisplay.setOnClickListener {
            if (songList.isEmpty()) {
                Toast.makeText(this, "No songs to display.", Toast.LENGTH_SHORT).show()
            } else {
                val builder = StringBuilder()
                for ((index, song) in songList.withIndex()) {
                    builder.append("🎵 Song ${index + 1}:\n")
                    builder.append("Name: ${song.songName}\n")
                    builder.append("Artist: ${song.artistName}\n")
                    builder.append("Rating: ${song.rating}\n")
                    builder.append("Comment: ${song.comment}\n\n")
                }
                txtDisplayResults.text = builder.toString()
            }
        }

        // ✅ Long press on Display to add a song
        btnDisplay.setOnLongClickListener {
            showAddSongDialog()
            true
        }

        // ✅ Calculate average rating
        btnCalculate.setOnClickListener {
            if (songList.isEmpty()) {
                Toast.makeText(this, "No songs to calculate.", Toast.LENGTH_SHORT).show()
            } else {
                val average = songList.map { it.rating }.average()
                txtAverage.text = "Average Rating: %.2f".format(average)
                AlertDialog.Builder(this)
                    .setTitle("Average Rating")
                    .setMessage("Average Rating: %.2f".format(average))
                    .setPositiveButton("OK", null)
                    .show()
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }

    // ✅ Add Song Dialog
    private fun showAddSongDialog() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val inputName = EditText(this).apply { hint = "Song Name" }
        val inputArtist = EditText(this).apply { hint = "Artist Name" }
        val inputRating = EditText(this).apply {
            hint = "Rating (1-5)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }
        val inputComment = EditText(this).apply { hint = "Comment" }

        layout.apply {
            addView(inputName)
            addView(inputArtist)
            addView(inputRating)
            addView(inputComment)
        }

        AlertDialog.Builder(this)
            .setTitle("Add Song")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val name = inputName.text.toString().trim()
                val artist = inputArtist.text.toString().trim()
                val ratingValue = inputRating.text.toString().toIntOrNull() ?: -1
                val commentText = inputComment.text.toString().trim()

                if (name.isNotEmpty() && artist.isNotEmpty() && ratingValue in 1..5) {
                    songList.add(Song(name, artist, ratingValue, commentText))
                    Toast.makeText(this, "Song added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Invalid input. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}