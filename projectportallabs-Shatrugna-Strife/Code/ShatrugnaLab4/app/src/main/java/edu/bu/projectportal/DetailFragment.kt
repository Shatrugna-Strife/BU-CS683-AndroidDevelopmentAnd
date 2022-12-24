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
import edu.bu.projectportal.datalayer.Project

class DetailFragment : Fragment(R.layout.fragment_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val editProj = view.findViewById<ImageButton>(R.id.editProj)
        val projectportalDatabase = (requireContext().applicationContext as ProjectPortalApplication).projectportalDatabase
        val projectDao = projectportalDatabase.projectDao()

        val position:Int = arguments?.getInt("projId")?:0
        Log.d("TAG","position:"+position)
        val project = projectDao.searchProjectById(position.toLong());

        (view.findViewById(R.id.projTitle2) as TextView).text =  project.title
        (view.findViewById(R.id.projAuthors) as TextView).text =  project.authors
        (view.findViewById(R.id.projLinks) as TextView).text =  project.links
        (view.findViewById(R.id.projSearch) as TextView).text =  project.keywords
        (view.findViewById(R.id.projDesc2) as TextView).text =  project.description
        (view.findViewById(R.id.projFavourite) as Switch).isChecked = project.favourite



        editProj.setOnClickListener{
            val action = DetailFragmentDirections
                .actionDetailFragmentToEditFragment(position)
            view.findNavController().navigate(action)
//            view.findNavController().
//            navigate(R.id.action_detailFragment_to_editFragment)
        }
    }
}