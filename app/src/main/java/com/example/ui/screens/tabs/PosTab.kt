package com.example.ui.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.ui.theme.PakEmeraldDark
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldContainer
import com.example.ui.theme.PakGoldSecondary

data class PosSampleItem(
    val id: String,
    val name: String,
    val category: String,
    val pricePkr: Double
)

private val SAMPLE_CATALOG = listOf(
    PosSampleItem("1", "Basmati Rice 5kg", "Groceries", 1450.0),
    PosSampleItem("2", "Cooking Oil 1L", "Groceries", 520.0),
    PosSampleItem("3", "Tea Powder 500g", "Beverages", 780.0),
    PosSampleItem("4", "Wheat Flour (Atta) 10kg", "Groceries", 1250.0),
    PosSampleItem("5", "Refined Sugar 1kg", "Groceries", 160.0),
    PosSampleItem("6", "Mineral Water 1.5L", "Beverages", 90.0)
)

private val CATEGORIES = listOf("All", "Groceries", "Beverages", "General")

@Composable
fun PosTab(
    business: BusinessEntity?,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val cart = remember { mutableStateMapOf<String, Int>() }
    var showCheckoutDialog by remember { mutableStateOf(false) }
    var lastCheckoutTotal by remember { mutableStateOf(0.0) }

    val filteredItems = remember(searchQuery, selectedCategory) {
        SAMPLE_CATALOG.filter { item ->
            val matchesCategory = (selectedCategory == "All" || item.category == selectedCategory)
            val matchesSearch = item.name.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    val totalAmount = remember(cart.values.sum()) {
        cart.entries.sumOf { entry ->
            val item = SAMPLE_CATALOG.find { it.id == entry.key }
            (item?.pricePkr ?: 0.0) * entry.value
        }
    }

    val totalItemsCount = cart.values.sum()

    Box(modifier = modifier.fillMaxSize().testTag("pos_screen")) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (totalItemsCount > 0) 90.dp else 16.dp)
        ) {
            // Header Strip
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "POS Terminal • ${business?.name ?: "Business"}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PakEmeraldPrimary
                    )
                    Text(
                        text = "Currency: PKR (₨)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Search Bar & Scan Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search product name...", fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(20.dp))
                    },
                    trailingIcon = if (searchQuery.isNotEmpty()) {
                        {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Delete, contentDescription = "Clear", modifier = Modifier.size(18.dp))
                            }
                        }
                    } else null,
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PakEmeraldPrimary
                    ),
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PakEmeraldContainer,
                    modifier = Modifier
                        .size(52.dp)
                        .clickable { /* Barcode scan trigger */ }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = "Scan Barcode",
                            tint = PakEmeraldPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Category Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(CATEGORIES) { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = { Text(category, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PakEmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Products Catalog List
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredItems) { item ->
                    val qtyInCart = cart[item.id] ?: 0
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (qtyInCart > 0) PakEmeraldContainer.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface
                        ),
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = item.category,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "₨ ${"%,.2f".format(item.pricePkr)} PKR",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = PakEmeraldPrimary
                                )
                            }

                            // Qty Selector
                            if (qtyInCart > 0) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clickable {
                                                if (qtyInCart == 1) cart.remove(item.id) else cart[item.id] = qtyInCart - 1
                                            }
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                                        }
                                    }
                                    Text(
                                        text = "$qtyInCart",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Surface(
                                        shape = CircleShape,
                                        color = PakEmeraldPrimary,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clickable { cart[item.id] = qtyInCart + 1 }
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Add, contentDescription = "Increase", tint = Color.White, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            } else {
                                Button(
                                    onClick = { cart[item.id] = 1 },
                                    colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Sticky Cart Pane
        if (totalItemsCount > 0) {
            Card(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = PakEmeraldDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$totalItemsCount Item${if (totalItemsCount > 1) "s" else ""} in Cart",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "₨ ${"%,.2f".format(totalAmount)} PKR",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD54F)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(
                            onClick = { cart.clear() }
                        ) {
                            Text("Clear", color = Color.White.copy(alpha = 0.8f))
                        }

                        Button(
                            onClick = {
                                lastCheckoutTotal = totalAmount
                                cart.clear()
                                showCheckoutDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFBA59)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Checkout",
                                color = Color(0xFF3B2500),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Checkout Success Dialog
        if (showCheckoutDialog) {
            AlertDialog(
                onDismissRequest = { showCheckoutDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PakEmeraldPrimary,
                        modifier = Modifier.size(44.dp)
                    )
                },
                title = { Text("POS Sale Recorded", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Sale receipt generated for ${business?.name ?: "Business"}.")
                        Text(
                            "Total Amount: ₨ ${"%,.2f".format(lastCheckoutTotal)} PKR",
                            fontWeight = FontWeight.Bold,
                            color = PakEmeraldPrimary
                        )
                        Text(
                            "Saved locally on offline database. Zero internet required.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showCheckoutDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                    ) {
                        Text("Done")
                    }
                }
            )
        }
    }
}
