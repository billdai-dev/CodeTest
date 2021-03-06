package com.app.migocodetest.domain.use_case.wallet

import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import io.reactivex.rxjava3.core.Single

class ActivatePassUseCase(private val walletRepository: IWalletRepository) {
    operator fun invoke(param: Param): Single<Unit> {
        return walletRepository.activatePass(param.entity)
    }

    data class Param(val entity: PassEntity)
}
