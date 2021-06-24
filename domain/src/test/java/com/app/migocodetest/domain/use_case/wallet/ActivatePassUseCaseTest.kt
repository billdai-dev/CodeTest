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


class ActivatePassUseCaseTest : BaseUseCaseTest() {
    private lateinit var activatePassUseCase: ActivatePassUseCase

    @Mock
    private lateinit var mockWalletRepository: IWalletRepository

    override fun before() {
        activatePassUseCase = ActivatePassUseCase(mockWalletRepository)
    }

    @Test
    fun shouldActivatePass() {
        val fakePass = PassEntity(
            duration = 1,
            type = PassEntity.PassType.Day,
            status = PassEntity.PassStatus.Inactivated
        )
        whenever(mockWalletRepository.activatePass(any())).thenReturn(Single.just(Unit))
        val param = ActivatePassUseCase.Param(fakePass)
        val testObserver = activatePassUseCase(param).test()
        verify(mockWalletRepository).activatePass(any())
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldFailPassActivation() {
        val error = Throwable()
        val fakePass = PassEntity(
            duration = 1,
            type = PassEntity.PassType.Day,
            status = PassEntity.PassStatus.Inactivated
        )
        whenever(mockWalletRepository.activatePass(any())).thenReturn(Single.error(error))
        val param = ActivatePassUseCase.Param(fakePass)
        val testObserver = activatePassUseCase(param).test()
        verify(mockWalletRepository).activatePass(any())
        testObserver.assertError(error)
    }
}