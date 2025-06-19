package za.ac.iie.musicplaylist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    private val songName = ArrayList<String>()
    private val artistName = ArrayList<String>()
    private val rating = ArrayList<Int>()
    private val comment = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val btnDisplay = findViewById<Button>(R.id.btnDisplay)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnReturn = findViewById<Button>(R.id.btnReturn)

        btnReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnCalculate.setOnClickListener {
            if (songName.isEmpty()) {
                Toast.makeText(this, "No songs to calculate.", Toast.LENGTH_SHORT).show()
            } else {
                val average = rating.sum().toDouble() / rating.size
                AlertDialog.Builder(this)
                    .setTitle("Average Rating")
                    .setMessage("Average Rating: %.2f".format(average))
                    .setPositiveButton("OK", null)
                    .show()
            }
        }


        btnDisplay.setOnClickListener {
            if (songName.isEmpty()) {
                Toast.makeText(this, "No songs to display.", Toast.LENGTH_SHORT).show()
            } else {
                val builder = StringBuilder()
                for (i in songName.indices) {
                    builder.append("🎵 Song ${i + 1}:\n")
                    builder.append("Name: ${songName[i]}\n")
                    builder.append("Artist: ${artistName[i]}\n")
                    builder.append("Rating: ${rating[i]}\n")
                    builder.append("Comment: ${comment[i]}\n\n")
                }
                AlertDialog.Builder(this)
                    .setTitle("Song List")
                    .setMessage(builder.toString())
                    .setPositiveButton("OK", null)
                    .show()
            }

            // Open dialog when user long-presses the display button (to simulate adding a song)
            btnDisplay.setOnLongClickListener {
                showAddSongDialog()
                true
            }


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        fun showAddSongDialog() {
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
                        Toast.makeText(this, "Invalid input. Please try again.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun showAddSongDialog() {
        TODO("Not yet implemented")
    }
}