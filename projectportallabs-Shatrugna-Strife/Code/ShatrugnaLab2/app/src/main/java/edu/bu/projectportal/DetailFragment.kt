package edu.bu.projectportal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.*

class DetailFragment : Fragment() {
//    private lateinit var projTitle:TextView
//    private lateinit var projDesc:TextView
//    private lateinit var editProj: ImageButton

   // private lateinit var editProjListener: EditProjectListener

    companion object {
        fun newInstance() = DetailFragment()
        val projectList = mutableListOf<Project>(
            Project("WebProject","shatru","www.facebook.com", true,"keyword webDevelopment","desciption good"),
        )
        var indexToEdit = -1
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
        return    inflater.inflate(R.layout.fragment_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

//        projTitle = view.findViewById(R.id.projTitle)
//        projDesc =  view.findViewById(R.id.projDesc)
//        var editProj:Button = view.findViewById(R.id.editProj)
        if(projectList.size>0) {
            var fragMan = getParentFragmentManager()
            var fragTransaction = fragMan.beginTransaction();
            for(i in 0..projectList.size-1){
                fragTransaction.add(R.id.linearLayoutDetail, DetailFragmentComponent(projectList.get(i), i), "fragment");
            }
            fragTransaction.commit();
        }
        view.findViewById<Button>(R.id.createButton).setOnClickListener {
            parentFragmentManager.commit{
                replace<CreateFragment>(R.id.container)
                addToBackStack("detail")
            }
        }
//        projTitle.text =  Project.project.title
//        projDesc.text = Project.project.description

//        editProj.setOnClickListener{
//            parentFragmentManager.commit{
//                replace<EditFragment>(R.id.container)
//                addToBackStack("detail")
//            }
//          // editProjListener.editProj()
//        }
    }

}