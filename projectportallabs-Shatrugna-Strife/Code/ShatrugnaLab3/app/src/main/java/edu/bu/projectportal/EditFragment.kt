package edu.bu.projectportal

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

        val position:Int = arguments?.getInt("projId")?:0
        Log.d("TAG","position:"+position)

        binding.projTitleEdit.setText(Project.projects[position].title)
        binding.projAuthorsEdit.setText( Project.projects[position].authors)
        binding.projLinkEdit.setText(Project.projects[position].links)
        binding.projKeywordsEdit.setText(Project.projects[position].keywords)
        binding.projDescEdit.setText(Project.projects[position].description)
        binding.projFavouriteEdit.isChecked = Project.projects[position].favourite


        binding.submit.setOnClickListener {

            Project.projects[position].title = binding.projTitleEdit.text.toString()
            Project.projects[position].authors = binding.projAuthorsEdit.text.toString()
            Project.projects[position].links = binding.projLinkEdit.text.toString()
            Project.projects[position].keywords = binding.projKeywordsEdit.text.toString()
            Project.projects[position].description = binding.projDescEdit.text.toString()
            Project.projects[position].favourite = binding.projFavouriteEdit.isChecked

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