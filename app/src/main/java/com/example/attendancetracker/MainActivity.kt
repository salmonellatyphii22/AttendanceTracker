package com.example.attendancetracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter
    private val viewModel: AttendanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize adapter with empty list and pass attendance change callback
        adapter = StudentAdapter(emptyList()) { position, isPresent ->
            viewModel.markAttendance(position, isPresent)
        }

        // Set up RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        // Observe LiveData from ViewModel and update adapter's data
        viewModel.students.observe(this) { students ->
            adapter.updateList(students)
        }

        // Handle Submit button click
        binding.btnSubmit.setOnClickListener {
            val presentStudents = viewModel.students.value
                ?.filter { it.isPresent }
                ?.map { it.name }

            if (!presentStudents.isNullOrEmpty()) {
                // Navigate to PresentStudentsActivity and pass the list
                val intent = Intent(this, PresentStudentsActivity::class.java).apply {
                    putStringArrayListExtra("PRESENT_STUDENTS", ArrayList(presentStudents))
                }
                startActivity(intent)
            } else {
                // If no students marked, show a message
                binding.tvResult.text = "No students are marked as present."
            }
        }
    }
}
