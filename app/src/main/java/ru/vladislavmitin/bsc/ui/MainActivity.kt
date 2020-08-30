package ru.vladislavmitin.bsc.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.vladislavmitin.bsc.R

class MainActivity: AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted) {
            toEventList()
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.read_contact_permission_deny_message),
                Toast.LENGTH_LONG
            ).show()

            requestReadContactPermission()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestReadContactPermission()
    }

    private fun requestReadContactPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                toEventList()
            }
            android.os.Build.VERSION.SDK_INT >= 23 && shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_contact_permission_deny_message),
                    Toast.LENGTH_LONG
                ).show()

                 finish()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private fun toEventList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment.newInstance(), ListFragment.tag)
            .commit()
    }

    fun toDetail() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance(), DetailFragment.tag)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return false
    }
}