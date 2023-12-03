package utils

import utils.ScannerInput.readNextInt
import java.util.*

object ValidateInput {

    fun readValidListIndex(prompt: String?, size: Int): Int { // https://discuss.kotlinlang.org/t/checking-for-valid-entries/4132/3
        var input: Int
        do {
            input = readNextInt(prompt)
            if (input in 0..size - 1) {
                return input
            } else {
                print("Invalid index entered :  $input :")
            }
        } while (true)
    }


    @JvmStatic
    fun readValidWorkout(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (WorkoutOptions.isValidWorkout(input))
                return input
            else { print("Invalid workout $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    @JvmStatic
    fun readValidIntensity(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (WorkoutOptions.isValidIntensity(input))
                return input
            else { print("Invalid intensity $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }


    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}
