package com.bagus.core.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.bagus.core.domain.model.Movie

class DiffUtils(private val oldList: List<Movie>, private val newList: List<Movie>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (overview,
            originalLanguage,
            releaseDate,
            popularity,
            voteAverage,
            _,
            title,
            voteCount,
            posterPath,
            backdropPath,
            favorite,
            isTvShows) = oldList[oldItemPosition]
        val (overview1,
            originalLanguage1,
            releaseDate1,
            popularity1,
            voteAverage1,
            _,
            title1,
            voteCount1,
            posterPath1,
            backdropPath1,
            favorite1,
            isTvShows1) = newList[newItemPosition]

        return overview == overview1
                && originalLanguage == originalLanguage1
                && releaseDate == releaseDate1
                && popularity == popularity1
                && voteAverage == voteAverage1
                && title == title1
                && voteCount == voteCount1
                && posterPath == posterPath1
                && backdropPath == backdropPath1
                && favorite == favorite1
                && isTvShows == isTvShows1
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}