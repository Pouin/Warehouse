package ru.energomera.mywarehouse.ui.fragmentMainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_post_comp_items.*
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.adapter.CompsItemAdapter
import ru.energomera.mywarehouse.data.model.local.database.CompListEntity

class PostCompItemsFragment : Fragment() {

    val itemLists: MutableList<CompListEntity> = generateList()

//    private var adapter: RecyclerView.Adapter<CompsItemAdapter.CompListViewHolder>? = null
//    private var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post_comp_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CompsItemAdapter(itemLists)
        }
    }

    fun generateList():MutableList<CompListEntity> {
        val list = mutableListOf<CompListEntity>()
        list.add(CompListEntity("1359796", "Z22_008709", "302012082005136", 66.0))
        list.add(CompListEntity("1359800", "Z22_008709", "302005151025255", 54.0))
        list.add(CompListEntity("1359733", "MZ22_00334", "101002008011745", 7.0))
        list.add(CompListEntity("1359795", "Z22_008712", "101002008011745", 1.0))
        list.add(CompListEntity("1359796", "Z22_008709", "302012082005136", 66.0))
        list.add(CompListEntity("1359800", "Z22_008709", "302005151025255", 54.0))
        list.add(CompListEntity("1359733", "MZ22_00334", "101002008011745", 7.0))
        list.add(CompListEntity("1359795", "Z22_008712", "101002008011745", 1.0))
        list.add(CompListEntity("1359796", "Z22_008709", "302012082005136", 66.0))
        list.add(CompListEntity("1359800", "Z22_008709", "302005151025255", 54.0))
        list.add(CompListEntity("1359733", "MZ22_00334", "101002008011745", 7.0))
        list.add(CompListEntity("1359795", "Z22_008712", "101002008011745", 1.0))

        return list
    }

}