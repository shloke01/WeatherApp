package hu.ait.weatherapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import hu.ait.weatherapp.adapter.CitiesAdapter
import hu.ait.weatherapp.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var cityAdapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        var cityList = mutableListOf<String>()
        cityAdapter = CitiesAdapter(this, cityList)
        binding.recyclerCities.adapter = cityAdapter

        binding.fab.setOnClickListener { view ->
            val dialog = MaterialDialog(this)
                .title(R.string.add_city)
                .input()

            dialog.show()
            dialog.positiveButton {
                cityAdapter.addCity(dialog.getInputField().text.toString().uppercase())
            }
        }

    }
}