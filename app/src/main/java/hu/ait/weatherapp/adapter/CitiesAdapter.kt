package hu.ait.weatherapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.weatherapp.DetailsActivity
import hu.ait.weatherapp.ScrollingActivity
import hu.ait.weatherapp.databinding.CityRowBinding

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    companion object {
        const val CITY_NAME = "CITY_NAME"
    }

    var context: Context
    var cityList = mutableListOf<String>()

    constructor(context: Context, cityList: MutableList<String>) : super() {
        this.context = context
        this.cityList = cityList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesAdapter.ViewHolder {
        val binding = CityRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesAdapter.ViewHolder, position: Int) {
        var city = cityList[holder.adapterPosition]
        holder.tvCity.text = city
        holder.binding.btnDetails.setOnClickListener {
            val intentDetails = Intent()
            intentDetails.setClass(context, DetailsActivity::class.java)
            intentDetails.putExtra(CITY_NAME, city)
            context.startActivity(intentDetails)
        }
        holder.binding.btnDelete.setOnClickListener {
            removeCity(holder.adapterPosition)
        }
    }

    fun addCity(city: String) {
        cityList.add(city)
        notifyItemInserted(cityList.lastIndex)
    }

    private fun removeCity(index: Int) {
        cityList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    inner class ViewHolder(val binding: CityRowBinding) : RecyclerView.ViewHolder(binding.root) {
        var tvCity = binding.tvCity
    }

}