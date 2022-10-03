package org.kryanbeane.coachr.console.views

import javafx.geometry.Pos
import org.kryanbeane.coachr.console.controllers.WorkoutUIController
import org.kryanbeane.coachr.console.models.WorkoutModel
import tornadofx.*

class WorkoutViewUI : View() {
    private val controller: WorkoutUIController by inject()

    override val root = borderpane {
        prefWidth = 823.0

        top = menubar {
            style {
                alignment = Pos.CENTER_LEFT
                padding = box(5.px)
            }
            menu().graphic = imageview("coachr-logo.png") {
                fitHeight = 50.0
                fitWidth = 150.0
            }
            menu("Clients") {
                item("Edit").action {
                    replaceWith<MainClientUI>()
                }
                item("List").action {
                    replaceWith<ClientListUI>()
                }
            }
            menu("Workouts") {
                item("Edit").action {

                }
                item("List").action {

                }
            }
            menu("Exercises") {
                item("Edit").action {

                }
                item("List").action {

                }
            }
        }

        left = form {
            fieldset{
                field("Workout Name") {
                    textfield()

                }
                field("Workout Type") {
                    textfield()
                }
            }
        }


        center = tableview {
            items = controller.retrieveAllWorkouts().asObservable()
            column("Workout Name", WorkoutModel::name)
            column("Workout Type", WorkoutModel::type)
        }
    }

    class Styles : Stylesheet() {
        companion object {
            val menuStyle by cssclass()
        }
        init {
            menuStyle {
                backgroundColor += c("red")
                alignment = Pos.CENTER_RIGHT
            }
        }
    }

}