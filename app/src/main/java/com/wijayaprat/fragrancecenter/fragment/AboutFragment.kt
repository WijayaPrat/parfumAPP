package com.wijayaprat.fragrancecenter.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.databinding.FragmentAboutBinding
import com.wijayaprat.fragrancecenter.helper.LocationHelper

class AboutFragment : Fragment(R.layout.fragment_about) {

    private lateinit var binding: FragmentAboutBinding

    // ✅ MODERN PERMISSION API (TIDAK DEPRECATED)
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                detectLocation()
            } else {
                binding.txtLocationInfo.text =
                    getString(R.string.location_permission_denied)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutBinding.bind(view)
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                detectLocation()
            }

            else -> {
                locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun detectLocation() {
        binding.txtLocationInfo.text =
            getString(R.string.detecting_location)

        LocationHelper(requireContext()).getLastLocation { lat, lon ->
            val branch = if (lat > -6.2)
                getString(R.string.branch_a)
            else
                getString(R.string.branch_b)

            binding.txtLocationInfo.text = getString(
                R.string.location_result_format,
                lat,
                lon,
                branch
            )
        }
    }
}