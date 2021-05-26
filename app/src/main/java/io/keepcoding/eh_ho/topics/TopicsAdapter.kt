package io.keepcoding.eh_ho.topics

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.databinding.ViewTopicBinding
import io.keepcoding.eh_ho.extensions.inflater
import io.keepcoding.eh_ho.model.Topic

class TopicsAdapter(diffUtilItemCallback: DiffUtil.ItemCallback<Topic> = DIFF, private val clickListener: (Int) -> Unit) :
    ListAdapter<Topic, TopicsAdapter.TopicViewHolder>(diffUtilItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder =
        TopicViewHolder(parent)

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic  = getItem(position)
        holder.bind(topic)
        holder.itemView.setOnClickListener { clickListener(topic.id) }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean = oldItem == newItem
        }
    }

    class TopicViewHolder(
        parent: ViewGroup,
        private val binding: ViewTopicBinding = ViewTopicBinding.inflate(
            parent.inflater,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Topic) {
            binding.title.text = topic.title
            binding.lastPosterUsername.text = topic.lastPosterUsername
            binding.likeCount.text = topic.likeCount.toString()
            binding.postCount.text = topic.postCount.toString()
        }
    }
}
