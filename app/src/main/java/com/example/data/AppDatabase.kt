package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        BusinessEntity::class,
        GymMemberEntity::class,
        GymCheckInEntity::class,
        RestaurantTableEntity::class,
        RestaurantMenuItemEntity::class,
        RestaurantOrderEntity::class,
        RestaurantOrderItemEntity::class,
        PatientEntity::class,
        DoctorEntity::class,
        AppointmentEntity::class,
        PrescriptionEntity::class,
        MedicineEntity::class,
        PharmacySaleEntity::class,
        StudentEntity::class,
        SchoolClassEntity::class,
        FeeVoucherEntity::class,
        StudentAttendanceEntity::class,
        PropertyEntity::class,
        LeadEntity::class,
        SiteVisitEntity::class,
        SalonServiceEntity::class,
        StylistEntity::class,
        SalonAppointmentEntity::class,
        HotelRoomEntity::class,
        HotelBookingEntity::class,
        HotelGuestEntity::class,
        TailorCustomerEntity::class,
        TailorMeasurementEntity::class,
        TailorOrderEntity::class,
        BakeryItemEntity::class,
        BakeryCakeOrderEntity::class,
        ElectronicsProductEntity::class,
        RepairTicketEntity::class,
        WorkshopVehicleEntity::class,
        WorkshopMechanicEntity::class,
        WorkshopJobCardEntity::class,
        LaundryCustomerEntity::class,
        LaundryOrderEntity::class,
        WholesalePartyEntity::class,
        WholesaleBulkOrderEntity::class,
        WholesalePaymentEntity::class,
        ExpenseEntity::class,
        StaffEntity::class,
        StaffAttendanceEntity::class
    ],
    version = 16,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun businessDao(): BusinessDao
    abstract fun gymDao(): GymDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun hospitalDao(): HospitalDao
    abstract fun pharmacyDao(): PharmacyDao
    abstract fun schoolDao(): SchoolDao
    abstract fun realEstateDao(): RealEstateDao
    abstract fun salonDao(): SalonDao
    abstract fun hotelDao(): HotelDao
    abstract fun tailorDao(): TailorDao
    abstract fun bakeryDao(): BakeryDao
    abstract fun electronicsDao(): ElectronicsDao
    abstract fun workshopDao(): WorkshopDao
    abstract fun laundryDao(): LaundryDao
    abstract fun wholesaleDao(): WholesaleDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun staffDao(): StaffDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pakbusiness_pro.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
