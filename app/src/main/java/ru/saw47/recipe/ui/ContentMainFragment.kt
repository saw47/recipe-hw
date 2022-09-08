package ru.saw47.recipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_content_main.*
import ru.saw47.recipe.R
import ru.saw47.recipe.adapter.RecipeAdapter
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.util.hideKeyboard
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
        viewModel.recipeData.observe(viewLifecycleOwner) {recipes ->
            adapter.submitList(recipes)
        }


        if (viewModel.favoriteIndex.value != true) {
            binding.tabLayoutMain.selectTab(binding.tabLayoutMain.getTabAt(0))
        } else {
            binding.tabLayoutMain.selectTab(binding.tabLayoutMain.getTabAt(1))
        }

        viewModel.editRecipe.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.action_contentMainFragment_to_editRecipeFragment)
            }
        }

        viewModel.expandRecipe.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_contentMainFragment_to_expandRecipeFragment)
        }

        binding.mainSearchCardTextTop.addTextChangedListener {
            viewModel.searchBarOnClick(it.toString())

        }

        binding.cancelSearchButtonTop.setOnClickListener() {
            viewModel.searchBarOnClick(null)
            binding.mainSearchCardTextTop.text!!.clear()
            binding.mainSearchCardTextTop.hideKeyboard()

        }

        binding.tabLayoutMain.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewModel.tabBarItemFavoriteClick(tab.position)
                    }
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            }
        )


        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter_menu_top -> {
                    findNavController().navigate(R.id.action_contentMainFragment_to_checkboxFragment)
                    true
                }
                R.id.add_menu_top -> {
                    viewModel.addNewOnClick()
                    true
                }
                else -> false
            }
        }


        return binding.root
    }
}