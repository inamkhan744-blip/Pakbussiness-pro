package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "staff_members")
data class StaffEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessId: Long,
    val name: String,
    val role: String, // Manager, Salesman, Cashier, Chef, Tailor Master, Mechanic, Cleaner, Trainer, Helper
    val phone: String,
    val monthlySalary: Double,
    val advancePaid: Double = 0.0, // Peshgi
    val joiningDate: String = "",
    val status: String = "ACTIVE" // ACTIVE, INACTIVE
)

@Entity(tableName = "staff_attendance")
data class StaffAttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessId: Long,
    val staffId: Long,
    val staffName: String,
    val dateString: String, // YYYY-MM-DD
    val status: String = "PRESENT", // PRESENT, ABSENT, HALF_DAY, LEAVE
    val notes: String = ""
)

@Dao
interface StaffDao {
    @Query("SELECT * FROM staff_members WHERE businessId = :businessId ORDER BY name ASC")
    fun getStaffForBusiness(businessId: Long): Flow<List<StaffEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaff(staff: StaffEntity): Long

    @Update
    suspend fun updateStaff(staff: StaffEntity)

    @Query("DELETE FROM staff_members WHERE id = :id")
    suspend fun deleteStaffById(id: Long)

    @Query("UPDATE staff_members SET advancePaid = advancePaid + :amount WHERE id = :staffId")
    suspend fun addSalaryAdvance(staffId: Long, amount: Double)

    @Query("UPDATE staff_members SET advancePaid = 0 WHERE id = :staffId")
    suspend fun clearSalaryAdvance(staffId: Long)

    // Attendance
    @Query("SELECT * FROM staff_attendance WHERE businessId = :businessId AND dateString = :dateString")
    fun getAttendanceForDate(businessId: Long, dateString: String): Flow<List<StaffAttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordAttendance(attendance: StaffAttendanceEntity): Long

    @Query("DELETE FROM staff_attendance WHERE staffId = :staffId AND dateString = :dateString")
    suspend fun deleteAttendanceForDay(staffId: Long, dateString: String)
}

class StaffRepository(private val dao: StaffDao) {
    fun getStaff(businessId: Long): Flow<List<StaffEntity>> =
        dao.getStaffForBusiness(businessId)

    suspend fun saveStaff(staff: StaffEntity): Long =
        dao.insertStaff(staff)

    suspend fun updateStaff(staff: StaffEntity) =
        dao.updateStaff(staff)

    suspend fun deleteStaff(id: Long) =
        dao.deleteStaffById(id)

    suspend fun addAdvance(staffId: Long, amount: Double) =
        dao.addSalaryAdvance(staffId, amount)

    suspend fun clearAdvance(staffId: Long) =
        dao.clearSalaryAdvance(staffId)

    fun getAttendance(businessId: Long, dateString: String): Flow<List<StaffAttendanceEntity>> =
        dao.getAttendanceForDate(businessId, dateString)

    suspend fun recordAttendance(attendance: StaffAttendanceEntity): Long =
        dao.recordAttendance(attendance)
}
