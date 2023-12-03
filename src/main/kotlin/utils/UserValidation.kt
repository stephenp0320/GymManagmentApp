package utils

import java.util.*

object UserValidation {


    fun readValidUserEmailAddress(prompt: String?): String? {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (isValidEmailAddress(input))
                return input
            else { print("Invalid Email Address: $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }



    /*https://gist.github.com/ironic-name/f8e8479c76e80d470cacd91001e7b45b*/
    private fun isValidEmailAddress(email: String?): Boolean{
        if (email.isNullOrBlank()) return false
        val emailRegex = ("^([\\w-_.]+)@([\\w-]+\\.)+[a-zA-Z]{2,6}$").toRegex()
        return emailRegex.matches(email)
    }
}