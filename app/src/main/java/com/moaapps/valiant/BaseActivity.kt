package com.moaapps.valiant

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

open class BaseActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "BaseActivity"
    }
    private lateinit var loadingDialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = Dialog(this)
        loadingDialog.setContentView(layoutInflater.inflate(R.layout.layout_loading, null))
        loadingDialog.setCancelable(false)
    }

    fun  showProgressBar(){
        loadingDialog.show()

    }

    fun hideProgressBar(){
        loadingDialog.dismiss()
    }

    fun snackBar(msg:String){
        Snackbar.make(container, msg, Snackbar.LENGTH_SHORT).show()
    }

    fun snackBar(msg:Int){
        Snackbar.make(container, msg, Snackbar.LENGTH_SHORT).show()
    }
}