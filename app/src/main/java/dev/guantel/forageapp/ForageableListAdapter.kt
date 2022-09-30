package dev.guantel.forageapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.guantel.forageapp.data.Forageable
import dev.guantel.forageapp.databinding.ListItemForageableBinding



class ForageableListAdapter(private val onItemClicked:(Forageable)->Unit) :
ListAdapter<Forageable,ForageableListAdapter.ForageableViewHolder>(DiffCallback){


    class ForageableViewHolder(private var binding:ListItemForageableBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(forageable: Forageable){
            binding.name.text = forageable.name
            binding.address.text = forageable.address
            binding.forageable = forageable
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForageableViewHolder {
        return ForageableViewHolder(
            ListItemForageableBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ForageableViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

}

object DiffCallback: DiffUtil.ItemCallback<Forageable>()  {
    override fun areItemsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
     return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
    return oldItem.name == newItem.name
    }

}



