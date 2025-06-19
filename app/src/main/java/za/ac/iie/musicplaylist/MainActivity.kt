package  za.ac.iie.musicplaylist

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



class MainActivity : AppCompatActivity() {

    //Parallel arrays for the songs, artists, ratings, and comments
    private val songList = ArrayList<Song>()

    private val songName = ArrayList<String>()
    private val artistName = ArrayList<String>()
    private val rating = ArrayList<Int>()
    private val comment = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val txtStart = findViewById<TextView>(R.id.txtStart)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnExit = findViewById<Button>(R.id.btnExit)

        //button to exit the app
        btnExit.setOnClickListener {
            finish()
        }

        //button to move to the second screen
        btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("songList", ArrayList(songList))
            startActivity(intent)

        }

        //button to prompt user to enter song details
        btnAdd.setOnClickListener {
            showAddSongDialog()
        }

        // Add sample songs here
        addSampleSongs()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addSampleSongs() {
        songList.add(Song("Alternative intro", "Lucki", 5, "Great"))
        songList.add(Song("LVL", "Rocky", 4, "Masterpiece"))
        songList.add(Song("Stoned", "Lucki", 5, "Great"))
        songList.add(Song("NDA", "Billie", 4, "Masterpiece."))

    }

    private fun showAddSongDialog() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val inputName = EditText(this)
        inputName.hint = "Song Name"
        layout.addView(inputName)

        val inputArtist = EditText(this)
        inputArtist.hint = "Artist Name"
        layout.addView(inputArtist)

        val inputRating = EditText(this)
        inputRating.hint = "Rating (1-5)"
        inputRating.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        layout.addView(inputRating)

        val inputComment = EditText(this)
        inputComment.hint = "Comment"
        layout.addView(inputComment)

        AlertDialog.Builder(this)
            .setTitle("Add Song")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val name = inputName.text.toString().trim()
                val artist = inputArtist.text.toString().trim()
                val ratingValue = inputRating.text.toString().toIntOrNull() ?: -1
                val commentText = inputComment.text.toString().trim()

                if (name.isNotEmpty() && artist.isNotEmpty() && ratingValue in 1..5) {
                    songName.add(name)
                    artistName.add(artist)
                    rating.add(ratingValue)
                    comment.add(commentText)

                    Toast.makeText(this, "Song added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Invalid input. Rating must be 1-5.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
