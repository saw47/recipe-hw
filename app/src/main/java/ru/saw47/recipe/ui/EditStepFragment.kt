package ru.saw47.recipe.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.saw47.recipe.R
import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.util.hideKeyboard
import ru.saw47.recipe.databinding.FragmentEditStepBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel

class EditStepFragment : Fragment() {
    private val viewModel: RecipeViewModel by activityViewModels()
    lateinit var step: Step

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val context: Context = this.context ?: throw Exception("no context")
        val binding = FragmentEditStepBinding.inflate(inflater, container, false)
        step = viewModel.editStep.value!!

        binding.editStepDescription.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.imageFrame.visibility = View.GONE
            } else {
                binding.imageFrame.visibility = View.VISIBLE
            }
        }

        binding.editStepParentConstraintLayout.setOnClickListener {
            binding.editStepDescription.clearFocus()
            it.hideKeyboard()
        }

        binding.deleteImageButton.setOnClickListener() {
            viewModel.deleteStepImageOnClick(step)
            Toast.makeText(context, "А это мы пока не умеем", Toast.LENGTH_LONG)
                .show()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cancel_edit_ed_st -> {
                    if(binding.editStepDescription.isFocused) {
                        binding.editStepDescription.clearFocus()
                        view?.hideKeyboard()
                    } else {
                        viewModel.cancelEditStepOnClick()
                        findNavController().popBackStack()
                    }
                    true
                }
                R.id.save_edit_ed_st -> {
                    if (binding.editStepDescription.text.toString().isNotBlank()) {
                        step = step.copy(
                            description = binding.editStepDescription.text.toString()
                        )
                        viewModel.saveStepOnClick(step)
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(context, "Пустой шаг не сохранить", Toast.LENGTH_LONG)
                            .show()
                    }
                    true
                }
                else -> false
            }
        }

        bind(step, binding)
        return binding.root
    }

    private fun bind(step: Step, binding: FragmentEditStepBinding) {
        binding.editStepDescription.setText(step.description)
        if (step.imageUri != null) {
            /*здесь не реализована загрузка картинки*/
        }
    }
}