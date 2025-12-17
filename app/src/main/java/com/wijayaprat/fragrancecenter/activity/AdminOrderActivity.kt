import com.wijayaprat.fragrancecenter.adapter.AdminOrderAdapter

class AdminOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminOrderBinding
    private lateinit var adapter: AdminOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderList = mutableListOf<OrderModel>() // dummy / dari firebase

        adapter = AdminOrderAdapter(orderList) { order ->
            order.status = "Completed"
            adapter.notifyDataSetChanged()
        }

        binding.recyclerOrderAdmin.adapter = adapter
    }
}
