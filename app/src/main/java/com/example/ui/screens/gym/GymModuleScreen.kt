package com.example.ui.screens.gym

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BusinessEntity
import com.example.data.GymCheckInEntity
import com.example.data.GymMemberEntity
import com.example.ui.theme.PakEmeraldContainer
import com.example.ui.theme.PakEmeraldPrimary
import kotlinx.coroutines.flow.Flow

@Composable
fun GymModuleScreen(
    business: BusinessEntity?,
    members: List<GymMemberEntity>,
    todayCheckIns: List<GymCheckInEntity>,
    selectedMember: GymMemberEntity?,
    isAddEditOpen: Boolean,
    memberToEdit: GymMemberEntity?,
    onSelectMember: (Long?) -> Unit,
    onOpenAddMember: () -> Unit,
    onOpenEditMember: (GymMemberEntity) -> Unit,
    onCloseAddEdit: () -> Unit,
    onSaveMember: (name: String, phone: String, gender: String, plan: String, startDate: Long, durationDays: Int, amount: Double) -> Unit,
    onDeleteMember: (Long) -> Unit,
    onToggleCheckIn: (GymMemberEntity) -> Unit,
    getMemberCheckIns: (Long) -> Flow<List<GymCheckInEntity>>,
    modifier: Modifier = Modifier
) {
    if (isAddEditOpen) {
        GymAddEditMemberScreen(
            memberToEdit = memberToEdit,
            onSave = { name, phone, gender, plan, startDate, durationDays, amount ->
                onSaveMember(name, phone, gender, plan, startDate, durationDays, amount)
            },
            onCancel = onCloseAddEdit,
            modifier = modifier
        )
    } else if (selectedMember != null) {
        GymMemberDetailsScreen(
            member = selectedMember,
            checkInsFlow = getMemberCheckIns(selectedMember.id),
            onToggleCheckIn = onToggleCheckIn,
            onEditMember = onOpenEditMember,
            onDeleteMember = { id ->
                onDeleteMember(id)
                onSelectMember(null)
            },
            onBack = { onSelectMember(null) },
            modifier = modifier
        )
    } else {
        var selectedSubTab by remember { mutableIntStateOf(0) }

        Column(modifier = modifier.fillMaxSize().testTag("gym_module_screen")) {
            // Secondary Sub-Tabs for Gym Module: Overview (Dashboard) & Members Directory
            TabRow(
                selectedTabIndex = selectedSubTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PakEmeraldPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedSubTab]),
                        color = PakEmeraldPrimary
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedSubTab == 0,
                    onClick = { selectedSubTab = 0 },
                    text = {
                        Text(
                            text = "Gym Dashboard",
                            fontWeight = if (selectedSubTab == 0) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                    selectedContentColor = PakEmeraldPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.testTag("gym_tab_dashboard")
                )

                Tab(
                    selected = selectedSubTab == 1,
                    onClick = { selectedSubTab = 1 },
                    text = {
                        Text(
                            text = "Members (${members.size})",
                            fontWeight = if (selectedSubTab == 1) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    },
                    icon = { Icon(Icons.Default.Groups, contentDescription = null) },
                    selectedContentColor = PakEmeraldPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.testTag("gym_tab_members")
                )
            }

            Crossfade(targetState = selectedSubTab, label = "gymSubTabTransition") { tabIndex ->
                when (tabIndex) {
                    0 -> GymDashboardView(
                        members = members,
                        todayCheckIns = todayCheckIns,
                        onAddMemberClick = onOpenAddMember,
                        onViewAllMembersClick = { selectedSubTab = 1 },
                        onMemberClick = onSelectMember,
                        onToggleCheckIn = onToggleCheckIn
                    )
                    1 -> GymMembersListView(
                        members = members,
                        onMemberClick = onSelectMember,
                        onAddMemberClick = onOpenAddMember,
                        onToggleCheckIn = onToggleCheckIn
                    )
                }
            }
        }
    }
}
