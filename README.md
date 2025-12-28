# Sudoku Arena

Sudoku Arena is a desktop application developed as a course project.  
The project represents a full-featured Sudoku game with user authentication, result tracking, and administrative functionality.

## Project Goal
The goal of this project is to demonstrate practical skills in:
- Object-Oriented Programming
- JavaFX application development
- Working with databases using JDBC
- Applying MVC architecture
- Role-based access control

## Functional Description

### User Functionality
- User registration and authentication
- Login system with role separation (USER / ADMIN)
- Sudoku gameplay with multiple difficulty levels
- Game timer
- Validation of user input
- Automatic result saving after each attempt
- Display of game completion status (solved / not solved)

### Admin Functionality
- Access to an administrative panel
- Viewing all registered users
- Viewing all game results
- Sorting results by creation date
- Database initialization from the UI

## Technologies Used
- **Java 17**
- **JavaFX** (UI and application logic)
- **FXML** (UI layout)
- **Maven** (dependency management)
- **SQLite** (embedded database)
- **JDBC** (database interaction)

## Architecture
The project follows the **MVC (Model–View–Controller)** architecture:
- `model` — application entities and game logic
- `controller` — JavaFX controllers
- `dao` — database access layer
- `resources/fxml` — UI layout files
- `resources/css` — application styles

## Database
The application uses SQLite as a local database.
Stored data includes:
- User accounts
- User roles
- Game results (difficulty, time, solved status, timestamp)

## How to Run
1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Make sure Java 17 is selected
4. Run the `MainApp` class

## Notes
- Database tables are created automatically on first launch
- Admin account is created automatically if it does not exist

## Authors
Mukhit Abylai, Ergesh Beknur
