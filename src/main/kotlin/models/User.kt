package models

class User(
    var userID: Int,
    var userName: String,
    var userEmail: String,
    var userPassword: String
) {

    override fun toString(): String{
        return "User ID: $userID, Name: $userName, Email: $userEmail"
    }

}

