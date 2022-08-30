package ru.saw47.recipe.adapter

import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.saw47.recipe.R
import ru.saw47.recipe.data.*
import ru.saw47.recipe.data.impl.TestTempRepository
import ru.saw47.recipe.databinding.CardRecipeBinding

class RecipeAdapter(
    private val recipeInteractionListener: RecipeInteractionListener
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardRecipeBinding.inflate(inflater)
        return ViewHolder(binding, recipeInteractionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    inner class ViewHolder(
        private val binding: CardRecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            val menu = PopupMenu(itemView.context, binding.optionsButton)
            menu.apply {
                inflate(R.menu.menu_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.edit_recipe_action -> {
                            listener.editOnClick(recipe)
                            true
                        }
                        R.id.delete_recipe_action -> {
                            listener.deleteOnClick(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.feedPostFrame.setOnClickListener {
                listener.frameOnShortClick(recipe)
            }

            binding.favoriteButton.setOnClickListener {
                listener.favoriteOnClick(recipe)
            }

            binding.optionsButton.setOnClickListener {
                popupMenu.show()
                binding.optionsButton.isChecked = true
            }

            popupMenu.setOnDismissListener() {
                popupMenu.dismiss()
                binding.optionsButton.isChecked = false
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                recipeName.text = recipe.name
                recipeAuthor.text = recipe.author
                recipeCategory.text = TestTempRepository.getResourceText(recipe.category)
                if (recipe.imageUri != null) {
                    recipeImage.setImageURI(recipe.imageUri)
                }
                favoriteButton.isChecked = recipe.isFavorite
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }

    }
}