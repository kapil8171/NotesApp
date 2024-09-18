package com.example.notesapp.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentCreateNotesBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CreateNotesFragment : Fragment() {

   private lateinit var binding: FragmentCreateNotesBinding
      var priority: String = "1"
      val viewModel: NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)


        binding.pGreen.setImageResource(R.drawable.done_click)

         binding.pGreen.setOnClickListener {
             priority ="1"
              binding.pGreen.setImageResource(R.drawable.done_click)
             binding.pRed.setImageResource(0)
             binding.pYellow.setImageResource(0)
         }

        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.done_click)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }

        binding.pRed.setOnClickListener {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.done_click)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }



      binding.btnSaveNotes.setOnClickListener {
          createNotes(it)
      }





        return binding.root
    }

    private fun createNotes(it: View?) {

          val title = binding.edtTitle.text.toString()
          val subtitle = binding.edtSubtitle.text.toString()
          val notes = binding.edtNotes.text.toString()

//        val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
//        val currentDateAndTime = sdf.format(Date())

        val currentDateAndTime = getCurrentDateAndTime()


        val data = Notes(null,title = title, subtitle = subtitle, notes = notes, date = currentDateAndTime,priority)
        viewModel.addNotes(data)

        Toast.makeText(requireContext(), "notes created successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)

    }

    private fun getCurrentDateAndTime(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
        return dateFormat.format(Date())
    }


}