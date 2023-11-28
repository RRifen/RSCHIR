package api

import (
	. "Prac8/pkg/cookie"
	"Prac8/pkg/logger"
	. "Prac8/pkg/types"
	"encoding/json"
	"net/http"
	"time"
)

func HandleSetData(w http.ResponseWriter, r *http.Request) {
	var requestData UserData
	if err := json.NewDecoder(r.Body).Decode(&requestData); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		logger.LogError(err.Error())
		return
	}

	err := SetDataInCookie(w, requestData)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		logger.LogError(err.Error())
		return
	}

	w.WriteHeader(http.StatusOK)
}

func HandleGetData(w http.ResponseWriter, r *http.Request) {
	userData, err := GetDataFromCookie(r)
	if err != nil {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusBadRequest)

		resp := make(map[string]string)
		resp["message"] = err.Error()

		json.NewEncoder(w).Encode(resp)
		logger.LogError(err.Error())
		return
	}

	err = json.NewEncoder(w).Encode(userData)
	if err != nil {
		return
	}
}

func HandleSetcData(w http.ResponseWriter, r *http.Request) {
	var requestData UserData
	if err := json.NewDecoder(r.Body).Decode(&requestData); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		logger.LogError(err.Error())
		return
	}

	err := SetDataInCookie(w, requestData)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		logger.LogError(err.Error())
		return
	}

	// Симуляция конкурентных вычислений с использованием time.Sleep()
	time.Sleep(5 * time.Second)
	w.WriteHeader(http.StatusOK)
}

func HandleGetcData(w http.ResponseWriter, r *http.Request) {
	userData, err := GetDataFromCookie(r)
	if err != nil {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusBadRequest)

		resp := make(map[string]string)
		resp["message"] = err.Error()

		json.NewEncoder(w).Encode(resp)
		logger.LogError(err.Error())
		return
	}
	// Симуляция конкурентных вычислений с использованием time.Sleep()
	time.Sleep(5 * time.Second)

	err = json.NewEncoder(w).Encode(userData)
	if err != nil {
		return
	}
}
