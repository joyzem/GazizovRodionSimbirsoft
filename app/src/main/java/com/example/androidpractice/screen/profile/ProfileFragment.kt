package com.example.androidpractice.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), PhotoDialogFragment.PhotoDialogListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val getPhoto =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { photo ->
            photo?.let {
                binding.profilePhotoImageView.setImageBitmap(it)
            }
        }

    private val viewModel: ProfileViewModel by viewModels()

    private val adapter by lazy {
        FriendsAdapter(viewModel.friends.value ?: listOf())
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
                PhotoDialogFragment.newInstance(this@ProfileFragment)
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

    override fun onChoosePhotoClicked(dialog: DialogFragment) {
        dialog.dismiss()
    }

    override fun onTakePhotoClicked(dialog: DialogFragment) {
        getPhoto.launch(null)
        dialog.dismiss()
    }

    override fun onDeletePhotoClicked(dialog: DialogFragment) {
        binding.profilePhotoImageView.setImageResource(R.drawable.ic_user_placeholder)
        dialog.dismiss()
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
