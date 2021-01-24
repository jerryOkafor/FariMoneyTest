package com.jerryhanks.farimoneytest.ui.userDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jerryhanks.farimoneytest.di.Injectable
import com.google.android.material.snackbar.Snackbar
import com.jerryhanks.farimoneytest.R
import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.databinding.FragmentUserDetailsBinding
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class UserDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(UserDetailsViewModel::class.java)
    }

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: UserDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            resource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {

                        binding.tvDateOfBirth.text =
                            it.data?.dateOfBirth?.format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy"))

                        binding.tvRegDate.text =
                            it.data?.registerDate?.format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy"))

                        binding.tvPhone.text = it.data?.phone
                        val location = it.data?.location

                        val locationPlaceholder = getString(R.string.location_placeholder,
                            location?.street,
                            location?.city,
                            location?.city,
                            location?.state,
                            location?.country)
                        binding.tvLocation.text = locationPlaceholder

                        binding.progressBar.visibility = View.INVISIBLE
                        binding.otherDetails.visibility = View.VISIBLE
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.otherDetails.visibility = View.INVISIBLE
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.otherDetails.visibility = View.VISIBLE
                        Snackbar.make(binding.root, it.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewModel.getUserDetails(args.userId)

        Glide.with(this@UserDetailsFragment).load(args.picture)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(binding.ivDetailProfilePic)

        val fullName = getString(R.string.fullname_placeholder,args.title,args.firstName,args.lastName)

        binding.tvDetailName.text = fullName
        binding.tvDetailEmail.text = args.email

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = fullName
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}