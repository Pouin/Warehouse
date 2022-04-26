package ru.energomera.mywarehouse.ui.fragmentMainActivity

import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import ru.energomera.mywarehouse.BluetoothAdapterProvider
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.util.Constants

class PutUnformedItemsFragment(private val adapterProvider: BluetoothAdapterProvider) : Fragment() {

    lateinit var bt: BluetoothSPP
    lateinit var mSettings: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_put_unformed_items, container, false)

        var textRead: TextView = view.findViewById(R.id.editTextToScanBth)
        var textStatus: TextView = view.findViewById(R.id.textStatusss)
        mSettings = activity!!.applicationContext.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)

        bt = BluetoothSPP(adapterProvider.getContext())
        bt.setOnDataReceivedListener { data, message ->
            textRead.text = message.toString()
        }

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                textStatus!!.text = getString(R.string.bthStatus_notCon)
            }

            override fun onDeviceConnectionFailed() {
                textStatus!!.text = getString(R.string.bthStatus_failed)
            }

            override fun onDeviceConnected(name: String, address: String) {
                textStatus!!.text = getString(R.string.btnStatus_Connect)
            }
        })

        return view
    }

    override fun onStart() {
        super.onStart()

        if (!bt.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
            } else {
                Toast.makeText(
                    activity, "Bluetooth was not enabled.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}