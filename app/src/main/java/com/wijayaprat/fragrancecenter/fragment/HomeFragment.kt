package com.wijayaprat.fragrancecenter.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.adapter.ParfumAdapter
import com.wijayaprat.fragrancecenter.databinding.FragmentHomeBinding
import com.wijayaprat.fragrancecenter.helper.ProductRepository

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ParfumAdapter
    private val productRepository = ProductRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        loadProducts()
    }

    private fun setupRecyclerView() {
        adapter = ParfumAdapter(mutableListOf())

        binding.recyclerParfum.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
    }

    private fun loadProducts() {
        binding.progressBar.visibility = View.VISIBLE

        productRepository.getProducts(
            onSuccess = { products ->
                binding.progressBar.visibility = View.GONE
                adapter.updateData(products)
            },
            onFailure = {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    getString(R.string.load_product_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}