package controllers

import models.Workout
import persistence.JSONSerializer
import utils.ValidateInput
import utils.ValidateInput.readValidListIndex
import utils.WorkoutValidation.isValidWorkoutName

class WorkoutAPI(serializerType: JSONSerializer) {
    private var workouts = ArrayList<Workout>() // notes are stored here
    private var serializer: JSONSerializer = serializerType


    fun addWorkout(workout: Workout): Boolean {
        if (!isValidWorkoutName(workout.workoutName)) {
            println("Invalid workout name: ${workout.workoutName}")
            return false
        }
        workouts.add(workout)
        return true
    }

    fun numberOfWorkouts(): Int { return workouts.size }

    fun listAllWorkouts(): String = if (workouts.isEmpty()) "no users stored" else writeList(workouts)

    fun chooseWorkout() {
        println(listAllWorkouts())
        val workoutChosen = readValidListIndex("Enter the index of the workout you want to select: ", numberOfWorkouts())
        if (isValidIndex(workoutChosen)) {
            val selectedWorkout = workouts[workoutChosen]
            println("you selected workout: ${selectedWorkout.workoutName}")
            println("Workout Details: $selectedWorkout")
        } else {
            println("not a valid index!")
        }
    }

    fun isValidIndex(index: Int): Boolean { return ValidateInput.isValidListIndex(index, workouts) } // validates the entered index

    fun updateWorkout(indexToUpdate: Int, workout: Workout?): Boolean {
        val foundWorkout = findWorkout(indexToUpdate)
        if ((foundWorkout != null) && (workout != null)) {
            foundWorkout.workoutID = workout.workoutID
            foundWorkout.workoutName = workout.workoutName
            foundWorkout.sessionType = workout.sessionType
            foundWorkout.date = workout.date
            foundWorkout.sessionDuration = workout.sessionDuration
            foundWorkout.sessionCompleted = workout.sessionCompleted
            return true
        }
        return false
    }

    /*https://www.baeldung.com/kotlin/lambda-expressions*/
    fun logWorkoutCompletion() {
        println(listAllWorkouts())
        readValidListIndex("Enter the index of the workout you want to complete: ", numberOfWorkouts())
            .takeIf { isValidIndex(it) }
            ?.let { workoutIndex ->
                workouts[workoutIndex].apply {
                    sessionCompleted = true
                    println("Workout: $workoutName has been completed")
                }
            } ?: println("not a valid index")
    }


    fun getArchivedWorkouts(): List<Workout> {
        return workouts.filter { it.sessionCompleted }
    }

    fun findWorkout(index: Int): Workout? = if (ValidateInput.isValidListIndex(index, workouts)) { workouts[index] } else null

    /*https://www.baeldung.com/kotlin/lambda-expressions*/
    fun deleteWorkout(indexToDelete: Int): Workout? =
        workouts.takeIf {ValidateInput.isValidListIndex(indexToDelete, it) }?.removeAt(indexToDelete)

    @Throws(Exception::class)
    fun load() {
        workouts = serializer.read() as ArrayList<Workout>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(workouts)
    }

    private fun formatListString(notesToFormat: List<Workout>): String = notesToFormat.joinToString(separator = "\n") { user -> workouts.indexOf(user).toString() + ": " + user.toString() }
    private fun writeList(notesToFormat: List<Workout>): String = notesToFormat.joinToString(separator = "\n") { user -> workouts.indexOf(user).toString() + ": " + user.toString() + "\n" }
}
