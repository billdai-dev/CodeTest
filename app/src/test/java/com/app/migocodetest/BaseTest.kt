package com.app.migocodetest

import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseTest {
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        before()
    }

    abstract fun before()
}