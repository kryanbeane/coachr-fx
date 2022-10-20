# coachr
> A Client-Management application for Personal Trainers, Coaches, and Fitness Enthusiasts

## Installation
Clone this repository and open into your favourite IDE (IntelliJ, VSCode..)
```bash
git clone git@github.com:kryanbeane/coachr-fx.git
```

## Configuration
Create a `.env` file in the project root folder:
```bash
USER_NAME=<mongo-user-name>
PASSWORD=<mongo-password>
```
### My Database (Recommended):
Credentials for the database used for this project should have already been received via a Secret Gist on slack which can be used for testing purposes during grading. If not, please reach out!

### Your Own Database (Not recommended):
You can create your own Atlas MongoDB and configure the project to use that database to avoid needing to use my own. This can be achieved by doing the following:
- Navigate to [this line of code](https://github.com/kryanbeane/coachr-fx/blob/7ee731a9bf93962f61a4f3cea92d6baed4d421a5/src/main/kotlin/org/kryanbeane/coachr/console/models/ClientMemStore.kt#L29) in your cloned project and change the url string parts from my database to that of your database. 
- You should already have a `.env` file created so navigate to `Database Access` on your database which can usually be found at `https://cloud.mongodb.com/v2/<your-random-hash>#security/database/users` and create a new database administrative user who has at least read and write access.
- Now you should be ready to run your app!

### Local Database (Works great if needed)
You can run the test database on localhost which does not have any preloaded data but the data on it is persistent if you decide to add any. To do this you just need to do the following:
- Navigate to [this line of code](https://github.com/kryanbeane/coachr-fx/blob/7ee731a9bf93962f61a4f3cea92d6baed4d421a5/src/main/kotlin/org/kryanbeane/coachr/console/controllers/ClientUIController.kt#L13) in your cloned project and change the Boolean value from `false` to `true`. This will allow you to use the local database that is configured for use during unit tests and works the exact same way, just not in the cloud!

## Maintainers
This project is mantained by:
* [Bryan Keane](http://github.com/kryanbeane)
