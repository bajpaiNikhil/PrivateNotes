package com.example.privatenotes.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.privatenotes.R
import com.example.privatenotes.database.entity.NotesItem
import com.example.privatenotes.database.viewModel.NotesViewModel
import com.example.privatenotes.databinding.ItemNoteBinding


class ItemAdapter(
    private val listNotes: List<NotesItem> ,
    private val roomViewModel : NotesViewModel ,
    val listener : (NotesItem)->Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(var binding : ItemNoteBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =  ItemNoteBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder){
            with(listNotes[position]){
                binding.notesListTitleTv.text = this.notesTitle
                binding.notesListItemDescriptionTv.text = this.notesDescription
                binding.notesDeleteIv.setOnClickListener {
                    roomViewModel.delete(listNotes[position])
                }
            }
        }
        val rainbow = holder.itemView.resources.getIntArray(R.array.rainbow)

        val randomColor  = (rainbow.indices).random()
        holder.binding.cardView.setCardBackgroundColor(rainbow[randomColor])
        holder.itemView.setOnClickListener {
            listener(listNotes[position])
            roomViewModel.delete(listNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }


}