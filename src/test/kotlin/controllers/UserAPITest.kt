package controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import java.io.File

class UserAPITest {
    @Nested
    inner class PersistenceTests {


        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty notes.json file.
            val storingNotes = UserAPI(JSONSerializer(File("user.json")))
            storingNotes.store()

            //Loading the empty notes.json file into a new object
            val loadedNotes = UserAPI(JSONSerializer(File("user.json")))
            loadedNotes.load()

            //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            Assertions.assertEquals(0, storingNotes.numberOfUsers())
            Assertions.assertEquals(0, loadedNotes.numberOfUsers())
            Assertions.assertEquals(storingNotes.numberOfUsers(), loadedNotes.numberOfUsers())
        }


    }
}