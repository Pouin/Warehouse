package ru.energomera.mywarehouse.ui

import android.Manifest
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
import androidx.navigation.ui.AppBarConfiguration
import androidx.preference.PreferenceFragmentCompat
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList

class SettingsActivity : AppCompatActivity() {

    private var bt: BluetoothSPP? = null
    var textStatus: TextView? = null

    lateinit var btnConnect: Button
    private val permissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        textStatus = findViewById(R.id.textStatus)
        bt = BluetoothSPP(this.baseContext)
        btnConnect = findViewById(R.id.btnConnectScan)

        if (!bt!!.isBluetoothAvailable) {
            Toast.makeText(applicationContext, "Bluetooth is not available", Toast.LENGTH_SHORT).show()
            finish()
        }
        else
            changeStatusBluetooth(bt!!)

        btnConnect.setOnClickListener {
            if (btnConnect.text == getString(R.string.connectBth)) {
                //connecting other kind of devices
                bt!!.setDeviceTarget(BluetoothState.DEVICE_OTHER)
                val intent = Intent(applicationContext, DeviceList::class.java)
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
            } else if (btnConnect.text == getString(R.string.disconnectBth)) {
                //disconnect from a device
                if (bt!!.serviceState == BluetoothState.STATE_CONNECTED) bt!!.disconnect()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    public override fun onStart() {
        super.onStart()

        checkPermission()

        if (!bt!!.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt!!.isServiceAvailable) {
                bt!!.setupService()
                bt!!.startService(BluetoothState.DEVICE_ANDROID)
            }
        }

        val textRead: TextView = findViewById(R.id.editTextToScanBth)
        bt!!.setOnDataReceivedListener { data, message ->
            textRead.text = message.toString()
        }

    }

    override fun onResume() {
        super.onResume()
        val textRead: TextView = findViewById(R.id.editTextToScanBth)
        val btAdapter: BluetoothAdapter = BluetoothSPP(this.baseContext).bluetoothAdapter
        BluetoothSPP.OnDataReceivedListener { data, message ->
            textRead.text = message.toString()
        }
    }

    private fun changeStatusBluetooth(bt: BluetoothSPP){
        var textConnectInfo: TextView? = findViewById(R.id.ConnectInfo)
        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                textStatus!!.text = getString(R.string.bthStatus_notCon)
                btnConnect.text = getString(R.string.connectBth)
                textConnectInfo!!.text = "Устройство не подключено"
            }

            override fun onDeviceConnectionFailed() {
                textStatus!!.text = getString(R.string.bthStatus_failed)
                btnConnect.text = getString(R.string.connectBth)
                textConnectInfo!!.text = "Возникла ошибка при подключению к устройству"
            }

            override fun onDeviceConnected(name: String, address: String) {

                textStatus!!.text = getString(R.string.btnStatus_Connect)
                btnConnect.text = getString(R.string.disconnectBth)
                textConnectInfo!!.text = "Информация о подключении: \nУстройство - ${name} \nMAC - ${address}"
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

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == RESULT_OK) bt!!.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                bt!!.setupService()
                bt!!.startService(BluetoothState.DEVICE_ANDROID)
            } else {
                Toast.makeText(
                    applicationContext, "Bluetooth was not enabled.", Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

}
