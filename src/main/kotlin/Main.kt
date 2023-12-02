import controllers.UserAPI
import controllers.WorkoutAPI
import models.User
import models.Workout
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput.readValidListIndex
import java.io.File
import kotlin.system.exitProcess


private val logger = KotlinLogging.logger {}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val UserAPI = UserAPI(JSONSerializer(File("user.json")))
private val WorkoutAPI = WorkoutAPI(JSONSerializer(File("workout.json")))


fun main() {
    runMenu()

}//code when run loads menu
fun mainMenu() : Int { //users notes app user interface menu
    return readNextInt(""" 
          ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
          █                  STEVE'S GYM MANAGEMENT APP                █
          █                                                            █
          ██████████████████████████████████████████████████████████████                                                       █
          █  CRUD OPTIONS:                                             █
          █    1) ADD USER          5) ADD WORKOUT        0) EXIT APP  █
          █    2) LIST USER         6) LIST WORKOUT       10) SAVE     █
          █    3) UPDATE USER       7) UPDATE WORKOUT     11) LOAD     █
          █    4) DELETE USER       8) DELETE WORKOUT                  █
          ██████████████████████████████████████████████████████████████                                              █
          █  ADDITIONAL FUNCTIONS:                                     █
          █    9) Open User Workout Menu                               █
          █    10) Complete Workout                                    █
          █                                                            █
          █                                                            █
          █                                                            █
          █                                                            █
          ██████████████████████████████████████████████████████████████
          
         Type here ==>> """.trimMargin(">"))
    //https://www.asciiart.eu/ascii-one-line

}

fun runMenu() { /*
 Method that allows the user to select options displayed in the ui, uses a switch statement to
 Which contains all the functions for the user
 */
    do {
        when (val option = mainMenu()) {
            1  -> addUser()
            2  -> listUsers()
            3 -> updateUser()
            4 -> deleteUser()
            5 -> addWorkout()
            6 -> listWorkout()
            7 -> updateWorkout()
            8 -> deleteWorkout()
            9 -> userWorkoutMenu()
            10 -> save()
            11 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}


/* user functionality*/


fun addUser(){
    val userID = readNextInt("Enter user ID: ")
    val userName = readNextLine("Enter your name: ")
    val userEmail = readNextLine("Enter your email address: ")
    val userPass = readNextLine("enter your password: ")
    val isAdded = UserAPI.add(User(userID,userName,userEmail,userPass))
    if (isAdded){
        println("User has been successfully added")
    } else {
        println("add failed")
    }
}


fun listUsers(){
    if (UserAPI.numberOfUsers() > 0){
        val option = readNextInt(
            """
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █>>> LIST USERS MENU:          █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █   1) List ALL Users          █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">"))
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> listAllUsers()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No users stored") }
}

fun listAllUsers(){ println(UserAPI.listAllUsers()) }


fun updateUser() {
    listUsers()
    if (UserAPI.numberOfUsers() > 0) {
        val indexToUpdate = readNextInt("Enter the index of what user to update: ")
        if (UserAPI.isValidIndex(indexToUpdate)) {
            val userID = readNextInt("Enter user ID: ")
            val userName = readNextLine("Enter your name: ")
            val userEmail = readNextLine("Enter your email address: ")
            val userPass = readNextLine("enter your password: ")
            if (UserAPI.updateUser(indexToUpdate, User(userID, userName, userEmail, userPass))) {
                println("update successful")
            } else {
                println("update failed")
            }
        } else {
            println("No users at this index!")
        }
    }
}

    fun deleteUser(){
        listUsers()
        if (UserAPI.numberOfUsers() > 0){
            val indexToDelete = readValidListIndex("Enter the index of the user you want to delete: ", UserAPI.numberOfUsers())
            val userToDelete = UserAPI.deleteUser(indexToDelete)
            if(userToDelete != null){
                println("Delete successful! Deleted User: ${userToDelete.userName}")
            } else{
                println("Delete not successful")
            }
        }
    }


/* workout functionality*/


fun addWorkout(){
    val workoutID = readNextInt("Enter Workout ID: ")
    val workoutName = readNextLine("Enter workout name: ")
    val sessionType = readNextLine("Enter the type of session: ")
    val date = readNextInt("enter the date: ")
    val sessionDuration = readNextInt("Enter session duration: ")
    val isAdded = WorkoutAPI.addWorkout(Workout(workoutID,workoutName,sessionType,date,sessionDuration, sessionCompleted = false))
    if (isAdded){
        println("Workout has been successfully added")
    } else {
        println("Workout addition  failed")
    }
}


fun listWorkout(){
    if (WorkoutAPI.numberOfWorkouts() > 0){
        val option = readNextInt(
            """
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █>>> LIST WORKOUT MENU:        █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █   1) List ALL Workouts       █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">"))
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> listAllWorkouts()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No workouts stored") }
}

fun listAllWorkouts(){ println(WorkoutAPI.listAllWorkouts()) }


fun updateWorkout() {
    listUsers()
    if (WorkoutAPI.numberOfWorkouts() > 0) {
        val indexToUpdate = readNextInt("Enter the index of what workout to update: ")
        if (WorkoutAPI.isValidIndex(indexToUpdate)) {
            val workoutID = readNextInt("Enter Workout ID: ")
            val workoutName = readNextLine("Enter workout name: ")
            val sessionType = readNextLine("Enter the type of session: ")
            val date = readNextInt("enter the date: ")
            val sessionDuration = readNextInt("Enter session duration: ")
            if (WorkoutAPI.updateWorkout(indexToUpdate, Workout(workoutID,workoutName,sessionType,date,sessionDuration, sessionCompleted = false))) {
                println("update successful")
            } else {
                println("update failed")
            }
        } else {
            println("No workouts at this index!")
        }
    }
}

fun deleteWorkout(){
    listWorkout()
    if (WorkoutAPI.numberOfWorkouts() > 0){
        val indexToDelete = readValidListIndex("Enter the index of the workout you want to delete: ", UserAPI.numberOfUsers())
        val workoutToDelete = WorkoutAPI.deleteWorkout(indexToDelete)
        if(workoutToDelete != null){
            println("Delete successful! Deleted Workout: ${workoutToDelete.workoutName}")
        } else{
            println("Delete not successful")
        }
    }
}

/* new functionality*/

fun userWorkoutMenu(){
    if (WorkoutAPI.numberOfWorkouts() > 0){
        val option = readNextInt(
            """
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █>>> YOUR WORKOUT MENU         █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █   1) Choose workout         █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">"))
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> WorkoutAPI.chooseWorkout()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No workouts stored") }
}

















/*running methods*/
    fun save() {
        try {
            UserAPI.store()
        } catch (e: Exception) {
            System.err.println("Error writing to file: $e")
        }
        println("SAVED!")
    }

    fun load() {
        try {
            UserAPI.load()
        } catch (e: Exception) {
            System.err.println("Error reading from file: $e")
        }
        println("LOADED!")

    }


    fun exitApp() {
        logger.info { "exitApp() function invoked" }
        exitProcess(0)
    } // exits app when finished

