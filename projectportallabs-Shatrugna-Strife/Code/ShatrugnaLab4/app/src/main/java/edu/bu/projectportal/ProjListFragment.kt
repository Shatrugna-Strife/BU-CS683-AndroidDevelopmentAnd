package edu.bu.projectportal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bu.projectportal.databinding.FragmentProjListBinding


/**
 * A fragment representing a list of Items.
 */
class ProjListFragment : Fragment() {

    private var _binding: FragmentProjListBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentProjListBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = activity?.getSharedPreferences(
            "getString(3)", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPref?.edit()!!
        super.onViewCreated(view, savedInstanceState)
        val projectportalDatabase = (requireContext().applicationContext as ProjectPortalApplication).projectportalDatabase
        val projectDao = projectportalDatabase.projectDao()
        var temp = sharedPref?.getInt("fav", 0)
        binding.favSwitch.isChecked = sharedPref?.getInt("fav", 0) == 1
        binding.projlist.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = if(binding.favSwitch.isChecked) MyProjListRecyclerViewAdapter(projectDao.getAllFavouriteProjects()) else MyProjListRecyclerViewAdapter(projectDao.getAllProjects())
        }
        binding.addProject.setOnClickListener {
            view.findNavController().navigate(R.id.action_projListRecycleViewFragment_to_createFragment)
        }
        binding.favSwitch.setOnClickListener {
            if(binding.favSwitch.isChecked){
                binding.projlist.apply{
                    layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }
                    adapter = MyProjListRecyclerViewAdapter(projectDao.getAllFavouriteProjects())
                }
            }else{
                binding.projlist.apply{
                    layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }
                    adapter = MyProjListRecyclerViewAdapter(projectDao.getAllProjects())
                }
            }

            editor.putInt("fav",  if (binding.favSwitch.isChecked) 1 else 0)
            editor.apply()
            editor.commit()
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//                ProjListFragment(application).apply {
//                    arguments = Bundle().apply {
//                        putInt(ARG_COLUMN_COUNT, columnCount)
//                    }
//                }
    }
}