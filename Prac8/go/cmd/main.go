package main

import (
	"log"
	"os"

	"Prac8/internal/server"
)

func main() {
	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}

	log.Println("Server started on port", port)
	server.StartServer(port)
}
