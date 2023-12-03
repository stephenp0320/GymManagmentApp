package controllers

import models.User
import persistence.JSONSerializer
import utils.ValidateInput

class UserAPI(serializerType: JSONSerializer) {
    private var users = ArrayList<User>() // notes are stored here
    private var serializer: JSONSerializer = serializerType

    fun add(user: User): Boolean = users.add(user)

    fun numberOfUsers(): Int = users.size

    /* https://www.educba.com/kotlin-takeif/ */
    fun listAllUsers(): String = users.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") { user -> "${users.indexOf(user)}: $user" }
            ?: "no users stored"

    fun isValidIndex(index: Int): Boolean { return ValidateInput.isValidListIndex(index, users) }

    /*https://www.baeldung.com/kotlin/lambda-expressions*/
    fun updateUser(indexToUpdate: Int, user: User?): Boolean {
        return findUser(indexToUpdate)?.let { foundUser ->
            user?.apply {
                foundUser.userID = userID
                foundUser.userName = userName
                foundUser.userEmail = userEmail
            } != null
        } ?: false
    }



    fun findUser(index: Int): User? = if (ValidateInput.isValidListIndex(index, users)) { users[index] } else null

    /* https://www.educba.com/kotlin-takeif/ */
    fun deleteUser(indexToDelete: Int): User? = users.takeIf {
        ValidateInput.isValidListIndex(indexToDelete, it) }?.removeAt(indexToDelete)

    @Throws(Exception::class)
    fun load() { users = serializer.read() as ArrayList<User> }

    @Throws(Exception::class)
    fun store() { serializer.write(users) }

    private fun formatListString(notesToFormat: List<User>): String = notesToFormat.joinToString(separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() }
    private fun writeList(notesToFormat: List<User>): String = notesToFormat.joinToString(separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() + "\n" }
}
