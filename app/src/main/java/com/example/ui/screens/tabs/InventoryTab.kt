package com.example.ui.screens.tabs

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BusinessEntity
import com.example.ui.BusinessCategory
import com.example.ui.resolveBusinessCategory
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldDark
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldContainer
import com.example.ui.theme.PakGoldSecondary
import java.util.Locale

data class InventoryItem(
    val id: String,
    val name: String,
    val sku: String,
    var quantity: Int,
    val salePrice: Double,
    val costPrice: Double,
    val minThreshold: Int = 10
)

private fun getInitialInventoryForBusiness(businessType: String?): List<InventoryItem> {
    val category = resolveBusinessCategory(businessType)
    return when (category) {
        BusinessCategory.PHARMACY -> listOf(
            InventoryItem("1", "Panadol 500mg (Box of 200)", "MED-PAN-01", 120, 900.0, 750.0, 20),
            InventoryItem("2", "Augmentin 625mg (Box of 14)", "MED-AUG-02", 45, 390.0, 320.0, 15),
            InventoryItem("3", "Brufen 400mg Strips", "MED-BRU-03", 8, 65.0, 50.0, 15),
            InventoryItem("4", "Surbex Z Multivitamins 30s", "MED-SRB-04", 25, 580.0, 490.0, 10),
            InventoryItem("5", "Omeprazole 20mg Capsules", "MED-OMP-05", 5, 140.0, 110.0, 10)
        )
        BusinessCategory.RESTAURANT -> listOf(
            InventoryItem("1", "Basmati Biryani Rice (50kg Bag)", "ING-RICE-01", 14, 14500.0, 13000.0, 5),
            InventoryItem("2", "Fresh Broiler Chicken (kg)", "ING-CHK-02", 42, 620.0, 540.0, 20),
            InventoryItem("3", "Cooking Oil Master Tin (16L)", "ING-OIL-03", 6, 8200.0, 7600.0, 4),
            InventoryItem("4", "Biryani & Karahi Masala Mix", "ING-MSL-04", 18, 450.0, 360.0, 10),
            InventoryItem("5", "Special Tea Leaves (10kg Sack)", "ING-TEA-05", 3, 12000.0, 10500.0, 2)
        )
        BusinessCategory.BAKERY -> listOf(
            InventoryItem("1", "Fine Cake Flour (Maida 50kg)", "RAW-FLR-01", 12, 6500.0, 5800.0, 4),
            InventoryItem("2", "Refined White Sugar (50kg)", "RAW-SGR-02", 8, 7200.0, 6800.0, 3),
            InventoryItem("3", "Dark Cocoa Compound (10kg)", "RAW-CCA-03", 5, 8500.0, 7200.0, 2),
            InventoryItem("4", "Cream Margarine (16kg Tin)", "RAW-MRG-04", 7, 9200.0, 8400.0, 3),
            InventoryItem("5", "Cake Packaging Boxes (Pack 100)", "PKG-BOX-05", 4, 1800.0, 1400.0, 5)
        )
        BusinessCategory.GYM -> listOf(
            InventoryItem("1", "Gold Whey Protein 2kg Tub", "SUP-WHEY-01", 18, 14500.0, 12000.0, 5),
            InventoryItem("2", "Pre-Workout Energy 300g", "SUP-PRE-02", 12, 4200.0, 3400.0, 4),
            InventoryItem("3", "BCAA 2:1:1 Fuel Powder", "SUP-BCAA-03", 7, 3800.0, 3000.0, 3),
            InventoryItem("4", "Gym Shaker Bottles 700ml", "ACC-SHK-04", 35, 650.0, 420.0, 10),
            InventoryItem("5", "Heavy Duty Wrist Wraps", "ACC-WRST-05", 22, 750.0, 480.0, 8)
        )
        BusinessCategory.ELECTRONICS -> listOf(
            InventoryItem("1", "Type-C Braided Cable 65W", "ACC-CAB-01", 55, 350.0, 180.0, 15),
            InventoryItem("2", "GaN Wall Fast Charger 45W", "ACC-CHG-02", 24, 1450.0, 950.0, 10),
            InventoryItem("3", "9D Glass Screen Protector", "ACC-GLS-03", 95, 250.0, 90.0, 20),
            InventoryItem("4", "20,000mAh Dual USB Powerbank", "PWR-BNK-04", 8, 3800.0, 2900.0, 5),
            InventoryItem("5", "Wireless Bluetooth Neckband", "AUD-NCK-05", 16, 1850.0, 1200.0, 6)
        )
        BusinessCategory.WORKSHOP -> listOf(
            InventoryItem("1", "Mobil 1 Synthetic Engine Oil 4L", "LUB-MOB-01", 16, 6800.0, 5800.0, 5),
            InventoryItem("2", "Toyota Genuine Oil Filter", "FLT-OIL-02", 30, 850.0, 550.0, 10),
            InventoryItem("3", "Ceramic Brake Pads Front Set", "BRK-PAD-03", 8, 2800.0, 2100.0, 4),
            InventoryItem("4", "Iridium Spark Plugs Set of 4", "IGN-PLG-04", 12, 3200.0, 2400.0, 4),
            InventoryItem("5", "Radiator Coolant Antifreeze 1L", "FLD-CLN-05", 22, 950.0, 680.0, 6)
        )
        BusinessCategory.WHOLESALE -> listOf(
            InventoryItem("1", "Refined Sugar Commercial 50kg", "WHL-SGR-01", 85, 7200.0, 6900.0, 25),
            InventoryItem("2", "Super Fine Wheat Flour 20kg", "WHL-FLR-02", 140, 2650.0, 2450.0, 40),
            InventoryItem("3", "Pure Cooking Oil Master 16L", "WHL-OIL-03", 65, 8200.0, 7700.0, 20),
            InventoryItem("4", "Vanaspati Ghee Commercial Tin", "WHL-GHE-04", 50, 7900.0, 7450.0, 15),
            InventoryItem("5", "Super Kernel Basmati 50kg Sack", "WHL-RCE-05", 40, 14500.0, 13800.0, 10)
        )
        else -> listOf(
            InventoryItem("1", "Super Basmati Rice 5kg", "RET-RICE-01", 45, 1450.0, 1300.0, 10),
            InventoryItem("2", "Sunflower Cooking Oil 1L", "RET-OIL-02", 28, 520.0, 480.0, 10),
            InventoryItem("3", "Danedar Tea Powder 500g", "RET-TEA-03", 14, 780.0, 720.0, 8),
            InventoryItem("4", "Fine Wheat Flour 10kg", "RET-FLR-04", 5, 1250.0, 1180.0, 8),
            InventoryItem("5", "Refined White Sugar 1kg", "RET-SGR-05", 60, 160.0, 145.0, 15)
        )
    }
}

@Composable
fun InventoryTab(
    business: BusinessEntity?,
    modifier: Modifier = Modifier
) {
    val currency = business?.currency ?: "PKR"
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var filterLowStockOnly by remember { mutableStateOf(false) }

    val itemsList = remember(business?.type) {
        mutableStateListOf<InventoryItem>().apply {
            addAll(getInitialInventoryForBusiness(business?.type))
        }
    }

    var newName by remember { mutableStateOf("") }
    var newSku by remember { mutableStateOf("") }
    var newQty by remember { mutableStateOf("") }
    var newCostPrice by remember { mutableStateOf("") }
    var newSalePrice by remember { mutableStateOf("") }

    val filteredList = itemsList.filter { item ->
        val matchesQuery = item.name.contains(searchQuery, ignoreCase = true) || item.sku.contains(searchQuery, ignoreCase = true)
        val matchesLowStock = if (filterLowStockOnly) item.quantity <= item.minThreshold else true
        matchesQuery && matchesLowStock
    }

    val totalValuation = itemsList.sumOf { it.quantity * it.salePrice }
    val totalCost = itemsList.sumOf { it.quantity * it.costPrice }
    val estimatedProfit = (totalValuation - totalCost).coerceAtLeast(0.0)
    val lowStockCount = itemsList.count { it.quantity <= it.minThreshold }

    Box(modifier = modifier.fillMaxSize().testTag("inventory_screen")) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Valuation and Stock Health Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PakEmeraldContainer.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Inventory Valuation",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = PakEmeraldPrimary
                            ) {
                                Text(
                                    text = "${itemsList.size} SKUs",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "$currency ${String.format(Locale.getDefault(), "%,.2f", totalValuation)}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = PakEmeraldPrimary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Cost Valuation", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$currency ${String.format(Locale.getDefault(), "%,.0f", totalCost)}", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }
                            Column {
                                Text("Potential Margin", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$currency ${String.format(Locale.getDefault(), "%,.0f", estimatedProfit)}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF16A34A))
                            }
                            Column {
                                Text("Low Stock Alert", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$lowStockCount Items", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (lowStockCount > 0) Color(0xFFDC2626) else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            // Search Bar & Low Stock Filter Chip
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search product name or SKU...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )

                    FilterChip(
                        selected = filterLowStockOnly,
                        onClick = { filterLowStockOnly = !filterLowStockOnly },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (filterLowStockOnly) Color.White else Color(0xFFDC2626),
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        label = { Text("Low Stock", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFDC2626),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Stock Items
            items(filteredList) { item ->
                val isLow = item.quantity <= item.minThreshold
                val marginPct = if (item.costPrice > 0) {
                    ((item.salePrice - item.costPrice) / item.costPrice * 100).toInt()
                } else 0

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth().testTag("inventory_item_${item.id}")
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "SKU: ${item.sku}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isLow) Color(0xFFFFEBEE) else PakEmeraldContainer
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    if (isLow) {
                                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFC62828), modifier = Modifier.size(14.dp))
                                    }
                                    Text(
                                        text = "${item.quantity} in Stock",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (isLow) Color(0xFFC62828) else PakEmeraldPrimary
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Column {
                                    Text("Sale Price", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        text = "$currency ${String.format(Locale.getDefault(), "%,.2f", item.salePrice)}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PakEmeraldPrimary
                                    )
                                }
                                Column {
                                    Text("Cost Price", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        text = "$currency ${String.format(Locale.getDefault(), "%,.2f", item.costPrice)}",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Column {
                                    Text("Margin", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        text = "+$marginPct%",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF16A34A)
                                    )
                                }
                            }

                            // Quick Adjust Stock Buttons
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        val idx = itemsList.indexOfFirst { it.id == item.id }
                                        if (idx != -1 && itemsList[idx].quantity > 0) {
                                            itemsList[idx] = itemsList[idx].copy(quantity = itemsList[idx].quantity - 1)
                                        }
                                    },
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Reduce 1", tint = MaterialTheme.colorScheme.error)
                                }

                                IconButton(
                                    onClick = {
                                        val idx = itemsList.indexOfFirst { it.id == item.id }
                                        if (idx != -1) {
                                            itemsList[idx] = itemsList[idx].copy(quantity = itemsList[idx].quantity + 1)
                                        }
                                    },
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Add 1", tint = PakEmeraldPrimary)
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = PakEmeraldContainer,
                                    modifier = Modifier
                                        .clickable {
                                            val idx = itemsList.indexOfFirst { it.id == item.id }
                                            if (idx != -1) {
                                                itemsList[idx] = itemsList[idx].copy(quantity = itemsList[idx].quantity + 10)
                                            }
                                        }
                                        .padding(horizontal = 6.dp, vertical = 4.dp)
                                ) {
                                    Text("+10 In", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PakEmeraldPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add Product FAB
        FloatingActionButton(
            onClick = { showAddDialog = true },
            containerColor = PakEmeraldPrimary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("fab_add_inventory_item")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Item")
        }

        // Add Item Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add Inventory Item", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Product / Item Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newSku,
                            onValueChange = { newSku = it },
                            label = { Text("SKU / Barcode Code") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newQty,
                            onValueChange = { newQty = it },
                            label = { Text("Initial Quantity") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCostPrice,
                            onValueChange = { newCostPrice = it },
                            label = { Text("Cost Price ($currency)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newSalePrice,
                            onValueChange = { newSalePrice = it },
                            label = { Text("Sale Price ($currency)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newName.isNotBlank()) {
                                itemsList.add(
                                    InventoryItem(
                                        id = System.currentTimeMillis().toString(),
                                        name = newName.trim(),
                                        sku = if (newSku.isNotBlank()) newSku.trim() else "SKU-${System.currentTimeMillis().toString().takeLast(4)}",
                                        quantity = newQty.toIntOrNull() ?: 1,
                                        costPrice = newCostPrice.toDoubleOrNull() ?: 0.0,
                                        salePrice = newSalePrice.toDoubleOrNull() ?: 0.0
                                    )
                                )
                                newName = ""
                                newSku = ""
                                newQty = ""
                                newCostPrice = ""
                                newSalePrice = ""
                                showAddDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                    ) {
                        Text("Save Item")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
