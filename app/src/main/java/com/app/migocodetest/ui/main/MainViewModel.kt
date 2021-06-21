package com.app.migocodetest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.migocodetest.domain.entity.info.InfoEntity
import com.app.migocodetest.domain.use_case.info.GetInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInfoUseCase: GetInfoUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _apiStatus = MutableLiveData<InfoEntity>()
    val apiStatus: LiveData<InfoEntity> = _apiStatus

    init {
        getInfoUseCase()
            .subscribeBy { _apiStatus.postValue(it) }
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}