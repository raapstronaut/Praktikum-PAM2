package com.example.demop4app.platform

expect class DeviceInfo() {
    fun getDeviceName(): String
    fun getOsVersion(): String
    fun getAppVersion(): String
}