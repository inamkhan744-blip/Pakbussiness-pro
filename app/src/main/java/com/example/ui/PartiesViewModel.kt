package com.example.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.PartyDao
import com.example.data.PartyEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for managing 'Parties' (Customers, Suppliers, Vendors, Ledger Accounts).
 * Provides reactive StateFlow streams for UI consumption.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PartiesViewModel(
    application: Application,
    private val partyDao: PartyDao = AppDatabase.getDatabase(application).partyDao()
) : BaseBusinessViewModel(application) {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedPartyType = MutableStateFlow<String?>("ALL") // ALL, CUSTOMER, SUPPLIER
    val selectedPartyType: StateFlow<String?> = _selectedPartyType.asStateFlow()

    private val _selectedParty = MutableStateFlow<PartyEntity?>(null)
    val selectedParty: StateFlow<PartyEntity?> = _selectedParty.asStateFlow()

    // Raw stream of parties for active business
    val rawParties: StateFlow<List<PartyEntity>> = activeBusiness.flatMapLatest { biz ->
        if (biz != null) partyDao.getParties(biz.id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered parties by query and type
    val parties: StateFlow<List<PartyEntity>> = combine(
        rawParties,
        searchQuery,
        selectedPartyType
    ) { list, query, type ->
        list.filter { party ->
            val matchesQuery = query.isBlank() ||
                party.name.contains(query, ignoreCase = true) ||
                party.phone.contains(query, ignoreCase = true) ||
                party.city.contains(query, ignoreCase = true)

            val matchesType = type == null || type == "ALL" ||
                party.partyType.equals(type, ignoreCase = true)

            matchesQuery && matchesType
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI state for parties screen
    val partiesUiState: StateFlow<EntityUiState<List<PartyEntity>>> = combine(
        parties,
        isLoading
    ) { list, loading ->
        when {
            loading && list.isEmpty() -> EntityUiState.Loading
            list.isEmpty() -> EntityUiState.Empty
            else -> EntityUiState.Success(list)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EntityUiState.Loading)

    // Khata ledger totals: receivables (positive) and payables (negative)
    val totalReceivables: StateFlow<Double> = rawParties.map { list ->
        list.filter { it.currentBalance > 0 }.sumOf { it.currentBalance }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalPayables: StateFlow<Double> = rawParties.map { list ->
        list.filter { it.currentBalance < 0 }.sumOf { -it.currentBalance }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setPartyTypeFilter(type: String?) {
        _selectedPartyType.value = type
    }

    fun selectParty(party: PartyEntity?) {
        _selectedParty.value = party
    }

    fun saveParty(
        id: Long = 0L,
        name: String,
        phone: String,
        partyType: String = "CUSTOMER",
        email: String = "",
        address: String = "",
        city: String = "",
        openingBalance: Double = 0.0,
        creditLimit: Double = 0.0,
        notes: String = "",
        onComplete: (Long) -> Unit = {}
    ) {
        val bizId = activeBusiness.value?.id ?: return
        launchWithLoading {
            val party = PartyEntity(
                id = id,
                businessId = bizId,
                name = name.trim(),
                phone = phone.trim(),
                partyType = partyType.trim(),
                email = email.trim(),
                address = address.trim(),
                city = city.trim(),
                currentBalance = openingBalance,
                creditLimit = creditLimit,
                notes = notes.trim(),
                updatedAt = System.currentTimeMillis()
            )
            val generatedId = if (id == 0L) {
                partyDao.insertParty(party)
            } else {
                partyDao.updateParty(party)
                id
            }
            onComplete(generatedId)
        }
    }

    fun updatePartyBalance(partyId: Long, delta: Double) {
        launchWithLoading {
            partyDao.updatePartyBalance(partyId, delta)
        }
    }

    fun deleteParty(partyId: Long) {
        launchWithLoading {
            partyDao.deleteParty(partyId)
            if (_selectedParty.value?.id == partyId) {
                _selectedParty.value = null
            }
        }
    }

    companion object {
        fun provideFactory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PartiesViewModel(application) as T
                }
            }
    }
}

typealias PartyViewModel = PartiesViewModel
