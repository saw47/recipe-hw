package ru.saw47.recipe.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_content_main.*
import ru.saw47.recipe.R
import ru.saw47.recipe.adapter.RecipeAdapter
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

        val adapter = RecipeAdapter(viewModel, viewModel)
        binding.recipeRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

//        viewModel.favoriteIndex.observe(viewLifecycleOwner) {
//            val tabAll = tabLayoutMain.getTabAt(0)
//            val tabFavorite = tabLayoutMain.getTabAt(1)
//            if (it) {
//                binding.tabLayoutMain.selectTab(tabFavorite)
//                println("tabFavorite")
//            } else {
//                binding.tabLayoutMain.selectTab(tabAll)
//                println("tabAll")
//            }
//        }

        viewModel.editRecipe.observe(viewLifecycleOwner) {
            if (it != null) {
                println("edit ${it.id}")
                findNavController().navigate(R.id.action_contentMainFragment_to_editRecipeFragment)
            } else println("edit NULL")
        }

        viewModel.expandRecipe.observe(viewLifecycleOwner) {
            println("expand ${it.id}")
            findNavController().navigate(R.id.action_contentMainFragment_to_expandRecipeFragment)
        }

        binding.mainSearchCardTextTop.addTextChangedListener {
            println("text search $it")
            viewModel.searchBarOnClick(it.toString())
        }

        binding.cancelSearchButtonTop.setOnClickListener() {
            viewModel.searchBarOnClick("")
            binding.mainSearchCardTextTop.text!!.clear()
            binding.mainSearchCardTextTop.hideKeyboard()
        }

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

        binding.tabLayoutMain.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewModel.tabBarItemClick(tab.position)
                    }
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            }
        )
        return binding.root
    }

    companion object {
        const val RECIPE = "recipe"
    }
}