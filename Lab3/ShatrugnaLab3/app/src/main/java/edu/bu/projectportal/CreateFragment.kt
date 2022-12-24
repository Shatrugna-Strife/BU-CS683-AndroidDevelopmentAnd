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
import edu.bu.projectportal.databinding.FragmentCreateBinding
import edu.bu.projectportal.databinding.FragmentEditBinding

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        binding.create.setOnClickListener {
            Project.projects.add(Project(
                Project.projects[Project.projects.size-1].id+1,
                binding.projTitleEdit.text.toString(),
                binding.projAuthorsEdit.text.toString(),
                binding.projLinkEdit.text.toString(),
                binding.projFavouriteEdit.isChecked,
                binding.projKeywordsEdit.text.toString(),
                binding.projDescEdit.text.toString()
            ))

            view.findNavController().
            navigate(R.id.action_createFragment_pop)
        }
        binding.cancel.setOnClickListener {
            view.findNavController().
            navigate(R.id.action_createFragment_pop)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}