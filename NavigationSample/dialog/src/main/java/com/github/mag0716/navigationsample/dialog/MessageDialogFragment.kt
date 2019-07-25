package com.github.mag0716.navigationsample.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class MessageDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        return builder
                .setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("OK") { _, _ ->
                    findNavController().navigate(R.id.action_messageDialog_to_subFragment)
                }
                .create()
    }
}