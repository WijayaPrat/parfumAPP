package com.wijayaprat.fragrancecenter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wijayaprat.fragrancecenter.adapter.ParfumAdapter
import com.wijayaprat.fragrancecenter.databinding.FragmentHomeBinding
import com.wijayaprat.fragrancecenter.model.ParfumModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val listParfum = ArrayList<ParfumModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        // RecyclerView setup
        binding.recyclerParfum.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ParfumAdapter(listParfum)
        binding.recyclerParfum.adapter = adapter

        // Firebase: ambil data dari node "Items"
        val ref = FirebaseDatabase.getInstance()
            .reference
            .child("Items")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listParfum.clear()
                for (data in snapshot.children) {
                    val parfum = data.getValue(ParfumModel::class.java)
                    if (parfum != null) {
                        listParfum.add(parfum)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Bisa tambahkan Log jika perlu
            }
        })
    }
}
