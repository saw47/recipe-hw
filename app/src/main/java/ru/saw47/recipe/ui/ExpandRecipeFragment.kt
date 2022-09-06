package ru.saw47.recipe.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.saw47.recipe.R
import ru.saw47.recipe.adapter.RecipeStepsAdapter
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.databinding.FragmentExpandRecipeBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel
import ru.saw47.recipe.viewmodel.RecipeViewModel.Companion.getResourceText
import java.lang.Exception

class ExpandRecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels()
    lateinit var recipe: Recipe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentExpandRecipeBinding.inflate(inflater, container, false)
        val context: Context = this.context ?: throw Exception("no context^")

        recipe = viewModel.expandRecipe.value!!
        val adapter = RecipeStepsAdapter(viewModel)
        binding.recipeStepsRecyclerview.adapter = adapter

        viewModel.stepData.observe(viewLifecycleOwner) { steps ->
            adapter.submitList(steps.filter { it.parentId == recipe.id })
        }


        viewModel.editStep.observe(viewLifecycleOwner) {
            println("edit stepId ${it?.stepId} from recipe ${it?.parentId}")
            if (it != null) {
                println("editStep ${it.stepId}")
                findNavController().navigate(R.id.action_expandRecipeFragment_to_editStepFragment)
            } else println("editStep NULL")
        }



        bind(recipe, binding)

        val menu = PopupMenu(context, binding.expandRecipeOptionsButton)
        menu.apply {
            inflate(R.menu.menu_recipe_expand)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_recipe_action -> {
                        viewModel.editOnClick(recipe)
                        findNavController()
                            .navigate(R.id.action_expandRecipeFragment_to_editRecipeFragment)
                        true
                    }
                    R.id.delete_recipe_action -> {
                        viewModel.deleteOnClick(recipe)
                        viewModel.clearEditRecipeValue()
                        findNavController()
                            .navigate(R.id.action_expandRecipeFragment_to_contentMainFragment)
                        true
                    }
                    R.id.add_step_recipe_action -> {
                        viewModel.addNewStepOnClick(recipe)
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

        binding.expandRecipeFavoriteButton.setOnClickListener {
            viewModel.favoriteOnClick(recipe)
            recipe = recipe.copy(
                isFavorite = !recipe.isFavorite
            )
            bind(recipe, binding)
        }

        return binding.root
    }

    private fun bind(recipe: Recipe, binding: FragmentExpandRecipeBinding) {
        //this.recipe = recipe
        with(binding) {
            expandRecipeName.text = recipe.name
            expandRecipeAuthor.text = recipe.author
            expandRecipeCategory.text = getResourceText(recipe.category)
            expandRecipeFavoriteButton.isChecked = recipe.isFavorite
        }
    }
}