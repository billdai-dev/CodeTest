package com.app.migocodetest.data.data_source.wallet

import com.app.migocodetest.data.dto.wallet.PassDto
import com.app.migocodetest.data.local_storage.database.WalletDao
import com.app.migocodetest.domain.entity.wallet.PassEntity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
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

    override fun activatePass(dto: PassDto): Single<Unit> {
        val calendar = Calendar.getInstance()
        val activationTs = calendar.timeInMillis / 1000

        val duration = dto.duration
        val type = PassEntity.PassType.valueOf(dto.type)
        val timeField =
            if (type == PassEntity.PassType.Day) Calendar.DAY_OF_MONTH else Calendar.HOUR_OF_DAY
        calendar.add(timeField, duration)
        val expirationTs = calendar.timeInMillis / 1000

        return walletDao.activatePass(dto.id, dto.status, activationTs, expirationTs)
            .subscribeOn(Schedulers.io())
    }
}