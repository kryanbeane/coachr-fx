package org.kryanbeane.coachr.console.views

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.TabPane
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import org.controlsfx.control.Notifications
import org.kryanbeane.coachr.console.controllers.ClientUIController
import org.kryanbeane.coachr.console.controllers.ExerciseUIController
import org.kryanbeane.coachr.console.controllers.WorkoutUIController
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.ExerciseModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import tornadofx.*

class TornadoUI : View() {
    private var selectedClient: ClientModel? = null
    private var selectedWorkout: WorkoutModel? = null
    private var selectedExercise: ExerciseModel? = null

    /**
     * Client Stuff
     */
    private val clientCtr: ClientUIController by inject()
    private var fullNameField: TextField = textfield()
    private var emailAddressField: TextField = textfield()
    private var phoneNumberField: TextField = textfield()
    private var phonePrefixField: TextField = textfield()
    private var clients: ObservableList<ClientModel>? = clientCtr.retrieveAllClients().asObservable()
    private var clientTable: TableView<ClientModel>? = null

    /**
     * Workout Stuff
     */
    private val workoutCtr: WorkoutUIController by inject()
    private var workoutNameField: TextField = textfield()
    private var workoutTypeField: TextField = textfield()
    private var workouts: ObservableList<WorkoutModel>? = null
    private var workoutTable: TableView<WorkoutModel>? = null

    /**
     * Exercise Stuff
     */
    private val exerciseCtr: ExerciseUIController by inject()
    private var exerciseNameField: TextField = textfield()
    private var exerciseDescField: TextField = textfield()
    private var exerciseSetsField: TextField = textfield()
    private var exerciseRepsField: TextField = textfield()
    private var exerciseRIRField: TextField = textfield()
    private var exercises: ObservableList<ExerciseModel>? = null
    private var exerciseTable: TableView<ExerciseModel>? = null

    /**
     * Search Stuff
     */
    private var searchTextField: TextField = textfield()

    override val root = borderpane {
        prefWidth = 823.0

        center = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

            /**
             * CLIENT TAB
             */
            tab("Clients") {
                borderpane {
                    /**
                     * SEARCH MENU BAR
                     */
                    top = hbox {
                        // LOGO HBOX
                        hbox {
                            style {
                                padding = box(8.px, 4.px, 8.px, 4.px)
                                alignment = Pos.CENTER_LEFT
                            }
                            imageview("coachr-logo.png") {
                                fitWidth = 200.0
                                fitHeight = 50.0
                            }
                        }

                        // MENU HBOX
                        hbox {
                            style {
                                padding = box(8.px, 4.px, 8.px, 260.px)
                                alignment = Pos.CENTER_RIGHT
                            }
                            val choices = listOf("Full Name", "Email Address", "Phone Number")
                            val choice = SimpleStringProperty(choices.first())
                            combobox(choice, choices) {
                                paddingRight = 4.0
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 6.0
                                }
                            }

                            searchTextField = textfield() {
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 6.0
                                }
                            }

                            button("", FontAwesomeIconView(FontAwesomeIcon.SEARCH)) {
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 12.0
                                }
                            }
                        }
                    }

                    /**
                     * CLIENT LIST
                     */
                    clientTable = tableview(clients) {
                        column("Name", ClientModel::fullName)
                        column("Email", ClientModel::emailAddress)
                        column("Phone", ClientModel::phoneNumber)
                        columnResizePolicy = SmartResize.POLICY

                        onUserSelect {
                            selectedClient = it
                            exerciseTable!!.items = null
                            workoutTable?.items = it.workoutPlan.asObservable()

                            fullNameField.text = it.fullName
                            emailAddressField.text = it.emailAddress
                            phonePrefixField.text = it.phoneNumber.toString().take(3)
                            phoneNumberField.text = it.phoneNumber.toString().drop(3)
                        }
                    }
                    center = clientTable

                    /**
                     * CLIENT FORM
                     */
                    left = form {
                        fieldset("Client Information", FontAwesomeIconView(FontAwesomeIcon.USER)) {
                            field("Full Name") {
                                fullNameField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                fullNameField.maxWidth = 175.0
                            }
                            field("Email Address") {
                                emailAddressField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                emailAddressField.maxWidth = 175.0
                            }
                            field("Code / Number") {
                                phonePrefixField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                phonePrefixField.maxWidth = 40.0
                                phoneNumberField = textfield()
                                phoneNumberField.maxWidth = 125.0
                            }
                        }

                        vbox {
                            button("Create") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }

                                setOnAction {
                                    // Create the new client
                                    val createResponseCode = clientCtr.createClient(
                                        fullNameField.text.toString(),
                                        emailAddressField.text.toString(),
                                        phonePrefixField.text.toString() + phoneNumberField.text.toString(),
                                    )

                                    when (createResponseCode) {
                                        0 -> Notifications.create().title("Client Creation Info")
                                            .text("Client created successfully!").owner(this).showInformation()

                                        1 -> {
                                            Notifications.create().title("Client Creation Error")
                                                .text("Please enter a valid email address!").owner(this).showError()
                                            emailAddressField.clear()
                                        }

                                        2 -> {
                                            Notifications.create().title("Client Creation Error")
                                                .text("Please enter a valid phone number!").owner(this).showError()
                                            phoneNumberField.clear()
                                            phonePrefixField.clear()
                                        }

                                        3 -> Notifications.create().title("Client Creation Error")
                                            .text("An error occurred while creating the client!").owner(this)
                                            .showError()
                                    }

                                    if (createResponseCode == 0) {
                                        // Clear text fields
                                        fullNameField.clear()
                                        emailAddressField.clear()
                                        phoneNumberField.clear()
                                        phonePrefixField.clear()

                                        // Update client table
                                        clientTable!!.items = clientCtr.retrieveAllClients().asObservable()
                                    }
                                }
                            }

                            button("Update") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }

                                setOnAction {
                                    // Update the current client
                                    val updateResponseCode = clientCtr.updateClient(
                                        selectedClient,
                                        fullNameField.text.toString(),
                                        emailAddressField.text.toString(),
                                        phonePrefixField.text.toString() + phoneNumberField.text.toString(),
                                    )

                                    when (updateResponseCode) {
                                        0 -> {
                                            Notifications.create().title("Client Update Info")
                                                .text("Client ${fullNameField.text} updated successfully!").owner(this)
                                                .showInformation()

                                            // Clear text fields
                                            fullNameField.clear()
                                            emailAddressField.clear()
                                            phoneNumberField.clear()
                                            phonePrefixField.clear()

                                            // Update client table
                                            clientTable!!.items = clientCtr.retrieveAllClients().asObservable()
                                        }

                                        1 -> {
                                            Notifications.create().title("Client Update Error")
                                                .text("Please enter a valid email address!").owner(this).showError()
                                            emailAddressField.text = selectedClient?.emailAddress
                                        }

                                        2 -> {
                                            Notifications.create().title("Client Update Error")
                                                .text("Please enter a valid phone number!").owner(this).showError()
                                            phoneNumberField.text = selectedClient?.phoneNumber.toString().drop(3)
                                            phonePrefixField.text = selectedClient?.phoneNumber.toString().take(3)
                                        }
                                    }
                                }
                            }

                            button("Delete") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }
                                setOnAction {
                                    if (clientTable!!.selectedItem != null) {
                                        if (clientCtr.deleteClient(clientTable!!.selectedItem!!)) {
                                            // Send notification to confirm client deletion
                                            Notifications.create().title("Client Deletion")
                                                .text("Client ${clientTable!!.selectedItem!!.fullName} successfully deleted!")
                                                .owner(this).showInformation()

                                            // Update client table & clear fields & clear selected client
                                            fullNameField.clear()
                                            emailAddressField.clear()
                                            phoneNumberField.clear()
                                            phonePrefixField.clear()


                                            selectedClient = null
                                            clientTable!!.items = clientCtr.retrieveAllClients().asObservable()

                                        } else {
                                            // Send notification to say client deletion failed
                                            Notifications.create().title("Client Deletion")
                                                .text("Client deletion failed!").owner(this).showError()
                                        }
                                    }
                                }
                            }

                            button("Clear") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }
                                setOnAction {
                                    // Clear text fields
                                    fullNameField.clear()
                                    emailAddressField.clear()
                                    phoneNumberField.clear()
                                    phonePrefixField.clear()
                                    selectedClient = null
                                }
                            }
                        }
                    }
                }
            }

            /**
             * WORKOUT TAB
             */
            tab("Workouts") {
                borderpane {
                    /**
                     * SEARCH MENU BAR
                     */
                    top = hbox {
                        // LOGO HBOX
                        hbox {
                            style {
                                padding = box(8.px, 4.px, 8.px, 4.px)
                                alignment = Pos.CENTER_LEFT
                            }
                            imageview("coachr-logo.png") {
                                fitWidth = 200.0
                                fitHeight = 50.0
                            }
                        }

                        // MENU HBOX
                        hbox {
                            style {
                                padding = box(8.px, 4.px, 8.px, 288.px)
                                alignment = Pos.CENTER_RIGHT
                            }
                            val choices = listOf("Name", "Type")
                            val choice = SimpleStringProperty(choices.first())
                            combobox(choice, choices) {
                                paddingRight = 4.0
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 6.0
                                }
                            }

                            searchTextField = textfield() {
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 6.0
                                }
                            }

                            button("", FontAwesomeIconView(FontAwesomeIcon.SEARCH)) {
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 12.0
                                }
                            }
                        }
                    }

                    /**
                     * WORKOUT LIST
                     */
                    workoutTable = tableview {
                        column("Name", WorkoutModel::name).contentWidth(padding = 50.0)
                        column("Type", WorkoutModel::type)
                        columnResizePolicy = SmartResize.POLICY

                        onUserSelect {
                            selectedWorkout = it
                            exercises = it.exercises.asObservable()
                            exerciseTable?.items = exercises

                            workoutNameField.text = it.name
                            workoutTypeField.text = it.type
                        }

                    }
                    center = workoutTable

                    /**
                     * WORKOUT FORM
                     */
                    left = form {
                        fieldset("Workout Plan Information", FontAwesomeIconView(FontAwesomeIcon.TROPHY)) {
                            field("Name") {
                                workoutNameField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                paddingLeft = 6.0
                                paddingRight = 12.0
                                workoutTypeField.maxWidth = 250.0
                            }
                            field("Type") {
                                workoutTypeField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                paddingLeft = 6.0
                                paddingRight = 12.0
                                workoutTypeField.maxWidth = 250.0
                            }
                        }

                        vbox {
                            button("Create") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }

                                setOnAction {
                                    // Create the new client
                                    val createResponseCode = workoutCtr.createWorkout(
                                        selectedClient!!,
                                        workoutNameField.text.toString(),
                                        workoutTypeField.text.toString(),
                                    )

                                    when (createResponseCode) {
                                        0 -> {
                                            Notifications.create().title("Workout Creation Info").text("Workout created successfully!").owner(this).showInformation()
                                            workoutNameField.clear()
                                            workoutTypeField.clear()
                                            workoutTable!!.items = workoutCtr.retrieveAllWorkouts(selectedClient!!).asObservable()
                                        }

                                        1 -> Notifications.create().title("Workout Creation Error")
                                            .text("Please include all workout fields during creation!").owner(this)
                                            .showWarning()

                                        else -> Notifications.create().title("Workout Creation Error")
                                            .text("An error occurred while creating the workout!").owner(this)
                                            .showWarning()
                                    }
                                }
                            }

                            button("Update") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }

                                setOnAction {
                                    // Update the current client
                                    val updateResponseCode = workoutCtr.updateWorkout(
                                        selectedClient!!,
                                        selectedWorkout!!,
                                        workoutNameField.text.toString(),
                                        workoutTypeField.text.toString(),
                                    )

                                    when (updateResponseCode) {
                                        0 -> {
                                            Notifications.create().title("Workout Update Info")
                                                .text("Workout ${workoutNameField.text} updated successfully!")
                                                .owner(this).showInformation()
                                            fullNameField.clear()
                                            emailAddressField.clear()
                                            workoutTable!!.items = workoutCtr.retrieveAllWorkouts(selectedClient!!).asObservable()
                                        }

                                        else -> {
                                            Notifications.create().title("Client Update Error")
                                                .text("Please enter a valid email address!").owner(this).showError()
                                            emailAddressField.text = selectedClient?.emailAddress
                                        }
                                    }
                                }
                            }

                            button("Delete") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }
                                setOnAction {
                                    if (workoutTable!!.selectedItem != null) {
                                        if (workoutCtr.deleteWorkout(selectedClient!!, workoutTable!!.selectedItem!!)) {
                                            // Send notification to confirm client deletion
                                            Notifications.create().title("Client Deletion")
                                                .text("Client ${workoutTable!!.selectedItem!!.name} successfully deleted!")
                                                .owner(this).showInformation()

                                            // Update table & clear fields
                                            workoutNameField.clear()
                                            workoutTypeField.clear()
                                            exerciseNameField.clear()
                                            exerciseDescField.clear()
                                            exerciseSetsField.clear()
                                            exerciseRepsField.clear()
                                            exerciseRIRField.clear()
                                            selectedWorkout = null
                                            exerciseTable!!.items = null
                                            workoutTable!!.items = workoutCtr.retrieveAllWorkouts(selectedClient!!).asObservable()

                                        } else {
                                            // Send notification to say client deletion failed
                                            Notifications.create().title("Client Deletion")
                                                .text("Client deletion failed!").owner(this).showError()
                                        }
                                    }
                                }
                            }

                            button("Clear") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }
                                setOnAction {
                                    // Clear text fields
                                    workoutNameField.clear()
                                    workoutTypeField.clear()
                                    selectedWorkout = null
                                }
                            }
                        }
                    }
                }
            }

            /**
             * EXERCISES TAB
             */
            tab("Exercises") {
                borderpane {
                    /**
                     * SEARCH MENU BAR
                     */
                    top = hbox {
                        // LOGO HBOX
                        hbox {
                            style {
                                padding = box(8.px, 4.px, 8.px, 4.px)
                                alignment = Pos.CENTER_LEFT
                            }
                            imageview("coachr-logo.png") {
                                fitWidth = 200.0
                                fitHeight = 50.0
                            }
                        }

                        // MENU HBOX
                        hbox {
                            style {
                                padding = box(8.px, 4.px, 8.px, 288.px)
                                alignment = Pos.CENTER_RIGHT
                            }
                            val choices = listOf("Name")
                            val choice = SimpleStringProperty(choices.first())
                            combobox(choice, choices) {
                                paddingRight = 4.0
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 6.0
                                }
                            }

                            searchTextField = textfield() {
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 6.0
                                }
                            }

                            button("", FontAwesomeIconView(FontAwesomeIcon.SEARCH)) {
                                hboxConstraints {
                                    marginLeft = 6.0
                                    marginRight = 12.0
                                }
                            }
                        }
                    }

                    /**
                     * EXERCISE LIST
                     */
                    exerciseTable = tableview {
                        columnResizePolicy = SmartResize.POLICY

                        column("Name", ExerciseModel::name).fixedWidth(100.0)
                        column("Description", ExerciseModel::description).remainingWidth()
                        column("Sets", ExerciseModel::sets).fixedWidth(40.0)
                        column("Reps", ExerciseModel::reps).fixedWidth(40.0)
                        column("RIR", ExerciseModel::repsInReserve).fixedWidth(40.0)

                        onUserSelect {
                            selectedExercise = it
                            exerciseNameField.text = it.name
                            exerciseDescField.text = it.description
                            exerciseSetsField.text = it.sets.toString()
                            exerciseRepsField.text = it.reps.toString()
                            exerciseRIRField.text = it.repsInReserve.toString()
                        }

                    }
                    center = exerciseTable

                    /**
                     * EXERCISE FORM
                     */
                    left = form {
                        fieldset("Exercise Information", FontAwesomeIconView(FontAwesomeIcon.TROPHY)) {
                            field("Name") {
                                exerciseNameField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                paddingLeft = 6.0
                                paddingRight = 12.0
                                exerciseNameField.maxWidth = 250.0
                            }
                            field("Description") {
                                exerciseDescField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                paddingLeft = 6.0
                                paddingRight = 12.0
                                exerciseDescField.maxWidth = 250.0
                            }
                            field("Sets") {
                                exerciseSetsField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                paddingLeft = 6.0
                                paddingRight = 12.0
                                exerciseSetsField.maxWidth = 250.0
                            }
                            field("Reps") {
                                exerciseRepsField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                paddingLeft = 6.0
                                paddingRight = 12.0
                                exerciseRepsField.maxWidth = 250.0
                            }
                            field("Reps In Reserve") {
                                exerciseRIRField = textfield()
                                paddingTop = 4.0
                                paddingBottom = 4.0
                                paddingLeft = 6.0
                                paddingRight = 12.0
                                exerciseRIRField.maxWidth = 250.0
                            }
                        }
                        vbox {
                            button("Create") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }

                                setOnAction {
                                    // Create the new exercise
                                    if (selectedWorkout != null) {
                                        val createResponseCode = exerciseCtr.createExercise(
                                            selectedClient!!,
                                            selectedWorkout!!,
                                            exerciseNameField.text.toString(),
                                            exerciseDescField.text.toString(),
                                            exerciseSetsField.text.toString().toInt(),
                                            exerciseRepsField.text.toString().toInt(),
                                            exerciseRIRField.text.toString().toInt()
                                        )
                                        println(createResponseCode)
                                        when (createResponseCode) {
                                            0 -> {
                                                Notifications.create().title("Exercise Creation Info").text("Exercise created successfully!").owner(this).showInformation()
                                                exerciseNameField.clear()
                                                exerciseDescField.clear()
                                                exerciseSetsField.clear()
                                                exerciseRepsField.clear()
                                                exerciseRIRField.clear()

                                                exerciseTable!!.items = exerciseCtr.retrieveAllExercises(selectedClient!!, selectedWorkout!!).asObservable()
                                            }
                                            else -> Notifications.create().title("Exercise Creation Error").text("An error occurred while creating the exercise!").owner(this).showWarning()
                                        }
                                    } else
                                        Notifications.create().title("Exercise Creation Error").text("Please select a workout first!").owner(this).showWarning()

                                }
                            }

                            button("Update") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }

                                setOnAction {
                                    // Update the current client
                                    val updateResponseCode = exerciseCtr.updateExercise(
                                        selectedClient!!,
                                        selectedWorkout!!,
                                        selectedExercise!!,
                                        exerciseNameField.text.toString(),
                                        exerciseDescField.text.toString(),
                                        exerciseSetsField.text.toString().toInt(),
                                        exerciseRepsField.text.toString().toInt(),
                                        exerciseRIRField.text.toString().toInt()
                                    )

                                    when (updateResponseCode) {
                                        0 -> {
                                            Notifications.create().title("Exercise Update Info")
                                                .text("Exercise ${workoutNameField.text} updated successfully!")
                                                .owner(this).showInformation()
                                            fullNameField.clear()
                                            emailAddressField.clear()

                                            exerciseTable!!.items = exerciseCtr.retrieveAllExercises(selectedClient!!, selectedWorkout!!).asObservable()
                                        }

                                        else -> {
                                            Notifications.create().title("Exercise Update Error")
                                                .text("!").owner(this).showError()
                                            emailAddressField.text = selectedClient?.emailAddress
                                        }
                                    }
                                }
                            }

                            button("Delete") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }
                                setOnAction {
                                    if (selectedExercise != null) {
                                        if (exerciseCtr.deleteExercise(
                                                selectedClient!!,
                                                selectedWorkout!!,
                                                selectedExercise!!
                                            )
                                        ) {
                                            // Send notification to confirm client deletion
                                            Notifications.create().title("Client Deletion").text("Client ${workoutTable!!.selectedItem!!.name} successfully deleted!").owner(this).showInformation()

                                            // Update client table & clear fields
                                            exerciseNameField.clear()
                                            exerciseDescField.clear()
                                            exerciseSetsField.clear()
                                            exerciseRepsField.clear()
                                            exerciseRIRField.clear()
                                            selectedExercise = null

                                            exerciseTable!!.items = exerciseCtr.retrieveAllExercises(selectedClient!!, selectedWorkout!!).asObservable()

                                        } else {
                                            // Send notification to say client deletion failed
                                            Notifications.create().title("Client Deletion")
                                                .text("Client deletion failed!").owner(this).showError()
                                        }
                                    }
                                }
                            }

                            button("Clear") {
                                vboxConstraints {
                                    fitToParentWidth()
                                    maxWidth = 270.0
                                    marginTop = 4.0
                                    marginBottom = 4.0
                                    marginLeft = 12.0
                                    marginRight = 12.0
                                    alignment = Pos.CENTER
                                }
                                setOnAction {
                                    exerciseNameField.clear()
                                    exerciseDescField.clear()
                                    exerciseSetsField.clear()
                                    exerciseRepsField.clear()
                                    exerciseRIRField.clear()
                                    selectedExercise = null
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

