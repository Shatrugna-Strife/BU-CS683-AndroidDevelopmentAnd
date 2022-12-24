package edu.bu.projectportal

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import edu.bu.projectportal.databinding.FragmentProjItemBinding
import edu.bu.projectportal.datalayer.Project


class MyProjListRecyclerViewAdapter(
        private var projects: MutableList<Project>
)
    : RecyclerView.Adapter<MyProjListRecyclerViewAdapter.ViewHolder>() {


    fun replaceItems(myProjects: List<Project>) {
        projects.clear()
        projects.addAll(myProjects)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentProjItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        if (project != null) {
            holder.idView.text = (project.id).toString()
        }
        if (project != null) {
            holder.contentView.text = project.title
        }
        holder.cardView.setOnClickListener{
            val action = ProjListFragmentDirections
                .actionProjListRecycleViewFragmentToDetailFragment(project.id)
            it.findNavController().navigate(action)

  //          it.findNavController().navigate(R.id.action_projListRecycleViewFragment_to_detailFragment)

        }
    }

    override fun getItemCount(): Int = projects.size?:0

    inner class ViewHolder(binding: FragmentProjItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.projIdView
        val contentView: TextView = binding.projTitleinCard
        val cardView: CardView = binding.projectCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}