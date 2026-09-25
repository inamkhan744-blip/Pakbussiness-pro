package com.example.ui.screens.tools

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppLanguage
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldDark
import com.example.ui.theme.PakEmeraldPrimary
import com.example.ui.theme.PakGoldContainer
import com.example.ui.theme.PakGoldSecondary

@Composable
fun PakBusinessToolsScreen(
    appLanguage: AppLanguage = AppLanguage.ENGLISH,
    modifier: Modifier = Modifier
) {
    var selectedToolIndex by remember { mutableIntStateOf(0) }

    val toolTitles = listOf(
        "Cash Galla",
        "Maund / KG",
        "Gold / Tola",
        "Fabric Gaz/M",
        "GST / Tax"
    )

    Column(modifier = modifier.fillMaxSize()) {
        // Tools Sub-Tab Row
        TabRow(
            selectedTabIndex = selectedToolIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = PakEmeraldPrimary
        ) {
            toolTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedToolIndex == index,
                    onClick = { selectedToolIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (selectedToolIndex == index) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1
                        )
                    }
                )
            }
        }

        when (selectedToolIndex) {
            0 -> CashDenominationCalculator(appLanguage)
            1 -> MandiMaundCalculator(appLanguage)
            2 -> GoldJewelryCalculator(appLanguage)
            3 -> FabricMeasurementCalculator(appLanguage)
            4 -> GstTaxCalculator(appLanguage)
        }
    }
}

/**
 * 1. Pakistani Galla Cash Denomination Counter (روپوں کی گنتی)
 */
@Composable
fun CashDenominationCalculator(appLanguage: AppLanguage) {
    var count5000 by remember { mutableStateOf("") }
    var count1000 by remember { mutableStateOf("") }
    var count500 by remember { mutableStateOf("") }
    var count100 by remember { mutableStateOf("") }
    var count50 by remember { mutableStateOf("") }
    var count20 by remember { mutableStateOf("") }
    var count10 by remember { mutableStateOf("") }

    val total5000 = (count5000.toLongOrNull() ?: 0L) * 5000L
    val total1000 = (count1000.toLongOrNull() ?: 0L) * 1000L
    val total500 = (count500.toLongOrNull() ?: 0L) * 500L
    val total100 = (count100.toLongOrNull() ?: 0L) * 100L
    val total50 = (count50.toLongOrNull() ?: 0L) * 50L
    val total20 = (count20.toLongOrNull() ?: 0L) * 20L
    val total10 = (count10.toLongOrNull() ?: 0L) * 10L

    val grandTotal = total5000 + total1000 + total500 + total100 + total50 + total20 + total10

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PakEmeraldDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Cash Counted / کل نقدی",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 13.sp
                    )
                    Text(
                        text = "₨ ${"%,d".format(grandTotal)}",
                        color = Color(0xFFFFD54F),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DenominationRow("Rs. 5,000", count5000, total5000) { count5000 = it }
                    DenominationRow("Rs. 1,000", count1000, total1000) { count1000 = it }
                    DenominationRow("Rs. 500", count500, total500) { count500 = it }
                    DenominationRow("Rs. 100", count100, total100) { count100 = it }
                    DenominationRow("Rs. 50", count50, total50) { count50 = it }
                    DenominationRow("Rs. 20", count20, total20) { count20 = it }
                    DenominationRow("Rs. 10", count10, total10) { count10 = it }
                }
            }
        }
    }
}

@Composable
fun DenominationRow(label: String, countStr: String, rowTotal: Long, onCountChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, modifier = Modifier.width(75.dp))
        OutlinedTextField(
            value = countStr,
            onValueChange = { onCountChange(it.filter { ch -> ch.isDigit() }) },
            placeholder = { Text("0") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(90.dp)
        )
        Text(
            text = "₨ ${"%,d".format(rowTotal)}",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = PakEmeraldPrimary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * 2. Mandi Maund (Mann) / KG Calculator (من اور کلو حساب)
 */
@Composable
fun MandiMaundCalculator(appLanguage: AppLanguage) {
    var ratePerMaund by remember { mutableStateOf("4500") }
    var maunds by remember { mutableStateOf("10") }
    var extraKgs by remember { mutableStateOf("15") }

    val rate = ratePerMaund.toDoubleOrNull() ?: 0.0
    val m = maunds.toDoubleOrNull() ?: 0.0
    val k = extraKgs.toDoubleOrNull() ?: 0.0

    val totalKgs = (m * 40.0) + k
    val ratePerKg = rate / 40.0
    val totalAmount = totalKgs * ratePerKg

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PakEmeraldDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Total Mandi Bill / من حساب بل", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                    Text(
                        text = "₨ ${"%,.2f".format(totalAmount)}",
                        color = Color(0xFFFFD54F),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Total Weight: ${"%.1f".format(totalKgs)} KG (${"%.2f".format(totalKgs / 40.0)} Mann)",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = ratePerMaund,
                        onValueChange = { ratePerMaund = it },
                        label = { Text("Rate Per Maund / من ریٹ (₨)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = maunds,
                            onValueChange = { maunds = it },
                            label = { Text("Maunds (من)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = extraKgs,
                            onValueChange = { extraKgs = it },
                            label = { Text("Extra KGs (کلو)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PakEmeraldContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Rate per KG: ₨ ${"%.2f".format(ratePerKg)} • 1 Mann = 40 KG",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = PakEmeraldDark,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 3. Gold & Jewelry Tola/Masha/Ratti Calculator (سونا چاندی ریٹ)
 */
@Composable
fun GoldJewelryCalculator(appLanguage: AppLanguage) {
    var goldRatePerTola by remember { mutableStateOf("275000") }
    var tolas by remember { mutableStateOf("1") }
    var mashas by remember { mutableStateOf("3") }
    var rattis by remember { mutableStateOf("4") }
    var makingCharges by remember { mutableStateOf("5000") }

    val rate = goldRatePerTola.toDoubleOrNull() ?: 0.0
    val t = tolas.toDoubleOrNull() ?: 0.0
    val m = mashas.toDoubleOrNull() ?: 0.0
    val r = rattis.toDoubleOrNull() ?: 0.0
    val making = makingCharges.toDoubleOrNull() ?: 0.0

    // 1 Tola = 12 Masha = 96 Ratti = 11.664 Grams
    val totalTolas = t + (m / 12.0) + (r / 96.0)
    val goldValue = totalTolas * rate
    val grandTotal = goldValue + making
    val totalGrams = totalTolas * 11.664

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PakEmeraldDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Total Gold Bill / سونا بل", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                    Text(
                        text = "₨ ${"%,.0f".format(grandTotal)}",
                        color = Color(0xFFFFD54F),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Weight: ${"%.3f".format(totalTolas)} Tola (${"%.2f".format(totalGrams)} Grams)",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = goldRatePerTola,
                        onValueChange = { goldRatePerTola = it },
                        label = { Text("Gold Rate per Tola / تولہ ریٹ (₨)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedTextField(
                            value = tolas,
                            onValueChange = { tolas = it },
                            label = { Text("Tola (تولہ)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = mashas,
                            onValueChange = { mashas = it },
                            label = { Text("Masha (ماشہ)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = rattis,
                            onValueChange = { rattis = it },
                            label = { Text("Ratti (رتی)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = makingCharges,
                        onValueChange = { makingCharges = it },
                        label = { Text("Making Charges / جڑائی اجرت (₨)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * 4. Fabric Gaz to Meter & Price Calculator (گز اور میٹر حساب)
 */
@Composable
fun FabricMeasurementCalculator(appLanguage: AppLanguage) {
    var ratePerGaz by remember { mutableStateOf("450") }
    var gazQuantity by remember { mutableStateOf("4.5") }

    val rate = ratePerGaz.toDoubleOrNull() ?: 0.0
    val gaz = gazQuantity.toDoubleOrNull() ?: 0.0

    // 1 Gaz = 0.9144 Meters, 1 Meter = 1.0936 Gaz
    val meters = gaz * 0.9144
    val totalAmount = gaz * rate
    val ratePerMeter = rate / 0.9144

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PakEmeraldDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Total Fabric Bill / کپڑے کا بل", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                    Text(
                        text = "₨ ${"%,.2f".format(totalAmount)}",
                        color = Color(0xFFFFD54F),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${"%.2f".format(gaz)} Gaz = ${"%.2f".format(meters)} Meters",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = ratePerGaz,
                        onValueChange = { ratePerGaz = it },
                        label = { Text("Rate Per Gaz / گز ریٹ (₨)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = gazQuantity,
                        onValueChange = { gazQuantity = it },
                        label = { Text("Quantity in Gaz / کل گز") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PakEmeraldContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Rate per Meter: ₨ ${"%.2f".format(ratePerMeter)} • 1 Gaz = 0.9144 Meter",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = PakEmeraldDark,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 5. FBR GST & Tax Calculator (سیلز ٹیکس حساب)
 */
@Composable
fun GstTaxCalculator(appLanguage: AppLanguage) {
    var amountStr by remember { mutableStateOf("10000") }
    var taxRateStr by remember { mutableStateOf("18") } // FBR Standard GST 18%
    var isTaxInclusive by remember { mutableStateOf(false) }

    val amount = amountStr.toDoubleOrNull() ?: 0.0
    val rate = taxRateStr.toDoubleOrNull() ?: 0.0

    val taxAmount = if (isTaxInclusive) {
        amount - (amount / (1.0 + (rate / 100.0)))
    } else {
        amount * (rate / 100.0)
    }

    val finalTotal = if (isTaxInclusive) amount else amount + taxAmount

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PakEmeraldDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Final Invoice Total / مکمل بل ٹیکس کے ساتھ", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                    Text(
                        text = "₨ ${"%,.2f".format(finalTotal)}",
                        color = Color(0xFFFFD54F),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "GST Tax: ₨ ${"%,.2f".format(taxAmount)} (${rate}%)",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = amountStr,
                        onValueChange = { amountStr = it },
                        label = { Text("Base Amount / رقم (₨)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = taxRateStr,
                        onValueChange = { taxRateStr = it },
                        label = { Text("GST Tax % (e.g. 18% Standard, 5% Services)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
