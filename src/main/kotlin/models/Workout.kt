package models
/**
 * Represents a workout session in the system.
 *
 * @property workoutID The unique identifier for the workout.
 * @property workoutName The name of the workout.
 * @property sessionType The type of workout session (e.g., cardio, strength).
 * @property date The date of the workout session.
 * @property sessionDuration The duration of the workout session in minutes.
 * @property sessionCompleted A Boolean indicating whether the workout session has been completed.
 */
class Workout(
    var workoutID: Int,
    var workoutName: String,
    var sessionType: String,
    var date: Int,
    var sessionDuration: Int,
    var sessionCompleted: Boolean
) {

    /**
     * Returns a string representation of the Workout object.
     *
     * @return A string that contains the workout's ID, name, date, session type, duration, and completion status.
     */
    override fun toString(): String {
        return "Workout ID: $workoutID, Workout Name: $workoutName, Date: $date, session type $sessionType, session duration $sessionDuration, session completed $sessionCompleted "
    }
}
