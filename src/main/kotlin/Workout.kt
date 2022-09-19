data class Workout(
    var type: String,
    var exercises: MutableMap<String, Exercise>
) {

    // Add exercise to a workout
    fun addExerciseToWorkout(exerciseName: String, desc: String, sets: Int, reps: Int, rir: Int) {
        val newExercise = Exercise(desc, sets, reps, rir)

        if (!exercises.contains(exerciseName)) {
            this.exercises[exerciseName] = newExercise
        }
    }

}