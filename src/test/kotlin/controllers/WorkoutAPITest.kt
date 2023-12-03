package controllers

import models.Workout
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import persistence.JSONSerializer
import java.io.File
import kotlin.test.Test

class WorkoutAPITest {

    private var yoga: Workout? = null
    private var back: Workout? = null
    private var legs: Workout? = null
    private var cycling: Workout? = null
    private var treadmill: Workout? = null
    private var populatedWorkouts: WorkoutAPI? = WorkoutAPI(JSONSerializer(File("workouts.json")))
    private var emptyWorkouts: WorkoutAPI? = WorkoutAPI(JSONSerializer(File("workouts.json")))

    @BeforeEach
    fun setup() {
        yoga = Workout(2, "yoga", "cardio", 10, 20, false)
        back = Workout(3, "back", "hypertrophy", 1, 60, true)
        legs = Workout(4, "legs", "hypertrophy", 17, 70, false)
        cycling = Workout(5, "cycling", "cardio", 20, 10, true)
        treadmill = Workout(6, "treadmill", "cardio", 12, 90, false)

        populatedWorkouts!!.addWorkout(yoga!!)
        populatedWorkouts!!.addWorkout(back!!)
        populatedWorkouts!!.addWorkout(legs!!)
        populatedWorkouts!!.addWorkout(cycling!!)
        populatedWorkouts!!.addWorkout(treadmill!!)
    }

    @AfterEach
    fun tearDown() {
        yoga = null
        back = null
        legs = null
        cycling = null
        treadmill = null
        populatedWorkouts = null
        emptyWorkouts = null
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty notes.json file.
            val storingNotes = WorkoutAPI(JSONSerializer(File("workout.json")))
            storingNotes.store()

            // Loading the empty notes.json file into a new object
            val loadedNotes = WorkoutAPI(JSONSerializer(File("workout.json")))
            loadedNotes.load()

            // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            assertEquals(0, storingNotes.numberOfWorkouts())
            assertEquals(0, loadedNotes.numberOfWorkouts())
            assertEquals(storingNotes.numberOfWorkouts(), loadedNotes.numberOfWorkouts())
        }
    }


    @Nested
    inner class AddWorkout {
        @org.junit.jupiter.api.Test
        fun `adding a workout to a populated list adds to ArrayList`() {
            val newWorkout = Workout(12, "legs", "Hypertrophy", 20, 70, false)
            assertEquals(5, populatedWorkouts!!.numberOfWorkouts())
            assertTrue(populatedWorkouts!!.addWorkout(newWorkout))
            assertEquals(6, populatedWorkouts!!.numberOfWorkouts())
            assertEquals(newWorkout, populatedWorkouts!!.findWorkout(populatedWorkouts!!.numberOfWorkouts() - 1))
        }

        @org.junit.jupiter.api.Test
        fun `adding a workout to an empty list adds to ArrayList`() {
            val newWorkout = Workout(12, "legs", "Hypertrophy", 20, 70, false)
            assertEquals(0, emptyWorkouts!!.numberOfWorkouts())
            assertTrue(emptyWorkouts!!.addWorkout(newWorkout))
            assertEquals(1, emptyWorkouts!!.numberOfWorkouts())
            assertEquals(newWorkout, emptyWorkouts!!.findWorkout(emptyWorkouts!!.numberOfWorkouts() - 1))
        }
    }


    @Nested
    inner class ListNotes {

        @org.junit.jupiter.api.Test
        fun `listAllWorkouts returns No workouts Stored message when ArrayList is empty`() {
            assertEquals(0, emptyWorkouts!!.numberOfWorkouts())
            assertFalse(emptyWorkouts!!.listAllWorkouts().lowercase().contains("no workouts"))
        }


    }

    @Nested
    inner class DeleteWorkout {

        @org.junit.jupiter.api.Test
        fun `deleting a workout that does not exist, returns null`() {
            assertNull(emptyWorkouts!!.deleteWorkout(0))
            assertNull(populatedWorkouts!!.deleteWorkout(-1))
            assertNull(populatedWorkouts!!.deleteWorkout(5))
        }

        @org.junit.jupiter.api.Test
        fun `deleting a workout that exists delete and returns deleted object`() {
            assertEquals(5, populatedWorkouts!!.numberOfWorkouts())
            assertEquals(legs, populatedWorkouts!!.deleteWorkout(2))
            assertEquals(4, populatedWorkouts!!.numberOfWorkouts())
            assertEquals(treadmill, populatedWorkouts!!.deleteWorkout(3))
            assertEquals(3, populatedWorkouts!!.numberOfWorkouts())
        }
    }

    @Nested
    inner class UpdateWorkout {
        @org.junit.jupiter.api.Test
        fun `updating a Workout that does not exist returns false`() {
            assertFalse(
                populatedWorkouts!!.updateWorkout(
                    6,
                    Workout(12, "legs", "Hypertrophy", 20, 70, false)
                )
            )
            assertFalse(
                populatedWorkouts!!.updateWorkout(
                    -1,
                    Workout(12, "legs", "Hypertrophy", 20, 70, false)
                )
            )
            assertFalse(
                emptyWorkouts!!.updateWorkout(
                    0, Workout(12, "legs", "Hypertrophy", 20, 70, false)
                )
            )
        }

        @org.junit.jupiter.api.Test
        fun `updating a workout that exists returns true and updates`() {
            // Assuming 'legs' is at index 2
            assertEquals(legs, populatedWorkouts!!.findWorkout(2))
            assertEquals("legs", populatedWorkouts!!.findWorkout(2)!!.workoutName)
            assertEquals(4, populatedWorkouts!!.findWorkout(2)!!.workoutID)
            assertEquals("hypertrophy", populatedWorkouts!!.findWorkout(2)!!.sessionType) // Original state

            // Update the workout at index 2
            assertTrue(
                populatedWorkouts!!.updateWorkout(
                    2,
                    Workout(12, "legs", "Hypertrophy", 20, 70, false)
                )
            )
            // Check if the workout at index 2 is updated correctly
            assertEquals("legs", populatedWorkouts!!.findWorkout(2)!!.workoutName)
            assertEquals(12, populatedWorkouts!!.findWorkout(2)!!.workoutID) // Updated ID
            assertEquals("Hypertrophy", populatedWorkouts!!.findWorkout(2)!!.sessionType) // Updated session type
        }
    }
}


