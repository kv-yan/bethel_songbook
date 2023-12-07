package ru.betel.domain.useCase.network

import ru.betel.domain.repository.network.NetworkUtils

class GetNetworkStateUseCase(private val networkUtils: NetworkUtils) {
    fun execute():Boolean = networkUtils.isConnectedNetwork()
}