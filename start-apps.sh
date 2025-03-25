#!/bin/sh

# Start the Spring Boot application in the background
./mvnw -ntp --batch-mode &
SPRING_PID=$!

# Change to the farmerApp directory and start the React application
cd src/main/webapp/farmerApp
npm start &
REACT_PID=$!

# Function to handle cleanup on script termination
cleanup() {
    echo "Shutting down applications..."
    kill $SPRING_PID
    kill $REACT_PID
    exit 0
}

# Set up trap for cleanup
trap cleanup SIGINT SIGTERM

# Wait for both processes
wait $SPRING_PID $REACT_PID