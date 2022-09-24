package org.kryanbeane.coachr.console.models

import java.util.*

interface ClientStore {
    fun findAll(): List<ClientModel>
    fun findClient(clientId: UUID): ClientModel?
    fun findWorkout(clientId: UUID, workoutId: UUID): WorkoutModel?
    fun findExercise(clientId: UUID, workoutId: UUID, exerciseId: UUID): ExerciseModel?
    fun createClient(client: ClientModel)
    fun createClientWorkout(client: ClientModel, workout: WorkoutModel)
    fun createExercise(workout: WorkoutModel, exercise: ExerciseModel)
    fun updateClientDetails(client: ClientModel)
    fun updateClientWorkout(client: ClientModel, workout: WorkoutModel)
    fun updateExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel)
}