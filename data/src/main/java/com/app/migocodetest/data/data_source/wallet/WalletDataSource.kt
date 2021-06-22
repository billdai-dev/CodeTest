package com.app.migocodetest.data.data_source.wallet

import com.app.migocodetest.data.dto.wallet.PassDto
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface WalletDataSource {
    fun getAllPass(): Flowable<List<PassDto>>

    fun addPass(passDto: PassDto): Single<Long>

    fun activatePass(passDto: PassDto): Single<Unit>
}