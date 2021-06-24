package com.app.migocodetest.data.repository.wallet

import com.app.migocodetest.data.data_source.wallet.WalletLocalDataSource
import com.app.migocodetest.data.dto.wallet.PassDto
import com.app.migocodetest.data.mapper.wallet.PassMapper
import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import com.app.migocodetest.data.repository.BaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.same
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mock


class WalletRepositoryTest : BaseTest() {
    @Mock
    private lateinit var mockWalletLocalDataSource: WalletLocalDataSource

    @Mock
    private lateinit var mockPassMapper: PassMapper

    private lateinit var walletRepository: IWalletRepository

    override fun before() {
        walletRepository = WalletRepository(mockWalletLocalDataSource, mockPassMapper)
    }

    @Test
    fun shouldReceivePassList() {
        val fakePassDtos = listOf(
            PassDto(
                1, 1, PassEntity.PassType.Day.name, PassEntity.PassStatus.Inactivated.name
            )
        )
        whenever(mockWalletLocalDataSource.getAllPass()).thenReturn(Flowable.just(fakePassDtos))

        val fakePassEntity =
            PassEntity(1, 1, PassEntity.PassType.Day, PassEntity.PassStatus.Inactivated)
        whenever(mockPassMapper.toEntity(any())).thenReturn(fakePassEntity)

        val fakePassEntities = listOf(fakePassEntity)
        val testObserver = walletRepository.getPassList().test()
        verify(mockWalletLocalDataSource).getAllPass()
        verify(mockPassMapper).toEntity(any())
        testObserver.assertNoErrors()
        testObserver.assertValue(fakePassEntities)
    }

    @Test
    fun shouldFailWhenReceivingErrorFromDatabase() {
        val error = Throwable()
        whenever(mockWalletLocalDataSource.getAllPass()).thenReturn(Flowable.error(error))

        val testObserver = walletRepository.getPassList().test()
        verify(mockWalletLocalDataSource).getAllPass()
        testObserver.assertError(error)
        testObserver.assertNoValues()
    }

    @Test
    fun shouldAddPass() {
        whenever(
            mockWalletLocalDataSource.addPass(any(), any(), any())
        ).thenReturn(Single.just(Unit))

        val testObserver = walletRepository.addPass(PassEntity.PassType.Day, 1).test()
        verify(mockWalletLocalDataSource).addPass(
            any(), any(), same(PassEntity.PassStatus.Inactivated.name)
        )
        testObserver.assertNoErrors()
        testObserver.assertValue(Unit)
    }

    @Test
    fun shouldFailWhenAddPassToDatabaseFailed() {
        val error = Throwable()
        whenever(
            mockWalletLocalDataSource.addPass(any(), any(), any())
        ).thenReturn(Single.error(error))

        val testObserver = walletRepository.addPass(PassEntity.PassType.Day, 1).test()
        verify(mockWalletLocalDataSource).addPass(
            any(), any(), same(PassEntity.PassStatus.Inactivated.name)
        )
        testObserver.assertError(error)
        testObserver.assertNoValues()
    }

    @Test
    fun shouldActivatePass() {
        whenever(mockWalletLocalDataSource.activatePass(any())).thenReturn(Single.just(Unit))

        val fakePassDto = PassDto(
            1, 1, PassEntity.PassType.Day.name, PassEntity.PassStatus.Inactivated.name
        )
        whenever(mockPassMapper.toDto(any())).thenReturn(fakePassDto)

        val fakePassEntity =
            PassEntity(1, 1, PassEntity.PassType.Day, PassEntity.PassStatus.Inactivated)

        val testObserver = walletRepository.activatePass(fakePassEntity).test()
        verify(mockWalletLocalDataSource).activatePass(any())

        testObserver.assertNoErrors()
        testObserver.assertValue(Unit)
    }
}