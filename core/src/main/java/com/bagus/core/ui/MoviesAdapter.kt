package com.bagus.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bagus.core.R
import com.bagus.core.databinding.ListItemMovieBinding
import com.bagus.core.domain.model.Movie
import com.bagus.core.utils.DiffUtils
import com.bumptech.glide.Glide

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        val diffUtilCallback = DiffUtils(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSwipedData(swipedPosition: Int): Movie = listData[swipedPosition]

    inner class MovieViewHolder(private val binding: ListItemMovieBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie) {
            with(binding) {
                tvTitle.text = movie.title
                tvDirector.text = movie.overview
                tvReleaseDate.text = movie.releaseDate

                ratingBar.numStars = 5
                ratingBar.stepSize = 0.5F
                ratingBar.rating = (movie.voteAverage / 2).toFloat()

                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.baseUrlImage, movie.posterPath))
                    .into(imgViewMovie)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val listItemMovieBinding =
            ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(listItemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}