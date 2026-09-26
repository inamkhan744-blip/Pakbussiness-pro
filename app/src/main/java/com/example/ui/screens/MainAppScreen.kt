package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.ContentCut
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.LocalPharmacy
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PointOfSale
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.TrendingDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import com.example.data.AppointmentEntity
import com.example.data.BusinessEntity
import com.example.data.DoctorEntity
import com.example.data.FeeVoucherEntity
import com.example.data.GymCheckInEntity
import com.example.data.GymMemberEntity
import com.example.data.MedicineEntity
import com.example.data.PatientEntity
import com.example.data.PharmacyCartItem
import com.example.data.PharmacySaleEntity
import com.example.data.PrescriptionEntity
import com.example.data.RestaurantMenuItemEntity
import com.example.data.RestaurantOrderEntity
import com.example.data.RestaurantOrderItemEntity
import com.example.data.RestaurantTableEntity
import com.example.data.SchoolClassEntity
import com.example.data.StudentAttendanceEntity
import com.example.data.StudentEntity
import com.example.ui.AppLanguage
import com.example.ui.BottomNavTab
import com.example.ui.BusinessViewModel
import com.example.ui.Localization
import com.example.ui.getTabsForBusiness
import com.example.ui.screens.bakery.BakeryModuleScreen
import com.example.ui.screens.electronics.ElectronicsModuleScreen
import com.example.ui.screens.gym.GymModuleScreen
import com.example.ui.screens.hospital.HospitalModuleScreen
import com.example.ui.screens.hotel.HotelModuleScreen
import com.example.ui.screens.laundry.LaundryModuleScreen
import com.example.ui.screens.wholesale.WholesaleModuleScreen
import com.example.ui.screens.pharmacy.PharmacyModuleScreen
import com.example.ui.screens.realestate.RealEstateModuleScreen
import com.example.ui.screens.restaurant.RestaurantModuleScreen
import com.example.ui.screens.salon.SalonModuleScreen
import com.example.ui.screens.school.SchoolModuleScreen
import com.example.ui.screens.tabs.CustomersTab
import com.example.ui.screens.tabs.DashboardTab
import com.example.ui.screens.tabs.InventoryTab
import com.example.ui.screens.tabs.PosTab
import com.example.ui.screens.tabs.SettingsTab
import com.example.ui.screens.tools.PakBusinessToolsScreen
import com.example.ui.screens.tabs.ExpensesTab
import com.example.ui.screens.tabs.StaffTab
import com.example.ui.screens.tabs.ReportsTab
import com.example.ui.screens.tailor.TailorModuleScreen
import com.example.ui.screens.workshop.WorkshopModuleScreen
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldDark
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldSecondary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

data class NavigationTabItem(
    val tab: BottomNavTab,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

val NAV_ITEMS = listOf(
    NavigationTabItem(
        tab = BottomNavTab.DASHBOARD,
        title = "Dashboard",
        selectedIcon = Icons.Filled.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard,
        testTag = "nav_dashboard"
    ),
    NavigationTabItem(
        tab = BottomNavTab.LAUNDRY,
        title = "Laundry",
        selectedIcon = Icons.Filled.LocalLaundryService,
        unselectedIcon = Icons.Filled.LocalLaundryService,
        testTag = "nav_laundry"
    ),
    NavigationTabItem(
        tab = BottomNavTab.WHOLESALE,
        title = "Wholesale",
        selectedIcon = Icons.Filled.LocalShipping,
        unselectedIcon = Icons.Filled.LocalShipping,
        testTag = "nav_wholesale"
    ),
    NavigationTabItem(
        tab = BottomNavTab.WORKSHOP,
        title = "Workshop",
        selectedIcon = Icons.Filled.DirectionsCar,
        unselectedIcon = Icons.Filled.DirectionsCar,
        testTag = "nav_workshop"
    ),
    NavigationTabItem(
        tab = BottomNavTab.ELECTRONICS,
        title = "Electronics",
        selectedIcon = Icons.Filled.Devices,
        unselectedIcon = Icons.Filled.Devices,
        testTag = "nav_electronics"
    ),
    NavigationTabItem(
        tab = BottomNavTab.BAKERY,
        title = "Bakery",
        selectedIcon = Icons.Filled.Cake,
        unselectedIcon = Icons.Filled.Cake,
        testTag = "nav_bakery"
    ),
    NavigationTabItem(
        tab = BottomNavTab.TAILOR,
        title = "Tailor",
        selectedIcon = Icons.Filled.Checkroom,
        unselectedIcon = Icons.Filled.Checkroom,
        testTag = "nav_tailor"
    ),
    NavigationTabItem(
        tab = BottomNavTab.HOTEL,
        title = "Hotel",
        selectedIcon = Icons.Filled.Hotel,
        unselectedIcon = Icons.Outlined.Hotel,
        testTag = "nav_hotel"
    ),
    NavigationTabItem(
        tab = BottomNavTab.SALON,
        title = "Salon",
        selectedIcon = Icons.Filled.ContentCut,
        unselectedIcon = Icons.Outlined.ContentCut,
        testTag = "nav_salon"
    ),
    NavigationTabItem(
        tab = BottomNavTab.SCHOOL,
        title = "School",
        selectedIcon = Icons.Filled.School,
        unselectedIcon = Icons.Filled.School,
        testTag = "nav_school"
    ),
    NavigationTabItem(
        tab = BottomNavTab.REAL_ESTATE,
        title = "Real Estate",
        selectedIcon = Icons.Filled.Apartment,
        unselectedIcon = Icons.Outlined.Apartment,
        testTag = "nav_real_estate"
    ),
    NavigationTabItem(
        tab = BottomNavTab.RESTAURANT,
        title = "Cafe",
        selectedIcon = Icons.Filled.Restaurant,
        unselectedIcon = Icons.Outlined.Restaurant,
        testTag = "nav_restaurant"
    ),
    NavigationTabItem(
        tab = BottomNavTab.HOSPITAL,
        title = "Clinic",
        selectedIcon = Icons.Filled.LocalHospital,
        unselectedIcon = Icons.Outlined.LocalHospital,
        testTag = "nav_hospital"
    ),
    NavigationTabItem(
        tab = BottomNavTab.PHARMACY,
        title = "Pharmacy",
        selectedIcon = Icons.Filled.LocalPharmacy,
        unselectedIcon = Icons.Outlined.LocalPharmacy,
        testTag = "nav_pharmacy"
    ),
    NavigationTabItem(
        tab = BottomNavTab.GYM,
        title = "Gym",
        selectedIcon = Icons.Filled.FitnessCenter,
        unselectedIcon = Icons.Filled.FitnessCenter,
        testTag = "nav_gym"
    ),
    NavigationTabItem(
        tab = BottomNavTab.POS,
        title = "POS",
        selectedIcon = Icons.Filled.PointOfSale,
        unselectedIcon = Icons.Outlined.PointOfSale,
        testTag = "nav_pos"
    ),
    NavigationTabItem(
        tab = BottomNavTab.INVENTORY,
        title = "Inventory",
        selectedIcon = Icons.Filled.Inventory2,
        unselectedIcon = Icons.Outlined.Inventory2,
        testTag = "nav_inventory"
    ),
    NavigationTabItem(
        tab = BottomNavTab.CUSTOMERS,
        title = "Customers",
        selectedIcon = Icons.Filled.People,
        unselectedIcon = Icons.Outlined.People,
        testTag = "nav_customers"
    ),
    NavigationTabItem(
        tab = BottomNavTab.EXPENSES,
        title = "Kharcha",
        selectedIcon = Icons.Filled.TrendingDown,
        unselectedIcon = Icons.Outlined.TrendingDown,
        testTag = "nav_expenses"
    ),
    NavigationTabItem(
        tab = BottomNavTab.STAFF,
        title = "Staff",
        selectedIcon = Icons.Filled.Badge,
        unselectedIcon = Icons.Outlined.Badge,
        testTag = "nav_staff"
    ),
    NavigationTabItem(
        tab = BottomNavTab.REPORTS,
        title = "Reports",
        selectedIcon = Icons.Filled.Assessment,
        unselectedIcon = Icons.Outlined.Assessment,
        testTag = "nav_reports"
    ),
    NavigationTabItem(
        tab = BottomNavTab.SETTINGS,
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        testTag = "nav_settings"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    activeBusiness: BusinessEntity?,
    allBusinesses: List<BusinessEntity>,
    currentTab: BottomNavTab,
    gymMembers: List<GymMemberEntity>,
    todayGymCheckIns: List<GymCheckInEntity>,
    selectedMember: GymMemberEntity?,
    isAddEditOpen: Boolean,
    memberToEdit: GymMemberEntity?,
    restaurantTables: List<RestaurantTableEntity> = emptyList(),
    restaurantMenuItems: List<RestaurantMenuItemEntity> = emptyList(),
    activeKotItems: List<RestaurantOrderItemEntity> = emptyList(),
    selectedRestaurantTable: RestaurantTableEntity? = null,
    currentTableOrder: RestaurantOrderEntity? = null,
    currentTableOrderItems: List<RestaurantOrderItemEntity> = emptyList(),
    hospitalPatients: List<PatientEntity> = emptyList(),
    hospitalDoctors: List<DoctorEntity> = emptyList(),
    hospitalAppointments: List<AppointmentEntity> = emptyList(),
    hospitalPrescriptions: List<PrescriptionEntity> = emptyList(),
    pharmacyMedicines: List<MedicineEntity> = emptyList(),
    pharmacySales: List<PharmacySaleEntity> = emptyList(),
    onSelectMember: (Long?) -> Unit,
    onOpenAddMember: () -> Unit,
    onOpenEditMember: (GymMemberEntity) -> Unit,
    onCloseAddEdit: () -> Unit,
    onSaveMember: (name: String, phone: String, gender: String, plan: String, startDate: Long, durationDays: Int, amount: Double) -> Unit,
    onDeleteMember: (Long) -> Unit,
    onToggleCheckIn: (GymMemberEntity) -> Unit,
    getMemberCheckIns: (Long) -> Flow<List<GymCheckInEntity>>,
    onSelectRestaurantTable: (RestaurantTableEntity?) -> Unit = {},
    onSaveRestaurantTable: (name: String, capacity: Int, section: String, id: Long) -> Unit = { _, _, _, _ -> },
    onDeleteRestaurantTable: (Long) -> Unit = {},
    onReserveRestaurantTable: (tableId: Long, customerName: String, phone: String) -> Unit = { _, _, _ -> },
    onClearRestaurantTableReservation: (Long) -> Unit = {},
    onSaveRestaurantMenuItem: (name: String, category: String, pricePkr: Double, description: String, prepTimeMinutes: Int, isAvailable: Boolean, id: Long) -> Unit = { _, _, _, _, _, _, _ -> },
    onDeleteRestaurantMenuItem: (Long) -> Unit = {},
    onToggleMenuAvailability: (Long, Boolean) -> Unit = { _, _ -> },
    onSaveAndSendOrderToKitchen: (order: RestaurantOrderEntity, items: List<RestaurantOrderItemEntity>) -> Unit = { _, _ -> },
    onSettleRestaurantOrder: (orderId: Long, tableId: Long, paymentMethod: String) -> Unit = { _, _, _ -> },
    onCancelRestaurantOrder: (orderId: Long, tableId: Long) -> Unit = { _, _ -> },
    onUpdateKotItemStatus: (itemId: Long, status: String) -> Unit = { _, _ -> },
    onSavePatient: (name: String, phone: String, age: Int, gender: String, bloodGroup: String, address: String, history: String, id: Long) -> Unit = { _, _, _, _, _, _, _, _ -> },
    onDeletePatient: (Long) -> Unit = {},
    onSaveDoctor: (name: String, spec: String, qual: String, fee: Double, days: String, hours: String, phone: String, isAvail: Boolean, id: Long) -> Unit = { _, _, _, _, _, _, _, _, _ -> },
    onDeleteDoctor: (Long) -> Unit = {},
    onToggleDoctorAvailability: (Long, Boolean) -> Unit = { _, _ -> },
    onBookAppointment: (patientId: Long, patientName: String, patientPhone: String, doctorId: Long, doctorName: String, spec: String, date: Long, timeSlot: String, fee: Double, symptoms: String) -> Unit = { _, _, _, _, _, _, _, _, _, _ -> },
    onUpdateAppointmentStatus: (Long, String) -> Unit = { _, _ -> },
    onDeleteAppointment: (Long) -> Unit = {},
    onSavePrescription: (PrescriptionEntity, (Long) -> Unit) -> Unit = { _, _ -> },
    onDeletePrescription: (Long) -> Unit = {},
    onSaveMedicine: (MedicineEntity) -> Unit = {},
    onDeleteMedicine: (Long) -> Unit = {},
    onUpdateMedicineStock: (Long, Int) -> Unit = { _, _ -> },
    onCompletePharmacySale: (
        cartItems: List<PharmacyCartItem>,
        customerName: String,
        customerPhone: String,
        doctorPrescriber: String,
        discount: Double,
        paymentMethod: String,
        notes: String,
        onSuccess: (PharmacySaleEntity) -> Unit
    ) -> Unit = { _, _, _, _, _, _, _, _ -> },
    onDeletePharmacySale: (Long) -> Unit = {},
    schoolStudents: List<StudentEntity> = emptyList(),
    schoolClasses: List<SchoolClassEntity> = emptyList(),
    schoolFeeVouchers: List<FeeVoucherEntity> = emptyList(),
    schoolAttendanceRecords: List<StudentAttendanceEntity> = emptyList(),
    selectedAttendanceDate: String = "",
    onSaveStudent: (StudentEntity) -> Unit = {},
    onDeleteStudent: (Long) -> Unit = {},
    onSaveSchoolClass: (SchoolClassEntity) -> Unit = {},
    onDeleteSchoolClass: (Long) -> Unit = {},
    onGenerateBulkVouchers: (monthYear: String, dueDate: Long, examFee: Double, labFee: Double, (Int) -> Unit) -> Unit = { _, _, _, _, _ -> },
    onSaveFeeVoucher: (FeeVoucherEntity) -> Unit = {},
    onMarkFeeVoucherPaid: (Long, String) -> Unit = { _, _ -> },
    onDeleteFeeVoucher: (Long) -> Unit = {},
    onAttendanceDateChange: (String) -> Unit = {},
    onRecordAttendance: (StudentAttendanceEntity) -> Unit = {},
    onMarkAllAttendancePresent: (List<StudentEntity>, String, Long) -> Unit = { _, _, _ -> },
    onTabSelected: (BottomNavTab) -> Unit,
    onSwitchBusiness: (Long) -> Unit,
    onDeleteBusiness: (Long) -> Unit,
    onAddNewBusiness: () -> Unit,
    onUpdateBusiness: (BusinessEntity) -> Unit,
    viewModel: BusinessViewModel? = null,
    appLanguage: AppLanguage = AppLanguage.ENGLISH,
    onSelectLanguage: (AppLanguage) -> Unit = {},
    isDarkMode: Boolean = false,
    onToggleDarkMode: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showTenantSwitchDialog by remember { mutableStateOf(false) }
    var showBusinessToolsDialog by remember { mutableStateOf(false) }

    // Live Date & Time ticker
    var liveDateTime by remember { mutableStateOf(Localization.formatLiveDateTime(appLanguage)) }
    LaunchedEffect(appLanguage) {
        while (true) {
            liveDateTime = Localization.formatLiveDateTime(appLanguage)
            delay(10000)
        }
    }

    // Multi-business selection flow:
    // Starts at the Business Selector screen so user can choose their active business.
    var isSelectingBusiness by rememberSaveable { mutableStateOf(true) }

    // If activeBusiness is null, ensure business selector is visible
    LaunchedEffect(activeBusiness) {
        if (activeBusiness == null) {
            isSelectingBusiness = true
        }
    }

    // Hardware back button returns user to Business Selector when inside an active business
    BackHandler(enabled = !isSelectingBusiness) {
        isSelectingBusiness = true
    }

    // Dynamic filtering of navigation tabs strictly based on activeBusiness.type + ERP suite
    val bottomBarTabs = remember(activeBusiness?.type) {
        getTabsForBusiness(activeBusiness?.type)
    }

    val allowedTabs = remember(bottomBarTabs) {
        bottomBarTabs + listOf(
            BottomNavTab.EXPENSES,
            BottomNavTab.STAFF,
            BottomNavTab.REPORTS
        )
    }

    val filteredNavItems = remember(bottomBarTabs) {
        NAV_ITEMS.filter { it.tab in bottomBarTabs }
    }

    // Auto-adjust current tab if switching tenants renders the current tab invalid
    LaunchedEffect(allowedTabs, currentTab) {
        if (currentTab !in allowedTabs) {
            onTabSelected(BottomNavTab.DASHBOARD)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(320.dp)
                    .testTag("navigation_drawer")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Drawer Header Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(PakEmeraldDark)
                            .padding(horizontal = 20.dp, vertical = 24.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.White.copy(alpha = 0.15f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Business,
                                            contentDescription = null,
                                            tint = Color(0xFFFFD54F),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "PakBusiness Pro",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = PakGoldSecondary.copy(alpha = 0.3f)
                                ) {
                                    Text(
                                        text = "₨ ${activeBusiness?.currency ?: "PKR"}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD54F),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = activeBusiness?.name ?: "No Business Selected",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Text(
                                text = "${activeBusiness?.type ?: "General"} • Owner: ${activeBusiness?.ownerName ?: "Not Set"}",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }

                    // Tenant Switcher Section in Drawer
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "TENANTS (${allBusinesses.size})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            TextButton(
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    showTenantSwitchDialog = true
                                },
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                            ) {
                                Text("Switch", fontSize = 12.sp, color = PakEmeraldPrimary, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Compact list of businesses in Drawer
                        allBusinesses.take(3).forEach { biz ->
                            val isActive = biz.id == activeBusiness?.id
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isActive) PakEmeraldContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSwitchBusiness(biz.id)
                                        scope.launch { drawerState.close() }
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        if (isActive) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = PakEmeraldPrimary, modifier = Modifier.size(14.dp))
                                        } else {
                                            Icon(Icons.Default.Business, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                                        }
                                        Column {
                                            Text(
                                                text = biz.name,
                                                fontSize = 13.sp,
                                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                                            )
                                            Text(
                                                text = biz.type,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    if (isActive) {
                                        Text(
                                            text = "Active",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PakEmeraldPrimary
                                        )
                                    }
                                }
                            }
                        }

                        // Add Business Button in Drawer
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    onAddNewBusiness()
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = PakEmeraldPrimary, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "+ Add Another Business",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PakEmeraldPrimary
                                )
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    // Switch Business Selector Button in Drawer
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = PakEmeraldContainer.copy(alpha = 0.6f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                scope.launch { drawerState.close() }
                                isSelectingBusiness = true
                            }
                            .testTag("drawer_switch_business")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = null,
                                tint = PakEmeraldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Switch Business / Hub",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PakEmeraldPrimary
                                )
                                Text(
                                    text = "Back to business selector list",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Pakistani Business Utilities Button in Drawer
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = PakGoldContainer.copy(alpha = 0.5f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable {
                                scope.launch { drawerState.close() }
                                showBusinessToolsDialog = true
                            }
                            .testTag("drawer_tools_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = PakGoldSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Business Tools & Calculators",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PakEmeraldDark
                                )
                                Text(
                                    text = "Galla Cash, Mann/KG, Gold, Tax",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Dynamically Filtered Navigation Items in Drawer
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "MODULES (${activeBusiness?.type ?: "All"})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )

                        filteredNavItems.forEach { item ->
                            val isSelected = currentTab == item.tab
                            NavigationDrawerItem(
                                label = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            item.title,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                        when (item.tab) {
                                            BottomNavTab.GYM -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = PakGoldSecondary
                                                ) {
                                                    Text(
                                                        text = "GYM",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.RESTAURANT -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = PakEmeraldPrimary
                                                ) {
                                                    Text(
                                                        text = "CAFE",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.HOSPITAL -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFF0284C7)
                                                ) {
                                                    Text(
                                                        text = "CLINIC",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.SCHOOL -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = PakEmeraldPrimary
                                                ) {
                                                    Text(
                                                        text = "EDU",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.REAL_ESTATE -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = PakGoldSecondary
                                                ) {
                                                    Text(
                                                        text = "ESTATE",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.HOTEL -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFF059669)
                                                ) {
                                                    Text(
                                                        text = "STAY",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.SALON -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFFD81B60)
                                                ) {
                                                    Text(
                                                        text = "SALON",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.LAUNDRY -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFF0284C7)
                                                ) {
                                                    Text(
                                                        text = "CLEAN",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.WHOLESALE -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFF00796B)
                                                ) {
                                                    Text(
                                                        text = "BILTY",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.WORKSHOP -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFFEA580C)
                                                ) {
                                                    Text(
                                                        text = "GARAGE",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.ELECTRONICS -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFF0284C7)
                                                ) {
                                                    Text(
                                                        text = "MOBILE",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.BAKERY -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFFD97706)
                                                ) {
                                                    Text(
                                                        text = "SWEETS",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.TAILOR -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFF7B1FA2)
                                                ) {
                                                    Text(
                                                        text = "BOUTIQUE",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            BottomNavTab.PHARMACY -> {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = Color(0xFF0D9488)
                                                ) {
                                                    Text(
                                                        text = "RX",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                            else -> {}
                                        }
                                    }
                                },
                                selected = isSelected,
                                onClick = {
                                    onTabSelected(item.tab)
                                    scope.launch { drawerState.close() }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = PakEmeraldContainer,
                                    selectedIconColor = PakEmeraldPrimary,
                                    selectedTextColor = PakEmeraldPrimary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))

                    // ADVANCED ERP & OPERATIONS SECTION IN DRAWER
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "ADVANCED ERP & FINANCE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )

                        // 1. Daily Expenses (Kharcha)
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    "Daily Kharcha (Expenses)",
                                    fontWeight = if (currentTab == BottomNavTab.EXPENSES) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            selected = currentTab == BottomNavTab.EXPENSES,
                            onClick = {
                                onTabSelected(BottomNavTab.EXPENSES)
                                scope.launch { drawerState.close() }
                            },
                            icon = {
                                Icon(Icons.Default.TrendingDown, contentDescription = "Kharcha")
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = PakEmeraldContainer,
                                selectedIconColor = PakEmeraldPrimary,
                                selectedTextColor = PakEmeraldPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )

                        // 2. Staff & Payroll
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    "Staff Haziri & Salaries",
                                    fontWeight = if (currentTab == BottomNavTab.STAFF) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            selected = currentTab == BottomNavTab.STAFF,
                            onClick = {
                                onTabSelected(BottomNavTab.STAFF)
                                scope.launch { drawerState.close() }
                            },
                            icon = {
                                Icon(Icons.Default.Badge, contentDescription = "Staff")
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = PakEmeraldContainer,
                                selectedIconColor = PakEmeraldPrimary,
                                selectedTextColor = PakEmeraldPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )

                        // 3. Day-End Closing & P&L
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    "Day-End (Z-Report) & P&L",
                                    fontWeight = if (currentTab == BottomNavTab.REPORTS) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            selected = currentTab == BottomNavTab.REPORTS,
                            onClick = {
                                onTabSelected(BottomNavTab.REPORTS)
                                scope.launch { drawerState.close() }
                            },
                            icon = {
                                Icon(Icons.Default.Assessment, contentDescription = "Reports")
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = PakEmeraldContainer,
                                selectedIconColor = PakEmeraldPrimary,
                                selectedTextColor = PakEmeraldPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))

                    // Theme & Language Controls in Drawer
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "PREFERENCES & THEME",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Day / Night Mode Row
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleDarkMode() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                        contentDescription = null,
                                        tint = if (isDarkMode) Color(0xFFFFD54F) else PakEmeraldPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = if (isDarkMode) Localization.getString("night_mode", appLanguage) else Localization.getString("day_mode", appLanguage),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Text(
                                    text = if (isDarkMode) "Dark" else "Light",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PakEmeraldPrimary
                                )
                            }
                        }

                        // Language Selector Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            AppLanguage.entries.forEach { langItem ->
                                val isSelected = appLanguage == langItem
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) PakEmeraldPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { onSelectLanguage(langItem) }
                                ) {
                                    Text(
                                        text = langItem.nativeName,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Drawer Offline Persistence Footer
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Storage,
                                contentDescription = null,
                                tint = PakEmeraldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "100% Offline Database",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Saved locally on device (SQLite)",
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        BoxWithConstraints(modifier = modifier.fillMaxSize()) {
            val isWideScreen = maxWidth >= 768.dp

            Row(modifier = Modifier.fillMaxSize()) {
                if (isWideScreen && !isSelectingBusiness) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        header = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 12.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = PakEmeraldContainer,
                                    modifier = Modifier.size(42.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Business,
                                            contentDescription = null,
                                            tint = PakEmeraldPrimary,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "PakBusiness",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PakEmeraldPrimary
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .testTag("desktop_nav_rail")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            filteredNavItems.forEach { item ->
                                val isSelected = currentTab == item.tab
                                NavigationRailItem(
                                    selected = isSelected,
                                    onClick = { onTabSelected(item.tab) },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = item.title,
                                            fontSize = 10.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            maxLines = 1
                                        )
                                    },
                                    colors = NavigationRailItemDefaults.colors(
                                        selectedIconColor = PakEmeraldPrimary,
                                        selectedTextColor = PakEmeraldPrimary,
                                        indicatorColor = PakEmeraldContainer
                                    ),
                                    modifier = Modifier.testTag("rail_${item.testTag}")
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.weight(1f),
                    topBar = {
                        TopAppBar(
                            title = {
                                Column {
                                    if (isSelectingBusiness) {
                                        Text(
                                            text = Localization.getString("business_selector", appLanguage),
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${allBusinesses.size} ${Localization.getString("registered_stores", appLanguage)} • $liveDateTime",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = PakEmeraldPrimary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    } else {
                                        Text(
                                            text = when (currentTab) {
                                                BottomNavTab.DASHBOARD -> Localization.getString("dashboard", appLanguage)
                                                BottomNavTab.POS -> Localization.getString("pos", appLanguage)
                                                BottomNavTab.INVENTORY -> Localization.getString("inventory", appLanguage)
                                                BottomNavTab.CUSTOMERS -> Localization.getString("customers", appLanguage)
                                                BottomNavTab.EXPENSES -> Localization.getString("expenses", appLanguage)
                                                BottomNavTab.STAFF -> Localization.getString("staff", appLanguage)
                                                BottomNavTab.REPORTS -> Localization.getString("reports", appLanguage)
                                                BottomNavTab.SETTINGS -> Localization.getString("settings", appLanguage)
                                                BottomNavTab.LAUNDRY -> "Laundry & Dry Cleaners"
                                                BottomNavTab.WHOLESALE -> "Wholesale & Distribution"
                                                BottomNavTab.WORKSHOP -> "Auto Workshop & Garage"
                                                BottomNavTab.ELECTRONICS -> "Electronics & Mobile Shop"
                                                BottomNavTab.BAKERY -> "Bakery & Sweets"
                                                BottomNavTab.TAILOR -> "Tailor & Boutique"
                                                BottomNavTab.HOTEL -> "Hotel & Guest House"
                                                BottomNavTab.SALON -> "Salon & Beauty Parlor"
                                                BottomNavTab.SCHOOL -> "School & Academy"
                                                BottomNavTab.REAL_ESTATE -> "Real Estate Agency"
                                                BottomNavTab.RESTAURANT -> "Restaurant & Cafe"
                                                BottomNavTab.HOSPITAL -> "Hospital & Clinic"
                                                BottomNavTab.PHARMACY -> "Pharmacy & Medical"
                                                BottomNavTab.GYM -> "Gym & Fitness"
                                            },
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (activeBusiness != null) {
                                            Text(
                                                text = "${activeBusiness.name} • $liveDateTime",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = PakEmeraldPrimary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = { scope.launch { drawerState.open() } },
                                    modifier = Modifier.testTag("drawer_menu_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Open Navigation Drawer"
                                    )
                                }
                            },
                            actions = {
                                // Day / Night Mode Toggle
                                IconButton(
                                    onClick = onToggleDarkMode,
                                    modifier = Modifier.testTag("appbar_theme_toggle")
                                ) {
                                    Icon(
                                        imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                        contentDescription = if (isDarkMode) "Day Mode" else "Night Mode",
                                        tint = if (isDarkMode) Color(0xFFFFD54F) else MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                // Language Quick Switcher
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = PakEmeraldContainer.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .clickable {
                                            val nextLang = when (appLanguage) {
                                                AppLanguage.ENGLISH -> AppLanguage.URDU
                                                AppLanguage.URDU -> AppLanguage.HINDI
                                                AppLanguage.HINDI -> AppLanguage.ENGLISH
                                            }
                                            onSelectLanguage(nextLang)
                                        }
                                        .padding(end = 4.dp)
                                        .testTag("appbar_language_toggle")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Translate,
                                            contentDescription = null,
                                            tint = PakEmeraldPrimary,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Text(
                                            text = when (appLanguage) {
                                                AppLanguage.ENGLISH -> "EN"
                                                AppLanguage.URDU -> "اردو"
                                                AppLanguage.HINDI -> "हिंदी"
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PakEmeraldPrimary
                                        )
                                    }
                                }

                                // Lock App button if security enabled
                                val isSecEnabled by (viewModel?.isSecurityEnabled?.collectAsStateWithLifecycle() ?: remember { mutableStateOf(false) })
                                if (isSecEnabled) {
                                    IconButton(
                                        onClick = { viewModel?.lockApp() },
                                        modifier = Modifier.testTag("appbar_lock_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = "Lock App",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                if (isSelectingBusiness) {
                                    Button(
                                        onClick = onAddNewBusiness,
                                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .testTag("btn_topbar_add_business")
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Add Business", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    // Switch Business Button
                                    Button(
                                        onClick = { isSelectingBusiness = true },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PakEmeraldContainer,
                                            contentColor = PakEmeraldPrimary
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .testTag("btn_switch_business_topbar")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.SwapHoriz,
                                            contentDescription = "Switch Business",
                                            tint = PakEmeraldPrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = Localization.getString("switch_business", appLanguage),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PakEmeraldPrimary
                                        )
                                    }

                                    // Offline status pill
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.FiberManualRecord,
                                                contentDescription = null,
                                                tint = Color(0xFF2E7D32),
                                                modifier = Modifier.size(10.dp)
                                            )
                                            Text(
                                                text = "Offline",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                titleContentColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    },
                    bottomBar = {
                        if (!isSelectingBusiness && !isWideScreen) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 6.dp,
                                modifier = Modifier.testTag("bottom_navigation_bar")
                            ) {
                                filteredNavItems.forEach { item ->
                                    val isSelected = currentTab == item.tab
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = { onTabSelected(item.tab) },
                                        icon = {
                                            Icon(
                                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = item.title,
                                                fontSize = 10.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                maxLines = 1
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = PakEmeraldPrimary,
                                            selectedTextColor = PakEmeraldPrimary,
                                            indicatorColor = PakEmeraldContainer
                                        ),
                                        modifier = Modifier.testTag(item.testTag)
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isSelectingBusiness) {
                    DashboardScreen(
                        activeBusiness = activeBusiness,
                        allBusinesses = allBusinesses,
                        isSelectingBusiness = true,
                        onSelectBusiness = { selectedBiz ->
                            onSwitchBusiness(selectedBiz.id)
                            isSelectingBusiness = false
                            onTabSelected(BottomNavTab.DASHBOARD)
                        },
                        onAddNewBusiness = onAddNewBusiness,
                        onBackToSelector = { isSelectingBusiness = true },
                        onNavigateTab = onTabSelected,
                        onOpenTenantSwitcher = { showTenantSwitchDialog = true },
                        appLanguage = appLanguage,
                        onSelectLanguage = onSelectLanguage,
                        isDarkMode = isDarkMode,
                        onToggleDarkMode = onToggleDarkMode
                    )
                } else {
                    when (currentTab) {
                        BottomNavTab.DASHBOARD -> DashboardScreen(
                            activeBusiness = activeBusiness,
                            allBusinesses = allBusinesses,
                            isSelectingBusiness = false,
                            onSelectBusiness = { selectedBiz ->
                                onSwitchBusiness(selectedBiz.id)
                                isSelectingBusiness = false
                                onTabSelected(BottomNavTab.DASHBOARD)
                            },
                            onAddNewBusiness = onAddNewBusiness,
                            onBackToSelector = { isSelectingBusiness = true },
                            onNavigateTab = onTabSelected,
                            onOpenTenantSwitcher = { isSelectingBusiness = true },
                            appLanguage = appLanguage,
                            onSelectLanguage = onSelectLanguage,
                            isDarkMode = isDarkMode,
                            onToggleDarkMode = onToggleDarkMode
                        )
                    BottomNavTab.LAUNDRY -> {
                        if (viewModel != null) {
                            LaundryModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.WHOLESALE -> {
                        if (viewModel != null) {
                            WholesaleModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.WORKSHOP -> {
                        if (viewModel != null) {
                            WorkshopModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.ELECTRONICS -> {
                        if (viewModel != null) {
                            ElectronicsModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.BAKERY -> {
                        if (viewModel != null) {
                            BakeryModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.TAILOR -> {
                        if (viewModel != null) {
                            TailorModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.SCHOOL -> SchoolModuleScreen(
                        activeBusiness = activeBusiness,
                        students = schoolStudents,
                        classes = schoolClasses,
                        vouchers = schoolFeeVouchers,
                        attendanceRecords = schoolAttendanceRecords,
                        selectedAttendanceDate = selectedAttendanceDate,
                        onSaveStudent = onSaveStudent,
                        onDeleteStudent = onDeleteStudent,
                        onSaveClass = onSaveSchoolClass,
                        onDeleteClass = onDeleteSchoolClass,
                        onGenerateBulkVouchers = onGenerateBulkVouchers,
                        onSaveVoucher = onSaveFeeVoucher,
                        onMarkVoucherPaid = onMarkFeeVoucherPaid,
                        onDeleteVoucher = onDeleteFeeVoucher,
                        onAttendanceDateChange = onAttendanceDateChange,
                        onRecordAttendance = onRecordAttendance,
                        onMarkAllAttendancePresent = onMarkAllAttendancePresent
                    )
                    BottomNavTab.REAL_ESTATE -> {
                        if (viewModel != null) {
                            RealEstateModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.SALON -> {
                        if (viewModel != null) {
                            SalonModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.HOTEL -> {
                        if (viewModel != null) {
                            HotelModuleScreen(viewModel = viewModel)
                        }
                    }
                    BottomNavTab.RESTAURANT -> RestaurantModuleScreen(
                        business = activeBusiness,
                        tables = restaurantTables,
                        menuItems = restaurantMenuItems,
                        activeKotItems = activeKotItems,
                        selectedTable = selectedRestaurantTable,
                        currentTableOrder = currentTableOrder,
                        currentTableOrderItems = currentTableOrderItems,
                        onSelectTable = onSelectRestaurantTable,
                        onSaveTable = onSaveRestaurantTable,
                        onDeleteTable = onDeleteRestaurantTable,
                        onReserveTable = onReserveRestaurantTable,
                        onClearReservation = onClearRestaurantTableReservation,
                        onSaveMenuItem = onSaveRestaurantMenuItem,
                        onDeleteMenuItem = onDeleteRestaurantMenuItem,
                        onToggleMenuAvailability = onToggleMenuAvailability,
                        onSaveAndSendToKitchen = onSaveAndSendOrderToKitchen,
                        onSettleOrder = onSettleRestaurantOrder,
                        onCancelOrder = onCancelRestaurantOrder,
                        onUpdateKotStatus = onUpdateKotItemStatus
                    )
                    BottomNavTab.HOSPITAL -> HospitalModuleScreen(
                        business = activeBusiness,
                        patients = hospitalPatients,
                        doctors = hospitalDoctors,
                        appointments = hospitalAppointments,
                        prescriptions = hospitalPrescriptions,
                        onSavePatient = onSavePatient,
                        onDeletePatient = onDeletePatient,
                        onSaveDoctor = onSaveDoctor,
                        onDeleteDoctor = onDeleteDoctor,
                        onToggleDoctorAvailability = onToggleDoctorAvailability,
                        onBookAppointment = onBookAppointment,
                        onUpdateAppointmentStatus = onUpdateAppointmentStatus,
                        onDeleteAppointment = onDeleteAppointment,
                        onSavePrescription = onSavePrescription,
                        onDeletePrescription = onDeletePrescription
                    )
                    BottomNavTab.PHARMACY -> PharmacyModuleScreen(
                        business = activeBusiness,
                        medicines = pharmacyMedicines,
                        sales = pharmacySales,
                        onSaveMedicine = onSaveMedicine,
                        onDeleteMedicine = onDeleteMedicine,
                        onUpdateStock = onUpdateMedicineStock,
                        onCompleteSale = onCompletePharmacySale,
                        onDeleteSale = onDeletePharmacySale
                    )
                    BottomNavTab.GYM -> GymModuleScreen(
                        business = activeBusiness,
                        members = gymMembers,
                        todayCheckIns = todayGymCheckIns,
                        selectedMember = selectedMember,
                        isAddEditOpen = isAddEditOpen,
                        memberToEdit = memberToEdit,
                        onSelectMember = onSelectMember,
                        onOpenAddMember = onOpenAddMember,
                        onOpenEditMember = onOpenEditMember,
                        onCloseAddEdit = onCloseAddEdit,
                        onSaveMember = onSaveMember,
                        onDeleteMember = onDeleteMember,
                        onToggleCheckIn = onToggleCheckIn,
                        getMemberCheckIns = getMemberCheckIns
                    )
                    BottomNavTab.POS -> PosTab(
                        business = activeBusiness
                    )
                    BottomNavTab.INVENTORY -> InventoryTab(
                        business = activeBusiness
                    )
                    BottomNavTab.CUSTOMERS -> CustomersTab(
                        business = activeBusiness
                    )
                    BottomNavTab.EXPENSES -> {
                        if (viewModel != null) {
                            ExpensesTab(
                                business = activeBusiness,
                                viewModel = viewModel
                            )
                        }
                    }
                    BottomNavTab.STAFF -> {
                        if (viewModel != null) {
                            StaffTab(
                                business = activeBusiness,
                                viewModel = viewModel
                            )
                        }
                    }
                    BottomNavTab.REPORTS -> {
                        if (viewModel != null) {
                            ReportsTab(
                                business = activeBusiness,
                                viewModel = viewModel
                            )
                        }
                    }
                    BottomNavTab.SETTINGS -> SettingsTab(
                        activeBusiness = activeBusiness,
                        allBusinesses = allBusinesses,
                        onSwitchBusiness = onSwitchBusiness,
                        onDeleteBusiness = onDeleteBusiness,
                        onAddNewBusiness = onAddNewBusiness,
                        onUpdateBusiness = onUpdateBusiness
                    )
                }
            }
        }
    }
}

    // Quick Tenant Switch Dialog
    if (showTenantSwitchDialog) {
        AlertDialog(
            onDismissRequest = { showTenantSwitchDialog = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = null,
                        tint = PakEmeraldPrimary
                    )
                    Text("Switch Business Tenant", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Choose which local business to manage. All data is isolated per tenant offline.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    allBusinesses.forEach { biz ->
                        val isActive = biz.id == activeBusiness?.id
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isActive) PakEmeraldContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSwitchBusiness(biz.id)
                                    showTenantSwitchDialog = false
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = biz.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${biz.type} • ${biz.currency}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                if (isActive) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = PakEmeraldPrimary
                                    ) {
                                        Text(
                                            text = "ACTIVE",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showTenantSwitchDialog = false
                        onAddNewBusiness()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddBusiness,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add New Business")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTenantSwitchDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    if (showBusinessToolsDialog) {
        Dialog(
            onDismissRequest = { showBusinessToolsDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.background,
                tonalElevation = 8.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pakistani Business Utilities",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        IconButton(onClick = { showBusinessToolsDialog = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                    PakBusinessToolsScreen(appLanguage = appLanguage)
                }
            }
        }
    }
}
}
}
