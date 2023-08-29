package com.example.androidpractice.feature.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.feature.profile.databinding.FragmentProfileBinding
import com.example.androidpractice.core.designsystem.R as designR

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    R.id.profileNavigation,
    FragmentProfileBinding::inflate
) {
    override val viewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { photo ->
            photo?.let {
                binding.profilePhotoImageView.setImageBitmap(it)
            }
        }
    private val getPhoto =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let { uri ->
                requireContext().contentResolver.openInputStream(uri)?.use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)!!
                    binding.profilePhotoImageView.setImageBitmap(bitmap)
                }
            }
        }
    private val photoDialog = PhotoDialogFragment.newInstance()

    private val adapter by lazy {
        FriendsAdapter(viewModel.friends.value ?: listOf())
    }

    override fun injectViewModelFactory() {
        ViewModelProvider(this).get<ProfileComponentViewModel>().profileComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(PhotoDialogFragment.RESULT_KEY) { _: String, bundle: Bundle ->
            when (bundle.getString(PhotoDialogFragment.ACTION_KEY)) {
                PhotoDialogFragment.ACTION_CHOOSE -> {
                    getPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }

                PhotoDialogFragment.ACTION_TAKE -> {
                    takePhoto.launch(null)
                }

                PhotoDialogFragment.ACTION_DELETE -> {
                    binding.profilePhotoImageView.setImageResource(designR.drawable.ic_user_placeholder)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            friendRecyclerView.adapter = adapter
            profilePhotoImageView.setOnClickListener {
                photoDialog.show(parentFragmentManager, "PhotoDialogFragment")
            }
        }
        observe()
    }

    private fun observe() {
        viewModel.friends.observe(viewLifecycleOwner) { friends ->
            adapter.setFriends(friends)
        }
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
