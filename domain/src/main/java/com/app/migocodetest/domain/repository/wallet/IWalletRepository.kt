package com.app.migocodetest.domain.repository.wallet

import com.app.migocodetest.domain.entity.wallet.PassEntity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface IWalletRepository {
    fun getPassList(): Flowable<List<PassEntity>>

    fun addPass(type: PassEntity.PassType, duration: Int): Single<Long>

    fun activatePass(entity: PassEntity): Single<Unit>
}