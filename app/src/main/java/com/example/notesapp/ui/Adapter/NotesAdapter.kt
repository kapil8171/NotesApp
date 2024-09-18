package com.example.notesapp.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.ui.Fragments.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

        fun filtering(newFilteredList: ArrayList<Notes>) {
              notesList = newFilteredList
            notifyDataSetChanged()
        }



    class notesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        holder.binding.apply {
            val data = notesList[position]
            notesTitle.text = data.title.toString()
            notesSubtitle.text = data.subtitle.toString()
            notesDate.text = data.date.toString()

            when (data.priority) {
                "1" -> viewPriority.setBackgroundResource(R.drawable.green_dot)
                "2" -> viewPriority.setBackgroundResource(R.drawable.yellow_dot)
                "3" -> viewPriority.setBackgroundResource(R.drawable.red_dot)
            }

            root.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(data)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }



    override fun getItemCount(): Int {
        return notesList.size
    }
}