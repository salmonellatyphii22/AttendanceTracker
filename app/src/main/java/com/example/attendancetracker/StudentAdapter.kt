package com.example.attendancetracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetracker.databinding.ItemStudentBinding

class StudentAdapter(
    private var students: List<Student>,
    private val onCheckedChange: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        // Avoid re-triggering listener when setting checkbox state programmatically
        holder.binding.checkboxPresent.setOnCheckedChangeListener(null)

        // Set student name and attendance checkbox state
        holder.binding.tvName.text = student.name
        holder.binding.checkboxPresent.isChecked = student.isPresent

        // Set listener after state is applied
        holder.binding.checkboxPresent.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(position, isChecked)
        }
    }

    override fun getItemCount(): Int = students.size

    // Call this method to update the data and refresh the RecyclerView
    fun updateList(newList: List<Student>) {
        if (students != newList) {
            students = newList
            notifyDataSetChanged() // Consider using DiffUtil for better performance on large lists
        }
    }
}
