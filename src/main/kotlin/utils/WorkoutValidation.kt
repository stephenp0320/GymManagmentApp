package utils

import java.util.*

object WorkoutValidation {

    fun readValidWorkoutName(prompt: String?): String? {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (isValidWorkoutName(input)) {
                return input
            } else {
                print("Invalid Workout name: $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    fun isValidWorkoutName(workoutNameCheck: String?): Boolean {
        return !workoutNameCheck.isNullOrBlank()
    }
}
