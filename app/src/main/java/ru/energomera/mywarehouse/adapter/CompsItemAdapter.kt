package ru.energomera.mywarehouse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_comp_list.view.*
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.data.model.local.database.CompListEntity

interface OnCompItemClickCallback {

    /**
     * Функция передающая клик по элементу
     */
    fun onItemClick(compNum: String)
}

class CompsItemAdapter(private val warehouse:List<CompListEntity>/*private val onItemClickCallback: OnItemClickCallback*/): RecyclerView.Adapter<CompsItemAdapter.CompListViewHolder>() {

    /**
     * Переменная хранящая список объектов [WarehouseEntity], по умолчанию содержит пустой список
     */
    private var compList = warehouse//mutableListOf<CompListEntity>()
    private val onCompItemClickCallback: OnCompItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompListViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_comp_list, parent, false)
        return CompListViewHolder(binding)
    }

    /**
     * Принимает объект ViewHolder и устанавливает необходимые
     * данные для соответствующего элемента RecyclerView
     */
    override fun onBindViewHolder(holder: CompListViewHolder, position: Int) {
        holder.bind(compList[position])//, onItemClickCallback!!)
    }

    /**
     * Возвращает общее количество элементов списка.
     */
    override fun getItemCount(): Int {
        return compList.size
    }

    class CompListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: CompListEntity){//}, onItemClickCallback: OnItemClickCallback) {
            itemView.CompNum.text = model.compNum
            itemView.TrnNum.text = model.trnNum

            //обрабатываем клик по элементу списка
//            itemView.setOnClickListener {
//                onItemClickCallback.onItemClick(model.compNum)
//            }
        }
    }

}