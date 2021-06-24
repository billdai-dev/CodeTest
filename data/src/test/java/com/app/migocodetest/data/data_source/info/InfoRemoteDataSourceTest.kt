package com.app.migocodetest.data.data_source.info

import com.app.migocodetest.data.repository.BaseTest
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mock

class InfoRemoteDataSourceTest : BaseTest() {
    private lateinit var infoRemoteDataSource: InfoRemoteDataSource

    @Mock
    private lateinit var mockInfoApiService: InfoApiService

    override fun before() {
        infoRemoteDataSource = InfoRemoteDataSource(mockInfoApiService)
    }

    @Test
    fun shouldCallPublicInfoApi() {
        val fakeResponseBody = "".toResponseBody()
        whenever(mockInfoApiService.getPublicStatusInfo()).thenReturn(Single.just(fakeResponseBody))

        val testObserver = infoRemoteDataSource.getPublicStatusInfo().test()
        verify(mockInfoApiService).getPublicStatusInfo()
        testObserver.assertValue(fakeResponseBody)
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldFailWhenPublicInfoApiReturnError() {
        val error = Throwable()
        whenever(mockInfoApiService.getPublicStatusInfo()).thenReturn(Single.error(error))

        val testObserver = infoRemoteDataSource.getPublicStatusInfo().test()
        verify(mockInfoApiService).getPublicStatusInfo()
        testObserver.assertNoValues()
        testObserver.assertError(error)
    }

    @Test
    fun shouldCallPrivateInfoApi() {
        val fakeResponseBody = "".toResponseBody()
        whenever(mockInfoApiService.getPrivateStatusInfo()).thenReturn(Single.just(fakeResponseBody))

        val testObserver = infoRemoteDataSource.getPrivateStatusInfo().test()
        verify(mockInfoApiService).getPrivateStatusInfo()
        testObserver.assertValue(fakeResponseBody)
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldFailWhenPrivateInfoApiReturnError() {
        val error = Throwable()
        whenever(mockInfoApiService.getPrivateStatusInfo()).thenReturn(Single.error(error))

        val testObserver = infoRemoteDataSource.getPrivateStatusInfo().test()
        verify(mockInfoApiService).getPrivateStatusInfo()
        testObserver.assertNoValues()
        testObserver.assertError(error)
    }
}