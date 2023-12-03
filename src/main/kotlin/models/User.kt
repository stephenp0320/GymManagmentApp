package models

/**
 * Represents a user in the system.
 *
 * @property userID The unique identifier for the user.
 * @property userName The name of the user.
 * @property userEmail The email address of the user.
 * @property userPassword The password of the user (stored in plain text for simplicity, though not recommended in a real-world scenario).
 */
class User(
    var userID: Int,
    var userName: String,
    var userEmail: String,
    var userPassword: String
) {


    /**
     * Returns a string representation of the User object.
     *
     * @return A string that contains the user's ID, name, and email.
     */
    override fun toString(): String {
        return "User ID: $userID, Name: $userName, Email: $userEmail"
    }
}
