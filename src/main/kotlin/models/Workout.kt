package models


class Workout(
    var workoutID: Int,
    var workoutName: String,
    var sessionType: String,
    var date: Int,
    var sessionDuration: Int,
    var sessionCompleted: Boolean
) {

    override fun toString(): String{
        return "Workout ID: ${workoutID}, Workout Name: $workoutName, Date: $date, session type $sessionType, session duration $sessionDuration, session completed $sessionCompleted "
    }
}