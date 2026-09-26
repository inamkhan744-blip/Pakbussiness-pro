package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.BottomNavTab
import com.example.ui.BusinessViewModel
import com.example.ui.Screen
import com.example.ui.screens.AppLockScreen
import com.example.ui.screens.BusinessSetupScreen
import com.example.ui.screens.MainAppScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.PakBusinessTheme

class MainActivity : ComponentActivity() {

    private val viewModel: BusinessViewModel by viewModels {
        BusinessViewModel.provideFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark by viewModel.isDarkMode.collectAsStateWithLifecycle()
            PakBusinessTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PakBusinessApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun PakBusinessApp(
    viewModel: BusinessViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val activeBusiness by viewModel.activeBusiness.collectAsStateWithLifecycle()
    val businesses by viewModel.businesses.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val appLanguage by viewModel.appLanguage.collectAsStateWithLifecycle()

    val gymMembers by viewModel.gymMembers.collectAsStateWithLifecycle()
    val todayGymCheckIns by viewModel.todayGymCheckIns.collectAsStateWithLifecycle()
    val selectedMember by viewModel.selectedMember.collectAsStateWithLifecycle()
    val isAddEditOpen by viewModel.isAddEditMemberOpen.collectAsStateWithLifecycle()
    val memberToEdit by viewModel.memberToEdit.collectAsStateWithLifecycle()

    val restaurantTables by viewModel.restaurantTables.collectAsStateWithLifecycle()
    val restaurantMenuItems by viewModel.restaurantMenuItems.collectAsStateWithLifecycle()
    val activeKotItems by viewModel.activeKotItems.collectAsStateWithLifecycle()
    val selectedRestaurantTable by viewModel.selectedRestaurantTable.collectAsStateWithLifecycle()
    val currentTableOrder by viewModel.currentTableOrder.collectAsStateWithLifecycle()
    val currentTableOrderItems by viewModel.currentTableOrderItems.collectAsStateWithLifecycle()

    val hospitalPatients by viewModel.hospitalPatients.collectAsStateWithLifecycle()
    val hospitalDoctors by viewModel.hospitalDoctors.collectAsStateWithLifecycle()
    val hospitalAppointments by viewModel.hospitalAppointments.collectAsStateWithLifecycle()
    val hospitalPrescriptions by viewModel.hospitalPrescriptions.collectAsStateWithLifecycle()

    val pharmacyMedicines by viewModel.pharmacyMedicines.collectAsStateWithLifecycle()
    val pharmacySales by viewModel.pharmacySales.collectAsStateWithLifecycle()

    val schoolStudents by viewModel.schoolStudents.collectAsStateWithLifecycle()
    val schoolClasses by viewModel.schoolClasses.collectAsStateWithLifecycle()
    val schoolFeeVouchers by viewModel.schoolFeeVouchers.collectAsStateWithLifecycle()
    val schoolAttendanceRecords by viewModel.schoolAttendanceRecords.collectAsStateWithLifecycle()
    val selectedAttendanceDate by viewModel.selectedAttendanceDate.collectAsStateWithLifecycle()
    val isAppLocked by viewModel.isAppLocked.collectAsStateWithLifecycle()
    val securityUsername by viewModel.securityUsername.collectAsStateWithLifecycle()

    if (isAppLocked) {
        AppLockScreen(
            savedUsername = securityUsername,
            onUnlock = { password -> viewModel.unlockApp(password) },
            onBypassSecurity = { viewModel.bypassSecurity() },
            appLanguage = appLanguage,
            businessName = activeBusiness?.name ?: "PakBusiness Pro"
        )
    } else {
        Crossfade(
            targetState = currentScreen,
            label = "screenTransition",
            modifier = modifier
        ) { screen ->
        when (screen) {
            Screen.SPLASH -> {
                SplashScreen()
            }
            Screen.BUSINESS_SETUP -> {
                val hasExistingBusinesses = businesses.isNotEmpty()
                BusinessSetupScreen(
                    onSaveBusiness = { name, type, owner, phone, address, currency, logoUri, tagline ->
                        viewModel.createBusiness(
                            name = name,
                            type = type,
                            ownerName = owner,
                            phone = phone,
                            address = address,
                            currency = currency,
                            logoUri = logoUri,
                            tagline = tagline,
                            onSuccess = {
                                viewModel.navigateTo(Screen.MAIN_APP)
                            }
                        )
                    },
                    canCancel = hasExistingBusinesses,
                    onCancel = {
                        if (hasExistingBusinesses) {
                            viewModel.navigateTo(Screen.MAIN_APP)
                        }
                    },
                    appLanguage = appLanguage,
                    onSelectLanguage = { viewModel.setAppLanguage(it) },
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { viewModel.toggleDarkMode() }
                )
            }
            Screen.MAIN_APP -> {
                MainAppScreen(
                    activeBusiness = activeBusiness,
                    allBusinesses = businesses,
                    currentTab = currentTab,
                    gymMembers = gymMembers,
                    todayGymCheckIns = todayGymCheckIns,
                    selectedMember = selectedMember,
                    isAddEditOpen = isAddEditOpen,
                    memberToEdit = memberToEdit,
                    onSelectMember = { id ->
                        viewModel.selectGymMember(id)
                    },
                    onOpenAddMember = {
                        viewModel.openAddMember()
                    },
                    onOpenEditMember = { member ->
                        viewModel.openEditMember(member)
                    },
                    onCloseAddEdit = {
                        viewModel.closeAddEditMember()
                    },
                    onSaveMember = { name, phone, gender, plan, startDate, durationDays, amount ->
                        viewModel.saveGymMember(
                            name = name,
                            phone = phone,
                            gender = gender,
                            plan = plan,
                            startDate = startDate,
                            durationDays = durationDays,
                            amountPkr = amount,
                            id = memberToEdit?.id ?: 0L
                        )
                    },
                    onDeleteMember = { id ->
                        viewModel.deleteGymMember(id)
                    },
                    onToggleCheckIn = { member ->
                        viewModel.toggleGymCheckIn(member)
                    },
                    getMemberCheckIns = { memberId ->
                        viewModel.getMemberCheckIns(memberId)
                    },
                    restaurantTables = restaurantTables,
                    restaurantMenuItems = restaurantMenuItems,
                    activeKotItems = activeKotItems,
                    selectedRestaurantTable = selectedRestaurantTable,
                    currentTableOrder = currentTableOrder,
                    currentTableOrderItems = currentTableOrderItems,
                    onSelectRestaurantTable = { table ->
                        viewModel.selectRestaurantTable(table?.id)
                    },
                    onSaveRestaurantTable = { name, capacity, section, id ->
                        viewModel.saveRestaurantTable(name, capacity, section, id)
                    },
                    onDeleteRestaurantTable = { id ->
                        viewModel.deleteRestaurantTable(id)
                    },
                    onReserveRestaurantTable = { tableId, customerName, phone ->
                        viewModel.reserveRestaurantTable(tableId, customerName, phone)
                    },
                    onClearRestaurantTableReservation = { tableId ->
                        viewModel.clearRestaurantTableReservation(tableId)
                    },
                    onSaveRestaurantMenuItem = { name, category, price, desc, prepTime, isAvail, id ->
                        viewModel.saveRestaurantMenuItem(name, category, price, desc, prepTime, isAvail, id)
                    },
                    onDeleteRestaurantMenuItem = { id ->
                        viewModel.deleteRestaurantMenuItem(id)
                    },
                    onToggleMenuAvailability = { id, isAvail ->
                        viewModel.toggleRestaurantMenuItemAvailability(id, isAvail)
                    },
                    onSaveAndSendOrderToKitchen = { order, items ->
                        viewModel.saveAndSendOrderToKitchen(order, items)
                    },
                    onSettleRestaurantOrder = { orderId, tableId, paymentMethod ->
                        viewModel.settleRestaurantOrder(orderId, tableId, paymentMethod)
                    },
                    onCancelRestaurantOrder = { orderId, tableId ->
                        viewModel.cancelRestaurantOrder(orderId, tableId)
                    },
                    onUpdateKotItemStatus = { itemId, status ->
                        viewModel.updateKotItemStatus(itemId, status)
                    },
                    hospitalPatients = hospitalPatients,
                    hospitalDoctors = hospitalDoctors,
                    hospitalAppointments = hospitalAppointments,
                    hospitalPrescriptions = hospitalPrescriptions,
                    onSavePatient = { name, phone, age, gender, bloodGroup, address, history, id ->
                        viewModel.savePatient(name, phone, age, gender, bloodGroup, address, history, id)
                    },
                    onDeletePatient = { id ->
                        viewModel.deletePatient(id)
                    },
                    onSaveDoctor = { name, spec, qual, fee, days, hours, phone, isAvail, id ->
                        viewModel.saveDoctor(name, spec, qual, fee, days, hours, phone, isAvail, id)
                    },
                    onDeleteDoctor = { id ->
                        viewModel.deleteDoctor(id)
                    },
                    onToggleDoctorAvailability = { id, isAvail ->
                        viewModel.toggleDoctorAvailability(id, isAvail)
                    },
                    onBookAppointment = { patientId, patientName, patientPhone, doctorId, doctorName, spec, date, slot, fee, symptoms ->
                        viewModel.bookAppointment(patientId, patientName, patientPhone, doctorId, doctorName, spec, date, slot, fee, symptoms)
                    },
                    onUpdateAppointmentStatus = { id, status ->
                        viewModel.updateAppointmentStatus(id, status)
                    },
                    onDeleteAppointment = { id ->
                        viewModel.deleteAppointment(id)
                    },
                    onSavePrescription = { rx, onSaved ->
                        viewModel.savePrescription(rx, onSaved)
                    },
                    onDeletePrescription = { id ->
                        viewModel.deletePrescription(id)
                    },
                    pharmacyMedicines = pharmacyMedicines,
                    pharmacySales = pharmacySales,
                    onSaveMedicine = { medicine ->
                        viewModel.saveMedicine(medicine)
                    },
                    onDeleteMedicine = { id ->
                        viewModel.deleteMedicine(id)
                    },
                    onUpdateMedicineStock = { id, qty ->
                        viewModel.updateMedicineStock(id, qty)
                    },
                    onCompletePharmacySale = { items, cName, cPhone, prescriber, discount, paymentMethod, notes, onSuccess ->
                        viewModel.processPharmacyQuickSale(
                            cartItems = items,
                            customerName = cName,
                            customerPhone = cPhone,
                            doctorPrescriber = prescriber,
                            discount = discount,
                            paymentMethod = paymentMethod,
                            notes = notes,
                            onSuccess = onSuccess
                        )
                    },
                    onDeletePharmacySale = { id ->
                        viewModel.deletePharmacySale(id)
                    },
                    schoolStudents = schoolStudents,
                    schoolClasses = schoolClasses,
                    schoolFeeVouchers = schoolFeeVouchers,
                    schoolAttendanceRecords = schoolAttendanceRecords,
                    selectedAttendanceDate = selectedAttendanceDate,
                    onSaveStudent = { student ->
                        viewModel.saveStudent(student)
                    },
                    onDeleteStudent = { id ->
                        viewModel.deleteStudent(id)
                    },
                    onSaveSchoolClass = { schoolClass ->
                        viewModel.saveSchoolClass(schoolClass)
                    },
                    onDeleteSchoolClass = { id ->
                        viewModel.deleteSchoolClass(id)
                    },
                    onGenerateBulkVouchers = { monthYear, dueDate, examFee, labFee, onDone ->
                        viewModel.generateBulkVouchers(monthYear, dueDate, examFee, labFee, onDone)
                    },
                    onSaveFeeVoucher = { voucher ->
                        viewModel.saveFeeVoucher(voucher)
                    },
                    onMarkFeeVoucherPaid = { id, method ->
                        viewModel.markFeeVoucherPaid(id, method)
                    },
                    onDeleteFeeVoucher = { id ->
                        viewModel.deleteFeeVoucher(id)
                    },
                    onAttendanceDateChange = { date ->
                        viewModel.setAttendanceDate(date)
                    },
                    onRecordAttendance = { record ->
                        viewModel.recordAttendance(record)
                    },
                    onMarkAllAttendancePresent = { students, dateStr, dateEpoch ->
                        viewModel.markAllAttendancePresent(students, dateStr, dateEpoch)
                    },
                    onTabSelected = { tab ->
                        viewModel.selectTab(tab)
                    },
                    onSwitchBusiness = { id ->
                        viewModel.switchActiveBusiness(id)
                    },
                    onDeleteBusiness = { id ->
                        viewModel.deleteBusiness(id)
                    },
                    onAddNewBusiness = {
                        viewModel.navigateTo(Screen.BUSINESS_SETUP)
                    },
                    onUpdateBusiness = { updatedBiz ->
                        viewModel.updateBusiness(updatedBiz)
                    },
                    viewModel = viewModel,
                    appLanguage = appLanguage,
                    onSelectLanguage = { viewModel.setAppLanguage(it) },
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { viewModel.toggleDarkMode() }
                )
            }
        }
    }
}
}
