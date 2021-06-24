package com.app.migocodetest.domain.use_case.info

import com.app.migocodetest.domain.repository.info.IInfoRepository
import com.app.migocodetest.domain.use_case.BaseUseCaseTest
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mock


class GetInfoUseCaseTest : BaseUseCaseTest() {
    private lateinit var getInfoUseCase: GetInfoUseCase

    @Mock
    private lateinit var mockInfoRepository: IInfoRepository

    override fun before() {
        getInfoUseCase = GetInfoUseCase(mockInfoRepository)
    }

    @Test
    fun shouldReceiveApiInfo() {
        val successResponse = "test"
        whenever(mockInfoRepository.getInfo()).thenReturn(Single.just(successResponse))
        val testObserver = getInfoUseCase().test()
        verify(mockInfoRepository).getInfo()
        testObserver.assertValue(successResponse)
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldReceiveErrorWhenFailure() {
        val error = Throwable()
        whenever(mockInfoRepository.getInfo()).thenReturn(Single.error(error))
        val testObserver = getInfoUseCase().test()
        verify(mockInfoRepository).getInfo()
        testObserver.assertNoValues()
        testObserver.assertError(error)
    }
}