package com.idpwf.medicationtracker.logic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.idpwf.medicationtracker.data.LocalMedicationStorage
import com.idpwf.medicationtracker.data.MedicationRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicationTrackerViewModel(application: Application) : AndroidViewModel(application) {

    private val localMedicationStorage = LocalMedicationStorage(application)
    private val dailyMedicationLogManager = DailyMedicationLogManager(application)

    // Using a StateFlow to hold the list of medications taken today.
    // This is the modern, recommended way to expose data from a ViewModel in Android.
    private val _takenToday = MutableStateFlow<Map<String, Int>>(emptyMap())
    val takenToday: StateFlow<Map<String, Int>> = _takenToday.asStateFlow()

    init {
        // Load the initial state when the ViewModel is created.
        refreshTakenToday()
    }

    private fun refreshTakenToday() {
        viewModelScope.launch {
            Log.i("MedicationTrackerViewModel", "Refreshing medication state.")
            _takenToday.value = dailyMedicationLogManager.getCurrentState().medicationsTaken
            Log.i("MedicationTrackerViewModel", "Medication state refreshed successfully.")
        }
    }

    fun takeMed(name: String) {
        viewModelScope.launch {
            Log.i("MedicationTrackerViewModel", "Taking medication: $name.")
            dailyMedicationLogManager.takeMedication(name)
            refreshTakenToday() // Refresh the public state after modification
            Log.i("MedicationTrackerViewModel", "Successfully took medication: $name.")
        }
    }

    fun addMedication(name: String, dosage: String) {
        viewModelScope.launch {
            Log.i("MedicationTrackerViewModel", "Adding new medication: $name, $dosage.")
            try {
                val medicationRecord = MedicationRecord(medicationName = name, dosage = dosage)
                localMedicationStorage.addMedication(medicationRecord)
                Log.i("MedicationTrackerViewModel", "Successfully added new medication.")
            } catch (e: Exception) {
                Log.e("MedicationTrackerViewModel", "Failed to add medication.", e)
            }
        }
    }
}
