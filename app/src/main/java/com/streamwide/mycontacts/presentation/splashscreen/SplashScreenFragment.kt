package com.streamwide.mycontacts.presentation.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.streamwide.mycontacts.R


class SplashScreenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigateToHomeScreen(delay = 2000)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun navigateToHomeScreen(delay: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            view?.post {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, delay)
    }

}