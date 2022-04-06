package ru.energomera.mywarehouse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_comp_list.view.*
import kotlinx.android.synthetic.main.item_grn_list.view.*
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.data.model.local.database.GrnListEntity


interface OnGrnItemClickCallback {

    /**
     * Функция передающая клик по элементу
     */
    fun onItemClick(compNum: String)
}

class GrnItemAdapter(private val GrnList:List<GrnListEntity>/*private val onItemClickCallback: OnItemClickCallback*/): RecyclerView.Adapter<GrnItemAdapter.GrnListViewHolder>() {

    private var grnList = GrnList//mutableListOf<CompListEntity>()
    private val onCompItemClickCallback: OnCompItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrnListViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_grn_list, parent, false)
        return GrnListViewHolder(binding)
    }

    /**
     * Принимает объект ViewHolder и устанавливает необходимые
     * данные для соответствующего элемента RecyclerView
     */
    override fun onBindViewHolder(holder: GrnListViewHolder, position: Int) {
        holder.bind(grnList[position])//, onItemClickCallback!!)
    }

    /**
     * Возвращает общее количество элементов списка.
     */
    override fun getItemCount(): Int {
        return grnList.size
    }

    class GrnListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: GrnListEntity){//}, onItemClickCallback: OnItemClickCallback) {
            itemView.GrnNum.text = model.grnNum
            itemView.VendorName.text = model.vendorName
            itemView.DateShip.text = model.dateShip

            //обрабатываем клик по элементу списка
//            itemView.setOnClickListener {
//                onItemClickCallback.onItemClick(model.compNum)
//            }
        }
    }
}