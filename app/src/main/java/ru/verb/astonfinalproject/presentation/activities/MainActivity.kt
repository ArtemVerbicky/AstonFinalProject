package ru.verb.astonfinalproject.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.ActivityMainBinding
import ru.verb.astonfinalproject.presentation.fragments.LocationsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, LocationsFragment())
                .commit()
        }

    }
}