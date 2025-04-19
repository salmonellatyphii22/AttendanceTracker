package com.example.attendancetracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AttendanceViewModel : ViewModel() {

    // LiveData to observe student list changes
    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> get() = _students

    init {
        // Initial student list (customize as needed)
        _students.value = listOf(
            Student("Sweta"),
            Student("Rahul"),
            Student("Ayesha"),
            Student("Ankit")
        )
    }

    // Update attendance status for a student at a given position
    fun markAttendance(position: Int, isPresent: Boolean) {
        _students.value = _students.value?.mapIndexed { index, student ->
            if (index == position && student.isPresent != isPresent) {
                student.copy(isPresent = isPresent)
            } else {
                student
            }
        }
    }

    // Optional utility to get present students
    fun getPresentStudents(): List<Student> {
        return _students.value?.filter { it.isPresent } ?: emptyList()
    }
}
