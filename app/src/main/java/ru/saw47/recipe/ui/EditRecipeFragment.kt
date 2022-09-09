package ru.saw47.recipe.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.saw47.recipe.R
import ru.saw47.recipe.adapter.RecipeStepsAdapter
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.util.Util
import ru.saw47.recipe.databinding.FragmentEditRecipeBinding
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
        val context: Context = this.context ?: throw Exception("no context^")

        recipe = viewModel.editRecipe.value!!

        val adapter = RecipeStepsAdapter(viewModel)
        binding.recipeEditStepsRecyclerview.adapter = adapter

        viewModel.stepData.observe(viewLifecycleOwner) { steps ->
            adapter.submitList(steps.filter { it.parentId == recipe.id })
        }

        //spinner>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        val spinner: Spinner = binding.categorySpinner
        var choiceUser: String? = null

        val spinnerAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.category_spinner,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.category_spinner)
                choiceUser = choose[selectedItemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.setSelection(Util.fullCheckBox.indexOf(recipe.category))

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        bind(recipe, binding)

        viewModel.editStep.observe(viewLifecycleOwner) {
            println("edit stepId ${it?.stepId} from recipe ${it?.parentId}")
            if (it != null) {
                println("editStep ${it.stepId}")
                findNavController().navigate(R.id.action_editRecipeFragment_to_editStepFragment)
            } else println("editStep NULL")
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cancel_edit_ed -> {
                    binding.editRecipeName.text.clear()
                    binding.editAuthorText.text.clear()
                    viewModel.clearEditRecipeValue()
                    findNavController().popBackStack()
                    true
                }
                R.id.save_edit_ed -> {
                    recipe = recipe.copy(
                        name = binding.editRecipeName.text.toString(),
                        author = binding.editAuthorText.text.toString(),
                        category = choiceUser?.let { Util.getCategory(it) } ?: recipe.category
                    )
                    viewModel.saveOnClick(recipe)
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }

        binding.expandAddStepFab.setOnClickListener() {
            viewModel.addNewStepOnClick(recipe)
            println("addNewStepOnClick in recipe ${recipe.id}")
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