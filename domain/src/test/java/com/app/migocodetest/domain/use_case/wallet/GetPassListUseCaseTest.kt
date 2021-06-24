package com.app.migocodetest.domain.use_case.wallet

import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import com.app.migocodetest.domain.use_case.BaseUseCaseTest
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Flowable
import org.junit.Test
import org.mockito.Mock


class GetPassListUseCaseTest : BaseUseCaseTest() {
    private lateinit var getPassListUseCase: GetPassListObservableUseCase

    @Mock
    private lateinit var mockWalletRepository: IWalletRepository

    override fun before() {
        getPassListUseCase = GetPassListObservableUseCase(mockWalletRepository)
    }

    @Test
    fun shouldReceivePassList() {
        val passList = emptyList<PassEntity>()
        whenever(mockWalletRepository.getPassList()).thenReturn(Flowable.just(passList))
        val testObserver = getPassListUseCase().test()
        verify(mockWalletRepository).getPassList()
        testObserver.assertNoErrors()
        testObserver.assertValue(passList)
    }

    @Test
    fun shouldFailGettingPassList() {
        val error = Throwable()
        whenever(mockWalletRepository.getPassList()).thenReturn(Flowable.error(error))
        val testObserver = getPassListUseCase().test()
        verify(mockWalletRepository).getPassList()
        testObserver.assertError(error)
    }
}