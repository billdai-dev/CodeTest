package com.app.migocodetest.data.data_source.wallet

import com.app.migocodetest.data.dto.wallet.PassDto
import com.app.migocodetest.data.local_storage.database.WalletDao
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WalletLocalDataSource @Inject constructor(private val walletDao: WalletDao) :
    WalletDataSource {
    override fun getAllPass(): Flowable<List<PassDto>> {
        return walletDao.getAllPass().subscribeOn(Schedulers.io())
    }

    override fun addPass(type: String, duration: Int, status: String): Single<Unit> {
        return walletDao.addPass(duration, type, status)
            .subscribeOn(Schedulers.io())
    }

    override fun activatePass(id: Int, status: String): Single<Unit> {
        return walletDao.activatePass(id, status,).subscribeOn(Schedulers.io())
    }
}