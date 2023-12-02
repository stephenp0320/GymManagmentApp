package controllers

import models.User
import persistence.JSONSerializer
import persistence.Serializer

class UserAPI(serializerType: JSONSerializer) {
    private var users = ArrayList<User>() // notes are stored here
    private var serializer: JSONSerializer = serializerType

    fun add(user: User): Boolean = users.add(user)


    fun numberOfUsers(): Int { return users.size}

    fun listAllUsers(): String = if(users.isEmpty()) "no users stored" else writeList(users)
    @Throws(Exception::class)
    fun load() { users = Serializer.read() as ArrayList<User> }

    @Throws(Exception::class)
    fun store() { serializer.write(users) }

    private fun formatListString(notesToFormat : List<User>) : String = notesToFormat.joinToString (separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() }
    private fun writeList(notesToFormat : List<User>) : String = notesToFormat.joinToString (separator = "\n") { user -> users.indexOf(user).toString() + ": " + user.toString() + "\n"}

}