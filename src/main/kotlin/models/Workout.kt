package models

import java.time.Duration

class Workout(
    var workoutID: Int,
    var workoutName: String,
    var sessionType: String,
    var date: Int,
    var sessionDuration: Int,
    var sessionCompleted: Boolean
) {
}