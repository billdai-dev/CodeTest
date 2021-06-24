package com.app.migocodetest.domain.use_case

import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseUseCaseTest {
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        before()
    }

    abstract fun before()
}