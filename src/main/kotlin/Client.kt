data class Client(
    var phoneNumber: Long,
    var emailAddress: String,
    // Map for the client's workout plan containing the name as the key and the workout as the value
    var workoutPlan: MutableMap<String, Workout>
) {

    fun addWorkoutToPlan(workoutName: String, workout: Workout) {
        this.workoutPlan[workoutName] = workout
    }

}