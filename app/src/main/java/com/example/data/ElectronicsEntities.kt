package com.example.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(
    tableName = "electronics_products",
    indices = [
        Index(value = ["businessId"]),
        Index(value = ["category"]),
        Index(value = ["imei1"]),
        Index(value = ["serialNumber"])
    ]
)
data class ElectronicsProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val businessId: Long,
    val name: String,
    val brand: String, // "Samsung", "Apple", "Xiaomi", "Infinix", "Vivo", "Tecno", "Realme", "Dell", "HP", "Other"
    val category: String, // "Smartphones", "Feature Phones", "Laptops", "Tablets", "Smart Watches", "Audio & Accessories"
    val condition: String = "Brand New", // "Brand New", "Used (Kit Only)", "Refurbished", "Box Open"
    val imei1: String = "",
    val imei2: String = "",
    val serialNumber: String = "",
    val ptaStatus: String = "PTA Approved", // "PTA Approved", "Non-PTA", "CPID / Patch", "Not Applicable"
    val storageSpecs: String = "", // e.g. "8GB / 128GB", "256GB NVMe"
    val color: String = "",
    val purchasePrice: Double,
    val salePrice: Double,
    val stockQuantity: Int = 1,
    val warrantyType: String = "1 Year Official", // "1 Year Official", "Company Warranty", "7 Days Checking", "No Warranty"
    val warrantyExpiryDate: String = "", // "YYYY-MM-DD"
    val supplierName: String = "",
    val purchaseDate: String = "",
    val notes: String = ""
) {
    val isWarrantyActive: Boolean
        get() {
            if (warrantyExpiryDate.isBlank()) return false
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val exp = sdf.parse(warrantyExpiryDate)
                val today = sdf.parse(sdf.format(Date()))
                exp != null && !exp.before(today)
            } catch (e: Exception) {
                false
            }
        }
}

@Entity(
    tableName = "electronics_repair_tickets",
    indices = [
        Index(value = ["businessId"]),
        Index(value = ["status"]),
        Index(value = ["ticketNumber"]),
        Index(value = ["customerPhone"])
    ]
)
data class RepairTicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val businessId: Long,
    val ticketNumber: String,
    val customerName: String,
    val customerPhone: String,
    val deviceModel: String, // e.g. "iPhone 13 Pro", "Samsung S21 FE", "Infinix Note 30"
    val deviceColor: String = "",
    val imeiOrSerial: String = "",
    val devicePasscode: String = "", // Screen unlock pin/pattern
    val problemDescription: String, // "Display glass broken, touch unresponsive", "Battery replacement & charging IC"
    val conditionNotes: String = "Minor scratches, no SIM tray",
    val assignedTechnician: String = "Master Farooq",
    val status: String = "RECEIVED", // "RECEIVED", "DIAGNOSING", "IN_REPAIR", "READY", "DELIVERED", "CANCELLED"
    val estimatedCost: Double,
    val advancePaid: Double = 0.0,
    val remainingBalance: Double = estimatedCost - advancePaid,
    val receivedDate: String, // "YYYY-MM-DD"
    val expectedDeliveryDate: String, // "YYYY-MM-DD"
    val completedDate: String = "",
    val technicianRemarks: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
