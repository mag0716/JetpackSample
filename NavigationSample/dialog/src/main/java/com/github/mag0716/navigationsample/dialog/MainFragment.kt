package com.github.mag0716.navigationsample.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        button.setOnClickListener(
//                Navigation.createNavigateOnClickListener(
//                        R.id.action_mainFragment_to_secondActivity,
//                        bundleOf(SecondActivity.DATA to "move from MainFragment")
//                )
//        )
    }
}
