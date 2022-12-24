package edu.bu.projectportal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch

class EditFragment : Fragment() {

    private lateinit var projTitle: EditText
    private lateinit var projDesc: EditText
    private lateinit var submit:Button
    private lateinit var cancel:Button

   // private lateinit var editProjListener: EditProjectListener

    companion object {
        fun newInstance() = EditFragment()
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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        projTitle = view.findViewById(R.id.projTitleEdit)
        projDesc =  view.findViewById(R.id.projDescEdit)

        if(DetailFragment.indexToEdit>-1) {
            (view.findViewById(R.id.projTitleEdit) as EditText).setText(DetailFragment.projectList.get(DetailFragment.indexToEdit).title)
            (view.findViewById(R.id.projAuthorsEdit) as EditText).setText( DetailFragment.projectList.get(DetailFragment.indexToEdit).authors)
            (view.findViewById(R.id.projLinkEdit) as EditText).setText(DetailFragment.projectList.get(DetailFragment.indexToEdit).links)
            (view.findViewById(R.id.projKeywordsEdit) as EditText).setText(DetailFragment.projectList.get(DetailFragment.indexToEdit).keywords)
            (view.findViewById(R.id.projDescEdit) as EditText).setText(DetailFragment.projectList.get(DetailFragment.indexToEdit).description)
            (view.findViewById(R.id.projFavouriteEdit) as Switch).isChecked = DetailFragment.projectList.get(DetailFragment.indexToEdit).favourite

            submit = view.findViewById(R.id.create)
            cancel = view.findViewById(R.id.cancel)

            submit.setOnClickListener {
                DetailFragment.projectList.get(DetailFragment.indexToEdit).title = (view.findViewById(R.id.projTitleEdit) as EditText).text.toString()
                DetailFragment.projectList.get(DetailFragment.indexToEdit).authors = (view.findViewById(R.id.projAuthorsEdit) as EditText).text.toString()
                DetailFragment.projectList.get(DetailFragment.indexToEdit).links = (view.findViewById(R.id.projLinkEdit) as EditText).text.toString()
                DetailFragment.projectList.get(DetailFragment.indexToEdit).keywords = (view.findViewById(R.id.projKeywordsEdit) as EditText).text.toString()
                DetailFragment.projectList.get(DetailFragment.indexToEdit).description = (view.findViewById(R.id.projDescEdit) as EditText).text.toString()
                DetailFragment.projectList.get(DetailFragment.indexToEdit).favourite = (view.findViewById(R.id.projFavouriteEdit) as Switch).isChecked
                parentFragmentManager.popBackStack()
            }
        }

        cancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }




    }



}