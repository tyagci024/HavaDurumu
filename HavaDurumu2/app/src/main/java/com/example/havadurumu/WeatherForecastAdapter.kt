package com.example.havadurumu

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.havadurumu.R
import com.example.havadurumu.model.Daily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.sql.DataSource

class WeatherForecastAdapter(private val context: Context, private var forecastList: List<Daily>) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDayOfWeek: TextView = itemView.findViewById(R.id.tv_day_of_week)
        val imgWeatherCondition: ImageView = itemView.findViewById(R.id.img_weather_condition)
        val tvMaxTemp: TextView = itemView.findViewById(R.id.tv_max_temp)
        val tvMinTemp: TextView = itemView.findViewById(R.id.tv_min_temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayForecast = forecastList[position]

        // Gün ismini hesaplama ve ayarlama işlemi yapılıyor.
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val date = Date(dayForecast.dt * 1000L) // dt Unix zamanıdır ve milisaniye cinsindendir.
        val dayOfWeek = sdf.format(date)
        holder.tvDayOfWeek.text = dayOfWeek

        // Burada günlük hava durumu ikonunu ayarlama işlemi yapılıyor.
        // Ikonun belirlenmesi genellikle API'den gelen hava durumu koduna veya ikon bilgisine dayanır.
        // Hava durumu koduna veya ikon bilgisine bağlı olarak bir dizi veya harita kullanabilirsiniz.
        val iconUrl = "http://openweathermap.org/img/w/${dayForecast.weather[0].icon}.png"
        Log.d("WeatherForecastAdapter", "Loading image from URL: $iconUrl")

        Glide.with(holder.itemView)
            .load(iconUrl)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("WeatherForecastAdapter", "Image load failed", e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("WeatherForecastAdapter", "Image load succeeded")
                    return false
                }
            })
            .into(holder.imgWeatherCondition)



        // Burada en yüksek ve en düşük sıcaklık değerlerini ayarlama işlemi yapılıyor.
        holder.tvMaxTemp.text = "${(dayForecast.temp.max-273).toInt()}°C"
        holder.tvMinTemp.text = "${(dayForecast.temp.min-273).toInt()}°C"
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun updateForecastData(newForecastData: List<Daily>) {
        forecastList = newForecastData
        notifyDataSetChanged()
    }


}