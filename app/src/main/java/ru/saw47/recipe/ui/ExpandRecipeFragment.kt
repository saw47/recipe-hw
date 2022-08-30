package ru.saw47.recipe.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.saw47.recipe.R
import ru.saw47.recipe.adapter.RecipeStepsAdapter
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.impl.TestTempRepository
import ru.saw47.recipe.databinding.FragmentExpandRecipeBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel
import java.lang.Exception

class ExpandRecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels()
    lateinit var recipe: Recipe

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentExpandRecipeBinding.inflate(inflater, container, false)
        val context: Context = this.context ?: throw Exception("no context^")

        val adapter = RecipeStepsAdapter(viewModel)

        viewModel.data.observe(viewLifecycleOwner) { steps ->
            adapter.submitList(recipe.steps)
        }

        binding.recipeStepsRecyclerview.adapter = adapter
        recipe = viewModel.expandRecipe.value!!
        bind(recipe, binding)

        val menu = PopupMenu(context, binding.expandRecipeOptionsButton)
        menu.apply {
            inflate(R.menu.menu_recipe)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_recipe_action -> {
                        viewModel.editOnClick(recipe)
                        findNavController()
                            .navigate(R.id.action_expandRecipeFragment_to_editRecipeFragment)
                        true
                    }
                    R.id.delete_recipe_action -> {
                        findNavController().popBackStack()
                        viewModel.deleteOnClick(recipe)
                        true
                    }
                    else -> false
                }
            }
        }

        binding.expandRecipeOptionsButton.setOnClickListener {
            menu.show()
            binding.expandRecipeOptionsButton.isChecked = true
        }

        menu.setOnDismissListener() {
            menu.dismiss()
            binding.expandRecipeOptionsButton.isChecked = false
        }


        return binding.root
    }

    private fun bind(recipe: Recipe, binding: FragmentExpandRecipeBinding) {
        this.recipe = recipe
        with(binding) {
            expandRecipeName.text = recipe.name
            expandRecipeAuthor.text = recipe.author
            expandRecipeCategory.text = TestTempRepository.getResourceText(recipe.category)
            expandRecipeFavoriteButton.isChecked = recipe.isFavorite


        }
    }
}