package org.kryanbeane.coachr.console.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoCollection
import mu.KotlinLogging
import org.kryanbeane.coachr.console.helpers.*
import java.util.*
import org.litote.kmongo.*
import io.github.cdimascio.dotenv.Dotenv
import java.lang.reflect.Type

private val logger = KotlinLogging.logger {}

const val JSON_FILE = "clients.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
val listType: Type = object: TypeToken<ArrayList<ClientModel>>() {}.type

private fun initializeMongoConnection(): MongoCollection<ClientModel> {
    val client =  KMongo.createClient("mongodb+srv://${Dotenv.load().get("USER_NAME")}:${Dotenv.load().get("PASSWORD")}@coachr-client-db.blxcxzn.mongodb.net/")
    val database = client.getDatabase("coachr-client-db")
    return database.getCollection<ClientModel>("coach-clients")
}

class ClientJSONStore: ClientStore {
    private var clients = mutableListOf<ClientModel>()
    private var clientsCol: MongoCollection<ClientModel> = initializeMongoConnection()

    init {
        if (exists(JSON_FILE)) {
            deserialize()
        }
    }

    private fun save() {
        clients.forEach{
            clientsCol.insertOne(gsonBuilder.toJson(it))
        }
    }

    /**
     * find all clients in client arraylist
     *
     * @return client list
     */
    override fun findAll(): List<ClientModel> {
        return clients
    }

    /**
     * find client matching name param
     *
     * @param clientName
     * @return client or null
     */
    override fun findClientByName(clientName: String): ClientModel? {
        println("Searching for client: $clientName")
        println(clientsCol.findOne(ClientModel::fullName eq clientName))
        return clientsCol.findOne(ClientModel::fullName eq clientName)
    }

    /**
     * find client matching id param
     *
     * @param clientName
     * @return client or null
     */
    fun findClientByID(id: UUID): ClientModel? {
        println("Searching for client id: $id")
        println(clientsCol.findOne(ClientModel::_id eq id))
        return clientsCol.findOne(ClientModel::_id eq id)
    }

    /**
     * find workout matching workoutId param
     *
     * @param clientId
     * @param workoutId
     * @return workout or null
     */
    override fun findWorkout(clientName: String, workoutName: String): WorkoutModel? {
        return findClientByName(clientName)?.workoutPlan?.find{
                workout -> workout.name == workoutName
        }
    }

    /**
     * find exercise matching exerciseId param
     *
     * @param clientId
     * @param workoutId
     * @param exerciseId
     * @return exercise or null
     */
    override fun findExercise(clientName: String, workoutName: String, exerciseName: String): ExerciseModel? {
        return findWorkout(clientName, workoutName)?.exercises?.find{
                exercise -> exercise.name == exerciseName
        }
    }

    /**
     * create client from client param
     *
     * @param client
     */
    override fun createClient(client: ClientModel) {
        clients.add(client)
        clientsCol.insertOne(gsonBuilder.toJson(client))
    }

    /**
     * create workout from workout param for client from param
     *
     * @param client
     * @param workout
     */
    override fun createClientWorkout(client: ClientModel, workout: WorkoutModel) {
        client.workoutPlan.add(workout)
    }

    /**
     * create exercise from param for workout from param
     *
     * @param workout
     * @param exercise
     */
    override fun createExercise(workout: WorkoutModel, exercise: ExerciseModel) {
        workout.exercises.add(exercise)
        serialize()
    }

    /**
     * update client with client param details
     *
     * @param client
     */
    override fun updateClientDetails(client: ClientModel) {
        val foundClient = findClientByName(client.fullName)
        if (foundClient != null) {
            val updateRes = clientsCol.updateOne(foundClient.json, client)
            println("update res $updateRes")
            foundClient.fullName = client.fullName
            foundClient.phoneNumber = client.phoneNumber
            foundClient.emailAddress = client.emailAddress

        }
    }

    /**
     * update workout with workout param details
     *
     * @param workout
     */
    override fun updateClientWorkout(client: ClientModel, workout: WorkoutModel) {
        val foundWorkout = findWorkout(client.fullName, workout.name)
        if (foundWorkout != null) {
            foundWorkout.name = workout.name
            foundWorkout.type = foundWorkout.type
        }
        serialize()
    }

    /**
     * update exercise in workout from param with exercise param details
     *
     * @param workout
     * @param exercise
     */
    override fun updateExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel) {
        val foundExercise = findExercise(client.fullName, workout.name, exercise.name)
        if (foundExercise != null) {
            foundExercise.name = exercise.name
            foundExercise.description = exercise.description
            foundExercise.sets = exercise.sets
            foundExercise.reps = exercise.reps
            foundExercise.repsInReserve = exercise.repsInReserve
        }
        serialize()
    }

    /**
     * delete client
     *
     * @param client
     */
    override fun deleteClient(client: ClientModel) {
        clients.remove(client)
        serialize()
    }

    /**
     * delete workout from client params workout plan
     *
     * @param client
     * @param workout
     */
    override fun deleteWorkout(client: ClientModel, workout: WorkoutModel) {
        client.workoutPlan.remove(workout)
        serialize()
    }

    /**
     * delete exercise from workout param exercise list
     *
     * @param workout
     * @param exercise
     */
    override fun deleteExercise(workout: WorkoutModel, exercise: ExerciseModel) {
        workout.exercises.remove(exercise)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(clients, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        clients = Gson().fromJson(jsonString, listType)
    }

    /**
     * loop through client arraylist param and log all clients
     *
     * @param clientList
     */
    internal fun logClients() {
        clients.forEach{
            logger.info {
                it.fullName + "\n" +
                        "Client ID: " + it._id + "\n" +
                        "Email Address: " + it.emailAddress + "\n" +
                        "Phone Number: " + it.phoneNumber + "\n" +
                        "Number of Workouts in Plan: " + it.workoutPlan.size + "\n" + "\n"
            }
        }
    }

    /**
     * log all client names for use of client selection
     */
    internal fun logClientNames() {
        clients.forEachIndexed{ index, client ->
            logger.info {
                "$index. Client Name: " + client.fullName + "\n"
            }
        }
    }

    /**
     * loop through workout plan arraylist of client param and log all workouts
     *
     * @param client
     */
    internal fun logWorkouts(client: ClientModel) {
        client.workoutPlan.forEach{
            logger.info {
                it.name + "\n" +
                        "Workout ID: " + it._id + "\n" +
                        "Workout Type: " + it.type + "\n" +
                        "Number of Exercises in Plan: " + it.exercises.size + "\n" + "\n"
            }
        }
    }

    /**
     *  log all workout names for use of workout selection
     *
     * @param client
     */
    internal fun logWorkoutNames(client: ClientModel) {
        client.workoutPlan.forEachIndexed{ index, workout ->
            logger.info {
                "$index. Workout Name: " + workout.name + "\n"
            }
        }
    }

    /**
     * loop through exercise arraylist of workout param and log all exercises
     *
     * @param workout
     */
    internal fun logExercises(workout: WorkoutModel) {
        workout.exercises.forEach{
            logger.info {
                it.name + "\n" +
                        "Exercise ID: " + it._id + "\n" +
                        "Exercise Description: " + it.description + "\n" +
                        "Sets: " + it.sets + "\n" +
                        "Reps: " + it.reps + "\n" +
                        "Reps in Reserve: " + it.repsInReserve + "\n" + "\n"
            }
        }
    }

    /**
     * log all exercise names for use of exercise selection
     *
     * @param workout
     */
    internal fun logExerciseNames(workout: WorkoutModel) {
        workout.exercises.forEachIndexed{ index, exercise ->
            logger.info {
                "$index. Exercise Name: " + exercise.name + "\n"
            }
        }
    }

    /**
     * log all recursively
     *
     */
    internal fun logAll() {
        clients.forEach{
            logger.info {
                it.fullName + "\n" +
                        "Client ID: " + it._id + "\n" +
                        "Email Address: " + it.emailAddress + "\n" +
                        "Phone Number: " + it.phoneNumber + "\n" +
                        "Number of Workouts in Plan: " + it.workoutPlan.size + "\n" + "\n"
            }
            it.workoutPlan.forEach{
                logger.info {
                    it.name + "\n" +
                            "Workout ID: " + it._id + "\n" +
                            "Workout Type: " + it.type + "\n" +
                            "Number of Exercises in Plan: " + it.exercises.size + "\n" + "\n"
                }
                it.exercises.forEach{
                    logger.info {
                        it.name + "\n" +
                                "Exercise ID: " + it._id + "\n" +
                                "Exercise Description: " + it.description + "\n" +
                                "Sets: " + it.sets + "\n" +
                                "Reps: " + it.reps + "\n" +
                                "Reps in Reserve: " + it.repsInReserve + "\n" + "\n"
                    }
                }
            }
        }
    }

}