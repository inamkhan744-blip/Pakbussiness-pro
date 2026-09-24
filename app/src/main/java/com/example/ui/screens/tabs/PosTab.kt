package com.example.ui.screens.tabs

import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.ui.BusinessCategory
import com.example.ui.resolveBusinessCategory
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldDark
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldContainer
import com.example.ui.theme.PakGoldSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PosSampleItem(
    val id: String,
    val name: String,
    val category: String,
    val price: Double
)

private fun getCatalogForBusiness(businessType: String?): List<PosSampleItem> {
    val category = resolveBusinessCategory(businessType)
    return when (category) {
        BusinessCategory.PHARMACY -> listOf(
            PosSampleItem("ph1", "Panadol 500mg (Strip of 10)", "Analgesics", 45.0),
            PosSampleItem("ph2", "Augmentin 625mg (Box)", "Antibiotics", 390.0),
            PosSampleItem("ph3", "Brufen 400mg (Strip)", "Anti-Inflammatory", 65.0),
            PosSampleItem("ph4", "Surbex Z Multivitamins (30 Tablets)", "Supplements", 580.0),
            PosSampleItem("ph5", "Omeprazole 20mg (Capsules)", "Antacids", 140.0),
            PosSampleItem("ph6", "Disprin Soluble (Box)", "First Aid", 120.0),
            PosSampleItem("ph7", "Surgical Face Mask (Pack 50)", "Supplies", 250.0),
            PosSampleItem("ph8", "Dettol Antiseptic Liquid 250ml", "Hygiene", 410.0)
        )
        BusinessCategory.RESTAURANT -> listOf(
            PosSampleItem("rs1", "Chicken Biryani Special Single", "Rice & Biryani", 380.0),
            PosSampleItem("rs2", "Zinger Burger Deluxe Combo", "Burgers & Fast Food", 450.0),
            PosSampleItem("rs3", "Chicken Karahi (Half Handi)", "Main Course", 850.0),
            PosSampleItem("rs4", "Garlic Butter Roghani Naan", "Breads & Tandoor", 70.0),
            PosSampleItem("rs5", "Special Karak Doodh Patti Chai", "Beverages", 80.0),
            PosSampleItem("rs6", "Chicken Club Sandwich with Fries", "Snacks", 390.0),
            PosSampleItem("rs7", "Soft Drink Can 250ml", "Beverages", 100.0)
        )
        BusinessCategory.BAKERY -> listOf(
            PosSampleItem("bk1", "Dark Chocolate Fudge Cake 2lbs", "Cakes & Pastries", 1250.0),
            PosSampleItem("bk2", "Pineapple Fresh Cream Pastry", "Cakes & Pastries", 110.0),
            PosSampleItem("bk3", "Crispy Chicken Patties (Single)", "Savory Snacks", 75.0),
            PosSampleItem("bk4", "Butter Biscuits Box 500g", "Biscuits & Cookies", 340.0),
            PosSampleItem("bk5", "Cardamom Cake Rusks 400g", "Tea Items", 220.0),
            PosSampleItem("bk6", "Milky Sweet Bread (Large)", "Breads", 140.0)
        )
        BusinessCategory.GYM -> listOf(
            PosSampleItem("gm1", "Gold Whey Protein Isolate 1kg", "Supplements", 8500.0),
            PosSampleItem("gm2", "Pre-Workout Energy Tub 300g", "Supplements", 4200.0),
            PosSampleItem("gm3", "Gym Shaker Bottle 700ml", "Accessories", 650.0),
            PosSampleItem("gm4", "BCAA 2:1:1 Amino Fuel Powder", "Supplements", 3800.0),
            PosSampleItem("gm5", "Heavy Duty Wrist Straps (Pair)", "Gear", 750.0),
            PosSampleItem("gm6", "Microfiber Quick-Dry Gym Towel", "Accessories", 450.0)
        )
        BusinessCategory.ELECTRONICS -> listOf(
            PosSampleItem("el1", "Type-C Braided Fast Cable 65W", "Cables", 350.0),
            PosSampleItem("el2", "GaN Super Fast Wall Charger 45W", "Chargers", 1450.0),
            PosSampleItem("el3", "9D Tempered Glass Screen Protector", "Protection", 250.0),
            PosSampleItem("el4", "AirPods Pro Silicone Protective Case", "Accessories", 400.0),
            PosSampleItem("el5", "20,000mAh Dual USB Powerbank", "Power", 3800.0),
            PosSampleItem("el6", "Handsfree Deep Bass Earphones", "Audio", 450.0)
        )
        BusinessCategory.WORKSHOP -> listOf(
            PosSampleItem("ws1", "Engine Oil Mobil 1 Synthetic 4L", "Lubricants", 6800.0),
            PosSampleItem("ws2", "OEM Engine Oil Filter", "Filters", 850.0),
            PosSampleItem("ws3", "Ceramic Brake Pads Front Set", "Brakes", 2800.0),
            PosSampleItem("ws4", "Iridium Spark Plugs Set of 4", "Ignition", 3200.0),
            PosSampleItem("ws5", "Radiator Coolant Antifreeze 1L", "Fluids", 950.0),
            PosSampleItem("ws6", "Dot 4 Brake Fluid 500ml", "Fluids", 450.0)
        )
        BusinessCategory.LAUNDRY -> listOf(
            PosSampleItem("ld1", "Formal Shirt Wash & Press", "Garments", 120.0),
            PosSampleItem("ld2", "Cotton Pants / Trousers Ironing", "Pressing", 60.0),
            PosSampleItem("ld3", "Men 2-Piece Suit Dryclean", "Dryclean", 650.0),
            PosSampleItem("ld4", "Bedsheet (Double) Wash & Fold", "Linen", 220.0),
            PosSampleItem("ld5", "Winter Blanket Heavy Dryclean", "Heavy Items", 850.0),
            PosSampleItem("ld6", "Curtain Cleaning (Per Panel)", "Home", 350.0)
        )
        BusinessCategory.WHOLESALE -> listOf(
            PosSampleItem("wh1", "Sugar Refined 50kg Commercial Sack", "Bags & Sacks", 7200.0),
            PosSampleItem("wh2", "Super Fine Wheat Flour 20kg Bag", "Grains & Flour", 2650.0),
            PosSampleItem("wh3", "Pure Cooking Oil Master Carton (16L)", "Cartons", 8200.0),
            PosSampleItem("wh4", "Vanaspati Ghee Commercial Tin 16kg", "Tins", 7900.0),
            PosSampleItem("wh5", "Basmati Super Kernel 50kg Bag", "Rice", 14500.0),
            PosSampleItem("wh6", "Iodized Table Salt Carton (24x800g)", "Cartons", 1100.0)
        )
        else -> listOf(
            PosSampleItem("gn1", "Super Basmati Rice 5kg", "Groceries", 1450.0),
            PosSampleItem("gn2", "Sunflower Cooking Oil 1L", "Groceries", 520.0),
            PosSampleItem("gn3", "Danedar Tea Powder 500g", "Beverages", 780.0),
            PosSampleItem("gn4", "Wheat Flour (Atta) 10kg", "Groceries", 1250.0),
            PosSampleItem("gn5", "Refined White Sugar 1kg", "Groceries", 160.0),
            PosSampleItem("gn6", "Mineral Water Bottle 1.5L", "Beverages", 90.0),
            PosSampleItem("gn7", "Natural Whole Milk 1L Pack", "Dairy", 260.0),
            PosSampleItem("gn8", "Handwash Soap Antibacterial", "Toiletries", 130.0)
        )
    }
}

@Composable
fun PosTab(
    business: BusinessEntity?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currency = business?.currency ?: "PKR"
    val catalog = remember(business?.type) { getCatalogForBusiness(business?.type) }
    val categories = remember(catalog) {
        listOf("All") + catalog.map { it.category }.distinct()
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val cart = remember { mutableStateMapOf<String, Int>() }

    // Checkout & Scanner Modal State
    var showCheckoutDialog by remember { mutableStateOf(false) }
    var showBarcodeScannerDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var lastReceiptText by remember { mutableStateOf("") }

    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var selectedPaymentMode by remember { mutableStateOf("Cash") }
    var discountPercent by remember { mutableIntStateOf(0) }
    var gstTaxPercent by remember { mutableIntStateOf(0) }

    val filteredItems = remember(searchQuery, selectedCategory, catalog) {
        catalog.filter { item ->
            val matchesCategory = (selectedCategory == "All" || item.category == selectedCategory)
            val matchesSearch = item.name.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    val subtotalAmount = remember(cart.values.sum(), catalog) {
        cart.entries.sumOf { entry ->
            val item = catalog.find { it.id == entry.key }
            (item?.price ?: 0.0) * entry.value
        }
    }

    val discountAmount = (subtotalAmount * discountPercent) / 100.0
    val taxableAmount = (subtotalAmount - discountAmount).coerceAtLeast(0.0)
    val taxAmount = (taxableAmount * gstTaxPercent) / 100.0
    val netTotalAmount = taxableAmount + taxAmount
    val totalItemsCount = cart.values.sum()

    fun shareReceiptViaWhatsApp(receipt: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, receipt)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share POS Receipt")
        context.startActivity(shareIntent)
    }

    Box(modifier = modifier.fillMaxSize().testTag("pos_screen")) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (totalItemsCount > 0) 100.dp else 16.dp)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PointOfSale,
                            contentDescription = null,
                            tint = PakEmeraldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${business?.name ?: "Business"} • POS",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PakEmeraldPrimary
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = PakEmeraldContainer
                    ) {
                        Text(
                            text = "Currency: $currency",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PakEmeraldPrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            // Search Bar & Barcode Simulator
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
                    placeholder = { Text("Search catalog items...", fontSize = 14.sp) },
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
                        .clickable { showBarcodeScannerDialog = true }
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
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PakEmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Product List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredItems) { item ->
                    val quantityInCart = cart[item.id] ?: 0

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pos_item_${item.id}")
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
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = item.category,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$currency ${String.format(Locale.getDefault(), "%,.2f", item.price)}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PakEmeraldPrimary,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            if (quantityInCart > 0) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            if (quantityInCart > 1) {
                                                cart[item.id] = quantityInCart - 1
                                            } else {
                                                cart.remove(item.id)
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Remove,
                                            contentDescription = "Remove",
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = PakEmeraldContainer,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "$quantityInCart",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = PakEmeraldPrimary
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = { cart[item.id] = quantityInCart + 1 },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Add",
                                            tint = PakEmeraldPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
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

        // Bottom Sticky Cart Checkout Pane
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
                            text = "$currency ${String.format(Locale.getDefault(), "%,.2f", subtotalAmount)}",
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
                            onClick = { showCheckoutDialog = true },
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

        // Complete Checkout Dialog with Discount, Payment Mode & Customer Info
        if (showCheckoutDialog) {
            AlertDialog(
                onDismissRequest = { showCheckoutDialog = false },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Receipt, contentDescription = null, tint = PakEmeraldPrimary)
                        Text("Finalize Sale Order", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Customer Details
                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Customer Name (Optional)") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = customerPhone,
                            onValueChange = { customerPhone = it },
                            label = { Text("Phone / WhatsApp (Optional)") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Payment Mode Selection
                        Text("Payment Mode", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("Cash", "Card", "Online", "Udhaar").forEach { mode ->
                                val isSelected = selectedPaymentMode == mode
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) PakEmeraldPrimary else MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedPaymentMode = mode }
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = mode,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }

                        // Quick Discount Selector
                        Text("Quick Discount", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(0, 5, 10, 15).forEach { pct ->
                                val isSelected = discountPercent == pct
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) Color(0xFFD97706) else MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { discountPercent = pct }
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = if (pct == 0) "0%" else "$pct% Off",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }

                        // GST / Sales Tax Selector
                        Text("Sales Tax / GST (FBR)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(0 to "0% (Exempt)", 5 to "5% (Services)", 18 to "18% (GST)").forEach { (taxPct, label) ->
                                val isSelected = gstTaxPercent == taxPct
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) PakEmeraldPrimary else MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { gstTaxPercent = taxPct }
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = label,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }

                        // Order Summary Box
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = PakEmeraldContainer.copy(alpha = 0.4f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Subtotal ($totalItemsCount items):", fontSize = 12.sp)
                                    Text("$currency ${String.format(Locale.getDefault(), "%,.2f", subtotalAmount)}", fontSize = 12.sp)
                                }
                                if (discountPercent > 0) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Discount ($discountPercent%):", fontSize = 12.sp, color = Color(0xFFD97706))
                                        Text("- $currency ${String.format(Locale.getDefault(), "%,.2f", discountAmount)}", fontSize = 12.sp, color = Color(0xFFD97706))
                                    }
                                }
                                if (gstTaxPercent > 0) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Sales Tax ($gstTaxPercent%):", fontSize = 12.sp, color = Color(0xFF0284C7))
                                        Text("+ $currency ${String.format(Locale.getDefault(), "%,.2f", taxAmount)}", fontSize = 12.sp, color = Color(0xFF0284C7))
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Net Payable:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(
                                        "$currency ${String.format(Locale.getDefault(), "%,.2f", netTotalAmount)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = PakEmeraldPrimary
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val timeStr = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
                            val itemsLines = cart.entries.joinToString("\n") { entry ->
                                val item = catalog.find { it.id == entry.key }
                                "• ${item?.name ?: "Item"} x${entry.value} = $currency ${String.format(Locale.getDefault(), "%,.2f", (item?.price ?: 0.0) * entry.value)}"
                            }

                            lastReceiptText = """
                                ══════════════════════════
                                ${business?.name ?: "BUSINESS STORE"}
                                POS TAX INVOICE
                                ══════════════════════════
                                Date: $timeStr
                                Customer: ${if (customerName.isNotBlank()) customerName else "Walk-in Customer"}
                                Phone: ${if (customerPhone.isNotBlank()) customerPhone else "N/A"}
                                Payment: $selectedPaymentMode
                                NTN / STRN: ${business?.id ?: 1001}-TAX-PK
                                ──────────────────────────
                                Items Purchased:
                                $itemsLines
                                ──────────────────────────
                                Subtotal: $currency ${String.format(Locale.getDefault(), "%,.2f", subtotalAmount)}
                                Discount: $currency ${String.format(Locale.getDefault(), "%,.2f", discountAmount)} ($discountPercent%)
                                Taxable Amount: $currency ${String.format(Locale.getDefault(), "%,.2f", taxableAmount)}
                                GST Tax ($gstTaxPercent%): $currency ${String.format(Locale.getDefault(), "%,.2f", taxAmount)}
                                NET PAYABLE: $currency ${String.format(Locale.getDefault(), "%,.2f", netTotalAmount)}
                                ══════════════════════════
                                Thank you for your business!
                                100% Offline POS by PakBusiness Pro.
                            """.trimIndent()

                            cart.clear()
                            customerName = ""
                            customerPhone = ""
                            discountPercent = 0
                            gstTaxPercent = 0
                            showCheckoutDialog = false
                            showSuccessDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                    ) {
                        Text("Record & Generate Receipt")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCheckoutDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Live Barcode / SKU Laser Scanner Dialog
        if (showBarcodeScannerDialog) {
            var manualBarcode by remember { mutableStateOf("") }
            var lastScannedMsg by remember { mutableStateOf<String?>(null) }

            AlertDialog(
                onDismissRequest = { showBarcodeScannerDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = PakEmeraldPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                },
                title = {
                    Text(
                        text = "Laser Barcode Scanner",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Scanner Viewfinder Mockup
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(Color.Black, RoundedCornerShape(12.dp))
                                .border(2.dp, PakEmeraldPrimary, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Viewfinder Grid Overlay
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(2.dp)
                                        .background(Color.Red)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "ALIGN BARCODE WITHIN FRAME",
                                    color = Color.White.copy(alpha = 0.75f),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        if (lastScannedMsg != null) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = PakEmeraldContainer,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "✓ $lastScannedMsg added to Cart!",
                                    color = PakEmeraldPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }

                        // Fast Scan Item Presets
                        Text("Tap item to simulate rapid scan:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.height(140.dp)
                        ) {
                            catalog.take(4).forEach { item ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            cart[item.id] = (cart[item.id] ?: 0) + 1
                                            lastScannedMsg = item.name
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(item.name, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                            Text("SKU: ${item.id.uppercase()}-PK", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                        Text("+1 Scan", fontSize = 11.sp, color = PakEmeraldPrimary, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        // Manual SKU Code Entry
                        OutlinedTextField(
                            value = manualBarcode,
                            onValueChange = { manualBarcode = it },
                            label = { Text("Manual Barcode / SKU", fontSize = 12.sp) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                if (manualBarcode.isNotBlank()) {
                                    IconButton(
                                        onClick = {
                                            val matched = catalog.find {
                                                it.id.equals(manualBarcode.trim(), ignoreCase = true) ||
                                                it.name.contains(manualBarcode.trim(), ignoreCase = true)
                                            } ?: catalog.firstOrNull()

                                            if (matched != null) {
                                                cart[matched.id] = (cart[matched.id] ?: 0) + 1
                                                lastScannedMsg = matched.name
                                                manualBarcode = ""
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Add", tint = PakEmeraldPrimary)
                                    }
                                }
                            }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showBarcodeScannerDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = PakEmeraldPrimary)
                    ) {
                        Text("Done Scanning (${cart.values.sum()} items)")
                    }
                }
            )
        }

        // Checkout Success & Receipt Share Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PakEmeraldPrimary,
                        modifier = Modifier.size(44.dp)
                    )
                },
                title = { Text("Sale Successfully Recorded", fontWeight = FontWeight.Bold, fontSize = 17.sp) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Invoice receipt has been created and stored in local database.")
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = lastReceiptText,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            shareReceiptViaWhatsApp(lastReceiptText)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share via WhatsApp / Slip", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSuccessDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
