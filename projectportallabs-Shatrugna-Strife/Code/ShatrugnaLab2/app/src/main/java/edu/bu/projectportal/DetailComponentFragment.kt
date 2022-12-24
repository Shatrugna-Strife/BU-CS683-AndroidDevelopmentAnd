package edu.bu.projectportal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class DetailFragmentComponent(var project: Project, var index: Int) : Fragment() {
    private lateinit var projTitle:TextView
    private lateinit var projDesc:TextView
    private lateinit var editProj: ImageButton

    // private lateinit var editProjListener: EditProjectListener

    companion object {
        fun newInstance() = DetailFragment()
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is EditProjectListener) {
//            editProjListener = context
//        } else {
//            throw RuntimeException("Must implement EditProjectListener")
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return    inflater.inflate(R.layout.fragment_detail_component, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)


        (view.findViewById(R.id.projTitle) as TextView).text =  project.title
        (view.findViewById(R.id.projAuthors) as TextView).text =  project.authors
        (view.findViewById(R.id.projLinks) as TextView).text =  project.links
        (view.findViewById(R.id.projSearch) as TextView).text =  project.keywords
        (view.findViewById(R.id.projDesc) as TextView).text =  project.description
        (view.findViewById(R.id.projFavourite) as Switch).isChecked = project.favourite


        (view.findViewById(R.id.editProj) as ImageButton).setOnClickListener{
            DetailFragment.indexToEdit  =index
            parentFragmentManager.commit{
                replace<EditFragment>(R.id.container)
                addToBackStack("detail")
            }
            // editProjListener.editProj()
        }
    }

}