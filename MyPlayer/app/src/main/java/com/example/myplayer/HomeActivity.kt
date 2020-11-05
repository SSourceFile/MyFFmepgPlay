package com.example.myplayer

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_home)

    test1.setOnClickListener {
      var permission_array = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
      AndPermission.with(this).runtime()
        .permission(permission_array)
        .onGranted {

        }
        .onDenied {  }
        .start()
    }

    test2.setOnClickListener {
      var intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }
  }
}