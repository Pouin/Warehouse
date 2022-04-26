package ru.energomera.mywarehouse

import android.app.Application

class App: Application() {

    val adapterProvider: BluetoothAdapterProvider by lazy {
        BluetoothAdapterProvider.Base(applicationContext)
    }

}