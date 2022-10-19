package org.kryanbeane.coachr.console.controllers

import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import tornadofx.*

class WorkoutUIController: Controller() {
    val clients = ClientUIController().clients

    fun retrieveAllWorkouts(selectedClient: ClientModel): ArrayList<WorkoutModel> {
        return selectedClient.workoutPlan
    }

    /**
     * Create new workout from user input
     *
     * @param client client to add workout to
     * @param nameEntry workout name
     * @param typeEntry workout type
     * @return 0 if workout was created successfully, 1 if workout name or type is empty, 2 if another error occurred
     */
    fun createWorkout(client: ClientModel, nameEntry: String, typeEntry: String): Int {
        // Validate new workout details and if they're okay, create a new workout
        return if (nameEntry.isEmpty() || typeEntry.isEmpty())
            1
        else {
            if (clients.createClientWorkout(client, WorkoutModel(name = nameEntry, type = typeEntry)))
                0
            else
                2
        }
    }

    /**
     * Delete workout from database
     *
     * @param client who owns the workout
     * @param workout to delete
     * @return true if successful and false otherwise
     */
    fun deleteWorkout(client: ClientModel, workout: WorkoutModel): Boolean {
        return clients.deleteWorkout(client, workout)
    }

    /**
     * Update client's workout
     *
     * @param client who owns the workout
     * @param oldWorkout
     * @param updatedNameEntry
     * @param updatedTypeEntry
     * @return
     */
    fun updateWorkout(client: ClientModel, oldWorkout: WorkoutModel?, updatedNameEntry: String?, updatedTypeEntry: String?): Int {
        if (oldWorkout != null) {
            val newWorkout = oldWorkout.copy()
            if (updatedNameEntry!!.isNotEmpty())
                newWorkout.name = updatedNameEntry

            if (updatedTypeEntry!!.isNotEmpty()) {
                newWorkout.type = updatedTypeEntry
            }

            return if (clients.updateWorkoutDetails(client, oldWorkout, newWorkout))
                0
            else
                1
        }
        return 1
    }

}