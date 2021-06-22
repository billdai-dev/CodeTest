package com.app.migocodetest.domain.use_case.wallet

import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import io.reactivex.rxjava3.core.Single

class AddPassUseCase(private val walletRepository: IWalletRepository) {
    operator fun invoke(param: Param): Single<Unit> {
        return walletRepository.addPass(param.passType, param.duration,)
    }

    data class Param(val passType: PassEntity.PassType, val duration: Int)
}
