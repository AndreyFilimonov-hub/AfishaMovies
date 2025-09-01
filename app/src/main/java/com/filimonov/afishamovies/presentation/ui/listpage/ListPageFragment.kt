package com.filimonov.afishamovies.presentation.ui.listpage

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Slide
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentListPageBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.homepage.Media
import com.filimonov.afishamovies.presentation.ui.homepage.MediaHorizontalAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentListPageBinding? = null

    private val binding: FragmentListPageBinding
        get() = _binding ?: throw RuntimeException("FragmentListPageBinding == null")

    private val mediaHorizontalAdapter = MediaHorizontalAdapter("", {}, {
        Log.d("AAA", "${it.id}")
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        enterTransition = Slide(Gravity.END).apply {
            duration = 500L
            interpolator = AccelerateInterpolator()
            propagation = null
        }
        exitTransition = Slide(Gravity.START).apply {
            duration = 500L
            interpolator = AccelerateInterpolator()
            propagation = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPaddingRootView()
        setToolbar()
        binding.rvContent.layoutManager = GridLayoutManager(this.context, 2)
        binding.rvContent.adapter = mediaHorizontalAdapter
        binding.rvContent.addItemDecoration(
            SpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_start60dp),
                resources.getDimensionPixelSize(R.dimen.margin_end60dp),
                resources.getDimensionPixelSize(R.dimen.margin_between16dp),
                resources.getDimensionPixelSize(R.dimen.margin_bottom16dp),
            )
        )
        mediaHorizontalAdapter.submitList(listOf(
            Media.MediaBanner(MediaBannerEntity(1, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(2, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(3, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
            Media.MediaBanner(MediaBannerEntity(4, "aaa", "aaa", 2.0, null)),
        ))
    }

    private fun setToolbar() {
        binding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setPaddingRootView() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bNav)
        val rootView = binding.root

        bottomNavigationView.post {
            val bottomHeight = bottomNavigationView.height

            val layoutParams = rootView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = bottomHeight
            rootView.layoutParams = layoutParams
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}