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
import ru.saw47.recipe.data.util.Util
import ru.saw47.recipe.data.util.Util.getResourceText
import ru.saw47.recipe.databinding.FragmentExpandRecipeBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel
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
            val list = steps.filter { it.parentId == recipe.id }
            adapter.submitList(list)
            if (list.isEmpty()) {
                binding.dummy.visibility = View.VISIBLE
            } else {
                binding.dummy.visibility = View.GONE
            }
        }


        viewModel.editStep.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.action_expandRecipeFragment_to_editStepFragment)
            }
        }

        bind(recipe, binding)

        viewModel.upDownButtonStateStep.observe(viewLifecycleOwner) { state ->
            when(state) {
                false -> {
                    binding.goneDownStep.visibility = View.GONE
                    binding.goneUpStep.visibility = View.GONE
                }
                true -> {
                    binding.goneDownStep.visibility = View.VISIBLE
                    binding.goneUpStep.visibility = View.VISIBLE
                }
            }
        }

        binding.goneUpStep.setOnClickListener() {
            viewModel.moveStep(Util.MOVE_UP)
        }

        binding.goneDownStep.setOnClickListener() {
            viewModel.moveStep(Util.MOVE_DOWN)
        }

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
        this.recipe = recipe
        with(binding) {
            expandRecipeName.text = recipe.name
            expandRecipeAuthor.text = recipe.author
            expandRecipeCategory.text = getResourceText(recipe.category)
            expandRecipeFavoriteButton.isChecked = recipe.isFavorite
        }
    }
}