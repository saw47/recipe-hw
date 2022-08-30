package ru.saw47.recipe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.saw47.recipe.R
import ru.saw47.recipe.adapter.RecipeStepsAdapter
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.impl.TestTempRepository
import ru.saw47.recipe.databinding.FragmentContentMainBinding
import ru.saw47.recipe.databinding.FragmentEditRecipeBinding
import ru.saw47.recipe.databinding.FragmentExpandRecipeBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel

class EditRecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels()
    lateinit var recipe: Recipe


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentEditRecipeBinding.inflate(
            inflater, container, false
        )

        recipe = viewModel.editRecipe.value!!

        val adapter = RecipeStepsAdapter(viewModel)
        binding.recipeStepsRecyclerview.adapter = adapter

        bind(recipe,binding)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cancel_edit_ed -> {
                    binding.editRecipeName.text.clear()
                    binding.editAuthorText.text.clear()
                    findNavController().popBackStack()
                    true
                }
                R.id.save_edit_ed -> {
                    viewModel.saveOnClick(recipe)
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }



        return binding.root
    }

    private fun bind(recipe: Recipe, binding: FragmentEditRecipeBinding) {
        this.recipe = recipe
        with(binding) {
            editRecipeName.setText(recipe.name)
            editAuthorText.setText(recipe.author)

        }
    }

}