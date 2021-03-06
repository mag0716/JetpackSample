package com.github.mag0716.bottomsheetdialog

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_top.*

class TopFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            BottomSheetNavigationView().show(childFragmentManager, BottomSheetNavigationView::class.java.canonicalName)
        }
    }

    class BottomSheetNavigationView : com.google.android.material.bottomsheet.BottomSheetDialogFragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val navigationView = inflater.inflate(R.layout.view_bottom_sheet, container, false) as com.google.android.material.navigation.NavigationView
            val mainActivity = requireActivity() as MainActivity
            navigationView.setupWithNavController(mainActivity.findNavController(R.id.containerMain))
            return navigationView
        }
    }
}