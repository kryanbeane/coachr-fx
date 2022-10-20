package org.kryanbeane.coachr.console.controllers

import javafx.beans.property.StringProperty
import org.kryanbeane.coachr.console.controllers.old.WorkoutController
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.ExerciseModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import tornadofx.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseUIController: Controller() {
    val clients = ClientUIController().clients

    /**
     * Retrieve all exercises for a given workout
     *
     * @param selectedWorkout workout to retrieve exercises for
     * @return list of exercises
     */
    fun retrieveAllExercises(selectedClient: ClientModel, selectedWorkout: WorkoutModel): ArrayList<ExerciseModel> {
        return clients.findClient(selectedClient.fullName)!!.workoutPlan.find { it.name == selectedWorkout.name }!!.exercises
    }

    /**
     * Create a new exercise
     *
     * @param client who owns the workout
     * @param workout to add exercise to
     * @param nameEntry exercise name
     * @param descEntry exercise description
     * @param repsEntry exercise reps
     * @param setsEntry exercise sets
     * @param rirEntry exercise rir
     * @return 0 if exercise was created successfully, 1 if exercise value is empty, 2 if another error occurred
     */
    fun createExercise(client: ClientModel?, workout: WorkoutModel?, nameEntry: String, descEntry: String, repsEntry: Int, setsEntry: Int, rirEntry: Int): Int {
        if (client != null) {
            if (workout != null) {
                return if (nameEntry.isEmpty() || descEntry.isEmpty() || repsEntry.toString().isEmpty() || setsEntry.toString().isEmpty() || rirEntry.toString().isEmpty())
                    1
                else
                    if (clients.createExercise(client, workout,
                            ExerciseModel(
                                name = nameEntry,
                                description = descEntry,
                                sets = setsEntry,
                                reps = repsEntry,
                                repsInReserve = rirEntry
                            )))
                        // Return 0 if successful
                        0
                    else
                        2
            }
            return 2
        }
        return 2
    }

    /**
     * Delete exercise from database
     *
     * @param client who owns the workout
     * @param workout who owns the exercise
     * @param exercise to delete
     * @return true if successful and false otherwise
     */
    fun deleteExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel): Boolean {
        return clients.deleteExercise(client, workout, exercise)
    }

    /**
     * Update exercise
     *
     * @param client who owns the workout
     * @param workout who owns the exercise
     * @param oldExercise to update
     * @param updatedNameEntry new name
     * @param updatedDescEntry exercise description
     * @param updatedSetsEntry exercise sets
     * @param updatedRepsEntry exercise reps
     * @param updatedRirEntry exercise rir
     * @return 0 if successful, 1 if any exercise value is empty, 2 if another error occurred
     */
    fun updateExercise(
        client: ClientModel,
        workout: WorkoutModel,
        oldExercise: ExerciseModel?,
        updatedNameEntry: String?,
        updatedDescEntry: String?,
        updatedSetsEntry: Int?,
        updatedRepsEntry: Int?,
        updatedRirEntry: Int?
    ): Int {
        if (oldExercise != null) {
            // Check all new entries for values and update if present otherwise leave the same
            val newExercise = oldExercise.copy()
            if (updatedNameEntry!!.isNotEmpty())
                newExercise.name = updatedNameEntry
            if (updatedDescEntry!!.isNotEmpty())
                newExercise.description = updatedDescEntry
            if (updatedSetsEntry.toString().isNotEmpty())
                newExercise.sets = updatedSetsEntry!!.toInt()
            if (updatedRepsEntry.toString().isNotEmpty())
                newExercise.reps = updatedRepsEntry!!.toInt()
            if (updatedRirEntry.toString().isNotEmpty())
                newExercise.repsInReserve = updatedRirEntry!!.toInt()

            return if (clients.updateExercise(client, workout, oldExercise, newExercise))
                0
            else
                1
        }
        return 1
    }

    fun searchExerciseSubstring(client: ClientModel, workout: WorkoutModel, searchString: StringProperty, searchType: String): ArrayList<ExerciseModel> {
        val foundExercises = arrayListOf<ExerciseModel>()
        val allExercises = retrieveAllExercises(client, workout)

        when (searchType) {
            "Name" -> allExercises.forEach {
                if (it.name.lowercase(Locale.getDefault()).contains(searchString.value.lowercase(Locale.getDefault())))
                    foundExercises.add(it)
            }
            "Description" -> allExercises.forEach {
                if (it.description.lowercase(Locale.getDefault()).contains(searchString.value.lowercase(Locale.getDefault())))
                    foundExercises.add(it)
            }
            "Sets" -> allExercises.forEach {
                if (it.sets.toString().lowercase(Locale.getDefault()).contains(searchString.value.lowercase(Locale.getDefault())))
                    foundExercises.add(it)
            }
            "Reps" -> allExercises.forEach {
                if (it.reps.toString().lowercase(Locale.getDefault()).contains(searchString.value.lowercase(Locale.getDefault())))
                    foundExercises.add(it)
            }
            "Reps In Reserve" -> allExercises.forEach {
                if (it.repsInReserve.toString().lowercase(Locale.getDefault()).contains(searchString.value.lowercase(Locale.getDefault())))
                    foundExercises.add(it)
            }
        }
        return foundExercises
    }
}