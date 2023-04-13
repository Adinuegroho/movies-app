package com.bagus.moviesapp.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagus.core.data.Resource
import com.bagus.core.domain.model.Movie
import com.bagus.core.ui.MoviesAdapter
import com.bagus.core.utils.SortUtils
import com.bagus.moviesapp.R
import com.bagus.moviesapp.databinding.FragmentMoviesBinding
import com.bagus.moviesapp.detail.DetailActivity
import com.bagus.moviesapp.home.HomeActivity
import com.bagus.moviesapp.home.SearchViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = activity?.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as HomeActivity).findViewById(R.id.searchView)
        return binding.root
    }

    private lateinit var searchView: MaterialSearchView
    private lateinit var moviesAdapter: MoviesAdapter
    private val moviesViewModel: MoviesViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()
    private var sort = SortUtils.NEWEST

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeSearchQuery()
        setSearchList()
    }

    private fun setUpView() {
        moviesAdapter = MoviesAdapter()
        setList(sort)
        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        moviesAdapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
    }

    private fun setList(sort: String) {
        moviesViewModel.getMovies(sort).observe(viewLifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.GONE
                    moviesAdapter.setData(movies.data!!)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setSearchList() {
        searchViewModel.movieResult.observe(viewLifecycleOwner) { movies ->
            if (movies.isNullOrEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.GONE
            }
            moviesAdapter.setData(movies)
        }
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener{
            override fun onSearchViewShown() {
                binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.GONE
            }

            override fun onSearchViewClosed() {
                binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.GONE
                setList(sort)
            }
        })
    }

    private fun observeSearchQuery() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchViewModel.setSearchQuery(it)
                }
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.search_button)
        searchView.setMenuItem(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}