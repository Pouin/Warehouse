package ru.energomera.mywarehouse.ui.fragmentMainActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_put_formed_items.*
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.adapter.GrnItemAdapter
import ru.energomera.mywarehouse.data.model.local.database.GrnListEntity


class putFormedItemsFragment : Fragment()  {

    val itemLists: MutableList<GrnListEntity> = generateList()

//    private var adapter: RecyclerView.Adapter<GrnItemAdapter.GrnListViewHolder>? = null
//    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_put_formed_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        grnListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = GrnItemAdapter(itemLists)
        }
    }

    fun generateList():MutableList<GrnListEntity> {
        val list = mutableListOf<GrnListEntity>()
        list.add(GrnListEntity("ZG0000073611", "\"РПК \"Оптима\" ООО", "04.04.2022"))
        list.add(GrnListEntity("ZG0000073634", "АО \"ВМС-Принт\"", "06.04.2022"))
        list.add(GrnListEntity("ZG0000073635", "АО \"ВМС-Принт\"", "06.04.2022"))
        list.add(GrnListEntity("ZG0000073628", "Микролит  ООО", "06.04.2022"))

        return list
    }

}