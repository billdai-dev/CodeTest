package com.app.migocodetest.data.repository.wallet

import android.annotation.SuppressLint
import com.app.migocodetest.data.data_source.wallet.WalletLocalDataSource
import com.app.migocodetest.data.mapper.wallet.PassMapper
import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val walletLocalDataSource: WalletLocalDataSource,
    private val passMapper: PassMapper
) : IWalletRepository {
    @SuppressLint("MissingPermission")
    override fun getPassList(): Flowable<List<PassEntity>> {
        return walletLocalDataSource.getAllPass().map { passes ->
            passes.map { passMapper.toEntity(it) }
        }
    }

    override fun addPass(type: PassEntity.PassType, duration: Int): Single<Unit> {
        return walletLocalDataSource.addPass(
            type.name, duration, PassEntity.PassStatus.Inactivated.name
        )
    }

    override fun activatePass(id: Int): Single<Unit> {
        return walletLocalDataSource.activatePass(id, PassEntity.PassStatus.Activated.name)
    }
}