package com.example.privatenotes

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.privatenotes.database.db.NotesItemDatabase
import com.example.privatenotes.database.entity.NotesItem
import com.example.privatenotes.database.repository.NotesRepository
import com.example.privatenotes.database.viewModel.NotesViewModel
import com.example.privatenotes.database.viewModel.NotesViewModelFactory
import com.example.privatenotes.databinding.FragmentNotesEditBinding
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import java.text.SimpleDateFormat
import java.util.*


class NotesEditFragment : Fragment() {

    private var _binding : FragmentNotesEditBinding? =  null
    private val binding get() = _binding!!
    private lateinit var item : NotesItem
    private  var updateDate:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable<NotesItem>("notesItem")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_notes_edit, container, false)
        _binding = FragmentNotesEditBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editNotesTitleTv.text = item.notesTitle
        binding.notesDescriptionTv.setText(item.notesDescription)
        binding.createdOnTv.text = item.notesCreatedAt

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            upDateLabel(myCalendar)
        }
        binding.datePickerIv.setOnClickListener {
            DatePickerDialog(
                context!!,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.notesEdit.setOnClickListener {

            val description = binding.notesDescriptionTv.text.toString()

            if (description.isNotEmpty() && updateDate.isNotEmpty()){
                setUpViewModel()
            }else{
                AestheticDialog.Builder(context as Activity, DialogStyle.TOASTER , DialogType.SUCCESS)
                    .setTitle("Data Incomplete")
                    .setMessage("Please fill the required data")
                    .setCancelable(true)
                    .setDarkMode(true)
                    .setAnimation(DialogAnimation.SLIDE_RIGHT)
                    .show()
            }
        }
    }

    private fun setUpViewModel() {
        val roomViewModel = ViewModelProvider(this ,NotesViewModelFactory(NotesRepository(NotesItemDatabase(context!!)))).get(NotesViewModel::class.java)
        item  =  NotesItem(
            item.notesTitle ,
            binding.notesDescriptionTv.text.toString() ,
            item.notesCreatedAt ,
            updateDate
        )
        roomViewModel.upsert(item)
        findNavController().navigate(R.id.action_notesEditFragment_to_notesListFragment)
    }


    private fun upDateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat , Locale.UK)
        updateDate = sdf.format(myCalendar.time)
        binding.UpdatedDateTv.text = sdf.format(myCalendar.time)
    }

}