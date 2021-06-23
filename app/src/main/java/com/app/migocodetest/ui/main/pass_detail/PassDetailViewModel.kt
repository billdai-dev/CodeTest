package com.app.migocodetest.ui.main.pass_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.migocodetest.ui.main.pass_detail.PassDetailFragment.Companion.ARG_PASS_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PassDetailViewModel @Inject constructor(private val state: SavedStateHandle) : ViewModel() {
    private val _passId = state.getLiveData<Int>(ARG_PASS_ID)
    val passId: LiveData<Int> = _passId

}