package com.moaapps.valiant.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moaapps.valiant.R
import com.moaapps.valiant.pojo.Question
import kotlinx.android.synthetic.main.item_exercise_results.view.*

class ExerciseResultAdapter(private var list:ArrayList<Question>):RecyclerView.Adapter<ExerciseResultAdapter.Holder>() {

    class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        val question:TextView = itemView.findViewById(R.id.question)
        val answer:TextView = itemView.findViewById(R.id.answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_results, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.question.text = list[position].question
        holder.answer.text = list[position].answer
    }

    override fun getItemCount(): Int {
        return list.size
    }
}