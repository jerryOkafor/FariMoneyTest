package com.jerryhanks.farimoneytest.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jerryhanks.farimoneytest.di.Injectable
import com.google.android.material.snackbar.Snackbar
import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.databinding.FragmentUsersBinding
import timber.log.Timber
import javax.inject.Inject

class UsersFragment : Fragment(), Injectable {

    @Inject
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(UsersViewModel::class.java)
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var usersAdapter: UsersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpSwipeToRefresh()

        with(viewModel) {
            result.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        binding.swipeContainer.isRefreshing = false
                        binding.recyclerView.visibility = View.VISIBLE
                        usersAdapter.submitList(it.data)
                    }

                    is Resource.Loading -> {
                        binding.swipeContainer.isRefreshing = true
                        binding.recyclerView.visibility = View.INVISIBLE
                    }
                    is Resource.Error -> {
                        binding.swipeContainer.isRefreshing = false
                        binding.recyclerView.visibility = View.VISIBLE
                        Snackbar.make(
                            binding.root,
                            "Error loading users: ${it.message}, please try again.",
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }

            getUsers()
        }
    }


    private fun setUpRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        usersAdapter = UsersAdapter(requireContext()) { user, imageView, tvName, tvEmail ->
            val extras = FragmentNavigatorExtras(
                imageView to "ivDetailProfilePic",
                tvName to "tvDetailName",
                tvEmail to "tvDetailEmail"
            )
            val action =
                UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(
                    userId = user.id,
                    email = user.email,
                    picture = user.picture,
                    title = user.title,
                    firstName = user.firstName,
                    lastName = user.lastName
                )
            findNavController().navigate(action, extras)
        }

        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = usersAdapter
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeContainer.setOnRefreshListener {
            usersAdapter.submitList(null)
            viewModel.getUsers(forceReload = true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}