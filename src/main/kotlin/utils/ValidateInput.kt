package utils

import utils.ScannerInput.readNextInt

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

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}
