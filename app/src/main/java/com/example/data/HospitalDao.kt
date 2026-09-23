package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HospitalDao {

    // --- Patients ---

    @Query("SELECT * FROM patients WHERE businessId = :businessId ORDER BY createdAt DESC")
    fun getPatients(businessId: Long): Flow<List<PatientEntity>>

    @Query("""
        SELECT * FROM patients 
        WHERE businessId = :businessId 
        AND (name LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%' OR medicalHistory LIKE '%' || :query || '%')
        ORDER BY name ASC
    """)
    fun searchPatients(businessId: Long, query: String): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patients WHERE id = :id")
    fun getPatientById(id: Long): Flow<PatientEntity?>

    @Query("SELECT * FROM patients WHERE id = :id")
    suspend fun getPatientByIdDirect(id: Long): PatientEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: PatientEntity): Long

    @Update
    suspend fun updatePatient(patient: PatientEntity)

    @Query("DELETE FROM patients WHERE id = :id")
    suspend fun deletePatientById(id: Long)

    @Query("SELECT COUNT(*) FROM patients WHERE businessId = :businessId")
    suspend fun getPatientCount(businessId: Long): Int

    // --- Doctors ---

    @Query("SELECT * FROM doctors WHERE businessId = :businessId ORDER BY name ASC")
    fun getDoctors(businessId: Long): Flow<List<DoctorEntity>>

    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getDoctorById(id: Long): Flow<DoctorEntity?>

    @Query("SELECT * FROM doctors WHERE id = :id")
    suspend fun getDoctorByIdDirect(id: Long): DoctorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: DoctorEntity): Long

    @Update
    suspend fun updateDoctor(doctor: DoctorEntity)

    @Query("DELETE FROM doctors WHERE id = :id")
    suspend fun deleteDoctorById(id: Long)

    @Query("UPDATE doctors SET isAvailable = :isAvailable WHERE id = :id")
    suspend fun setDoctorAvailability(id: Long, isAvailable: Boolean)

    @Query("SELECT COUNT(*) FROM doctors WHERE businessId = :businessId")
    suspend fun getDoctorCount(businessId: Long): Int

    // --- Appointments ---

    @Query("SELECT * FROM appointments WHERE businessId = :businessId ORDER BY appointmentDate DESC, tokenNumber ASC")
    fun getAppointments(businessId: Long): Flow<List<AppointmentEntity>>

    @Query("""
        SELECT * FROM appointments 
        WHERE businessId = :businessId AND appointmentDate >= :startOfDay AND appointmentDate <= :endOfDay
        ORDER BY tokenNumber ASC
    """)
    fun getAppointmentsForDate(businessId: Long, startOfDay: Long, endOfDay: Long): Flow<List<AppointmentEntity>>

    @Query("""
        SELECT MAX(tokenNumber) FROM appointments 
        WHERE businessId = :businessId AND appointmentDate >= :startOfDay AND appointmentDate <= :endOfDay
    """)
    suspend fun getMaxTokenNumberForDate(businessId: Long, startOfDay: Long, endOfDay: Long): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity): Long

    @Update
    suspend fun updateAppointment(appointment: AppointmentEntity)

    @Query("UPDATE appointments SET status = :status WHERE id = :id")
    suspend fun updateAppointmentStatus(id: Long, status: String)

    @Query("DELETE FROM appointments WHERE id = :id")
    suspend fun deleteAppointmentById(id: Long)

    // --- Prescriptions ---

    @Query("SELECT * FROM prescriptions WHERE businessId = :businessId ORDER BY createdAt DESC")
    fun getPrescriptions(businessId: Long): Flow<List<PrescriptionEntity>>

    @Query("SELECT * FROM prescriptions WHERE patientId = :patientId ORDER BY createdAt DESC")
    fun getPrescriptionsByPatient(patientId: Long): Flow<List<PrescriptionEntity>>

    @Query("SELECT * FROM prescriptions WHERE id = :id")
    fun getPrescriptionById(id: Long): Flow<PrescriptionEntity?>

    @Query("SELECT * FROM prescriptions WHERE id = :id")
    suspend fun getPrescriptionByIdDirect(id: Long): PrescriptionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrescription(prescription: PrescriptionEntity): Long

    @Update
    suspend fun updatePrescription(prescription: PrescriptionEntity)

    @Query("DELETE FROM prescriptions WHERE id = :id")
    suspend fun deletePrescriptionById(id: Long)
}
