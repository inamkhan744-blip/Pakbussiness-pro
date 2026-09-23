package com.example.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(
    tableName = "school_students",
    indices = [
        Index("businessId"),
        Index("className"),
        Index("rollNo")
    ]
)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val businessId: Long,
    val rollNo: String,
    val name: String,
    val fatherName: String,
    val className: String,
    val section: String = "A",
    val monthlyFee: Double = 3000.0,
    val phone: String = "",
    val address: String = "",
    val gender: String = "Male",
    val admissionDate: Long = System.currentTimeMillis(),
    val isActive: Boolean = true,
    val notes: String = ""
) {
    val formattedAdmissionDate: String
        get() {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return sdf.format(Date(admissionDate))
        }
}

@Entity(
    tableName = "school_classes",
    indices = [
        Index("businessId"),
        Index(value = ["businessId", "className", "section"], unique = true)
    ]
)
data class SchoolClassEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val businessId: Long,
    val className: String,
    val section: String = "A",
    val roomNumber: String = "",
    val classTeacher: String = "",
    val defaultMonthlyFee: Double = 3000.0,
    val maxCapacity: Int = 40
)

@Entity(
    tableName = "school_fee_vouchers",
    indices = [
        Index("businessId"),
        Index("studentId"),
        Index("monthYear"),
        Index("isPaid")
    ]
)
data class FeeVoucherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val businessId: Long,
    val voucherNumber: String,
    val studentId: Long,
    val studentName: String,
    val rollNo: String,
    val className: String,
    val section: String,
    val fatherName: String = "",
    val monthYear: String, // e.g. "September 2026"
    val tuitionFee: Double,
    val examFee: Double = 0.0,
    val labOrGenCharges: Double = 0.0, // Generator / Computer Lab charges
    val fine: Double = 0.0,
    val discount: Double = 0.0,
    val totalAmount: Double,
    val issueDate: Long = System.currentTimeMillis(),
    val dueDate: Long,
    val isPaid: Boolean = false,
    val paidDate: Long? = null,
    val paymentMethod: String = "", // "Cash", "JazzCash", "EasyPaisa", "Bank Deposit"
    val remarks: String = ""
) {
    val formattedDueDate: String
        get() = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(dueDate))

    val formattedPaidDate: String?
        get() = paidDate?.let { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it)) }

    fun isOverdue(currentTime: Long = System.currentTimeMillis()): Boolean {
        return !isPaid && currentTime > dueDate
    }
}

@Entity(
    tableName = "school_attendance",
    indices = [
        Index("businessId"),
        Index("studentId"),
        Index("dateString"),
        Index(value = ["businessId", "studentId", "dateString"], unique = true)
    ]
)
data class StudentAttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val businessId: Long,
    val studentId: Long,
    val studentName: String,
    val rollNo: String,
    val className: String,
    val section: String,
    val date: Long,
    val dateString: String, // e.g. "2026-09-20"
    val status: String, // "PRESENT", "ABSENT", "LATE", "LEAVE"
    val remarks: String = ""
)
