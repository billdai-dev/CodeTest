package com.app.migocodetest.domain.entity.wallet

data class PassEntity(
    val id: Int = 0,
    val duration: Int,
    val type: PassType,
    val status: PassStatus,
    val insertionTimestamp: Long? = null,
    val activationTimestamp: Long? = null,
    val expirationTimestamp: Long? = null
) {
    enum class PassType { Day, Hour }
    enum class PassStatus { Inactivated, Activated, Expired }
}