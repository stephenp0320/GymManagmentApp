package controllers

import models.User
import persistence.JSONSerializer
import utils.ValidateInput

class UserAPI(serializerType: JSONSerializer) {
    private var users = ArrayList<User>() // notes are stored here
    private var serializer: JSONSerializer = serializerType

    fun add(user: User): Boolean = users.add(user)

    fun numberOfUsers(): Int { return users.size }

    fun listAllUsers(): String = if (users.isEmpty()) "no users stored" else formatListString(users)

    fun isValidIndex(index: Int): Boolean { return ValidateInput.isValidListIndex(index, users) } // validates the entered index

    fun updateUser(indexToUpdate: Int, user: User?): Boolean {
        val foundUser = findUser(indexToUpdate)
        if ((foundUser != null) && (user != null)) {
            foundUser.userID = user.userID
            foundUser.userName = user.userName
            foundUser.userEmail = user.userEmail
            return true
        }
        return false
    }

    private fun findUser(index: Int): User? = if (ValidateInput.isValidListIndex(index, users)) { users[index] } else null // finds a note

    fun deleteUser(indexToDelete: Int): User? =
        if (ValidateInput.isValidListIndex(indexToDelete, users)) { users.removeAt(indexToDelete) } else null // deletes notes stored in the notes arrayList

    @Throws(Exception::class)
    fun load() { users = serializer.read() as ArrayList<User> }

    @Throws(Exception::class)
    fun store() { serializer.write(users) }

    private fun formatListString(notesToFormat: List<User>): String = notesToFormat.joinToString(separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() }
    private fun writeList(notesToFormat: List<User>): String = notesToFormat.joinToString(separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() + "\n" }
}
