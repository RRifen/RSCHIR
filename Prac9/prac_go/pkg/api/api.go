package api

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/gridfs"
	"io"
	"log"
	"mime/multipart"
	"net/http"
	"prac_go/pkg/global"
	"strconv"
)

func UploadFileHandler(w http.ResponseWriter, r *http.Request) {
	// Обработка запроса на загрузку файла
	file, fileHeader, err := r.FormFile("file")
	if err != nil {
		http.Error(w, "Unable to read file", http.StatusBadRequest)
		return
	}

	defer func(file multipart.File) {
		err := file.Close()
		if err != nil {

		}
	}(file)

	// Загрузка файла в GridFS
	fileID, err := uploadFileToGridFS(fileHeader)
	if err != nil {
		http.Error(w, "Failed to upload file", http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusCreated)
	log.Println("File uploaded with ID:" + fileID)
	_, err = fmt.Fprintf(w, "File uploaded with ID: %s", fileID)
	if err != nil {
		return
	}
}

func uploadFileToGridFS(filePart *multipart.FileHeader) (string, error) {
	// Создание файла в GridFS
	uploadStream, err := global.FilesBucket.OpenUploadStream(
		filePart.Filename,
	)
	if err != nil {
		return "", err
	}

	defer func(uploadStream *gridfs.UploadStream) {
		err := uploadStream.Close()
		if err != nil {

		}
	}(uploadStream)

	// Копирование содержимого файла в GridFS
	file, err := filePart.Open()
	if err != nil {
		return "", err
	}

	defer func(file multipart.File) {
		err := file.Close()
		if err != nil {

		}
	}(file)

	_, err = io.Copy(uploadStream, file)
	if err != nil {
		return "", err
	}

	return uploadStream.FileID.(primitive.ObjectID).Hex(), nil
}

func GetFilesHandler(w http.ResponseWriter, r *http.Request) {
	// Обработка запроса на получение списка файлов
	files, err := getFilesFromGridFS()
	if err != nil {
		http.Error(w, "Failed to get files", http.StatusInternalServerError)
		return
	}

	// Отправить список файлов как JSON
	w.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(files)
	if err != nil {
		return
	}
}

func getFilesFromGridFS() ([]map[string]string, error) {
	// Получение списка файлов из GridFS
	cursor, err := global.FilesBucket.Find(context.Background(), nil)
	if err != nil {
		return nil, err
	}
	defer func(cursor *mongo.Cursor, ctx context.Context) {
		err := cursor.Close(ctx)
		if err != nil {

		}
	}(cursor, context.Background())

	var files []map[string]string
	for cursor.Next(context.Background()) {
		fileInfo := cursor.Current
		idAndName := make(map[string]string)
		idAndName["id"] = fileInfo.Lookup("_id").ObjectID().Hex()
		idAndName["filename"] = fileInfo.Lookup("filename").StringValue()
		files = append(files, idAndName)
	}
	return files, nil
}

func GetFileHandler(w http.ResponseWriter, r *http.Request) {
	// Обработка запроса на получение файла по ID
	vars := mux.Vars(r)
	fileID := vars["id"]

	// Получение файла из GridFS по ID
	file, err := getFileFromGridFS(fileID)
	if err != nil {
		http.Error(w, "File not found", http.StatusNotFound)
		return
	}

	// Отправить файл как вложение
	w.Header().Set("Content-Disposition", "attachment; filename="+(file).GetFile().Name)
	w.Header().Set("Content-Type", "application/octet-stream")
	_, err = io.Copy(w, file)
	if err != nil {
		http.Error(w, "Failed to send file", http.StatusInternalServerError)
	}
}

func getFileFromGridFS(fileID string) (*gridfs.DownloadStream, error) {
	objectID, err := primitive.ObjectIDFromHex(fileID)
	if err != nil {
		return nil, err
	}

	file, err := global.FilesBucket.OpenDownloadStream(objectID)
	if err != nil {
		return nil, err
	}
	return file, nil
}

func GetFileInfoHandler(w http.ResponseWriter, r *http.Request) {
	// Обработка запроса на получение информации о файле по ID
	vars := mux.Vars(r)
	fileID := vars["id"]

	// Получение информации о файле из GridFS по ID
	fileInfo, err := getFileInfoFromGridFS(fileID)
	if err != nil {
		http.Error(w, "File not found", http.StatusNotFound)
		return
	}

	// Отправить информацию о файле как JSON
	w.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(fileInfo)
	if err != nil {
		return
	}
}

func getFileInfoFromGridFS(fileID string) (map[string]string, error) {
	objectID, err := primitive.ObjectIDFromHex(fileID)
	if err != nil {
		return nil, err
	}

	stream, err := global.FilesBucket.OpenDownloadStream(objectID)
	if err != nil {
		return nil, err
	}
	file := stream.GetFile()
	m := make(map[string]string)
	m["filename"] = file.Name
	m["length"] = strconv.FormatInt(file.Length, 10)
	m["uploadDate"] = file.UploadDate.String()
	return m, nil
}

func UpdateFileHandler(w http.ResponseWriter, r *http.Request) {
	// Обработка запроса на обновление файла по ID
	vars := mux.Vars(r)
	fileID := vars["id"]

	// Здесь вы можете добавить логику обновления файла, если необходимо
	// Например, вы можете перезаписать файл с новым содержимым
	err := deleteFileFromGridFS(fileID)
	if err != nil {
		return
	}
	UploadFileHandler(w, r)
}

func DeleteFileHandler(w http.ResponseWriter, r *http.Request) {
	// Обработка запроса на удаление файла по ID
	vars := mux.Vars(r)
	fileID := vars["id"]

	// Удаление файла из GridFS
	err := deleteFileFromGridFS(fileID)
	if err != nil {
		http.Error(w, "Failed to delete file", http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusOK)
	_, err = fmt.Fprintf(w, "File with ID %s has been deleted", fileID)
	if err != nil {
		return
	}
}

func deleteFileFromGridFS(fileID string) error {
	objectID, err := primitive.ObjectIDFromHex(fileID)
	if err != nil {
		return err
	}

	err = global.FilesBucket.Delete(objectID)
	if err != nil {
		return err
	}

	return nil
}
