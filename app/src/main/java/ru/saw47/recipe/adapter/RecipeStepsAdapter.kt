package ru.saw47.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.saw47.recipe.data.*
import ru.saw47.recipe.databinding.RecipeStepCardBinding

class RecipeStepsAdapter(
    private val recipeStepsListener: RecipeStepsListener
) : ListAdapter<CookingStep, RecipeStepsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeStepCardBinding.inflate(inflater)
        return ViewHolder(binding, recipeStepsListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = getItem(position)
        holder.bind(step)
    }

    inner class ViewHolder(
        private val binding: RecipeStepCardBinding,
        listener: RecipeStepsListener
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var step: CookingStep

        fun bind(step: CookingStep) {
            this.step = step
            with(binding) {
                stepText.text = step.description
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<CookingStep>() {
        override fun areItemsTheSame(oldItem: CookingStep, newItem: CookingStep): Boolean {
            return oldItem.step == newItem.step
        }

        override fun areContentsTheSame(oldItem: CookingStep, newItem: CookingStep): Boolean {
            return oldItem == newItem
        }

    }
}