package com.app.migocodetest.domain.entity.wallet

data class PassEntity(
    val id: Int? = null,
    val duration: Int? = null,
    val type: PassType? = null,
    val status: PassStatus? = null,
    val insertionTimestamp: Long? = null,
    val activationTimestamp: Long? = null,
    val expirationTimestamp: Long? = null
) {
    enum class PassType { Day, Hour }
    enum class PassStatus { Inactivated, Activated, Expired }
}