package ru.saw47.recipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_checkbox.*
import ru.saw47.recipe.R
import ru.saw47.recipe.data.util.Category
import ru.saw47.recipe.databinding.FragmentCheckboxBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel


class CheckboxFragment : Fragment() {
    private val viewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCheckboxBinding.inflate(
            inflater, container, false
        )

        bind(binding)

        binding.topAppBarCheckBox.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cancel_edit_cb -> {
                    viewModel.skipCheckboxFilter()
                    findNavController().popBackStack()
                    true
                }
                R.id.save_edit_cb -> {
                    viewModel.filterOnCategoryClick()
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }

        with(binding) {
            checkboxAmerican.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }

            checkboxAsian.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }

            checkboxPanAsian.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }

            checkboxMediterranean.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }

            checkboxRussian.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }

            checkboxEastern.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }

            checkboxEuropean.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }

            checkboxOther.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
            }
        }
        return binding.root
    }

    private fun bind(binding: FragmentCheckboxBinding) {
        with(binding) {
            checkboxAmerican.isChecked =
                viewModel.checkboxSet.contains(Category.AMERICAN)
            checkboxAsian.isChecked =
                viewModel.checkboxSet.contains(Category.ASIAN)
            checkboxPanAsian.isChecked =
                viewModel.checkboxSet.contains(Category.PAN_ASIAN)
            checkboxMediterranean.isChecked =
                viewModel.checkboxSet.contains(Category.MEDITERRANEAN)
            checkboxRussian.isChecked =
                viewModel.checkboxSet.contains(Category.RUSSIAN)
            checkboxEastern.isChecked =
                viewModel.checkboxSet.contains(Category.EASTERN)
            checkboxEuropean.isChecked =
                viewModel.checkboxSet.contains(Category.EUROPEAN)
            checkboxOther.isChecked =
                viewModel.checkboxSet.contains(Category.OTHER)
        }
    }

    private fun lastCheckbox(binding: FragmentCheckboxBinding) {
        if (viewModel.checkboxSet.size < 1) {
            viewModel.skipCheckboxFilter()
            bind(binding)
        }
    }


    private fun onCheckboxClicked(view: View, checked: Boolean, binding: FragmentCheckboxBinding) {
        when (view) {
            checkbox_american -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.AMERICAN)
                } else {
                        viewModel.checkboxSet.add(Category.AMERICAN)
                }
            }
            checkbox_asian -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.ASIAN)
                } else {
                    viewModel.checkboxSet.add(Category.ASIAN)
                }
            }
            checkbox_mediterranean -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.MEDITERRANEAN)
                } else {
                    viewModel.checkboxSet.add(Category.MEDITERRANEAN)
                }
            }
            checkbox_eastern -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.EASTERN)
                } else {
                    viewModel.checkboxSet.add(Category.EASTERN)
                }
            }

            checkbox_other -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.OTHER)
                } else {
                    viewModel.checkboxSet.add(Category.OTHER)
                }
            }
            checkbox_european -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.EUROPEAN)
                } else {
                    viewModel.checkboxSet.add(Category.EUROPEAN)
                }
            }
            checkbox_pan_asian -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.PAN_ASIAN)
                } else {
                    viewModel.checkboxSet.add(Category.PAN_ASIAN)
                }
            }
            checkbox_russian -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.RUSSIAN)
                } else {
                    viewModel.checkboxSet.add(Category.RUSSIAN)
                }
            }
        }
        lastCheckbox(binding)
    }
}