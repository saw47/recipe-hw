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
import ru.saw47.recipe.data.Category
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
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }

            checkboxAsian.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }

            checkboxPanAsian.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }

            checkboxMediterranean.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }

            checkboxRussian.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }

            checkboxEastern.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }

            checkboxEuropean.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }

            checkboxOther.setOnCheckedChangeListener { view, checked ->
                onCheckboxClicked(view, checked, binding)
                println(viewModel.checkboxSet!!.size)  //SERVICE!!!!!
            }
        }

        return binding.root
    }

    private fun bind(binding: FragmentCheckboxBinding) {
        with(binding) {
            checkboxAmerican.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.AMERICAN)
            checkboxAsian.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.ASIAN)
            checkboxPanAsian.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.PAN_ASIAN)
            checkboxMediterranean.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.MEDITERRANEAN)
            checkboxRussian.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.RUSSIAN)
            checkboxEastern.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.EASTERN)
            checkboxEuropean.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.EUROPEAN)
            checkboxOther.isChecked =
                viewModel.checkboxSet.contains(ru.saw47.recipe.data.Category.OTHER)
        }
        println("BIND" + viewModel.checkboxSet.size)  //SERVICE!!!!!
    }

    fun lastCheckbox(binding: FragmentCheckboxBinding) {
        if (viewModel.checkboxSet.size < 1) {
            viewModel.skipCheckboxFilter()
            bind(binding)
        }
    }


    fun onCheckboxClicked(view: View, checked: Boolean, binding: FragmentCheckboxBinding) {
        when (view) {
            checkbox_american -> {
                if (!checked) {
                    viewModel.checkboxSet.remove(Category.AMERICAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet.size}")  //SERVICE!!!!!

                } else {
                        viewModel.checkboxSet.add(Category.AMERICAN)
                        println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }
            checkbox_asian -> {
                if (!checked) {
                    viewModel.checkboxSet!!.remove(Category.ASIAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!

                } else {
                    viewModel.checkboxSet!!.add(Category.ASIAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }
            checkbox_mediterranean -> {
                if (!checked) {
                    viewModel.checkboxSet!!.remove(Category.MEDITERRANEAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!

                } else {
                    viewModel.checkboxSet!!.add(Category.MEDITERRANEAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }
            checkbox_eastern -> {
                if (!checked) {
                    viewModel.checkboxSet!!.remove(Category.EASTERN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!

                } else {
                    viewModel.checkboxSet!!.add(Category.EASTERN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }

            checkbox_other -> {
                if (!checked) {
                    viewModel.checkboxSet!!.remove(Category.OTHER)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!

                } else {
                    viewModel.checkboxSet!!.add(Category.OTHER)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }

            checkbox_european -> {
                if (!checked) {
                    viewModel.checkboxSet!!.remove(Category.EUROPEAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!

                } else {
                    viewModel.checkboxSet!!.add(Category.EUROPEAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }

            checkbox_pan_asian -> {
                if (!checked) {
                    viewModel.checkboxSet!!.remove(Category.PAN_ASIAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!

                } else {
                    viewModel.checkboxSet!!.add(Category.PAN_ASIAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }

            checkbox_russian -> {
                if (!checked) {
                    viewModel.checkboxSet!!.remove(Category.RUSSIAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!

                } else {
                    viewModel.checkboxSet!!.add(Category.RUSSIAN)
                    println("${view.javaClass.name} --> $checked + ${viewModel.checkboxSet!!.size}")  //SERVICE!!!!!
                }
            }
        }

        lastCheckbox(binding)

    }


}