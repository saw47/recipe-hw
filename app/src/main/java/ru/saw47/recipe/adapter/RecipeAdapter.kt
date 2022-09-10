package ru.saw47.recipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.saw47.recipe.R
import ru.saw47.recipe.data.*
import ru.saw47.recipe.data.util.Util.getResourceText
import ru.saw47.recipe.databinding.CardRecipeBinding


class RecipeAdapter(
    private val listener: AppListener,
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardRecipeBinding.inflate(inflater)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    inner class ViewHolder(
        private val binding: CardRecipeBinding,
        listener: AppListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            val menu = PopupMenu(itemView.context, binding.optionsButton)
            menu.apply {
                inflate(R.menu.menu_recipe_rv)
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
            with(binding) {
                feedPostFrame.setOnClickListener {
                    if(listener.upDownButtonState.value == true) {
                        listener.hideUpDownButtons()
                    } else {
                        listener.frameOnShortClick(recipe)
                    }
                }

                feedPostFrame.setOnLongClickListener(OnLongClickListener {
                    listener.showUpDownButtons()
                    listener.setMovableRecipe(recipe)
                    true
                }
                )

                favoriteButton.setOnClickListener {
                    listener.favoriteOnClick(recipe)
                    bind(recipe)
                }

                optionsButton.setOnClickListener {
                    popupMenu.show()
                }
            }

            popupMenu.setOnDismissListener() {
                popupMenu.dismiss()
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                recipeName.text = recipe.name
                recipeAuthor.text = recipe.author
                recipeCategory.text = getResourceText(recipe.category)
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