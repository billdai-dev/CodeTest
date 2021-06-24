package com.app.migocodetest.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.app.migocodetest.BaseTest
import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.use_case.info.GetInfoUseCase
import com.app.migocodetest.domain.use_case.wallet.ActivatePassUseCase
import com.app.migocodetest.domain.use_case.wallet.AddPassUseCase
import com.app.migocodetest.domain.use_case.wallet.GetPassListObservableUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class MainViewModelTest : BaseTest() {
    @Rule
    @JvmField
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getInfoUseCase: GetInfoUseCase

    @Mock
    private lateinit var getPassListObservableUseCase: GetPassListObservableUseCase

    @Mock
    private lateinit var addPassUseCase: AddPassUseCase

    @Mock
    private lateinit var activatePassUseCase: ActivatePassUseCase

    @Mock
    private lateinit var apiInfoObserver: Observer<String>

    @Captor
    private lateinit var apiInfoCaptor: ArgumentCaptor<String>

    @Mock
    private lateinit var passListObserver: Observer<List<PassEntity>>

    @Captor
    private lateinit var passListCaptor: ArgumentCaptor<List<PassEntity>>

    @Mock
    private lateinit var dayPassListObserver: Observer<List<PassEntity>>

    @Captor
    private lateinit var dayPassListCaptor: ArgumentCaptor<List<PassEntity>>

    @Mock
    private lateinit var hourPassListObserver: Observer<List<PassEntity>>

    @Captor
    private lateinit var hourPassListCaptor: ArgumentCaptor<List<PassEntity>>

    private val viewModel by lazy {
        MainActivityViewModel(
            getInfoUseCase,
            getPassListObservableUseCase,
            addPassUseCase,
            activatePassUseCase
        )
    }

    override fun before() {
        whenever(getPassListObservableUseCase()).thenReturn(Flowable.just(emptyList()))
    }

    @Test
    fun shouldLiveDataReceiveApiInfo() {
        val response = "test"
        whenever(getInfoUseCase()).thenReturn(Single.just(response))
        viewModel.apiStatus.observeForever(apiInfoObserver)
        viewModel.onNetworkChanged()

        verify(getInfoUseCase).invoke()
        verify(apiInfoObserver).onChanged(apiInfoCaptor.capture())
        assertEquals(apiInfoCaptor.value, response)
    }

    @Test
    fun shouldLiveDataGetPlaceholderWhenFailReceivingApiInfo() {
        val response = "N/A"
        whenever(getInfoUseCase()).thenReturn(Single.error(Throwable()))
        viewModel.apiStatus.observeForever(apiInfoObserver)
        viewModel.onNetworkChanged()

        verify(getInfoUseCase).invoke()
        verify(apiInfoObserver).onChanged(apiInfoCaptor.capture())
        assertEquals(apiInfoCaptor.value, response)
    }

    @Test
    fun shouldLiveDataReceivePassList() {
        val passList =
            listOf(
                PassEntity(1, 1, PassEntity.PassType.Day, PassEntity.PassStatus.Inactivated),
                PassEntity(2, 1, PassEntity.PassType.Hour, PassEntity.PassStatus.Inactivated)
            )
        whenever(getPassListObservableUseCase()).thenReturn(Flowable.just(passList))

        viewModel.passList.observeForever(passListObserver)
        viewModel.dayPasses.observeForever(dayPassListObserver)
        viewModel.hourPasses.observeForever(hourPassListObserver)

        verify(getPassListObservableUseCase).invoke()
        verify(passListObserver).onChanged(passListCaptor.capture())
        verify(dayPassListObserver).onChanged(dayPassListCaptor.capture())
        verify(hourPassListObserver).onChanged(hourPassListCaptor.capture())
        assertTrue { passListCaptor.value.size == 2 }

        val dayPassList = dayPassListCaptor.value
        assertTrue { dayPassList.size == 1 && dayPassList.first().id == 1 }

        val hourPassList = hourPassListCaptor.value
        assertTrue { hourPassList.size == 1 && hourPassList.first().id == 2 }
    }

    @Test
    fun shouldAddNewPass() {
        whenever(addPassUseCase(any())).thenReturn(Single.just(Unit))
        viewModel.addPass(1, PassEntity.PassType.Day)

        verify(addPassUseCase).invoke(any())
    }

    @Test
    fun shouldActivatePass() {
        whenever(activatePassUseCase(any())).thenReturn(Single.just(Unit))
        val passEntity = PassEntity(
            duration = 1,
            type = PassEntity.PassType.Day,
            status = PassEntity.PassStatus.Inactivated
        )
        viewModel.activatePass(passEntity)

        verify(activatePassUseCase).invoke(any())
    }
}