package com.d2k.appictask.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.d2k.appictask.databinding.RowListBinding
import com.d2k.appictask.ui.main.model.FilterListModel

class FilterListAdapter(
    val context: Context,
    private val list: List<FilterListModel>?
) : RecyclerView.Adapter<FilterListAdapter.Holder>(), Filterable {
    var filterList = ArrayList<FilterListModel>()
    // exampleListFull . exampleList

    init {
        filterList = list as ArrayList<FilterListModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = RowListBinding.inflate(LayoutInflater.from(context), parent, false)
        return Holder(
            v
        )
    }

    override fun getItemCount(): Int {
        return filterList.size ?: 0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        list?.let { holder.bind(it, position, context) }
        holder.setIsRecyclable(false)
    }

    class Holder(var binding: RowListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            levelListResponse: List<FilterListModel>,
            position: Int,
            context: Context
        ) {
            val levelResponse = levelListResponse.get(position)
            binding.apply {
                cbName.text = levelResponse.name
                cbName.isChecked = levelResponse.isSelected
                cbName.setOnCheckedChangeListener { _, b ->
                    if (b) {
                        levelListResponse.get(position).isSelected = true
/*                        if (levelResponse.levelName?.lowercase(Locale.ROOT).equals("all")) {
                            for (i in levelListResponse.indices) {
                                levelListResponse.get(i).isSelected = "Y"
                            }
                        }
                        listener.onClicked(adapterPosition, levelResponse)*/
                    } else
                        levelListResponse.get(position).isSelected = false
                }
            }

        }

    }

    public fun getList(): List<FilterListModel>? {
        return list
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = list as ArrayList<FilterListModel>
                } else {
                    val resultList = ArrayList<FilterListModel>()
                    for (row in list!!) {
                        if (row.name.lowercase().contains(constraint.toString().lowercase())) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<FilterListModel>
                notifyDataSetChanged()
            }
        }
    }
}