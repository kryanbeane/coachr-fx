package org.kryanbeane.coachr.console.models

import mu.KotlinLogging
import java.util.*
import kotlin.collections.ArrayList

private val logger = KotlinLogging.logger {}

class ClientMemStore: ClientStore {
    private val clients = ArrayList<ClientModel>()

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
    override fun findClient(clientName: String): ClientModel? {
        return clients.find{
                client -> client.fullName == clientName
        }
    }

    /**
     * find workout matching workoutId param
     *
     * @param clientId
     * @param workoutId
     * @return workout or null
     */
    override fun findWorkout(clientName: String, workoutName: String): WorkoutModel? {
        return findClient(clientName)?.workoutPlan?.find{
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
        logClients()
    }

    /**
     * create workout from workout param for client from param
     *
     * @param client
     * @param workout
     */
    override fun createClientWorkout(client: ClientModel, workout: WorkoutModel) {
        client.workoutPlan.add(workout)
        logWorkouts(client)
    }

    /**
     * create exercise from param for workout from param
     *
     * @param workout
     * @param exercise
     */
    override fun createExercise(workout: WorkoutModel, exercise: ExerciseModel) {
        workout.exercises.add(exercise)
        logExercises(workout)
    }

    /**
     * update client with client param details
     *
     * @param client
     */
    override fun updateClientDetails(client: ClientModel) {
        val foundClient = findClient(client.fullName)
        if (foundClient != null) {
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
    }

    /**
     * delete client
     *
     * @param client
     */
    override fun deleteClient(client: ClientModel) {
        clients.remove(client)
    }

    /**
     * delete workout from client params workout plan
     *
     * @param client
     * @param workout
     */
    override fun deleteWorkout(client: ClientModel, workout: WorkoutModel) {
        client.workoutPlan.remove(workout)
    }

    /**
     * delete exercise from workout param exercise list
     *
     * @param workout
     * @param exercise
     */
    override fun deleteExercise(workout: WorkoutModel, exercise: ExerciseModel) {
        workout.exercises.remove(exercise)
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
                "Client ID: " + it.id + "\n" +
                "Email Address: " + it.emailAddress + "\n" +
                "Phone Number: " + it.phoneNumber.rawInput + "\n" +
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
                "Workout ID: " + it.id + "\n" +
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
                "Exercise ID: " + it.id + "\n" +
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
                "Client ID: " + it.id + "\n" +
                "Email Address: " + it.emailAddress + "\n" +
                "Phone Number: " + it.phoneNumber.rawInput + "\n" +
                "Number of Workouts in Plan: " + it.workoutPlan.size + "\n" + "\n"
            }
            it.workoutPlan.forEach{
                logger.info {
                    it.name + "\n" +
                    "Workout ID: " + it.id + "\n" +
                    "Workout Type: " + it.type + "\n" +
                    "Number of Exercises in Plan: " + it.exercises.size + "\n" + "\n"
                }
                it.exercises.forEach{
                    logger.info {
                        it.name + "\n" +
                        "Exercise ID: " + it.id + "\n" +
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