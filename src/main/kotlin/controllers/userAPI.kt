package controllers

import models.User
import persistence.JSONSerializer
import persistence.Serializer

class userAPI(serializerType: JSONSerializer) {
    private var users = ArrayList<User>() // notes are stored here
    private var serializer: JSONSerializer = serializerType
    @Throws(Exception::class)
    fun load() { users = Serializer.read() as ArrayList<User> }

    @Throws(Exception::class)
    fun store() { serializer.write(users) }

    companion object {
        fun store() {
            TODO("Not yet implemented")
        }

        fun load() {
            TODO("Not yet implemented")
        }
    }

}