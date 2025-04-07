package com.example.physiobuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2

class IntroFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var nextButton: Button
    private lateinit var introAdapter: IntroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        nextButton = view.findViewById(R.id.buttonNext)

        introAdapter = IntroAdapter(getIntroItems())
        viewPager.adapter = introAdapter

        nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < introAdapter.itemCount - 1) {
                viewPager.currentItem = currentItem + 1
            } else {
                findNavController().navigate(R.id.action_introFragment_to_loginFragment)
            }
        }

        return view
    }

    private fun getIntroItems(): List<IntroItem> {
        val context = requireContext()
        return listOf(
            IntroItem(R.drawable.intro1, context.getString(R.string.intro1_title), context.getString(R.string.intro1_subtitle), context.getString(R.string.intro1_description)),
            IntroItem(R.drawable.intro2, context.getString(R.string.intro2_title), context.getString(R.string.intro2_subtitle), context.getString(R.string.intro2_description)),
            IntroItem(R.drawable.intro3, context.getString(R.string.intro3_title), context.getString(R.string.intro3_subtitle), context.getString(R.string.intro3_description)),
            IntroItem(R.drawable.intro4, context.getString(R.string.intro4_title), context.getString(R.string.intro4_subtitle), context.getString(R.string.intro4_description)),
            IntroItem(R.drawable.intro5, context.getString(R.string.intro5_title), context.getString(R.string.intro5_subtitle), context.getString(R.string.intro5_description)),
            IntroItem(R.drawable.intro6, context.getString(R.string.intro6_title), context.getString(R.string.intro6_subtitle), context.getString(R.string.intro6_description))
        )
    }

}
