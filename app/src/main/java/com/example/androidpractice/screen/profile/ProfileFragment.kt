package com.example.androidpractice.screen.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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

    private val viewModel: ProfileViewModel by viewModels()

    private val adapter by lazy {
        FriendsAdapter(viewModel.friends.value ?: listOf())
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
                    binding.profilePhotoImageView.setImageResource(R.drawable.ic_user_placeholder)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            friendRecyclerView.adapter = adapter
            profilePhotoImageView.setOnClickListener {
                PhotoDialogFragment.newInstance()
                    .show(parentFragmentManager, "PhotoDialogFragment")
            }
        }
        observe()
    }

    private fun observe() {
        viewModel.friends.observe(viewLifecycleOwner) { friends ->
            adapter.setFriends(friends)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
