package hu.ait.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import hu.ait.weatherapp.adapter.CitiesAdapter
import hu.ait.weatherapp.data.WeatherResult
import hu.ait.weatherapp.databinding.ActivityDetailsBinding
import hu.ait.weatherapp.network.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(CitiesAdapter.CITY_NAME)) {
            cityName = intent.getStringExtra(CitiesAdapter.CITY_NAME).toString()
        }
    }

    override fun onResume() {
        super.onResume()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherAPI::class.java)

        val call = weatherService.getWeatherDetails(
            cityName,
            "metric",
            "c82537e1fe357d2c01294f9092dabaf8"
        )

        call.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                lateinit var results: WeatherResult
                try {
                    results = response.body()!!
                } catch (e: Exception){
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.invalid_city),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    return
                }

                binding.tvCityName.text = cityName
                binding.tvTemperature.text = getString(
                    R.string.temperature,
                    results.main!!.temp!!.toString()
                )
                binding.tvFeelsLike.text = getString(
                    R.string.feels_like,
                    results.main!!.feels_like!!.toString()
                )
                binding.tvDescription.text = getString(
                    R.string.description,
                    results.weather!![0].description!!.toString()
                )
                binding.tvMinTemp.text = getString(
                    R.string.min_temp,
                    results.main!!.temp_min!!.toString()
                )
                binding.tvMaxTemp.text = getString(
                    R.string.max_temp,
                    results.main!!.temp_max!!.toString()
                )
                binding.tvHumidity.text = getString(
                    R.string.humidity,
                    results.main!!.humidity!!.toString()
                )
                binding.tvVisbility.text = getString(
                    R.string.visibility,
                    results.visibility!!.toString()
                )
                binding.tvWind.text = getString(
                    R.string.wind_speed,
                    results.wind!!.speed!!.toString()
                )

                Glide.with(this@DetailsActivity)
                    .load(
                        ("https://openweathermap.org/img/w/" +
                                response.body()!!.weather!![0].icon
                                + ".png")
                    )
                    .into(binding.imgWeather)
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.on_failure),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}