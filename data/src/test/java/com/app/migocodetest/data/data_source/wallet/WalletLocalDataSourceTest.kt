package com.app.migocodetest.data.data_source.wallet

import com.app.migocodetest.data.dto.wallet.PassDto
import com.app.migocodetest.data.local_storage.database.WalletDao
import com.app.migocodetest.data.repository.BaseTest
import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import java.util.*
import kotlin.test.assertEquals


class WalletLocalDataSourceTest : BaseTest() {
    private lateinit var walletLocalDataSource: WalletLocalDataSource

    @Mock
    private lateinit var mockWalletDao: WalletDao

    @Captor
    private lateinit var activationTimeCaptor: ArgumentCaptor<Long>

    @Captor
    private lateinit var expirationTimeCaptor: ArgumentCaptor<Long>

    override fun before() {
        walletLocalDataSource = WalletLocalDataSource(mockWalletDao)
    }

    @Test
    fun shouldReceivePassList() {
        val result = emptyList<PassDto>()
        whenever(mockWalletDao.getAllPass()).thenReturn(Flowable.just(result))

        val testObserver = walletLocalDataSource.getAllPass().test()
        verify(mockWalletDao).getAllPass()
        testObserver.assertNoErrors()
        testObserver.assertValue(result)
    }

    @Test
    fun shouldFailGettingPassListWhenDatabaseError() {
        val error = Throwable()
        whenever(mockWalletDao.getAllPass()).thenReturn(Flowable.error(error))

        val testObserver = walletLocalDataSource.getAllPass().test()
        verify(mockWalletDao).getAllPass()
        testObserver.assertError(error)
        testObserver.assertNoValues()
    }

    @Test
    fun shouldAddPassToDatabase() {
        whenever(mockWalletDao.addPass(any(), any(), any(), any())).thenReturn(Single.just(Unit))

        val testObserver = walletLocalDataSource.addPass("", 1, "").test()
        verify(mockWalletDao).addPass(any(), any(), any(), any())
        testObserver.assertNoErrors()
        testObserver.assertValue(Unit)
    }

    @Test
    fun shouldFailAddingPassWhenDatabaseError() {
        val error = Throwable()
        whenever(mockWalletDao.addPass(any(), any(), any(), any()))
            .thenReturn(Single.error(error))

        val testObserver = walletLocalDataSource.addPass("", 1, "").test()
        verify(mockWalletDao).addPass(any(), any(), any(), any())
        testObserver.assertError(error)
        testObserver.assertNoValues()
    }

    @Test
    fun shouldActivateDayPassInDatabase() {
        whenever(
            mockWalletDao.activatePass(any(), any(), any(), any())
        ).thenReturn(Single.just(Unit))


        val type = PassEntity.PassType.Day.name
        val duration = 1
        val fakePass = PassDto(0, duration, type, "")

        val testObserver = walletLocalDataSource.activatePass(fakePass).test()
        verify(mockWalletDao).activatePass(
            any(), any(), activationTimeCaptor.capture(), expirationTimeCaptor.capture()
        )
        val activationTime = activationTimeCaptor.value
        val capturedExpirationTime = expirationTimeCaptor.value
        val expirationCalendar = Calendar.getInstance().apply {
            timeInMillis = activationTime
            add(Calendar.DAY_OF_MONTH, duration + 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.MILLISECOND, -1)
        }

        assertEquals(capturedExpirationTime, expirationCalendar.timeInMillis)

        testObserver.assertNoErrors()
        testObserver.assertValue(Unit)
    }

    @Test
    fun shouldActivateHourPassInDatabase() {
        whenever(
            mockWalletDao.activatePass(any(), any(), any(), any())
        ).thenReturn(Single.just(Unit))


        val type = PassEntity.PassType.Hour.name
        val duration = 1
        val fakePass = PassDto(0, duration, type, "")

        val testObserver = walletLocalDataSource.activatePass(fakePass).test()
        verify(mockWalletDao).activatePass(
            any(), any(), activationTimeCaptor.capture(), expirationTimeCaptor.capture()
        )
        val activationTime = activationTimeCaptor.value
        val capturedExpirationTime = expirationTimeCaptor.value
        val expirationCalendar = Calendar.getInstance().apply {
            timeInMillis = activationTime
            add(Calendar.HOUR_OF_DAY, duration)
        }

        assertEquals(capturedExpirationTime, expirationCalendar.timeInMillis)

        testObserver.assertNoErrors()
        testObserver.assertValue(Unit)
    }

    @Test
    fun shouldFailActivatingPass() {
        val error = Throwable()
        whenever(mockWalletDao.activatePass(any(), any(), any(), any()))
            .thenReturn(Single.error(error))

        val fakePass = PassDto(0, 1, PassEntity.PassType.Day.name, "")

        val testObserver = walletLocalDataSource.activatePass(fakePass).test()
        verify(mockWalletDao).activatePass(any(), any(), any(), any())
        testObserver.assertError(error)
        testObserver.assertNoValues()
    }
}