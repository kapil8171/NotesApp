package com.example.notesapp.ui.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class EditNotesFragment : Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()
    lateinit var binding: FragmentEditNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEditNotesBinding.inflate(layoutInflater,container,false)

        setHasOptionsMenu(true)

          binding.edtTitle.setText(oldNotes.Data.title)
          binding.edtSubtitle.setText(oldNotes.Data.subtitle)
          binding.edtNotes.setText(oldNotes.Data.notes)

        when (oldNotes.Data.priority) {
            "1" -> {
                priority ="1"
                binding.pGreen.setImageResource(R.drawable.done_click)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)

            }
            "2" -> {
                priority ="2"
                binding.pYellow.setImageResource(R.drawable.done_click)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)

            }
            "3" -> {
                priority ="3"
                binding.pRed.setImageResource(R.drawable.done_click)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)

            }
        }

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


           binding.btnEditSaveNotes.setOnClickListener {

               updateNotes(it)
           }

        return binding.root
    }

    private fun updateNotes(it: View?) {


        val title = binding.edtTitle.text.toString()
        val subtitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()

//        val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
//        val currentDateAndTime = sdf.format(Date())

        val currentDateAndTime = getCurrentDateAndTime()


        val data = Notes(
            oldNotes.Data.id,
            title = title,
            subtitle = subtitle,
            notes = notes,
            date = currentDateAndTime,
            priority
        )
        viewModel.updateNotes(data)

        Toast.makeText(requireContext(), "  Notes updated successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.Data.id!!)
                bottomSheet.dismiss()

                // Show custom toast message
                showToast(requireContext(), "Notes Deleted successfully")

                // Navigate after dismissing the dialog
                findNavController().navigate(R.id.action_editNotesFragment_to_homeFragment)
            }

            bottomSheet.show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun showToast(context: Context, message: String) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.toast_custom, null)

        val textToast = view.findViewById<TextView>(R.id.textToast)
        textToast.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
    }


    private fun getCurrentDateAndTime(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
        return dateFormat.format(Date())
    }


}