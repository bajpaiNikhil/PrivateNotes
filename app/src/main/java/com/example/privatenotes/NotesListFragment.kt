package com.example.privatenotes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Query
import com.example.privatenotes.adapter.ItemAdapter
import com.example.privatenotes.database.db.NotesItemDatabase
import com.example.privatenotes.database.entity.NotesItem
import com.example.privatenotes.database.repository.NotesRepository
import com.example.privatenotes.database.viewModel.NotesViewModel
import com.example.privatenotes.database.viewModel.NotesViewModelFactory
import com.example.privatenotes.databinding.FragmentNotesListBinding


class NotesListFragment : Fragment() {

    private var _binding : FragmentNotesListBinding? = null
    private val binding get() = _binding!!


    private var searchText = ""
    private var flag = 0

    private var notesList : MutableList<NotesItem> = mutableListOf()
    private var searchList : MutableList<NotesItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesListBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_createNotesFragment)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchText = newText!!
                if(flag == 1){
                    setUpViewModel()
                }else if(searchText.length>=2){
                    flag = 1
                    setUpViewModel()
                }
                return false
            }

        })

        setUpViewModel()

    }

    private fun setUpViewModel() {

        Log.d("noteslistFragment" , "this is the entered text $searchText")
        val roomViewModel = ViewModelProvider(this ,
            NotesViewModelFactory(NotesRepository(NotesItemDatabase(context!!)))
        ).get(NotesViewModel::class.java)

        roomViewModel.getAllNotesListItem().observe(viewLifecycleOwner  , Observer {
            for(item in it){
                binding.ListAnimation.visibility = View.INVISIBLE
                notesList.add(item)
            }
            for(searchItem in notesList){
                if(searchText.length>=5){
                    if (searchItem.notesTitle.contains(searchText)){
                        searchList.add(searchItem)
                    }
                }
            }
//            Log.d("noteslistFragment" , "list is ${notesList.distinct()} , ${notesList.size}")
//            Log.d("noteslistFragment" , "Search list is ${searchList.distinct()} , ${searchList.size} , $searchText")
            binding.rView.layoutManager = LinearLayoutManager(context)

            fun onItemSelected(notesItem: NotesItem) {
                val bundle = bundleOf("notesItem" to notesItem)
                findNavController().navigate(R.id.action_notesListFragment_to_notesEditFragment , bundle)
            }
            if (searchList.size!=0){
                binding.rView.adapter = ItemAdapter(searchList.distinct() , roomViewModel , ::onItemSelected)
                binding.rView.adapter?.notifyDataSetChanged()
                searchList.clear()
            }else{
                binding.rView.adapter = ItemAdapter(notesList.distinct() , roomViewModel , ::onItemSelected)
                binding.rView.adapter?.notifyDataSetChanged()
                notesList.clear()
            }

        })
    }
}