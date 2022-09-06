package ru.saw47.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recipe_step_card.view.*
import ru.saw47.recipe.data.*
import ru.saw47.recipe.databinding.RecipeStepCardBinding

class RecipeStepsAdapter(
    private val stepsInteractionListener: StepsInteractionListener
) : ListAdapter<Step, RecipeStepsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeStepCardBinding.inflate(inflater)
        return ViewHolder(binding, stepsInteractionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = getItem(position)
        holder.bind(step)
    }

    inner class ViewHolder(
        private val binding: RecipeStepCardBinding,
        listener: StepsInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var step: Step

        fun bind(step: Step) {
            this.step = step
            with(binding) {
                stepText.text = step.description
                if (step.imageUri != null) {
                    stepImage.setImageURI(step.imageUri)
                }
            }
        }

        init {
            binding.editStepFab.setOnClickListener() {
                listener.editStepOnClick(step)
            }
            binding.removeStepFab.setOnClickListener() {
                listener.deleteStepOnClick(step)
            }
        }
    }


    object DiffCallback : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem.stepId == newItem.stepId
        }

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem == newItem
        }

    }
}