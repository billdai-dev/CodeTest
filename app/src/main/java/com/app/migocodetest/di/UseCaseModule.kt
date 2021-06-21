package com.app.migocodetest.di

import com.app.migocodetest.domain.repository.info.IInfoRepository
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
import com.app.migocodetest.domain.use_case.info.GetInfoUseCase
import com.app.migocodetest.domain.use_case.wallet.ActivatePassUseCase
import com.app.migocodetest.domain.use_case.wallet.AddPassUseCase
import com.app.migocodetest.domain.use_case.wallet.GetPassListObservableUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetInfoUseCase(infoRepository: IInfoRepository): GetInfoUseCase {
        return GetInfoUseCase(infoRepository)
    }

    @Provides
    fun provideGetPassListUseCase(walletRepository: IWalletRepository): GetPassListObservableUseCase {
        return GetPassListObservableUseCase(walletRepository)
    }

    @Provides
    fun provideAddPassUseCase(walletRepository: IWalletRepository): AddPassUseCase {
        return AddPassUseCase(walletRepository)
    }

    @Provides
    fun provideActivatePassUseCase(walletRepository: IWalletRepository): ActivatePassUseCase {
        return ActivatePassUseCase(walletRepository)
    }
}
