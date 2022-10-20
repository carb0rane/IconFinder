package com.kode.iconfinder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.kode.iconfinder.IconFinder.di.DaggerComponent
import com.kode.iconfinder.IconFinder.di.RetrofitModule
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.databinding.ActivityMainBinding
import com.kode.iconfinder.ui.fragments.HomeFragment
import com.kode.iconfinder.ui.fragments.SearchFragment
import retrofit2.Retrofit
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            getpermission()
        val iconViewModel = ViewModelProvider(this)[IconFinderViewModel::class.java]
        val component =
            DaggerComponent.builder().retrofitModule(RetrofitModule("https://api.iconfinder.com"))
                .build()
                .inject(this)

        iconViewModel.retrofit = retrofit
        iconViewModel.apiKey = resources.getString(R.string.api_key)
        iconViewModel.accept = resources.getString(R.string.accept_type)
        iconViewModel.setRetrofitApi()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frmlMain, HomeFragment())
            commit()
        }
        binding.imgViewHomeSearch.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frmlMain, SearchFragment())
                commit()
            }
        }


    }

    fun getpermission() {

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d(TAG, "getpermission: We now have the permission")
                    // musicc()
                } else {
                    Log.d(TAG, "getpermission: Failed to Get the Permission")
                }
            }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (requestPermissionLauncher == null) {
                Log.d(TAG, "getpermission: Some Serious Issue asking for permission !  ")
            }

            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (requestPermissionLauncher == null) {
                Log.d(TAG, "getpermission: Some Serious Issue asking for permission !  ")
            }
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            Log.d(
                TAG,
                "getpermission: All necessary permissions have been granted , Further execution should begin! "
            )
            //  musicc()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCode -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {

                    Log.d(TAG, "onRequestPermissionsResult: We nw have he permisson")
                    //   musicc()
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Log.d(TAG, "onRequestPermissionsResult: Failed to get the permission")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                Log.d(TAG, "onRequestPermissionsResult: Can't continue using the app ")
                // Ignore all other requests.
            }
        }
    }
}