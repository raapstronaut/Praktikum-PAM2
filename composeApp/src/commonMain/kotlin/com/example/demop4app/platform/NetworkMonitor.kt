package com.example.demop4app.platform

import kotlinx.coroutines.flow.Flow

expect class NetworkMonitor {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}