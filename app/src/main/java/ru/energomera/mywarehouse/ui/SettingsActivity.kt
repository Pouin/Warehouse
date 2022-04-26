package ru.energomera.mywarehouse.ui

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import ru.energomera.mywarehouse.R
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import ru.energomera.mywarehouse.util.SharedPreference


class SettingsActivity() : AppCompatActivity() {

    lateinit var bt: BluetoothSPP
    var textStatus: TextView? = null

    lateinit var btnConnect: Button
    private val permissionCode = 2
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        textStatus = findViewById(R.id.textStatus)
        bt = BluetoothSPP(applicationContext)
        btnConnect = findViewById(R.id.btnConnectScan)
        val textRead: TextView = findViewById(R.id.editTextToScanBth)
        sharedPreference = SharedPreference(this)

        if (!bt.isBluetoothAvailable) {
            Toast.makeText(applicationContext, "Bluetooth is not available", Toast.LENGTH_SHORT).show()
            finish()
        }
        else
            changeStatusBluetooth(bt)

        bt.setOnDataReceivedListener { data, message ->
            textRead.text = message.toString()
        }

        btnConnect.setOnClickListener {
            if (btnConnect.text == getString(R.string.connectBth)) {
                //connecting other kind of devices
                bt.setDeviceTarget(BluetoothState.DEVICE_OTHER)
                val intent = Intent(applicationContext, DeviceList::class.java)
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
            } else if (btnConnect.text == getString(R.string.disconnectBth)) {
                //disconnect from a device
                if (bt.serviceState == BluetoothState.STATE_CONNECTED) bt.disconnect()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        bt.stopService()
//    }

    override fun onStart() {
        super.onStart()

        checkPermission()

        if (!bt.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                changeStatusBluetooth(bt)
            }
        }
    }

    private fun changeStatusBluetooth(bt: BluetoothSPP){
        var textDeviceName: TextView? = findViewById(R.id.deviceName)
        var textDeviceMac: TextView? = findViewById(R.id.deviceMac)
        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                textStatus!!.text = getString(R.string.bthStatus_notCon)
                btnConnect.text = getString(R.string.connectBth)
                textDeviceName!!.text = "Устройство не подключено"
            }

            override fun onDeviceConnectionFailed() {
                textStatus!!.text = getString(R.string.bthStatus_failed)
                btnConnect.text = getString(R.string.connectBth)
                textDeviceName!!.text = "Возникла ошибка при подключению к устройству"
            }

            override fun onDeviceConnected(name: String, address: String) {

//                val stateIntent = Intent(applicationContext, bt)
//                startActivityForResult(stateIntent, BluetoothState.STATE_CONNECTED)
//                val stateIntent = Intent(applicationContext, bt)
//                startActivityForResult(stateIntent, BluetoothState.)

                textStatus!!.text = getString(R.string.btnStatus_Connect)
                btnConnect.text = getString(R.string.disconnectBth)
                textDeviceName!!.text = name
                textDeviceMac!!.text = address

                sharedPreference.save("BT_NAME", name)
                sharedPreference.save("BT_MAC", address)
            }
        })
    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.BLUETOOTH), permissionCode)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.INTERNET), permissionCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == RESULT_OK) bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
            } else {
                Toast.makeText(
                    applicationContext, "Bluetooth was not enabled.", Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

//    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
//            if (resultCode == RESULT_OK) bt.connect(data)
//        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
//            if (resultCode == RESULT_OK) {
//                bt.setupService()
//                bt.startService(BluetoothState.DEVICE_ANDROID)
//            } else {
//                Toast.makeText(
//                    applicationContext, "Bluetooth was not enabled.", Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }
}
