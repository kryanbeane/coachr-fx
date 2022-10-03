package org.kryanbeane.coachr.console.controllers

import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import tornadofx.*

class WorkoutUIController: Controller() {
    private lateinit var selectedClient: ClientModel

    fun retrieveAllWorkouts(): ArrayList<WorkoutModel> {
        return selectedClient.workoutPlan
    }
}