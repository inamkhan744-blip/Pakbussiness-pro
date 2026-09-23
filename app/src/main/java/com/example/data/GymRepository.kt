package com.example.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GymRepository(private val gymDao: GymDao) {

    private fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getMembers(businessId: Long): Flow<List<GymMemberEntity>> =
        gymDao.getMembersByBusiness(businessId)

    fun getMemberById(id: Long): Flow<GymMemberEntity?> =
        gymDao.getMemberById(id)

    suspend fun getMemberByIdDirect(id: Long): GymMemberEntity? =
        gymDao.getMemberByIdDirect(id)

    suspend fun saveMember(member: GymMemberEntity): Long {
        return if (member.id == 0L) {
            gymDao.insertMember(member)
        } else {
            gymDao.updateMember(member)
            member.id
        }
    }

    suspend fun deleteMember(id: Long) {
        gymDao.deleteMemberById(id)
    }

    suspend fun toggleCheckIn(member: GymMemberEntity): Boolean {
        val now = System.currentTimeMillis()
        val todayStr = getTodayDateString()

        return if (!member.isCheckedIn) {
            // Check in
            gymDao.updateCheckInStatus(member.id, isCheckedIn = true, checkInTime = now)
            gymDao.insertCheckIn(
                GymCheckInEntity(
                    memberId = member.id,
                    memberName = member.name,
                    businessId = member.businessId,
                    checkInTime = now,
                    checkOutTime = null,
                    dateString = todayStr
                )
            )
            true
        } else {
            // Check out
            gymDao.updateCheckInStatus(member.id, isCheckedIn = false, checkInTime = member.lastCheckInTime)
            val openCheckIn = gymDao.getOpenCheckIn(member.id, todayStr)
            if (openCheckIn != null) {
                gymDao.updateCheckIn(openCheckIn.copy(checkOutTime = now))
            } else {
                // In case open check-in wasn't found from today, record checkout
                gymDao.insertCheckIn(
                    GymCheckInEntity(
                        memberId = member.id,
                        memberName = member.name,
                        businessId = member.businessId,
                        checkInTime = member.lastCheckInTime ?: now,
                        checkOutTime = now,
                        dateString = todayStr
                    )
                )
            }
            false
        }
    }

    fun getTodayCheckIns(businessId: Long): Flow<List<GymCheckInEntity>> {
        return gymDao.getTodayCheckIns(businessId, getTodayDateString())
    }

    fun getMemberCheckIns(memberId: Long): Flow<List<GymCheckInEntity>> {
        return gymDao.getCheckInsForMember(memberId)
    }

    suspend fun ensureInitialGymData(businessId: Long) {
        val count = gymDao.getMemberCount(businessId)
        if (count == 0) {
            val now = System.currentTimeMillis()
            val dayMillis = 24L * 60L * 60L * 1000L

            val sampleMembers = listOf(
                GymMemberEntity(
                    businessId = businessId,
                    name = "Hamza Abbasi",
                    phone = "+92 300 1234567",
                    gender = "Male",
                    plan = "Annual VIP",
                    startDate = now - (30 * dayMillis),
                    durationDays = 365,
                    amountPkr = 35000.0,
                    isCheckedIn = true,
                    lastCheckInTime = now - (45 * 60 * 1000L)
                ),
                GymMemberEntity(
                    businessId = businessId,
                    name = "Ayesha Malik",
                    phone = "+92 321 7654321",
                    gender = "Female",
                    plan = "Monthly Standard",
                    startDate = now - (25 * dayMillis),
                    durationDays = 30,
                    amountPkr = 4500.0,
                    isCheckedIn = false,
                    lastCheckInTime = now - (20 * 60 * 60 * 1000L)
                ),
                GymMemberEntity(
                    businessId = businessId,
                    name = "Bilal Farooq",
                    phone = "+92 333 9988776",
                    gender = "Male",
                    plan = "Quarterly (3 Mos)",
                    startDate = now - (85 * dayMillis),
                    durationDays = 90,
                    amountPkr = 11000.0,
                    isCheckedIn = false,
                    lastCheckInTime = now - (2 * dayMillis)
                ),
                GymMemberEntity(
                    businessId = businessId,
                    name = "Zainab Tariq",
                    phone = "+92 345 5566778",
                    gender = "Female",
                    plan = "CrossFit & Cardio",
                    startDate = now - (10 * dayMillis),
                    durationDays = 30,
                    amountPkr = 6000.0,
                    isCheckedIn = true,
                    lastCheckInTime = now - (15 * 60 * 1000L)
                )
            )

            sampleMembers.forEach { member ->
                val id = gymDao.insertMember(member)
                if (member.isCheckedIn) {
                    gymDao.insertCheckIn(
                        GymCheckInEntity(
                            memberId = id,
                            memberName = member.name,
                            businessId = businessId,
                            checkInTime = member.lastCheckInTime ?: now,
                            checkOutTime = null,
                            dateString = getTodayDateString()
                        )
                    )
                }
            }
        }
    }
}
