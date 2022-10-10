package org.kryanbeane.coachr.console.models

interface ClientStore {
    fun findAll(): ArrayList<ClientModel>
    fun findClient(clientName: String): ClientModel?
    fun findWorkout(clientName: String, workoutName: String): WorkoutModel?
    fun findExercise(clientName: String, workoutName: String, exerciseName: String): ExerciseModel?
    fun createClient(client: ClientModel): Boolean
    fun createClientWorkout(client: ClientModel, workout: WorkoutModel): Boolean
    fun createExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel): Boolean
    fun updateClientDetails(clientDBRef: String, client: ClientModel): Boolean
    fun updateWorkoutDetails(client: ClientModel, oldWorkout: WorkoutModel, newWorkout: WorkoutModel): Boolean
    fun updateExercise(client: ClientModel, workout: WorkoutModel, oldExercise: ExerciseModel, newExercise: ExerciseModel): Boolean
    fun deleteClient(client: ClientModel): Boolean
    fun deleteWorkout(client: ClientModel, workout: WorkoutModel): Boolean
    fun deleteExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel): Boolean
}