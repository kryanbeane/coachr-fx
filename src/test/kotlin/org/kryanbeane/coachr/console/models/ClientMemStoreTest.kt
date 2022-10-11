package org.kryanbeane.coachr.console.models

import org.junit.jupiter.api.*
import org.kryanbeane.coachr.console.controllers.ClientController
import org.junit.jupiter.api.Assertions.*
import java.util.*

class ClientMemStoreTest {
    private val clientController = ClientController(
        "coachr-client-db",
        "test-collection"
    )

    private val testClient = ClientModel(
        UUID.randomUUID(),
        "Test Client Full Name",
        353851234567,
        "foobar@foo.bar",
        arrayListOf(
            WorkoutModel(
                UUID.randomUUID(),
                "Test Workout Name One",
                "Test Workout Type One",
                arrayListOf(
                    ExerciseModel(
                        UUID.randomUUID(),
                        "Test Exercise Name One",
                        "Test Exercise Type One",
                        3,
                        10,
                        1
                    ),
                    ExerciseModel(
                        UUID.randomUUID(),
                        "Test Exercise Name Two",
                        "Test Exercise Type Two",
                        5,
                        5,
                        1
                    )
                )
            ),
            WorkoutModel(
                UUID.randomUUID(),
                "Test Workout Name Two",
                "Test Workout Type Two",
                arrayListOf()
            )
        )
    )
    private val db = clientController.clients

    @BeforeEach
    fun setUp() {
        db.createClient(testClient)
    }

    @AfterEach
    fun tearDown() {
        db.dropCollection()
    }

    @Test
    fun findAll() {
        val clientList = db.findAll()
        assertEquals(1, clientList.size)
        assertEquals(testClient, clientList[0])
    }

    @Test
    fun findClient() {
        assertEquals(testClient, db.findClient(testClient.fullName))
    }

    @Test
    fun findWorkout() {
        val workout = db.findWorkout(testClient.fullName, testClient.workoutPlan[0].name)
        assertEquals(testClient.workoutPlan[0], workout)
    }

    @Test
    fun findExercise() {
        val exercise = db.findExercise(
            testClient.fullName,
            testClient.workoutPlan[0].name,
            testClient.workoutPlan[0].exercises[0].name
        )
        assertEquals(testClient.workoutPlan[0].exercises[0], exercise)
    }

    @Test
    fun createClient() {
        assertTrue(
            db.createClient(
                ClientModel(
                    fullName = "Test Client Full Name",
                    emailAddress = "foobar@foo.bar",
                    phoneNumber = 353851234567,
                    workoutPlan = arrayListOf()
                )
            )
        )
    }

    @Test
    fun createClientWorkout() {
        assertTrue(
            db.createClientWorkout(
                testClient,
                WorkoutModel(
                    UUID.randomUUID(),
                    "Test Client Workout",
                    "Test Workout Type",
                    arrayListOf()
                )
            )
        )
    }

    @Test
    fun createExercise() {
        assertTrue(
            db.createExercise(
                testClient,
                testClient.workoutPlan[0],
                ExerciseModel(
                    UUID.randomUUID(),
                    "Test Exercise Name",
                    "Test Exercise Description",
                    3,
                    5,
                    1
                )
            )
        )
    }

    @Test
    fun updateClientDetails() {
        val testClientCopy = testClient.copy()
        testClientCopy.emailAddress = "updatedfoobar@bar.foo"
        assertTrue(
            db.updateClientDetails(
                testClient.fullName,
                testClientCopy
            )
        )
    }

    @Test
    fun updateWorkoutDetails() {
        val testClientWorkoutCopy = testClient.workoutPlan[0].copy()
        testClientWorkoutCopy.name = "New Test Client Workout Copy Name"
        assertTrue(
            db.updateWorkoutDetails(
                testClient,
                testClient.workoutPlan[0],
                testClientWorkoutCopy
            )
        )
    }

    @Test
    fun updateExercise() {
        val testClientExerciseCopy = testClient.workoutPlan[0].exercises[0].copy()
        testClientExerciseCopy.name = "New Test Client Exercise Copy Name"
        assertTrue(
            db.updateExercise(
                testClient,
                testClient.workoutPlan[0],
                testClient.workoutPlan[0].exercises[0],
                testClientExerciseCopy
            )
        )
    }

    @Test
    fun deleteClient() {
        assertTrue(
            db.deleteClient(
                ClientModel(
                    UUID.randomUUID(),
                    "Test Client Full Name",
                    353851234567,
                    "foobar@foo.bar",
                    arrayListOf()
                )
            )
        )
    }

    @Test
    fun deleteWorkout() {
        assertTrue(
            db.deleteWorkout(
                testClient,
                testClient.workoutPlan[0]
            )
        )
    }

    @Test
    fun deleteExercise() {
        assertTrue(
            db.deleteExercise(
                testClient,
                testClient.workoutPlan[0],
                testClient.workoutPlan[0].exercises[0]
            )
        )
    }
}