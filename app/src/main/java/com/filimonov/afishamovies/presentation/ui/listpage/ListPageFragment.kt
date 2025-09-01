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
import com.filimonov.afishamovies.presentation.ui.homepage.MediaSection
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val CATEGORY_ID = "category_id"
private const val TITLE = "title"

class ListPageFragment : Fragment() {

    private var _binding: FragmentListPageBinding? = null

    private val binding: FragmentListPageBinding
        get() = _binding ?: throw RuntimeException("FragmentListPageBinding == null")

    private var categoryId: Int = UNDEFINED_ID
    private var title: String = UNDEFINED_TITLE

    private val mediaHorizontalAdapter = MediaHorizontalAdapter(MediaSection(0, "", listOf()), {}, {
        Log.d("AAA", "${it.id}")
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(CATEGORY_ID)) {
            throw RuntimeException("Param category id is absent")
        }
        val categoryIdBundle = args.getInt(CATEGORY_ID)
        if (categoryIdBundle < 0) {
            throw RuntimeException("Category ID is wrong")
        }
        categoryId = categoryIdBundle

        if (!args.containsKey(TITLE)) {
            throw RuntimeException("Param title id is absent")
        }
        val titleBundle = args.getString(TITLE) ?: UNDEFINED_TITLE
        if (titleBundle.isEmpty()) {
            throw RuntimeException("Param title is empty")
        }
        title = titleBundle
    }

    companion object {
        private const val UNDEFINED_ID = -1
        private const val UNDEFINED_TITLE = ""

        @JvmStatic
        fun newInstance(categoryId: Int, title: String) =
            ListPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY_ID, categoryId)
                    putString(TITLE, title)
                }
            }
    }
}