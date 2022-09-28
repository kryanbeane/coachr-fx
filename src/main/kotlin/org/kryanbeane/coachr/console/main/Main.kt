import mu.KotlinLogging
import org.kryanbeane.coachr.console.controllers.ClientController

private val logger = KotlinLogging.logger {}

var clients: MutableMap<String, Client> = mutableMapOf()
var clientController: ClientController = ClientController()

fun main(args: Array<String>) {
    clientController.start()
}

fun mainMenuChoices() {
    println(
        "\n" +
            "░█████╗░░█████╗░░█████╗░░█████╗░██╗░░██╗██████╗░\n" +
            "██╔══██╗██╔══██╗██╔══██╗██╔══██╗██║░░██║██╔══██╗\n" +
            "██║░░╚═╝██║░░██║███████║██║░░╚═╝███████║██████╔╝\n" +
            "██║░░██╗██║░░██║██╔══██║██║░░██╗██╔══██║██╔══██╗\n" +
            "╚█████╔╝╚█████╔╝██║░░██║╚█████╔╝██║░░██║██║░░██║\n" +
            "░╚════╝░░╚════╝░╚═╝░░╚═╝░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝"

    )
    var input: Int
    do {
        input = displayMainMenu()
        when(input) {
            1 ->
                creationMenuChoices()
            2 ->
                listAllClientsInfo()
            3 ->
                println()
            4 ->
                println()
            0 ->
                println("Exiting Coachr")
            else -> println("Invalid Option")
        }
        println()
    } while (input != 0)
    logger.info {"Shutting Down Coachr"}
}

fun displayMainMenu() : Int {
    println()
    val option: Int
    var input: String? = null

    println("Main Menu")
    println("1. Client Creation Menu")
    println("2. List All Clients")
    println("3. Client Update Menu")
    println("4. Client Deletion Menu")
    println("0. Exit")
    println()
    print("Enter an integer: ")
    input = readLine()!!
    option =
        if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
    return option
}

fun creationMenuChoices() {
    println()
    var input: Int
    do {
        input = displayCreationMenu()
        when(input) {
            1 ->
                createClient(clients)
            2 ->
                createWorkoutPlan()
            3 ->
               mainMenuChoices()
            0 ->
                println("Exiting Coachr")
            else -> println("Invalid Option")
        }
        println()
    } while (input != 0)
    logger.info {"Shutting Down Coachr"}
}

fun displayCreationMenu(): Int {
    val option: Int
    var input: String? = null
    println("Creation Menu")
    println()
    println("1. Create Client")
    println("2. Create Workout for a client")
    println("3. Back to Main Menu")
    println("0. Exit")
    println()
    print("Enter an integer: ")
    input = readLine()!!
    option =
        if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
    return option
}

fun createClient(clientMap: MutableMap<String, Client>) {
    println()
    println("Creating a new client,,,")

    println("Enter client's full name: ")
    val name = readLine()!!

    if (!clientMap.containsKey(name)) {
        println("Enter client's phone number: ")
        val phoneNumber = readLine()!!.toLong()

        println("Enter client's email address: ")
        val emailAddress = readLine()!!

        clientMap[name] = Client(phoneNumber, emailAddress, mutableMapOf())
        println("$name added to the client list!")
        return
    }
    println("Client $name already exists!")
}

fun createWorkoutPlan() {
    println()
    println("Creating a new workout...")
    println()
    println("Enter a client name: ")
    listClientNames()
    val clientName = readLine()!!

    println()
    println("$clientName selected")
    println()

    println("1. Begin Workout Creation")
    println("2. Return to Creation Menu")
    println("0. Return to Main Menu")
    println()
    print("Enter an integer: ")
    do {
        val input = readLine()!!.toInt()
        when (input) {
            1 -> createWorkout(clients[clientName]!!)
            2 -> {
                println("Exiting to creation menu")
                creationMenuChoices()
            }
            0 -> {
                println("Exiting to main menu")
                mainMenuChoices()
            }
            else -> println("Invalid Option")
        }
        println()
    } while (input != 0)
}

fun createWorkout(client: Client) {
    println("Enter workout name: ")
    val workoutName = readLine()!!
    println("Enter workout type: ")
    val workoutType = readLine()!!
    client.addWorkoutToPlan(workoutName, Workout(workoutType, mutableMapOf()))

    do {
        println("1. Add exercise")
        println("2. Complete workout")
        println("0. Return to Main Menu")
        println()
        print("Enter an integer: ")

        val input = readLine()!!.toInt()
        when (input) {
            1 -> addExercise(client.workoutPlan[workoutName]!!)
            2 -> {
                println("Exiting to creation menu")
                creationMenuChoices()
            }
            0 -> {
                println("Exiting to main menu")
                mainMenuChoices()
            }
            else -> println("Invalid Option")
        }
    println()
    } while (input != 0)
}

fun addExercise(workout: Workout) {
    println("Enter exercise name: ")
    val exerciseName = readLine()!!
    println("Enter exercise description: ")
    val exerciseDesc = readLine()!!
    println("Enter number of sets: ")
    val sets = readLine()!!.toInt()
    println("Enter number of reps: ")
    val reps = readLine()!!.toInt()
    println("Enter target Reps in Reserve: ")
    val rir = readLine()!!.toInt()

    workout.addExerciseToWorkout(exerciseName, exerciseDesc, sets, reps, rir)
}

fun listClientNames() {
    println()
    clients.forEach {
        println("• ${it.key}")
    }
    println()
}

fun listAllClientsInfo() {
    for(client in clients) {
        println("Name: ${client.key}")
        println("Email: ${client.value.emailAddress}")
        println("Number: ${client.value.phoneNumber}")
        println()

        println("<<<< WORKOUT PLAN >>>>")
        for(workout in client.value.workoutPlan) {
            println("Workout name: ${workout.key}")
            println("Workout type: ${workout.value.type}")
            println()

            for(exercise in workout.value.exercises) {
                println("Exercise name ${exercise.key}")
                println("Exercise description: ${exercise.value.description}")
                println("Number of perscribed sets: ${exercise.value.sets}")
                println("Number of perscribed reps: ${exercise.value.reps}")
                println("Desired set RIR: ${exercise.value.repsInReserve}")
                println()
            }
        }
        println()
    }
}