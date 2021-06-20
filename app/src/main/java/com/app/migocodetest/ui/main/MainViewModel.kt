package com.app.migocodetest.ui.main

import androidx.lifecycle.ViewModel
import com.app.migocodetest.domain.use_case.info.GetInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInfoUseCase: GetInfoUseCase
) : ViewModel() {

}