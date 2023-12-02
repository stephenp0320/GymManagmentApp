import controllers.UserAPI
import controllers.workoutAPI
import models.User
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import kotlin.system.exitProcess


private val logger = KotlinLogging.logger {}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val UserAPI = UserAPI(JSONSerializer(File("user.json")))

fun main() {
    runMenu()

}//code when run loads menu
fun mainMenu() : Int { //users notes app user interface menu
    return readNextInt(""" 
           ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
          █                                █
         █                                  █
         █     WORKOUT APP                  █
         █                                  █
         █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
         █ ---> MENU:                       █  
         █                                  █
         █  11) Save a note                 █
         █  12) Load a note                 █
         █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
         █  0) Exit {-_-}                   █
         █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
         █▄▄▄▄▄▄▄▄▄▄▄▄██████████▄▄▄▄▄▄▄▄▄▄▄▄█
          ▀▄▄▄▄▄▄▄▄▄▄▄██████████▄▄▄▄▄▄▄▄▄▄▄▀
         
         
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
            11  -> save()
            12  -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}




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
                  █   1) List ALL notes          █
                  █   2) List ACTIVE notes       █
                  █   3) List ARCHIVED notes     █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">"))
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> listAllUsers()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No users stored") }
}

fun listAllUsers(){ println(UserAPI.listAllUsers()) }





fun save() {
    try { UserAPI.store() } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try { UserAPI.load() } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}


fun exitApp(){ logger.info { "exitApp() function invoked" }
    exitProcess(0) } // exits app when finished
