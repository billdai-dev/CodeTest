package com.app.migocodetest.data.repository.info

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.RemoteException
import com.app.migocodetest.data.data_source.info.InfoRemoteDataSource
import com.app.migocodetest.data.repository.BaseTest
import com.app.migocodetest.domain.repository.info.IInfoRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mock

class InfoRepositoryTest : BaseTest() {
    private lateinit var infoRepository: IInfoRepository

    @Mock
    private lateinit var mockConnectivityManager: ConnectivityManager

    @Mock
    private lateinit var mockInfoRemoteDataSource: InfoRemoteDataSource

    @Mock
    private lateinit var mockWifiNetwork: Network

    @Mock
    private lateinit var mockWifiNetworkCapabilities: NetworkCapabilities

    @Mock
    private lateinit var mockCellularNetwork: Network

    @Mock
    private lateinit var mockCellularNetworkCapabilities: NetworkCapabilities

    @Mock
    private lateinit var mockOtherNetwork: Network

    @Mock
    private lateinit var mockOtherNetworkCapabilities: NetworkCapabilities

    override fun before() {
        whenever(mockConnectivityManager.getNetworkCapabilities(mockWifiNetwork))
            .thenReturn(mockWifiNetworkCapabilities)
        whenever(mockConnectivityManager.getNetworkCapabilities(mockCellularNetwork))
            .thenReturn(mockCellularNetworkCapabilities)
        whenever(mockWifiNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)
        whenever(mockWifiNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            .thenReturn(false)
        whenever(mockCellularNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            .thenReturn(true)
        whenever(mockWifiNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(false)
        whenever(mockOtherNetworkCapabilities.hasTransport(any()))
            .thenReturn(false)

        infoRepository = InfoRepository(mockConnectivityManager, mockInfoRemoteDataSource)
    }

    @Test
    fun shouldReceivePrivateApiInfoWhenBothNetworkAvailable() {
        whenever(mockConnectivityManager.allNetworks).thenReturn(
            arrayOf(mockWifiNetwork, mockCellularNetwork)
        )

        val response = "success"

        whenever(mockInfoRemoteDataSource.getPrivateStatusInfo())
            .thenReturn(Single.just(response.toResponseBody()))

        val testObserver = infoRepository.getInfo().test()
        verify(mockInfoRemoteDataSource).getPrivateStatusInfo()
        verify(mockInfoRemoteDataSource, never()).getPublicStatusInfo()
        testObserver.assertValue(response)
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldReceivePrivateApiInfoWhenOnlyWifiNetworkAvailable() {
        whenever(mockConnectivityManager.allNetworks).thenReturn(
            arrayOf(mockWifiNetwork)
        )

        val response = "success"

        whenever(mockInfoRemoteDataSource.getPrivateStatusInfo())
            .thenReturn(Single.just(response.toResponseBody()))

        val testObserver = infoRepository.getInfo().test()
        verify(mockInfoRemoteDataSource).getPrivateStatusInfo()
        verify(mockInfoRemoteDataSource, never()).getPublicStatusInfo()
        testObserver.assertValue(response)
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldReceivePublicApiInfoWhenOnlyCellularNetworkAvailable() {
        whenever(mockConnectivityManager.allNetworks).thenReturn(
            arrayOf(mockCellularNetwork)
        )

        val response = "success"

        whenever(mockInfoRemoteDataSource.getPublicStatusInfo())
            .thenReturn(Single.just(response.toResponseBody()))

        val testObserver = infoRepository.getInfo().test()
        verify(mockInfoRemoteDataSource).getPublicStatusInfo()
        verify(mockInfoRemoteDataSource, never()).getPrivateStatusInfo()
        testObserver.assertValue(response)
        testObserver.assertNoErrors()
    }

    @Test(expected = Throwable::class)
    fun shouldFailWhenAllNetworksThrowException() {
        whenever(mockConnectivityManager.allNetworks).thenThrow(Throwable())

        val testObserver = infoRepository.getInfo().test()
        verify(mockInfoRemoteDataSource, never()).getPublicStatusInfo()
        verify(mockInfoRemoteDataSource, never()).getPrivateStatusInfo()
        testObserver.assertError(Throwable::class.java)
    }

    @Test
    fun shouldFailWhenNoNetworksAvailable() {
        whenever(mockConnectivityManager.allNetworks).thenReturn(arrayOf())

        val testObserver = infoRepository.getInfo().test()
        verify(mockInfoRemoteDataSource, never()).getPublicStatusInfo()
        verify(mockInfoRemoteDataSource, never()).getPrivateStatusInfo()
        testObserver.assertError(Throwable::class.java)
    }

    @Test
    fun shouldFailWhenOnlyOtherTypeNetworkAvailable() {
        whenever(mockConnectivityManager.allNetworks).thenReturn(arrayOf(mockOtherNetwork))

        val testObserver = infoRepository.getInfo().test()
        verify(mockInfoRemoteDataSource, never()).getPublicStatusInfo()
        verify(mockInfoRemoteDataSource, never()).getPrivateStatusInfo()
        testObserver.assertError(Throwable::class.java)
    }
}