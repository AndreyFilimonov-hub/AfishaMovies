package com.filimonov.afishamovies.presentation.ui.profilepage.dialogfragment

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.DialogCreateCollectionBinding
import java.lang.RuntimeException

class CreateCollectionDialog(
    private val onCollectionCreated: (String) -> Unit
) : DialogFragment() {

    private var _binding: DialogCreateCollectionBinding? = null
    private val binding: DialogCreateCollectionBinding
        get() = _binding ?: throw RuntimeException("DialogCreateCollectionBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCreateCollectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btReady.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if (name.isNotEmpty()) {
                binding.etName.backgroundTintList = null
                onCollectionCreated(name)
                dismiss()
            } else {
                with(binding.etName) {
                    error = getString(R.string.empty_collection_name_error)
                }
            }
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}