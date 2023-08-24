package com.example.havadurumu

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {



    private lateinit var viewModel : WeatherViewModel

    private val adapterForecastAdapter = WeatherForecastAdapter(this, arrayListOf())



    private lateinit var GET : SharedPreferences
    private lateinit var SET:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        findViewById<RecyclerView>(R.id.rv_weekly_forecast).layoutManager= LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rv_weekly_forecast).adapter=adapterForecastAdapter

        // Son aratılan şehri SharedPreferences'ten al
        var lastSearchedCity = GET.getString("cityname", "ankara")
        findViewById<EditText>(R.id.edt_city_name).setText(lastSearchedCity)

        findViewById<ImageView>(R.id.img_search_city).setOnClickListener {
            val newCityName = findViewById<EditText>(R.id.edt_city_name).text.toString()
            SET.putString("cityname", newCityName)
            SET.apply()
            viewModel.refreshData(newCityName)
        }

        getLiveData()

        // Son aratılan şehirle hava durumu bilgisini getir
        lastSearchedCity?.let {
            viewModel.refreshData(it)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }



    private fun getLiveData() {
        viewModel.weather_data.observe(this, Observer {


            it?.let {
                findViewById<TextView>(R.id.tv_degree).setText(it.main.temp.toString()+"°C")
                findViewById<TextView>(R.id.tv_city_name).setText(it.name)

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + it.weather.get(0).icon + "@2x.png")
                    .into(findViewById<ImageView>(R.id.img_weather_pictures)
                    )



            }
        })

        viewModel.oneCall_data.observe(this, Observer {
            it?.let {
                // Update RecyclerView
                adapterForecastAdapter.updateForecastData(it.daily)
            }
        })
    }
}