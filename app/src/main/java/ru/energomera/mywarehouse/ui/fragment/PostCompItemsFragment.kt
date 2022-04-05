package ru.energomera.mywarehouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.adapter.CompsItemAdapter
import ru.energomera.mywarehouse.data.model.local.database.CompListEntity

class PostCompItemsFragment : Fragment() {

    val itemLists: MutableList<CompListEntity> = generateList()
    private var compListAdapter = CompsItemAdapter(itemLists)

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_comp_items, container, false)
    }

    fun generateList():List<CompListEntity> {
        val list = mutableListOf<CompListEntity>()
        list.add(CompListEntity("13213", "zczcz", ))
    }

}