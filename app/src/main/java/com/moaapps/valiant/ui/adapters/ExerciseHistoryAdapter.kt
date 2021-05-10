package com.moaapps.valiant.ui.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moaapps.valiant.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ExerciseHistoryAdapter(private var list: ArrayList<String>) :
    RecyclerView.Adapter<ExerciseHistoryAdapter.Holder>() {

    private lateinit var listener:OnItemClickListener

    interface OnItemClickListener{
        fun onClick(id:String)
    }

    fun setOnClickListener(listener:OnItemClickListener ){
        this.listener = listener
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time:TextView = itemView.findViewById(R.id.time)
        val date:TextView = itemView.findViewById(R.id.date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_exercise_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val time = list[position].replace("test_","").toLong()
        val date = Date(time)
        val timeFormatter = SimpleDateFormat("HH:mm", Locale.US)
        val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        holder.time.text = timeFormatter.format(date)
        holder.date.text = dateFormatter.format(date)
        holder.itemView.setOnClickListener {
            listener.onClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}