package com.app.migocodetest.domain.use_case.wallet

import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import com.app.migocodetest.domain.use_case.BaseUseCaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mock


class AddPassUseCaseTest : BaseUseCaseTest() {
    private lateinit var addPassUseCase: AddPassUseCase

    @Mock
    private lateinit var mockWalletRepository: IWalletRepository

    override fun before() {
        addPassUseCase = AddPassUseCase(mockWalletRepository)
    }

    @Test
    fun shouldAddPass() {
        whenever(mockWalletRepository.addPass(any(), any())).thenReturn(Single.just(Unit))
        val param = AddPassUseCase.Param(PassEntity.PassType.Day, 1)
        val testObserver = addPassUseCase(param).test()
        verify(mockWalletRepository).addPass(any(), any())
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldFailAddingPass() {
        val error = Throwable()
        whenever(mockWalletRepository.addPass(any(), any())).thenReturn(Single.error(error))
        val param = AddPassUseCase.Param(PassEntity.PassType.Day, 1)
        val testObserver = addPassUseCase(param).test()
        verify(mockWalletRepository).addPass(any(), any())
        testObserver.assertError(error)
    }
}