package main

import (
	"log"
	"os"

	"prac_go/internal/server"
)

func main() {
	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}

	log.Println("Server started on port", port)
	server.StartServer(port)
}
