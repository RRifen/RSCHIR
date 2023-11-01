package server

import (
	"context"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/gridfs"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"net/http"
	"os"
	"prac_go/pkg/api"
	"prac_go/pkg/global"
	"prac_go/pkg/logger"
)

func StartServer(port string) {
	uri := os.Getenv("MONGODB_URL")
	if uri == "" {
		uri = "mongodb://localhost:27017"
	}
	
	clientOptions := options.Client().ApplyURI(uri)

	var err error
	global.MongoClient, err = mongo.Connect(context.Background(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}

	// Инициализация GridFS Bucket
	opts := options.GridFSBucket().SetName(global.FilesBucketName)
	global.FilesBucket, err = gridfs.NewBucket(
		global.MongoClient.Database(global.DatabaseName),
		opts,
	)
	if err != nil {
		log.Fatal(err)
	}

	r := mux.NewRouter()

	// GET /files - получение списка файлов
	r.HandleFunc("/files", api.GetFilesHandler).Methods("GET")

	// GET /files/{id} - получение файла по id
	r.HandleFunc("/files/{id}", api.GetFileHandler).Methods("GET")

	// GET /files/{id}/info - получение информации о файле по id
	r.HandleFunc("/files/{id}/info", api.GetFileInfoHandler).Methods("GET")

	// POST /files - загрузка файла
	r.HandleFunc("/files", api.UploadFileHandler).Methods("POST")

	// PUT /files/{id} - обновление файла по id
	r.HandleFunc("/files/{id}", api.UpdateFileHandler).Methods("PUT")

	// DELETE /files/{id} - удаление файла по id
	r.HandleFunc("/files/{id}", api.DeleteFileHandler).Methods("DELETE")

	err = http.ListenAndServe(":"+port, r)
	if err != nil {
		logger.LogError("Server error: " + err.Error())
	}
}
