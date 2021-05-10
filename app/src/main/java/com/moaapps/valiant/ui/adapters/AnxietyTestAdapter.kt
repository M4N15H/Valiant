package com.moaapps.valiant.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.moaapps.valiant.R
import kotlinx.android.synthetic.main.activity_anxiety_result.*
import kotlinx.android.synthetic.main.item_anxiety_score.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AnxietyTestAdapter(val scores:List<Map<String,Any>>) : RecyclerView.Adapter<AnxietyTestAdapter.Holder>(){
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val result:CircularProgressBar = itemView.findViewById(R.id.progress_bar)
        val resultText: TextView = itemView.findViewById(R.id.result)
        val testTime: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_anxiety_score, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val score = scores[position]
        holder.result.progress = (score["score"] as Long).toFloat()
        val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US)
        holder.testTime.text = formatter.format(Date(score["time"] as Long))
        holder.resultText.text = when(score["score"] as Long){
            in 0..4 -> "Mild Anxiety"
            in 5..9 -> "Moderate Anxiety"
            in 10..14 -> "Moderately Severe Anxiety"
            in 15..21 -> "Severe Anxiety"
            else -> "Couldn't get results"
        }
    }

    override fun getItemCount(): Int {
        return scores.size
    }



}