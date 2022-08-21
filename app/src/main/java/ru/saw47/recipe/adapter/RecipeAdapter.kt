package ru.saw47.recipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.databinding.CardRecipeBinding

class RecipeAdapter(
    private val interactionListener: InteractionListener
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardRecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    inner class ViewHolder(
        private val binding: CardRecipeBinding,
        listener: InteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.optionsButton).apply {
                inflate(R.menu.options_post)

                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClick(post.postId)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClick(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.feedPostFrame.setOnClickListener {
                listener.onFrameClick(post)
            }

            binding.likesButton.setOnClickListener {
                listener.onLikeClick(post)
            }

            binding.shareButton.setOnClickListener {
                listener.onRepostClick(post)
            }

            binding.optionsButton.setOnClickListener {
                popupMenu.show()
                binding.optionsButton.isChecked = true
            }

            popupMenu.setOnDismissListener() {
                binding.optionsButton.isChecked = false
            }

            binding.video.setOnClickListener {
                listener.onVideoLinkClicked(post)
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                authorNameTextView.text = post.authorName
                mainTextView.text = post.content
                postDateTextView.text = getSimpleDateFormat()
                shareButton.text = Service.peopleCounter(post.repostCounter)
                likesButton.text = Service.peopleCounter(post.favoriteCounter)
                mainTextLink.text = post.link ?: ""
                viewCount.text = Service.peopleCounter(post.viewCounter)
                likesButton.isChecked = post.favoriteSet.contains(user.userId)
                shareButton.isChecked = post.repostCounter >= 1
                if (!post.video.isNullOrBlank()) {
                    video.visibility = View.VISIBLE
                }
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