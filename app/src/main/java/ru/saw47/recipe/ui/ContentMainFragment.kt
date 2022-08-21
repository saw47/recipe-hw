package ru.saw47.recipe.ui

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.saw47.recipe.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.*
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.databinding.ActivityMainBinding
import ru.saw47.recipe.viewmodel.RecipeViewModel

class ContentMainFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = ActivityMainBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }


        viewModel.urlContent.observe(viewLifecycleOwner) { video ->
            if (video.isNullOrBlank()) return@observe
            val intent = Intent().apply {
                action = ACTION_VIEW
                data = Uri.parse(video)
            }
            val videoIntent = Intent.createChooser(intent, null)
            startActivity(videoIntent)
        }

        viewModel.editPost.observe(viewLifecycleOwner) { post ->

            if (post.content.isNullOrBlank() && post.video.isNullOrBlank()) {
                return@observe
            } else {
                val direction = FeedFragmentDirections
                    .actionFeedFragmentToPostContentFragment(post.content, post.video)
                findNavController().navigate(direction)
            }
        }

        binding.fab.setOnClickListener() {
            val direction = FeedFragmentDirections
                .actionFeedFragmentToPostContentFragment(null, null)
            findNavController().navigate(direction)
        }

        viewModel.expandPost.observe(viewLifecycleOwner) { post ->
            val bundle = Bundle()
            val serializablePost = gson.toJson(post)
            bundle.putString(POST, serializablePost)
            findNavController().navigate(
                R.id.action_feedFragment_to_postFullSizeFragment,
                bundle
            )
        }

        return binding.root
    }

    companion object {
        const val POST = "post"
    }
}