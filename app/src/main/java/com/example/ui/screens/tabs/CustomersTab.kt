package com.example.ui.screens.tabs

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldContainer
import com.example.ui.theme.PakGoldSecondary
import java.util.Locale

data class CustomerItem(
    val id: String,
    val name: String,
    val phone: String,
    var balance: Double
)

@Composable
fun CustomersTab(
    business: BusinessEntity?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currency = business?.currency ?: "PKR"
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    // Customer payment recording state
    var customerToPay by remember { mutableStateOf<CustomerItem?>(null) }
    var paymentAmountInput by remember { mutableStateOf("") }

    val customersList = remember {
        mutableStateListOf(
            CustomerItem("1", "Muhammad Rizwan", "+92 321 9876543", 0.0),
            CustomerItem("2", "Chaudhry Bilal", "+92 300 4567890", 2450.0),
            CustomerItem("3", "Kashif Ali & Co.", "+92 333 1122334", 11200.0),
            CustomerItem("4", "Tariq Mehmood Traders", "+92 345 5566778", 5800.0)
        )
    }

    var newCustomerName by remember { mutableStateOf("") }
    var newCustomerPhone by remember { mutableStateOf("") }
    var newCustomerBalance by remember { mutableStateOf("") }

    val filteredList = customersList.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.phone.contains(searchQuery)
    }

    val totalReceivables = customersList.sumOf { it.balance }

    fun sendWhatsAppReminder(customer: CustomerItem) {
        val reminderMessage = """
            Assalam-o-Alaikum ${customer.name} Sahab,
            
            ${business?.name ?: "Our Business"} ki taraf se aap ka baqaya Udhaar/Khata balance $currency ${String.format(Locale.getDefault(), "%,.2f", customer.balance)} hai.
            
            Baraye meherbani jald az jald is ki adaigi farmayein.
            
            JazakAllah Khair!
            ${business?.name ?: "Business"} (${business?.phone ?: ""})
        """.trimIndent()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, reminderMessage)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Send Khata Reminder via WhatsApp")
        context.startActivity(shareIntent)
    }

    fun callCustomer(phone: String) {
        if (phone.isNotBlank()) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${phone.replace(" ", "")}")
            }
            context.startActivity(intent)
        }
    }

    Box(modifier = modifier.fillMaxSize().testTag("customers_screen")) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Khata Summary Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PakGoldContainer.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Udhaar / Khata Receivable",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = PakGoldSecondary
                            ) {
                                Text(
                                    text = "${customersList.count { it.balance > 0 }} with Balance",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "$currency ${String.format(Locale.getDefault(), "%,.2f", totalReceivables)}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )

                        Text(
                            text = "Customer ledger balances are stored locally with zero internet dependency.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search customer name or phone...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Customer List
            items(filteredList) { customer ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth().testTag("customer_item_${customer.id}")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(PakEmeraldContainer, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = PakEmeraldPrimary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = customer.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = customer.phone,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (customer.balance > 0) "Udhaar Due" else "Clear",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (customer.balance > 0) Color(0xFFDC2626) else PakEmeraldPrimary
                                )
                                Text(
                                    text = "$currency ${String.format(Locale.getDefault(), "%,.0f", customer.balance)}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (customer.balance > 0) Color(0xFFDC2626) else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Action Buttons: Call, WhatsApp Reminder & Pay Khata
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { callCustomer(customer.phone) },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp), tint = PakEmeraldPrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Call", fontSize = 11.sp, color = PakEmeraldPrimary)
                            }

                            if (customer.balance > 0) {
                                Button(
                                    onClick = { sendWhatsAppReminder(customer) },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.weight(1.3f)
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("WhatsApp", fontSize = 11.sp, color = Color.White)
                                }

                                Button(
                                    onClick = {
                                        customerToPay = customer
                                        paymentAmountInput = customer.balance.toInt().toString()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.weight(1.3f)
                                ) {
                                    Icon(Icons.Default.Payment, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Receive", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add Customer FAB
        FloatingActionButton(
            onClick = { showAddDialog = true },
            containerColor = PakEmeraldPrimary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("fab_add_customer")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Customer")
        }

        // Add Customer Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Register New Customer", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newCustomerName,
                            onValueChange = { newCustomerName = it },
                            label = { Text("Customer Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCustomerPhone,
                            onValueChange = { newCustomerPhone = it },
                            label = { Text("Phone Number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCustomerBalance,
                            onValueChange = { newCustomerBalance = it },
                            label = { Text("Opening Udhaar / Balance ($currency)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newCustomerName.isNotBlank()) {
                                customersList.add(
                                    CustomerItem(
                                        id = System.currentTimeMillis().toString(),
                                        name = newCustomerName.trim(),
                                        phone = newCustomerPhone.trim(),
                                        balance = newCustomerBalance.toDoubleOrNull() ?: 0.0
                                    )
                                )
                                newCustomerName = ""
                                newCustomerPhone = ""
                                newCustomerBalance = ""
                                showAddDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                    ) {
                        Text("Add Customer")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Receive Payment Dialog
        customerToPay?.let { payingCust ->
            AlertDialog(
                onDismissRequest = { customerToPay = null },
                title = { Text("Receive Udhaar Payment", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Receiving payment from ${payingCust.name}")
                        Text(
                            text = "Current Due: $currency ${String.format(Locale.getDefault(), "%,.2f", payingCust.balance)}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDC2626)
                        )
                        OutlinedTextField(
                            value = paymentAmountInput,
                            onValueChange = { paymentAmountInput = it },
                            label = { Text("Amount Paid ($currency)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val paid = paymentAmountInput.toDoubleOrNull() ?: 0.0
                            val idx = customersList.indexOfFirst { it.id == payingCust.id }
                            if (idx != -1 && paid > 0) {
                                val remaining = (customersList[idx].balance - paid).coerceAtLeast(0.0)
                                customersList[idx] = customersList[idx].copy(balance = remaining)
                            }
                            customerToPay = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                    ) {
                        Text("Save & Update Khata")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { customerToPay = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
