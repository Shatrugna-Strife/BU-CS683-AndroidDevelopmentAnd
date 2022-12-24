package edu.bu.projectportal

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import edu.bu.projectportal.databinding.FragmentEditBinding

class EditFragment : Fragment() {
    // use ViewBinding
    private var _binding: FragmentEditBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val projectportalDatabase = (requireContext().applicationContext as ProjectPortalApplication).projectportalDatabase
        val projectDao = projectportalDatabase.projectDao()

        val position:Int = arguments?.getInt("projId")?:0
        Log.d("TAG","position:"+position)
        val project = projectDao.searchProjectById(position.toLong());

        binding.projTitleEdit.setText(project.title)
        binding.projAuthorsEdit.setText( project.authors)
        binding.projLinkEdit.setText(project.links)
        binding.projKeywordsEdit.setText(project.keywords)
        binding.projDescEdit.setText(project.description)
        binding.projFavouriteEdit.isChecked = project.favourite
//
//
        binding.submit.setOnClickListener {

            project.title = binding.projTitleEdit.text.toString()
            project.authors = binding.projAuthorsEdit.text.toString()
            project.links = binding.projLinkEdit.text.toString()
            project.keywords = binding.projKeywordsEdit.text.toString()
            project.description = binding.projDescEdit.text.toString()
            project.favourite = binding.projFavouriteEdit.isChecked

            projectDao.updateProject(project)

            view.findNavController().
            navigate(R.id.action_editFragment_pop)
        }
        binding.cancel.setOnClickListener {
            view.findNavController().
            navigate(R.id.action_editFragment_pop)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}