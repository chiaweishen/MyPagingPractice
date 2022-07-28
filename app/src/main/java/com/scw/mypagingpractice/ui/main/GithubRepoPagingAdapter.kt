package com.scw.mypagingpractice.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scw.mypagingpractice.databinding.ItemGithubRepoBinding
import com.scw.mypagingpractice.network.api.entity.Repo

class GithubRepoPagingAdapter :
    PagingDataAdapter<Repo, GithubRepoPagingAdapter.RepoViewHolder>(RepoComparator) {

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindData(getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.newInstance(parent)
    }

    class RepoViewHolder(private val binding: ItemGithubRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun newInstance(parent: ViewGroup): RepoViewHolder {
                val binding = ItemGithubRepoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return RepoViewHolder(binding)
            }
        }

        fun bindData(repo: Repo?, position: Int) {
            repo?.also {
                binding.order.text = "index: $position"
                binding.repoFullName.text = it.fullName
                binding.repoDescription.text = it.description
                binding.repoStars.text = "stars: ${it.stars}"
                binding.repoForks.text = "forks: ${it.forks}"
            }
        }
    }

    object RepoComparator : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }
    }
}