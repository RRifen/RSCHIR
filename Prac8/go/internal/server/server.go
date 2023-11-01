package server

import (
	"Prac8/pkg/api"
	"Prac8/pkg/logger"
	"net/http"
)

func StartServer(port string) {
	http.HandleFunc("/set", api.HandleSetData)
	http.HandleFunc("/get", api.HandleGetData)
	http.HandleFunc("/setc", api.HandleSetcData)
	http.HandleFunc("/getc", api.HandleGetcData)

	err := http.ListenAndServe(":"+port, nil)
	if err != nil {
		logger.LogError("Server error: " + err.Error())
	}
}
