package com.example.basicweather.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.basicweather.viewmodel.MainViewModel
import androidx.lifecycle.Observer
import com.example.basicweather.R
import com.example.basicweather.databinding.ActivityMainBinding
import com.example.basicweather.viewmodel.SettingsViewModel
import io.reactivex.internal.disposables.DisposableHelper.replace

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel
    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName", "Istanbul")?.lowercase()
        binding.edtCityName.setText(cName)
        viewmodel.refreshData(cName!!)

        getLiveData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.llData.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE

            var cityName = GET.getString("cityName", cName)?.lowercase()
            binding.edtCityName.setText(cityName)
            viewmodel.refreshData(cityName!!)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.imgSearchCity.setOnClickListener {
            val cityName = binding.edtCityName.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refreshData(cityName)
            getLiveData()
            viewmodel.hideKeyboard(this)
            Log.i(TAG, "onCreate: " + cityName)
        }

        binding.settingsButton.setOnClickListener {
            showSettingsDialog()
        }
    }

    private fun showSettingsDialog() {
        val settingsDialogFragment = SettingsFragment()
        settingsDialogFragment.show(supportFragmentManager, "SettingsFragment")
    }

    private fun getLiveData() {

        viewmodel.weather_data.observe(this, Observer { data ->
            data?.let {
                binding.llData.visibility = View.VISIBLE

                binding.tvCityCode.text = data.sys.country

                binding.tvCityName.text = data.name

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(binding.imgWeatherPictures)

                binding.tvDegree.text = data.main.temp.toString() + "°C"

                binding.tvHumidity.text = data.main.humidity.toString() + "%"
                binding.tvWindSpeed.text = data.wind.speed.toString()
                binding.tvLat.text = data.coord.lat.toString()
                binding.tvLon.text = data.coord.lon.toString()

            }
        })

        viewmodel.weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    binding.tvError.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                    binding.llData.visibility = View.GONE
                } else {
                    binding.tvError.visibility = View.GONE
                }
            }
        })

        viewmodel.weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.llData.visibility = View.GONE
                } else {
                    binding.pbLoading.visibility = View.GONE
                }
            }
        })

    }
}