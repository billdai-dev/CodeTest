package com.app.migocodetest.data.data_source.wallet

import com.app.migocodetest.data.dto.wallet.PassDto
import com.app.migocodetest.data.local_storage.database.WalletDao
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WalletLocalDataSource @Inject constructor(private val walletDao: WalletDao) :
    WalletDataSource {
    override fun getAllPass(): Flowable<List<PassDto>> {
        return walletDao.getAllPass()
    }

    override fun addPass(passDto: PassDto): Single<Long> {
        return walletDao.addPass(passDto)
    }

    override fun activatePass(passDto: PassDto): Single<Unit> {
        return walletDao.updatePass(passDto)
    }
}