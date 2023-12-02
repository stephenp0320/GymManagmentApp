package controllers

import utils.ValidateInput.readValidListIndex
import models.Workout
import persistence.JSONSerializer
import persistence.Serializer
import utils.ValidateInput
import utils.Utilities

class WorkoutAPI(serializerType: JSONSerializer) {
    private var workouts = ArrayList<Workout>() // notes are stored here
    private var serializer: JSONSerializer = serializerType

    fun addWorkout(workout: Workout): Boolean = workouts.add(workout)


    fun numberOfWorkouts(): Int { return workouts.size}

    fun listAllWorkouts(): String = if(workouts.isEmpty()) "no users stored" else writeList(workouts)

    fun chooseWorkout(){
        println(listAllWorkouts())
        val workoutChosen = readValidListIndex("Enter the index of the workout you want to select: ", numberOfWorkouts())
        if (isValidIndex(workoutChosen)){
            val selectedWorkout = workouts[workoutChosen]
            println("you selected workout: ${selectedWorkout.workoutName}")
            println("Workout Details: $selectedWorkout")
        } else{
            println("not a valid index!")
        }
    }

    fun isValidIndex(index: Int) :Boolean{ return ValidateInput.isValidListIndex(index, workouts) } // validates the entered index



    fun updateWorkout(indexToUpdate: Int, workout: Workout?): Boolean{
        val foundWorkout = findWorkout(indexToUpdate)
        if ((foundWorkout != null) && (workout != null)){
            foundWorkout.workoutID = workout.workoutID
            foundWorkout.workoutName = workout.workoutName
            foundWorkout. sessionType = workout.sessionType
            foundWorkout.date = workout.date
            foundWorkout.sessionDuration = workout.sessionDuration
            foundWorkout.sessionCompleted = workout.sessionCompleted
            return true
        }
        return false
    }

    private fun findWorkout(index: Int): Workout? = if (ValidateInput.isValidListIndex(index, workouts)) { workouts[index] } else null // finds a note

    fun deleteWorkout(indexToDelete: Int): Workout? =
        if (ValidateInput.isValidListIndex(indexToDelete, workouts)) { workouts.removeAt(indexToDelete) } else null // deletes notes stored in the notes arrayList








    @Throws(Exception::class)
    fun load() { workouts = Serializer.read() as ArrayList<Workout> }

    @Throws(Exception::class)
    fun store() { serializer.write(workouts) }

    private fun formatListString(notesToFormat : List<Workout>) : String = notesToFormat.joinToString (separator = "\n") { user -> workouts.indexOf(user).toString() + ": " + user.toString() }
    private fun writeList(notesToFormat : List<Workout>) : String = notesToFormat.joinToString (separator = "\n") { user -> workouts.indexOf(user).toString() + ": " + user.toString() + "\n"}

}