package logger

import (
	"io"
	"log"
	"os"
)

func init() {
	logFile, err := os.OpenFile("server.log", os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
	if err != nil {
		log.Fatal("Error opening log file:", err)
	}
	log.SetOutput(io.MultiWriter(os.Stdout, logFile))
}

func LogError(message string) {
	log.Println("Error: " + message)
}

func LogInfo(message string) {
	log.Println("Info: " + message)
}
