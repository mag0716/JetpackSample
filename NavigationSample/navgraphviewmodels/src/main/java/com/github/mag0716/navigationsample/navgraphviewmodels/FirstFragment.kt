package com.github.mag0716.navigationsample.navgraphviewmodels


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : androidx.fragment.app.Fragment() {

    val activityScopeViewModel: SampleViewModel by activityViewModels()
    val navGraphScopeViewModel: SampleViewModel by navGraphViewModels(R.id.navigation_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_move_to_second))
        countButton.setOnClickListener {
            activityScopeViewModel.increment()
            navGraphScopeViewModel.increment()
            updateText()
        }
        updateText()
    }

    private fun updateText() {
        text.text = "activity=${activityScopeViewModel.count} : navGraph=${navGraphScopeViewModel.count}"
    }
}
