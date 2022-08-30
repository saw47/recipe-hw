package ru.saw47.recipe.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.saw47.recipe.R
import ru.saw47.recipe.adapter.RecipeAdapter
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.databinding.FragmentContentMainBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel

class ContentMainFragment : Fragment() {
    private val viewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContentMainBinding.inflate(
            inflater, container, false
        )

        val adapter = RecipeAdapter(viewModel)
        binding.recipeRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        viewModel.editRecipe.observe(viewLifecycleOwner) {
            println("edit ${it.id}")
            findNavController().navigate(R.id.action_contentMainFragment_to_editRecipeFragment)
        }


        viewModel.expandRecipe.observe(viewLifecycleOwner) {
            println("expand ${it.id}")
            findNavController().navigate(R.id.action_contentMainFragment_to_expandRecipeFragment)
        }

        binding.mainSearchCardTextTop.addTextChangedListener {
            println("text search ${it}")
            viewModel.searchBarOnClick(it.toString())
        }

        binding.cancelSearchButtonTop.setOnClickListener() {
            viewModel.searchBarOnClick("")
            binding.mainSearchCardTextTop.clearFocus()
        }


        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter_menu_top -> {
                    findNavController().navigate(R.id.action_contentMainFragment_to_checkboxFragment)
                    true
                }
                R.id.add_menu_top -> {
                    viewModel.editRecipe.value = Recipe(
                            id = -1L,
                            author = "",
                            name = ""
                                )
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

    companion object{
        const val RECIPE = "recipe"
    }
}