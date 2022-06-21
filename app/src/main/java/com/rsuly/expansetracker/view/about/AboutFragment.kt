package com.rsuly.expansetracker.view.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rsuly.expansetracker.BuildConfig
import com.rsuly.expansetracker.R
import com.rsuly.expansetracker.databinding.FragmentAboutBinding
import com.rsuly.expansetracker.view.base.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>() {
    override val viewModel: AboutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        appVersion.text = getString(
            R.string.text_app_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )

        licence.setOnClickListener {
            viewModel.launchLicence().run {
                launchBrowser(viewModel.url.value)
            }
        }

        visitUrl.setOnClickListener {
            viewModel.launchRepository().run {
                launchBrowser(viewModel.url.value)
            }
        }
    }

    private fun launchBrowser(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
            startActivity(it)
        }
    }

    override fun getViewBinging(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAboutBinding.inflate(inflater, container, false)
}