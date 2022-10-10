package org.kryanbeane.coachr.console.models

import com.mongodb.client.*
import mu.KotlinLogging
import java.util.*
import kotlin.collections.ArrayList
import org.litote.kmongo.*
import io.github.cdimascio.dotenv.Dotenv

private val logger = KotlinLogging.logger {}

private fun initializeMongoConnection(): MongoCollection<ClientModel> {
    val client =  KMongo.createClient("mongodb+srv://${Dotenv.load().get("USER_NAME")}:${Dotenv.load().get("PASSWORD")}@coachr-client-db.blxcxzn.mongodb.net/")
    val database = client.getDatabase("coachr-client-db")
    return database.getCollection<ClientModel>("coach-clients")
}

class ClientMemStore: ClientStore {
    private val clientsCol = initializeMongoConnection()
    private val clients = ArrayList<ClientModel>()

    /**
     * find all clients in client db
     *
     * @return client list
     */
    override fun findAll(): ArrayList<ClientModel> {
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
        findClient(client.fullName)
            ?: return true
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
        findWorkout(client.fullName, workout.name)
            ?: return true
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
        findExercise(client.fullName, workout.name, exercise.name)
            ?: return true
        return false
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
        clients.forEach{
            logger.info {
                "Client Name: " + it.fullName + "\n"
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
        client.workoutPlan.forEach{
            logger.info {
                "Workout Name: " + it.name + "\n"
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
        workout.exercises.forEach{
            logger.info {
                "Exercise Name: " + it.name + "\n"
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