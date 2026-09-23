package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesses")
data class BusinessEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String,
    val ownerName: String,
    val phone: String,
    val address: String,
    val currency: String = "PKR",
    val isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
