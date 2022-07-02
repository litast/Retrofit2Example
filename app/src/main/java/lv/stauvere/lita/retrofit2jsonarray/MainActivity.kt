package lv.stauvere.lita.retrofit2jsonarray

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import lv.stauvere.lita.retrofit2jsonarray.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressBar.visibility = View.VISIBLE
            val response =  try {
                RetrofitInstance.api.getItems()
            } catch (e: IOException) {
                Log.e(TAG, "IOException: No Internet connection")
                binding.progressBar.visibility = View.GONE
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException: Unexpected response")
                binding.progressBar.visibility = View.GONE
                return@launchWhenCreated
            }

            if(response.isSuccessful && response.body() != null) {
                countryAdapter.countries = response.body()!!
            } else {
                Log.e(TAG, "Response not successful")
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() = binding.rvCountries.apply {
        countryAdapter = CountryAdapter()
        adapter = countryAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}

