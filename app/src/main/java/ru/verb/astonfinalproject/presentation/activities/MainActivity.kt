package ru.verb.astonfinalproject.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.ActivityMainBinding
import ru.verb.astonfinalproject.presentation.application.appComponent
import ru.verb.astonfinalproject.presentation.fragments.character.CharactersFragment
import ru.verb.astonfinalproject.presentation.fragments.episode.EpisodesFragment
import ru.verb.astonfinalproject.presentation.fragments.HasBackArrow
import ru.verb.astonfinalproject.presentation.fragments.location.LocationsFragment

class MainActivity : AppCompatActivity(), Navigator, SelectedItemController {
    private lateinit var binding: ActivityMainBinding
    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)
    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        appComponent.inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CharactersFragment())
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.characters -> {
                    if (currentFragment is CharactersFragment) return@setOnItemSelectedListener false
                    launchScreen(CharactersFragment())
                    true
                }
                R.id.locations -> {
                    if (currentFragment is LocationsFragment) return@setOnItemSelectedListener false
                    launchScreen(LocationsFragment())
                    true
                }
                R.id.episodes -> {
                    if (currentFragment is EpisodesFragment) return@setOnItemSelectedListener false
                    launchScreen(EpisodesFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        return true
    }

    override fun launchScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()

    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun updateUi() {
        if (currentFragment is HasBackArrow) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun setSelectedItem(@IdRes itemId: Int) {
        binding.bottomNavView.selectedItemId = itemId
    }
}