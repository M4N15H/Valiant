package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.moaapps.valiant.BaseActivity
import com.moaapps.valiant.R
import com.moaapps.valiant.utils.InternetChecker.isConnected
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

class MainActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(starter)
        }

        private const val QuotesDelay:Long = 5000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = FirebaseAuth.getInstance().currentUser
        if (user!= null) {
            top_text.text = "HI, ${user.displayName}"
            Picasso.get().load(user.photoUrl).into(profile_image)
        }

        profile_image.setOnClickListener {
            if (user == null){
                LoginActivity.start(this)
            }else{
                ProfileActivity.start(this)
            }
        }

        feeling_suicidal.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.layout_feeling_suicidal, null)
            bottomSheet.setContentView(view)
            bottomSheet.show()
        }

        asses_my_anxiety.setOnClickListener { AssesAnxietyActivity.start(this) }

        tools.setOnClickListener { ExercisesActivity.start(this) }

        initQuotesTimer()

    }

    private fun initQuotesTimer() {
        var quotes: List<*> = ArrayList<Any>()
        GlobalScope.launch {
            while (true) {
                if (isConnected(this@MainActivity)) {
                    quotes = getQuotes()
                }
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    quote_text.text = if (quotes.isEmpty()) {
                        getString(R.string.no_quotes)
                    } else {
                        val index = Random.nextInt(quotes.size)
                        quotes[index].toString()
                    }
                }


                delay(QuotesDelay)
            }

        }
    }

    private suspend fun getQuotes(): List<*> {
        val quotesCollection = FirebaseFirestore.getInstance()
            .collection("quotes")
        val quotesDoc = quotesCollection.document("quotes").get().await()
        return quotesDoc.get("quotes") as List<*>
    }
}