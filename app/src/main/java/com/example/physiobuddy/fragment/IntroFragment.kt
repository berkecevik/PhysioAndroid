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
                findNavController().navigate(R.id.action_introFragment_to_permissionsFragment)
            }
        }

        return view
    }

    private fun getIntroItems(): List<IntroItem> {
        return listOf(
            IntroItem(R.drawable.intro1, "Welcome Laura", "Let me give you a quick tour...","This is your body. It is composed by parts unifidied by joints."),
            IntroItem(R.drawable.intro2, "My eyes are not as good as yours", "So instead of your handsome body i will actually see you like this...","This way i can understand every movement you make"),
            //IntroItem(R.drawable.intro3, "Pose Detection", "Real-time analysis of your movements."),
            //IntroItem(R.drawable.intro4, "Track Progress", "Visualize improvement over time."),
            //IntroItem(R.drawable.intro5, "Privacy First", "Your data stays on your device."),
            //IntroItem(R.drawable.intro6, "Let’s begin!", "Click below to get started!")
        )
    }
}
