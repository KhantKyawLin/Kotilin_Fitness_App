package com.example.V_Fit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.V_Fit.databinding.EachWorkoutItemViewBinding

class workoutAdapter(val context:Context,val workoutList:List<WorkoutModel>)
    :RecyclerView.Adapter<workoutAdapter.WorkoutViewHolder>()
{
    inner class WorkoutViewHolder(val binding: EachWorkoutItemViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = EachWorkoutItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentItem = workoutList[position]
        with(holder){
            binding.txtWorkoutType.text = currentItem.workoutType
            binding.txtWorkoutDate.text = currentItem.workoutDate
            binding.txtWorkoutTime.text = currentItem.workoutTime

            binding.btnWorkoutDetails.setOnClickListener(){
                (context as UserActivity).showWorkoutDetails(currentItem.workoutID)
            }

            binding.btnWorkoutDelete.setOnClickListener(){
                (context as UserActivity).deleteWorkout(currentItem.workoutID)
            }

        } //with

    } //onBindingViewHolder
}