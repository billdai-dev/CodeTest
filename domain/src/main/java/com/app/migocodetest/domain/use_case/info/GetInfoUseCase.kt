package com.app.migocodetest.domain.use_case.info

import com.app.migocodetest.domain.repository.info.IInfoRepository
import io.reactivex.rxjava3.core.Single

class GetInfoUseCase(private val infoRepository: IInfoRepository) {
    operator fun invoke(): Single<String> {
        return infoRepository.getInfo()
    }
}
