package com.bagus.moviesapp.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.bagus.core.domain.model.Movie
import com.bagus.moviesapp.R
import com.bagus.moviesapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMovie = intent.getParcelableExtra<Movie>(EXTRA_DATA)
        if (detailMovie != null) {
            populateDetail(detailMovie)
        }

        binding.btnShare.setOnClickListener { share() }
    }

    private fun populateDetail(movie: Movie) {
        with(binding) {
            collapseActionView.title = movie.title
            collapseActionView.setCollapsedTitleTextColor(getColor(R.color.white))
            collapseActionView.setExpandedTitleColor(getColor(R.color.white))
            synopsis.text = movie.overview
            Glide.with(this@DetailActivity)
                .load(getString(R.string.baseUrlImage, movie.posterPath))
                .into(imgBackdrop)

            Glide.with(this@DetailActivity)
                .load(getString(R.string.baseUrlImage, movie.posterPath))
                .into(imgPoster)

            var favoriteState = movie.favorite
            setFavorite(favoriteState)
            binding.btnFavorite.setOnClickListener {
                favoriteState = !favoriteState
                viewModel.setFavoriteMovie(movie, favoriteState)
                setFavorite(favoriteState)
                setToast(favoriteState)
            }
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state) {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_true
                )
            )
        } else {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_false
                )
            )
        }
    }

    private fun setToast(state: Boolean) {
        if (state) {
            Toast.makeText(this, "Ditambahkan ke Daftar Favorite", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Dihapus dari Daftar Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun share() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder.from(this).apply {
            setType(mimeType)
            setChooserTitle(getString(R.string.shareTitle))
            setText(getString(R.string.shareText))
            startChooser()
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}