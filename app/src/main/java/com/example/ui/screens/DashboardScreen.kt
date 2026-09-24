package com.example.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.data.BusinessEntity
import com.example.ui.BottomNavTab
import com.example.ui.screens.tabs.DashboardTab

/**
 * Main Dashboard Composable screen that dynamically filters modules and metrics
 * strictly according to the activeBusiness.type.
 */
@Composable
fun DashboardScreen(
    business: BusinessEntity?,
    onNavigateTab: (BottomNavTab) -> Unit,
    onOpenTenantSwitcher: () -> Unit,
    modifier: Modifier = Modifier
) {
    DashboardTab(
        business = business,
        onNavigateTab = onNavigateTab,
        onOpenTenantSwitcher = onOpenTenantSwitcher,
        modifier = modifier
    )
}
