package org.kryanbeane.coachr.console.models

interface ClientStore {
    fun findAll(): List<ClientModel>
    fun findClientByName(clientName: String): ClientModel?
    fun findWorkout(clientName: String, workoutName: String): WorkoutModel?
    fun findExercise(clientName: String, workoutName: String, exerciseName: String): ExerciseModel?
    fun createClient(client: ClientModel)
    fun createClientWorkout(client: ClientModel, workout: WorkoutModel)
    fun createExercise(workout: WorkoutModel, exercise: ExerciseModel)
    fun updateClientDetails(client: ClientModel)
    fun updateClientWorkout(client: ClientModel, workout: WorkoutModel)
    fun updateExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel)
    fun deleteClient(client: ClientModel)
    fun deleteWorkout(client: ClientModel, workout: WorkoutModel)
    fun deleteExercise(workout: WorkoutModel, exercise: ExerciseModel)
}