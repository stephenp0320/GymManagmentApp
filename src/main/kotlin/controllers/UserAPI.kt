package controllers

import models.User
import persistence.JSONSerializer
import utils.ValidateInput
/**
 * A controller class for managing users.
 *
 * @property serializerType An instance of JSONSerializer for data persistence.
 */
class UserAPI(serializerType: JSONSerializer) {
    private var users = ArrayList<User>() // notes are stored here
    private var serializer: JSONSerializer = serializerType

    /**
     * Adds a new user to the list.
     *
     * @param user The User object to add.
     * @return Boolean indicating success of the operation.
     */
    fun add(user: User): Boolean = users.add(user)

    /**
     * Returns the number of users.
     *
     * @return Int representing the total number of users.
     */
    fun numberOfUsers(): Int = users.size

    /**
     * Lists all users in a formatted string.
     *
     * @return A String containing all users or a message if no users are stored.
     */
    /* https://www.educba.com/kotlin-takeif/ */
    fun listAllUsers(): String = users.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") { user -> "${users.indexOf(user)}: $user" }
            ?: "no users stored"


    /**
     * Validates if the provided index is valid for the users list.
     *
     * @param index The index to validate.
     * @return Boolean indicating if the index is valid.
     */
    fun isValidIndex(index: Int): Boolean { return ValidateInput.isValidListIndex(index, users) }



    /*https://www.baeldung.com/kotlin/lambda-expressions*/

    /**
     * Updates a user at a specified index.
     *
     * @param indexToUpdate The index of the user to update.
     * @param user The new User object.
     * @return Boolean indicating if the update was successful.
     */
    fun updateUser(indexToUpdate: Int, user: User?): Boolean {
        return findUser(indexToUpdate)?.let { foundUser ->
            user?.apply {
                foundUser.userID = userID
                foundUser.userName = userName
                foundUser.userEmail = userEmail
            } != null
        } ?: false
    }

    /**
     * Finds and returns a user at a specified index.
     *
     * @param index The index of the user to find.
     * @return The User object if found, otherwise null.
     */

    fun findUser(index: Int): User? = if (ValidateInput.isValidListIndex(index, users)) { users[index] } else null

    /* https://www.educba.com/kotlin-takeif/ */

    /**
     * Deletes a user at a specified index.
     *
     * @param indexToDelete The index of the user to delete.
     * @return The deleted User object or null if the operation fails.
     */
    fun deleteUser(indexToDelete: Int): User? = users.takeIf {
        ValidateInput.isValidListIndex(indexToDelete, it) }?.removeAt(indexToDelete)

    /**
     * Loads the user data from the persistence layer.
     *
     * @throws Exception if the read operation fails.
     */
    @Throws(Exception::class)
    fun load() { users = serializer.read() as ArrayList<User> }


    /**
     * Stores the user data to the persistence layer.
     *
     * @throws Exception if the write operation fails.
     */
    @Throws(Exception::class)
    fun store() { serializer.write(users) }

    private fun formatListString(notesToFormat: List<User>): String = notesToFormat.joinToString(separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() }
    private fun writeList(notesToFormat: List<User>): String = notesToFormat.joinToString(separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() + "\n" }
}
