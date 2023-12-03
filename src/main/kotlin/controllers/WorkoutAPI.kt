package controllers

import models.Workout
import persistence.JSONSerializer
import utils.ValidateInput
import utils.ValidateInput.readValidListIndex
import utils.WorkoutValidation.isValidWorkoutName

class WorkoutAPI(serializerType: JSONSerializer) {
    private var workouts = ArrayList<Workout>() // notes are stored here
    private var serializer: JSONSerializer = serializerType

    /**
     * Adds a workout to the list if it has a valid name.
     * @param workout The workout to be added.
     * @return `true` if the workout was added successfully, `false` if the workout name is invalid.
     */
    fun addWorkout(workout: Workout): Boolean {
        if (!isValidWorkoutName(workout.workoutName)) {
            println("Invalid workout name: ${workout.workoutName}")
            return false
        }
        workouts.add(workout)
        return true
    }

    /**
     * Returns the number of workouts in the list.
     * @return The size of the workouts list.
     */
    fun numberOfWorkouts(): Int = workouts.size


    /**
     * Lists all workouts in a formatted string.
     * @return A string representation of all workouts, or a message if no workouts are stored.
     */
    fun listAllWorkouts(): String = if (workouts.isEmpty()) "no users stored" else writeList(workouts)

    /**
     * Allows the user to choose a workout from the list.
     * Prints the list of workouts and prompts for a selection.
     */
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

    /**
     * Validates if the provided index is within the range of the workouts list.
     * @param index The index to be validated.
     * @return `true` if the index is valid, `false` otherwise.
     */
    fun isValidIndex(index: Int): Boolean { return ValidateInput.isValidListIndex(index, workouts) } // validates the entered index

    /**
     * Updates a workout at a specified index with the provided workout details.
     * @param indexToUpdate The index of the workout to be updated.
     * @param workout The new workout details.
     * @return `true` if the update is successful, `false` if not.
     */
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



    /**
     * Logs the completion of a workout at a specified index.
     * Marks the workout as completed and prints a confirmation message.
     */
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

    /**
     * Retrieves a list of archived workouts (completed workouts).
     * @return A list of workouts where `sessionCompleted` is `true`.
     */

    fun getArchivedWorkouts(): List<Workout> {
        return workouts.filter { it.sessionCompleted }
    }

    /**
     * Finds and returns a workout at a given index.
     * @param index The index of the workout to find.
     * @return The workout at the specified index, or `null` if the index is invalid.
     */
    fun findWorkout(index: Int): Workout? = if (ValidateInput.isValidListIndex(index, workouts)) { workouts[index] } else null

    /**
     * Deletes a workout at a specified index.
     * @param indexToDelete The index of the workout to delete.
     * @return The deleted workout, or `null` if the index is invalid.
     */
    /*https://www.baeldung.com/kotlin/lambda-expressions*/
    fun deleteWorkout(indexToDelete: Int): Workout? =
        workouts.takeIf {ValidateInput.isValidListIndex(indexToDelete, it) }?.removeAt(indexToDelete)



    /**
     * Loads the list of workouts from a persistent storage.
     * @throws Exception If there is an error during loading.
     */
    @Throws(Exception::class)
    fun load() {
        workouts = serializer.read() as ArrayList<Workout>
    }


    /**
     * Stores the current list of workouts to a persistent storage.
     * @throws Exception If there is an error during saving.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(workouts)
    }

    private fun formatListString(notesToFormat: List<Workout>): String = notesToFormat.joinToString(separator = "\n") { user -> workouts.indexOf(user).toString() + ": " + user.toString() }
    private fun writeList(notesToFormat: List<Workout>): String = notesToFormat.joinToString(separator = "\n") { user -> workouts.indexOf(user).toString() + ": " + user.toString() + "\n" }
}
