package com.aayar94.foodrecipes.ui.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.aayar94.foodrecipes.databinding.FragmentInstructionsBinding
import com.aayar94.foodrecipes.utils.Constants


class InstructionsFragment : Fragment() {
    private var mBinding: FragmentInstructionsBinding? = null
    private val binding get() = mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)
        val args = arguments
        val mBundle: com.aayar94.foodrecipes.model.Result? =
            args?.getParcelable(Constants.RECIPE_KEY_RESULT)


        binding.instrucionsWebView.webViewClient = object : WebViewClient() {}
        val mySource: String = mBundle!!.sourceUrl
        binding.instrucionsWebView.loadUrl(mySource)

        return binding.root
    }

}