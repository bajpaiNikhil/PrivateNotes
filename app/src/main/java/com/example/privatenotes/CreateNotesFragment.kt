package com.example.privatenotes

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.privatenotes.database.db.NotesItemDatabase
import com.example.privatenotes.database.entity.NotesItem
import com.example.privatenotes.database.repository.NotesRepository
import com.example.privatenotes.database.viewModel.NotesViewModel
import com.example.privatenotes.database.viewModel.NotesViewModelFactory
import com.example.privatenotes.databinding.FragmentCreateNotesBinding
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import java.text.SimpleDateFormat
import java.util.*


class CreateNotesFragment : Fragment()  {

    private var _binding : FragmentCreateNotesBinding? = null
    private val binding get() = _binding!!
    private var dateText : String = ""
    lateinit var item : NotesItem
    private var buttonPress : Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_create_notes, container, false)

        _binding = FragmentCreateNotesBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{view , year , month , dayOfMonth ->
            myCalendar.set(Calendar.YEAR  , year)
            myCalendar.set(Calendar.MONTH  , month)
            myCalendar.set(Calendar.DAY_OF_MONTH  , dayOfMonth)
            upDateLabel(myCalendar)
        }
        binding.datePickerIv.setOnClickListener {
            DatePickerDialog(context!! ,
                datePicker ,
                myCalendar.get(Calendar.YEAR) ,
                myCalendar.get(Calendar.MONTH) ,
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.notesAdd.setOnClickListener {
            val title = binding.notesTitleTv.text.toString()
            val description = binding.notesDescriptionTv.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty() && dateText.isNotEmpty()){
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

        val roomViewModel = ViewModelProvider(this ,NotesViewModelFactory(NotesRepository(NotesItemDatabase(context!!))))[NotesViewModel::class.java]
        item  =  NotesItem(
            binding.notesTitleTv.text.toString() ,
            binding.notesDescriptionTv.text.toString() ,
            dateText ,
            ""
        )
        roomViewModel.upsert(item)
        findNavController().navigate(R.id.action_createNotesFragment_to_notesListFragment)
    }

    private fun upDateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat , Locale.UK)
        dateText = sdf.format(myCalendar.time)
        binding.dateTV.text = sdf.format(myCalendar.time)
    }


}