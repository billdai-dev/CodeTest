package com.app.migocodetest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.migocodetest.domain.entity.info.InfoEntity
import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.use_case.info.GetInfoUseCase
import com.app.migocodetest.domain.use_case.wallet.ActivatePassUseCase
import com.app.migocodetest.domain.use_case.wallet.AddPassUseCase
import com.app.migocodetest.domain.use_case.wallet.GetPassListObservableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getInfoUseCase: GetInfoUseCase,
    private val getPassListObservableUseCase: GetPassListObservableUseCase,
    private val addPassUseCase: AddPassUseCase,
    private val activatePassUseCase: ActivatePassUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _apiStatus = MutableLiveData<InfoEntity>()
    val apiStatus: LiveData<InfoEntity> = _apiStatus

    private val _passList = MutableLiveData<List<PassEntity>>()
    private val _dayPasses = MediatorLiveData<List<PassEntity>>()
    val dayPasses: LiveData<List<PassEntity>> = _dayPasses
    private val _hourPasses = MediatorLiveData<List<PassEntity>>()
    val hourPasses: LiveData<List<PassEntity>> = _hourPasses


    init {
        _dayPasses.addSource(_passList) {
            _dayPasses.postValue(it.filter { it.type == PassEntity.PassType.Day })
        }
        _hourPasses.addSource(_passList) {
            _hourPasses.postValue(it.filter { it.type == PassEntity.PassType.Hour })
        }

        getInfoUseCase()
            .subscribeBy { _apiStatus.postValue(it) }
            .addTo(compositeDisposable)

        getPassListObservableUseCase()
            .subscribeBy { _passList.postValue(it) }
            .addTo(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun addPass(duration: Int, type: PassEntity.PassType) {
        addPassUseCase(AddPassUseCase.Param(type, duration))
            .subscribeBy {}
            .addTo(compositeDisposable)
    }

    fun activatePass(pass: PassEntity) {
        activatePassUseCase(ActivatePassUseCase.Param(pass))
            .subscribeBy {}
            .addTo(compositeDisposable)
    }
}