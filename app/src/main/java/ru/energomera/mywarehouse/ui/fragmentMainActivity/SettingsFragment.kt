package ru.energomera.mywarehouse.ui.fragmentMainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.util.SharedPreference

class SettingsFragment: Fragment() {

    private lateinit var bt: BluetoothSPP
    private lateinit var mSettings: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_settings, container, false)

        val textRead: TextView = view.findViewById(R.id.editTextToScanBth)
        val textStatus: TextView = view.findViewById(R.id.textStatus)
        mSettings = SharedPreference(requireActivity().applicationContext)

        textRead.text = mSettings.getValueString("BT_NAME") + "\n" + mSettings.getValueString("BT_MAC")
        bt = BluetoothSPP(this.requireActivity().applicationContext)

        bt.setOnDataReceivedListener { _data, message ->
            textRead.text = message.toString()
        }

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                textStatus.text = getString(R.string.bthStatus_notCon)
            }

            override fun onDeviceConnectionFailed() {
                textStatus.text = getString(R.string.bthStatus_failed)
            }

            override fun onDeviceConnected(name: String, address: String) {
                textStatus.text = getString(R.string.btnStatus_Connect)
            }
        })


        return view
    }
}