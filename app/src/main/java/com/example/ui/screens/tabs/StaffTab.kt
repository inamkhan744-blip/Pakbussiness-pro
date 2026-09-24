package com.example.ui.screens.tabs

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BusinessEntity
import com.example.data.StaffAttendanceEntity
import com.example.data.StaffEntity
import com.example.ui.BusinessViewModel
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldDark
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldContainer
import com.example.ui.theme.PakGoldSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffTab(
    business: BusinessEntity?,
    viewModel: BusinessViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val staffList by viewModel.staffMembers.collectAsState()
    val attendanceRecords by viewModel.staffAttendanceRecords.collectAsState()
    val currency = business?.currency ?: "PKR"

    var selectedSubTab by remember { mutableStateOf(0) } // 0: Attendance, 1: Staff Directory & Payroll
    var showAddStaffDialog by remember { mutableStateOf(false) }
    var staffForAdvanceDialog by remember { mutableStateOf<StaffEntity?>(null) }

    val todayDateString = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    val todayDateFormatted = remember {
        SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(Date())
    }

    val attendanceMap = remember(attendanceRecords) {
        attendanceRecords.associateBy { it.staffId }
    }

    val presentCount = remember(attendanceRecords) {
        attendanceRecords.count { it.status == "PRESENT" }
    }

    val totalSalaryExpenditure = remember(staffList) {
        staffList.sumOf { it.monthlySalary }
    }

    val totalAdvancesGiven = remember(staffList) {
        staffList.sumOf { it.advancePaid }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("staff_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PakEmeraldDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Staff Haziri & Payroll Manager",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = todayDateFormatted,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }

                        Button(
                            onClick = { showAddStaffDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PakGoldSecondary),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("btn_add_staff")
                        ) {
                            Icon(Icons.Default.PersonAdd, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+ Add Staff", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }

                    // Stat metrics
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.12f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Present Today", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                                Text("$presentCount / ${staffList.size}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA5D6A7))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.12f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Monthly Payroll", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                                Text("$currency ${String.format(Locale.getDefault(), "%,.0f", totalSalaryExpenditure)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.12f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Total Peshgi", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                                Text("$currency ${String.format(Locale.getDefault(), "%,.0f", totalAdvancesGiven)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFCC80))
                            }
                        }
                    }
                }
            }
        }

        // Sub-tabs (Attendance Register vs Staff & Salaries)
        item {
            TabRow(
                selectedTabIndex = selectedSubTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PakEmeraldPrimary
            ) {
                Tab(
                    selected = selectedSubTab == 0,
                    onClick = { selectedSubTab = 0 },
                    text = { Text("Daily Attendance (Haziri)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }
                )
                Tab(
                    selected = selectedSubTab == 1,
                    onClick = { selectedSubTab = 1 },
                    text = { Text("Staff & Salaries (Peshgi)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }
                )
            }
        }

        if (staffList.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Badge,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Text("No staff members added yet", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            "Add your sales team, cashiers, helpers, mechanics, tailor masters or instructors to mark attendance and manage salaries.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Button(
                            onClick = { showAddStaffDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Add First Staff Member")
                        }
                    }
                }
            }
        } else {
            if (selectedSubTab == 0) {
                // ATTENDANCE TAB
                items(staffList, key = { it.id }) { staff ->
                    val record = attendanceMap[staff.id]
                    val currentStatus = record?.status ?: "PENDING"

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = staff.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${staff.role} • ${staff.phone}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Quick attendance toggles (P, A, H, L)
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                StatusButton(
                                    label = "Present",
                                    shortCode = "P",
                                    isSelected = currentStatus == "PRESENT",
                                    selectedColor = Color(0xFF2E7D32),
                                    onClick = {
                                        viewModel.recordStaffAttendance(staff.id, staff.name, "PRESENT", "")
                                    }
                                )
                                StatusButton(
                                    label = "Absent",
                                    shortCode = "A",
                                    isSelected = currentStatus == "ABSENT",
                                    selectedColor = Color(0xFFC62828),
                                    onClick = {
                                        viewModel.recordStaffAttendance(staff.id, staff.name, "ABSENT", "")
                                    }
                                )
                                StatusButton(
                                    label = "Half-Day",
                                    shortCode = "1/2",
                                    isSelected = currentStatus == "HALF_DAY",
                                    selectedColor = Color(0xFFEF6C00),
                                    onClick = {
                                        viewModel.recordStaffAttendance(staff.id, staff.name, "HALF_DAY", "")
                                    }
                                )
                                StatusButton(
                                    label = "Leave",
                                    shortCode = "L",
                                    isSelected = currentStatus == "LEAVE",
                                    selectedColor = Color(0xFF1565C0),
                                    onClick = {
                                        viewModel.recordStaffAttendance(staff.id, staff.name, "LEAVE", "")
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                // STAFF & SALARY TAB
                items(staffList, key = { it.id }) { staff ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = staff.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "${staff.role} • 📞 ${staff.phone}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.deleteStaffMember(staff.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = PakEmeraldContainer.copy(alpha = 0.5f)
                                ) {
                                    Text(
                                        text = "Salary: $currency ${String.format(Locale.getDefault(), "%,.0f", staff.monthlySalary)}/mo",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        color = PakEmeraldPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (staff.advancePaid > 0) Color(0xFFFFEBEE) else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = "Peshgi Advance: $currency ${String.format(Locale.getDefault(), "%,.0f", staff.advancePaid)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (staff.advancePaid > 0) Color(0xFFC62828) else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Actions row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { staffForAdvanceDialog = staff }) {
                                    Icon(Icons.Default.Payments, contentDescription = null, modifier = Modifier.size(16.dp), tint = PakEmeraldPrimary)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("+ Advance Peshgi", fontSize = 12.sp, color = PakEmeraldPrimary)
                                }

                                Button(
                                    onClick = {
                                        val netPayable = maxOf(0.0, staff.monthlySalary - staff.advancePaid)
                                        val slip = """
                                            *EMPLOYEE SALARY SLIP / PARCHA*
                                            🏢 *${business?.name ?: "Business"}*
                                            ────────────────────
                                            👤 *Employee:* ${staff.name}
                                            💼 *Designation:* ${staff.role}
                                            📞 *Contact:* ${staff.phone}
                                            ────────────────────
                                            💵 *Basic Salary:* $currency ${String.format(Locale.getDefault(), "%,.0f", staff.monthlySalary)}
                                            🔻 *Advance / Peshgi Deducted:* $currency ${String.format(Locale.getDefault(), "%,.0f", staff.advancePaid)}
                                            💰 *NET PAYABLE:* $currency ${String.format(Locale.getDefault(), "%,.0f", netPayable)}
                                            📅 *Generated:* $todayDateFormatted
                                            ────────────────────
                                            _Authorized Signature / PakBusiness Pro ERP_
                                        """.trimIndent()

                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, slip)
                                            type = "text/plain"
                                        }
                                        context.startActivity(Intent.createChooser(sendIntent, "Share Salary Slip"))
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("WhatsApp Slip", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Staff Dialog
    if (showAddStaffDialog) {
        AddStaffDialog(
            currency = currency,
            onDismiss = { showAddStaffDialog = false },
            onSave = { name, role, phone, salary ->
                viewModel.saveStaffMember(
                    StaffEntity(
                        businessId = business?.id ?: 0L,
                        name = name,
                        role = role,
                        phone = phone,
                        monthlySalary = salary,
                        joiningDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
                    )
                )
                showAddStaffDialog = false
            }
        )
    }

    // Advance Peshgi Dialog
    if (staffForAdvanceDialog != null) {
        val staff = staffForAdvanceDialog!!
        var advanceAmount by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { staffForAdvanceDialog = null },
            title = { Text("Give Advance (Peshgi) to ${staff.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Current Advance Balance: $currency ${String.format(Locale.getDefault(), "%,.0f", staff.advancePaid)}", fontSize = 13.sp)
                    OutlinedTextField(
                        value = advanceAmount,
                        onValueChange = { advanceAmount = it.filter { ch -> ch.isDigit() || ch == '.' } },
                        label = { Text("Advance Amount ($currency)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = advanceAmount.toDoubleOrNull() ?: 0.0
                        if (amount > 0) {
                            viewModel.addStaffAdvance(staff.id, amount)
                        }
                        staffForAdvanceDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                ) {
                    Text("Add Advance")
                }
            },
            dismissButton = {
                TextButton(onClick = { staffForAdvanceDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun StatusButton(
    label: String,
    shortCode: String,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) selectedColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .clickable(onClick = onClick)
            .size(36.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = shortCode,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun AddStaffDialog(
    currency: String,
    onDismiss: () -> Unit,
    onSave: (name: String, role: String, phone: String, salary: Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var salaryText by remember { mutableStateOf("") }

    val presetRoles = listOf("Salesman", "Cashier", "Manager", "Helper", "Chef", "Tailor Master", "Mechanic", "Trainer", "Accountant")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Staff Member", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role / Designation *") },
                    placeholder = { Text("e.g. Salesman, Cashier") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(presetRoles) { preset ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable { role = preset }
                        ) {
                            Text(preset, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = salaryText,
                    onValueChange = { salaryText = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Monthly Salary ($currency) *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val sal = salaryText.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank() && role.isNotBlank()) {
                        onSave(name, role, phone, sal)
                    }
                },
                enabled = name.isNotBlank() && role.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
            ) {
                Text("Add Member")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
