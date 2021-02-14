package com.example.airlift.ui.main

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.airlift.R
import com.example.airlift.apiservices.Status
import com.example.airlift.databinding.MainFragmentBinding
import com.example.airlift.di.Injection
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainFragment : Fragment(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: ImagesAdapter
    private var searchJob: Job? = null

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MainFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        search(null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_options, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.action_search)

        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_move_to_top -> {
                binding.rvMoviesList.smoothScrollToPosition(0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setAdapter() {
        adapter = ImagesAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToImageDetailsFragment(
                    it
                )
            )
        }
        binding.rvMoviesList.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            when (loadState.append) {
                // more data loading and adding at bottom
                is LoadState.Loading -> {
                    viewModel.moreDataLoading.value = Status.LOADING
                }
                is LoadState.Error -> {
                    viewModel.moreDataLoading.value = Status.ERROR
                }
                is LoadState.NotLoading -> {
                    viewModel.moreDataLoading.value = Status.SUCCESS
                }
            }
            when (loadState.refresh) {
                // refresh or initial data loading state
                is LoadState.Loading -> {
                    viewModel.initialDataLoading.value = Status.LOADING
                }
                is LoadState.Error -> {
                    viewModel.initialDataLoading.value = Status.ERROR
                }
                is LoadState.NotLoading -> {
                    if (adapter.itemCount != 0) {
                        viewModel.initialDataLoading.value = Status.SUCCESS
                    } else {
                        viewModel.initialDataLoading.value = Status.NO_DATA_FOUND
                    }

                }
            }
        }
    }

    private fun search(query: String?) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { search(it) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onClose(): Boolean {
        search(null)
        return false
    }

}