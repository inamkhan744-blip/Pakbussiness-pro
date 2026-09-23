package com.example.ui.screens.tabs

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BusinessEntity
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldSecondary

data class InventoryItem(
    val id: String,
    val name: String,
    val sku: String,
    val quantity: Int,
    val salePricePkr: Double,
    val costPricePkr: Double
)

@Composable
fun InventoryTab(
    business: BusinessEntity?,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    val itemsList = remember {
        mutableStateListOf(
            InventoryItem("1", "Super Basmati Rice", "PKR-RICE-01", 45, 1450.0, 1300.0),
            InventoryItem("2", "Kisan Sunflower Oil 1L", "PKR-OIL-02", 28, 520.0, 480.0),
            InventoryItem("3", "Tapal Danedar Tea 500g", "PKR-TEA-03", 14, 780.0, 720.0),
            InventoryItem("4", "Fine Wheat Flour 10kg", "PKR-FLOUR-04", 5, 1250.0, 1180.0)
        )
    }

    var newName by remember { mutableStateOf("") }
    var newSku by remember { mutableStateOf("") }
    var newQty by remember { mutableStateOf("") }
    var newSalePrice by remember { mutableStateOf("") }

    val filteredList = itemsList.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.sku.contains(searchQuery, ignoreCase = true)
    }

    val totalValuation = itemsList.sumOf { it.quantity * it.salePricePkr }

    Box(modifier = modifier.fillMaxSize().testTag("inventory_screen")) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Summary Strip
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PakEmeraldContainer.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total Inventory Stock",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${itemsList.sumOf { it.quantity }} Units (${itemsList.size} SKUs)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Stock Valuation",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "₨ ${"%,.2f".format(totalValuation)} PKR",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PakEmeraldPrimary
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search inventory by product or SKU...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Product Items List
            items(filteredList) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Inventory2,
                                    contentDescription = null,
                                    tint = PakEmeraldPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "SKU: ${item.sku}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "₨ ${"%,.2f".format(item.salePricePkr)} PKR",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PakEmeraldPrimary
                                )
                            }
                        }

                        // Stock Pill
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (item.quantity <= 10) Color(0xFFFFEBEE) else PakEmeraldContainer
                        ) {
                            Text(
                                text = "${item.quantity} in stock",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (item.quantity <= 10) Color(0xFFC62828) else PakEmeraldPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Bottom spacer for FAB
            item {
                Spacer(modifier = Modifier.height(72.dp))
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
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
        }

        // Add Product Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add Inventory Item") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Product Name") },
                            placeholder = { Text("e.g. Cooking Oil 5L") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = newSku,
                            onValueChange = { newSku = it },
                            label = { Text("SKU / Barcode") },
                            placeholder = { Text("e.g. OIL-5L-01") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = newQty,
                            onValueChange = { newQty = it },
                            label = { Text("Initial Stock Quantity") },
                            placeholder = { Text("20") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = newSalePrice,
                            onValueChange = { newSalePrice = it },
                            label = { Text("Sale Price (PKR)") },
                            placeholder = { Text("2500") },
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newName.isNotBlank()) {
                                itemsList.add(
                                    InventoryItem(
                                        id = "${System.currentTimeMillis()}",
                                        name = newName.trim(),
                                        sku = newSku.ifBlank { "SKU-${System.currentTimeMillis() % 1000}" },
                                        quantity = newQty.toIntOrNull() ?: 10,
                                        salePricePkr = newSalePrice.toDoubleOrNull() ?: 100.0,
                                        costPricePkr = (newSalePrice.toDoubleOrNull() ?: 100.0) * 0.85
                                    )
                                )
                                newName = ""
                                newSku = ""
                                newQty = ""
                                newSalePrice = ""
                                showAddDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                    ) {
                        Text("Save")
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
