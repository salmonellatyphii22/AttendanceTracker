package com.example.attendancetracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PresentStudentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentstudentsactivity)

        // Get the list of present students (including manually added names) from the intent
        val presentStudents = intent.getStringArrayListExtra("PRESENT_STUDENTS")

        // Get the TextView where you want to display the names
        val tvPresentStudents = findViewById<TextView>(R.id.tvPresentStudents)

        // Set the names to the TextView
        tvPresentStudents.text = presentStudents?.joinToString(", ")
    }
}
