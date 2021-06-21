package com.app.migocodetest.domain.entity.wallet

data class PassEntity(
    val id: Int,
    val duration: Int,
    val type: PassType,
    val status: PassStatus,
    val insertionTimestamp: Long,
    val activationTimestamp: Long? = null,
    val expirationTimestamp: Long? = null
) {
    enum class PassType { Day, Hour }
    enum class PassStatus { Inactivated, Activated, Expired }
}