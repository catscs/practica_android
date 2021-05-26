package io.keepcoding.eh_ho.posts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import io.keepcoding.eh_ho.databinding.ActivityPostsBinding
import io.keepcoding.eh_ho.di.DIProvider
import kotlin.properties.Delegates


class PostsActivity : AppCompatActivity() {

    private val binding: ActivityPostsBinding by lazy { ActivityPostsBinding.inflate(layoutInflater) }
    private val postsAdapter = PostsAdapter()
    private val vm: PostsViewModel by viewModels { DIProvider.postsViewModelProviderFactory }
    private val topicId: Int by lazy { intent.extras?.getInt(TOPIC_ID_EXTRA)?:-1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (topicId == -1) { finish() }


        binding.posts.apply {
            adapter = postsAdapter
            addItemDecoration(DividerItemDecoration(this@PostsActivity, LinearLayout.VERTICAL))
        }
        vm.state.observe(this) {
            when (it) {
                is PostsViewModel.State.LoadingPosts -> renderLoading(it)
                is PostsViewModel.State.PostsReceived -> {
                    postsAdapter.submitList(it.posts)
                    binding.swipeRefresh.isRefreshing = false
                }
                is PostsViewModel.State.NoPosts -> renderEmptyState()
            }
        }



        binding.swipeRefresh.setOnRefreshListener {
            vm.loadPosts(topicId)
        }
    }

    override fun onResume() {
        super.onResume()
        vm.loadPosts(topicId)
    }

    private fun renderEmptyState() {
        // Render empty state
    }

    private fun renderLoading(loadingState: PostsViewModel.State.LoadingPosts) {
        (loadingState as? PostsViewModel.State.LoadingPosts.LoadingWithPosts)?.let { postsAdapter.submitList(it.posts) }
    }

    companion object {
        const val TOPIC_ID_EXTRA = "TOPIC_ID_EXTRA"
        @JvmStatic
        fun createIntent(context: Context): Intent = Intent(context, PostsActivity::class.java)
    }
}
