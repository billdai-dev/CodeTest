package com.app.migocodetest.domain.use_case.wallet

import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import io.reactivex.rxjava3.core.Flowable

class GetPassListObservableUseCase(private val walletRepository: IWalletRepository) {
    operator fun invoke(): Flowable<List<PassEntity>> {
        return walletRepository.getPassList()
    }
}
