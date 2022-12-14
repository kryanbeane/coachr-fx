package org.kryanbeane.coachr.console.models

import com.mongodb.client.*
import java.util.*
import kotlin.collections.ArrayList
import org.litote.kmongo.*
import io.github.cdimascio.dotenv.Dotenv

/**
 * function to initialize mongoDB client, DB, and collection
 *
 * @param isTest
 * @param databaseName
 * @param collectionName
 * @return mongo collection
 */
private fun initializeMongoConnection(
    isTest: Boolean,
    databaseName: String,
    collectionName: String
): MongoCollection<ClientModel> {
    // Set the client to a local client if testing, otherwise use the Atlas client form .env file
    return if (isTest) {
        val client = KMongo.createClient()
        val database = client.getDatabase("test-database")
        database.getCollection()
    } else {
        val dotenv = Dotenv.load()
        val client = KMongo.createClient("mongodb+srv://${dotenv.get("USER_NAME")}:${dotenv.get("PASSWORD")}@coachr-client-db.blxcxzn.mongodb.net/")
        val database = client.getDatabase(databaseName)
        database.getCollection<ClientModel>(collectionName)
    }
}

class ClientMemStore(
    isTest: Boolean,
    databaseName: String,
    collectionName: String
): ClientStore {
    private val clientsCol = initializeMongoConnection(
        isTest,
        databaseName,
        collectionName
    )

    internal fun dropCollection() {
        clientsCol.drop()
    }

    /**
     * find all clients in client db
     *
     * @return client list
     */
    override fun findAllClients(): ArrayList<ClientModel> {
        val clientList: ArrayList<ClientModel> = arrayListOf()
        val allDocuments = clientsCol.find()
        for (doc in allDocuments) {
            clientList.add(doc)
        }
        return clientList
    }

    /**
     * find client matching name param
     *
     * @param clientName
     * @return client or null
     */
    override fun findClient(clientName: String): ClientModel? {
        return clientsCol.findOne {ClientModel::fullName eq clientName}
    }

    /**
     * find workout matching name param
     *
     * @param clientName
     * @param workoutName
     * @return found workout or null
     */
    override fun findWorkout(clientName: String, workoutName: String): WorkoutModel? {
        val client = findClient(clientName)
        return client!!.workoutPlan.find {
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
        val workout = findWorkout(clientName, workoutName)
        return workout!!.exercises.find{
            exercise -> exercise.name == exerciseName
        }
    }

    /**
     * create client from client param
     *
     * @param client
     */
    override fun createClient(client: ClientModel): Boolean {
        val result = clientsCol.insertOne(client.json)
        if (!result.wasAcknowledged())
            return false
        return true
    }

    /**
     * create workout from workout param for client from param
     *
     * @param client
     * @param workout
     */
    override fun createClientWorkout(client: ClientModel, workout: WorkoutModel): Boolean {
        client.workoutPlan.add(workout)
        val result = clientsCol.updateOne(ClientModel::fullName eq client.fullName, client)
        if (!result.wasAcknowledged())
            return false
        return true
    }

    /**
     * create exercise from param for workout from param
     *
     * @param workout
     * @param exercise
     */
    override fun createExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel): Boolean {
        client.workoutPlan.find {
                foundWorkout -> foundWorkout.name == workout.name
        }!!.exercises.add(exercise)
        val result = clientsCol.updateOne(ClientModel::fullName eq client.fullName, client)
        if (!result.wasAcknowledged())
            return false
        return true
    }

    /**
     * update client with client param details
     *
     * @param client
     */
    override fun updateClientDetails(clientDBRef: String, client: ClientModel): Boolean {
        val result = clientsCol.updateOne(ClientModel::fullName eq clientDBRef, client)
        if (result.wasAcknowledged())
            return true
        return false
    }

    /**
     * update workout with workout param details
     *
     * @param workout
     */
    override fun updateWorkoutDetails(client: ClientModel, oldWorkout: WorkoutModel, newWorkout: WorkoutModel): Boolean {
        client.workoutPlan.remove(oldWorkout)
        client.workoutPlan.add(newWorkout)
        val result = clientsCol.updateOne(ClientModel::fullName eq client.fullName, client)
        if (result.wasAcknowledged())
            return true
        return false
    }

    /**
     * update exercise in workout from param with exercise param details
     *
     * @param workout
     * @param exercise
     */
    override fun updateExercise(client: ClientModel, workout: WorkoutModel, oldExercise: ExerciseModel, newExercise: ExerciseModel): Boolean {
        client.workoutPlan.find {
                foundWorkout -> foundWorkout.name == workout.name
        }!!.exercises.remove(oldExercise)
        client.workoutPlan.find {
                foundWorkout -> foundWorkout.name == workout.name
        }!!.exercises.add(newExercise)
        val result = clientsCol.updateOne(ClientModel::fullName eq client.fullName, client)
        if (result.wasAcknowledged())
            return true
        return false
    }

    /**
     * delete client
     *
     * @param client
     */
    override fun deleteClient(client: ClientModel): Boolean {
        clientsCol.findOneAndDelete(ClientModel::fullName eq client.fullName)
        if (findClient(client.fullName) == null)
            return true
        return false
    }

    /**
     * delete workout from client params workout plan
     *
     * @param client
     * @param workout
     */
    override fun deleteWorkout(client: ClientModel, workout: WorkoutModel): Boolean {
        client.workoutPlan.find{
                foundWorkout -> foundWorkout.name == workout.name
        }?.let {
            client.workoutPlan.remove(it)
        }
        clientsCol.updateOne(ClientModel::fullName eq client.fullName, client)
        if (findWorkout(client.fullName, workout.name) == null)
            return true
        return false
    }

    /**
     * delete exercise from workout param exercise list
     *
     * @param workout
     * @param exercise
     */
    override fun deleteExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel): Boolean {
        client.workoutPlan.find { foundWorkout ->
            foundWorkout.name == workout.name
        }?.exercises?.remove(exercise)
        clientsCol.updateOne(ClientModel::fullName eq client.fullName, client)
        if (findExercise(client.fullName, workout.name, exercise.name) == null)
            return true
        return false
    }

    /**
     * loop through client arraylist param and log all clients
     *
     * @param clientList
     */
    internal fun logClients() {
        val clients = findAllClients()
        clients.forEach{
            println(it.fullName)
            println("Email Address: ${it.emailAddress}")
            println("Phone Number: ${it.phoneNumber}")
            println("Number of Workouts in Plan: ${it.workoutPlan.size}")
        }
    }

    /**
     * log all client names for use of client selection
     */
    internal fun logClientNames() {
        val clients = findAllClients()
        clients.forEachIndexed { index, client ->
            println("$index. ${client.fullName}")
        }
    }

    /**
     * loop through workout plan arraylist of client param and log all workouts
     *
     * @param client
     */
    internal fun logWorkouts(client: ClientModel) {
        client.workoutPlan.forEach{
            println(it.name)
            println("Workout Type: ${it.type}")
            println("Number of Exercises: ${it.exercises.size}")
        }
    }

    /**
     *  log all workout names for use of workout selection
     *
     * @param client
     */
    internal fun logWorkoutNames(client: ClientModel) {
        client.workoutPlan.forEachIndexed { index, workout ->
            println("$index. ${workout.name}")
        }
    }

    /**
     * loop through exercise arraylist of workout param and log all exercises
     *
     * @param workout
     */
    internal fun logExercises(workout: WorkoutModel) {
        workout.exercises.forEach{
            println(it.name)
            println("Exercise Description: ${it.description}")
            println("Exercise Sets: ${it.sets}")
            println("Exercise Reps: ${it.reps}")
            println("Exercise Reps in Reserve: ${it.repsInReserve}")
        }
    }

    /**
     * log all exercise names for use of exercise selection
     *
     * @param workout
     */
    internal fun logExerciseNames(workout: WorkoutModel) {
        workout.exercises.forEachIndexed { index, exercise ->
            println("$index. ${exercise.name}")
        }
    }

    /**
     * log all recursively
     *
     */
    internal fun logAll() {
        val clients = findAllClients()
        clients.forEach{ it ->
            println(it.fullName)
            println("Email Address: ${it.emailAddress}")
            println("Phone Number: ${it.phoneNumber}")
            println("Number of Workouts in Plan: ${it.workoutPlan.size}")
            it.workoutPlan.forEach{
                println(it.name)
                println("Workout Type: ${it.type}")
                println("Number of Exercises: ${it.exercises.size}")
                it.exercises.forEach{
                    println(it.name)
                    println("Exercise Description: ${it.description}")
                    println("Exercise Sets: ${it.sets}")
                    println("Exercise Reps: ${it.reps}")
                    println("Exercise Reps in Reserve: ${it.repsInReserve}")
                }
            }
        }
    }
}