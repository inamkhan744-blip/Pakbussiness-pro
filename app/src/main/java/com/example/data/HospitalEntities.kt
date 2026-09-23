package com.example.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity(
    tableName = "patients",
    foreignKeys = [
        ForeignKey(
            entity = BusinessEntity::class,
            parentColumns = ["id"],
            childColumns = ["businessId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["businessId"])]
)
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessId: Long,
    val name: String,
    val phone: String,
    val age: Int,
    val gender: String, // Male, Female, Other
    val bloodGroup: String = "", // A+, B+, O+, etc.
    val address: String = "",
    val medicalHistory: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "doctors",
    foreignKeys = [
        ForeignKey(
            entity = BusinessEntity::class,
            parentColumns = ["id"],
            childColumns = ["businessId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["businessId"])]
)
data class DoctorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessId: Long,
    val name: String,
    val specialization: String,
    val qualification: String,
    val consultationFeePkr: Double,
    val availableDays: String, // e.g. Mon, Tue, Wed, Thu, Fri
    val availableHours: String, // e.g. 05:00 PM - 09:00 PM
    val phone: String = "",
    val isAvailable: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "appointments",
    foreignKeys = [
        ForeignKey(
            entity = BusinessEntity::class,
            parentColumns = ["id"],
            childColumns = ["businessId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["businessId"]), Index(value = ["doctorId"]), Index(value = ["patientId"])]
)
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessId: Long,
    val patientId: Long,
    val patientName: String,
    val patientPhone: String,
    val doctorId: Long,
    val doctorName: String,
    val doctorSpecialization: String,
    val appointmentDate: Long, // timestamp (start of day)
    val timeSlot: String, // e.g. "06:30 PM"
    val tokenNumber: Int,
    val consultationFeePkr: Double,
    val status: String = "SCHEDULED", // SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    val symptoms: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "prescriptions",
    foreignKeys = [
        ForeignKey(
            entity = BusinessEntity::class,
            parentColumns = ["id"],
            childColumns = ["businessId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["businessId"]), Index(value = ["patientId"]), Index(value = ["doctorId"])]
)
data class PrescriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessId: Long,
    val appointmentId: Long? = null,
    val patientId: Long,
    val patientName: String,
    val patientAge: Int,
    val patientGender: String,
    val patientPhone: String,
    val doctorId: Long,
    val doctorName: String,
    val doctorSpecialization: String,
    val vitalsBp: String = "", // e.g. "120/80"
    val vitalsPulse: String = "", // e.g. "72 bpm"
    val vitalsTemp: String = "", // e.g. "98.6 °F"
    val vitalsWeight: String = "", // e.g. "70 kg"
    val diagnosis: String = "",
    val symptoms: String = "",
    val medicinesJson: String = "[]",
    val labTests: String = "",
    val advice: String = "",
    val followUpDays: Int = 7,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun parseMedicines(): List<PrescriptionMedicineItem> {
        return try {
            val array = JSONArray(medicinesJson)
            val list = mutableListOf<PrescriptionMedicineItem>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    PrescriptionMedicineItem(
                        name = obj.optString("name", ""),
                        dosage = obj.optString("dosage", ""),
                        duration = obj.optString("duration", ""),
                        instructions = obj.optString("instructions", "")
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        fun encodeMedicines(items: List<PrescriptionMedicineItem>): String {
            return try {
                val array = JSONArray()
                for (item in items) {
                    val obj = JSONObject().apply {
                        put("name", item.name)
                        put("dosage", item.dosage)
                        put("duration", item.duration)
                        put("instructions", item.instructions)
                    }
                    array.put(obj)
                }
                array.toString()
            } catch (e: Exception) {
                "[]"
            }
        }
    }
}

data class PrescriptionMedicineItem(
    val name: String,
    val dosage: String, // e.g. "1-0-1", "OD", "BD", "TDS"
    val duration: String, // e.g. "5 Days"
    val instructions: String // e.g. "After meal"
)
