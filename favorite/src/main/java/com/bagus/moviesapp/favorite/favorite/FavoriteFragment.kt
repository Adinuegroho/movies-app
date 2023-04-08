package com.bagus.moviesapp.favorite.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagus.core.data.Resource
import com.bagus.core.domain.model.Movie
import com.bagus.core.ui.MoviesAdapter
import com.bagus.core.utils.SortUtils
import com.bagus.moviesapp.detail.DetailActivity
import com.bagus.moviesapp.favorite.R
import com.bagus.moviesapp.favorite.R.string
import com.bagus.moviesapp.favorite.databinding.FragmentFavoriteBinding
import com.bagus.moviesapp.favorite.di.favoriteModule
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.util.*
import kotlin.concurrent.schedule

class FavoriteFragment : Fragment() {

    private var _fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = _fragmentFavoriteBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFavoriteBinding =
            FragmentFavoriteBinding.inflate(inflater, container, false)
        loadKoinModules(favoriteModule)
        return binding.root
    }

    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: FavoriteViewModel by viewModel()
    private val sort = SortUtils.RANDOM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    override fun onResume() {
        super.onResume()
        setList(sort)
    }

    override fun onStop() {
        super.onStop()
        unloadKoinModules(favoriteModule)
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFavoriteBinding = null
    }

    private fun setUpView(){
        itemTouchHelper.attachToRecyclerView(binding.rvFavorite)

        moviesAdapter = MoviesAdapter()

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = moviesAdapter
        }

        moviesAdapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
    }


    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipePosition = viewHolder.adapterPosition
                val movie = moviesAdapter.getSwipedData(swipePosition)
                var state = movie.favorite
                viewModel.setFavorite(movie, !state)
                state = !state
                val snackbar =
                    Snackbar.make(view as View, string.message_confirm, Snackbar.LENGTH_SHORT)
                snackbar.setAction(string.message_undo) {
                    viewModel.setFavorite(movie, !state)
                }
                snackbar.show()
            }
        }
    })

    private fun setList(sort: String) {
        viewModel.getFavoriteMovies(sort).observe(viewLifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<List<Movie>> { movies ->
        if (movies.isNullOrEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            moviesAdapter.setData(movies)
        }
    }
}