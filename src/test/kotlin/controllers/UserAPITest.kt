package controllers

import models.User
import controllers.UserAPI
import models.Workout
import org.junit.jupiter.api.*
import persistence.JSONSerializer
import java.io.File

class UserAPITest {
    private var userJohn: User? = null
    private var userAlice: User? = null
    private var userBob: User? = null
    private var userEve: User? = null
    private var userMike: User? = null

    private var populatedUsers: UserAPI? = UserAPI(JSONSerializer(File("user.json")))
    private var emptyUsers: UserAPI? = UserAPI(JSONSerializer(File("user.json")))

    @BeforeEach
    fun setup() {
        userMike = User(12, "michael", "michael@gmail.com", "111")
        userJohn = User(12, "johnny", "johnny@gmail.com", "222")
        userBob = User(12, "bobby", "bobby@gmail.com", "333")
        userEve = User(12, "evie", "evie@gmail.com", "444")
        userAlice = User(12, "ally", "ally@gmail.com", "555")

        //adding 5 Note to the notes api
        populatedUsers!!.add(userMike!!)
        populatedUsers!!.add(userJohn!!)
        populatedUsers!!.add(userBob!!)
        populatedUsers!!.add(userEve!!)
        populatedUsers!!.add(userAlice!!)
    }

    @AfterEach
    fun tearDown() {
        userJohn = null
        userAlice = null
        userBob = null
        userEve = null
        userMike = null
    }


    private var userAPI: UserAPI = UserAPI(JSONSerializer(File("user.json")))

    @Test
    fun `getANewSecurePassword generates a password that has the correct length`() {
        val passLength = 10
        val password = userAPI.getANewSecurePassword(passLength)
        Assertions.assertEquals(passLength, password.length, "The generated password should have this length: $passLength")
    }

        @Nested
        inner class AddUser {
            @org.junit.jupiter.api.Test
            fun `adding a user to a populated list adds to ArrayList`() {
                val newUser = User(12, "bob", "bob@gmail.com", "111")
                Assertions.assertEquals(5, populatedUsers!!.numberOfUsers())
                Assertions.assertTrue(populatedUsers!!.add(newUser))
                Assertions.assertEquals(6, populatedUsers!!.numberOfUsers())
                Assertions.assertEquals(
                    newUser,
                    populatedUsers!!.findUser(populatedUsers!!.numberOfUsers() - 1)
                )
            }

            @org.junit.jupiter.api.Test
            fun `adding a user to an empty list adds to ArrayList`() {
                val newUser = User(12, "bob", "bob@gmail.com", "111")
                Assertions.assertEquals(0, emptyUsers!!.numberOfUsers())
                Assertions.assertTrue(emptyUsers!!.add(newUser))
                Assertions.assertEquals(1, emptyUsers!!.numberOfUsers())
                Assertions.assertEquals(newUser, emptyUsers!!.findUser(emptyUsers!!.numberOfUsers() - 1))
            }
        }


        @Nested
        inner class ListUsers {

            @org.junit.jupiter.api.Test
            fun `listallusers returns No workouts Stored message when ArrayList is empty`() {
                Assertions.assertEquals(0, emptyUsers!!.numberOfUsers())
                Assertions.assertTrue(emptyUsers!!.listAllUsers().lowercase().contains("no users"))
            }


        }

    @Nested
    inner class DeleteUsers {

        @org.junit.jupiter.api.Test
        fun `deleting a user that does not exist, returns null`() {
            Assertions.assertNull(emptyUsers!!.deleteUser(0))
            Assertions.assertNull(populatedUsers!!.deleteUser(-1))
            Assertions.assertNull(populatedUsers!!.deleteUser(5))
        }

        @org.junit.jupiter.api.Test
        fun `deleting a user that exists delete and returns deleted object`() {
            Assertions.assertEquals(5, populatedUsers!!.numberOfUsers())
            val deletedUser = populatedUsers!!.deleteUser(2) // Assuming deleteUser returns the deleted User object
            Assertions.assertNotNull(deletedUser)
            Assertions.assertEquals(4, populatedUsers!!.numberOfUsers())
        }
    }

        @Nested
        inner class UpdateWorkout {
            @org.junit.jupiter.api.Test
            fun `updating a user that does not exist returns false`() {
                Assertions.assertFalse(
                    populatedUsers!!.updateUser(
                        6,
                        User(12, "bob", "bob@gmail.com", "111")
                    )
                )
                Assertions.assertFalse(
                    populatedUsers!!.updateUser(
                        -1,
                        User(12, "bob", "bob@gmail.com", "111")
                    )
                )
            }


            @Nested
            inner class UpdateWorkout {
                @org.junit.jupiter.api.Test
                fun `updating a user that exists returns true and updates`() {

                    val existingUser = populatedUsers!!.findUser(2)
                    Assertions.assertNotNull(existingUser)
                    Assertions.assertNotEquals("bob", existingUser!!.userName) // Ensure the name is not already 'bob'

                    val updatedUser = User(12, "bob", "bob@gmail.com", "111")
                    Assertions.assertTrue(
                        populatedUsers!!.updateUser(2, updatedUser)
                    )


                    val userAfterUpdate = populatedUsers!!.findUser(2)
                    Assertions.assertNotNull(userAfterUpdate)
                    Assertions.assertEquals("bob", userAfterUpdate!!.userName)
                    Assertions.assertEquals("333", userAfterUpdate.userPassword)
                }
            }


        @Nested
        inner class PersistenceTests {

            @Test
            fun `saving and loading an empty collection in JSON doesn't crash app`() {
                // Saving an empty notes.json file.
                val storingNotes = UserAPI(JSONSerializer(File("user.json")))
                storingNotes.store()

                // Loading the empty notes.json file into a new object
                val loadedNotes = UserAPI(JSONSerializer(File("user.json")))
                loadedNotes.load()

                // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
                Assertions.assertEquals(0, storingNotes.numberOfUsers())
                Assertions.assertEquals(0, loadedNotes.numberOfUsers())
                Assertions.assertEquals(storingNotes.numberOfUsers(), loadedNotes.numberOfUsers())
            }
        }
    }
}


