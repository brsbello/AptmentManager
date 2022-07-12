package com.example.aptmentmanager.ui.warnings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aptmentmanager.data.models.WarningModel
import com.example.aptmentmanager.databinding.ItemWarningBinding

class WarningAdapter : ListAdapter<WarningModel, WarningAdapter.WarningViewHolder>(DiffCallback()) {

    var listenerEdit: (WarningModel) -> Unit = {}
    var listenerDelete: (WarningModel) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarningViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWarningBinding.inflate(inflater, parent, false)
        return WarningViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WarningViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WarningViewHolder(private val binding: ItemWarningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WarningModel) {
            binding.TVItemTitle.text = item.title
            binding.TVItemDate.text = item.date
            binding.TVItemHour.text = item.hour
            binding.TVContentDescription.text = item.description
            //binding.IVMore.setOnClickListener { showPopup(item) }
        }

        //private fun showPopup(item:Task) {
        //    val ivMore = binding.IVMore
        //    val popUpMenu = PopupMenu(ivMore.context, ivMore)
        //    popUpMenu.menuInflater.inflate(R.menu.popup_menu, popUpMenu.menu)
        //    popUpMenu.setOnMenuItemClickListener {
        //        when (it.itemId) {
        //            R.id.action_edit -> listenerEdit(item)
        //            R.id.action_delete -> listenerDelete(item)
        //        }
        //        return@setOnMenuItemClickListener true
        //    }
        //    popUpMenu.show()
        //}
    }
}

class DiffCallback : DiffUtil.ItemCallback<WarningModel>() {
    override fun areItemsTheSame(oldItem: WarningModel, newItem: WarningModel) = oldItem == newItem

    override fun areContentsTheSame(oldItem: WarningModel, newItem: WarningModel) =
        oldItem.id == newItem.id

}