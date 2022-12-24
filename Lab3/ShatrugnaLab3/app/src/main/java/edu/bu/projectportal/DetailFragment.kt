package edu.bu.projectportal

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class DetailFragment : Fragment(R.layout.fragment_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val editProj = view.findViewById<ImageButton>(R.id.editProj)

        val position:Int = arguments?.getInt("projId")?:0
        Log.d("TAG","position:"+position)
        (view.findViewById(R.id.projTitle2) as TextView).text =  Project.projects[position].title
        (view.findViewById(R.id.projAuthors) as TextView).text =  Project.projects[position].authors
        (view.findViewById(R.id.projLinks) as TextView).text =  Project.projects[position].links
        (view.findViewById(R.id.projSearch) as TextView).text =  Project.projects[position].keywords
        (view.findViewById(R.id.projDesc2) as TextView).text =  Project.projects[position].description
        (view.findViewById(R.id.projFavourite) as Switch).isChecked = Project.projects[position].favourite



        editProj.setOnClickListener{
            val action = DetailFragmentDirections
                .actionDetailFragmentToEditFragment(position)
            view.findNavController().navigate(action)
//            view.findNavController().
//            navigate(R.id.action_detailFragment_to_editFragment)
        }
    }
}