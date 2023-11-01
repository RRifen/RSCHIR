package global

import (
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/gridfs"
	"os"
)

var (
	MongoClient     *mongo.Client
	FilesBucket     *gridfs.Bucket
	DatabaseName    string
	FilesBucketName string
)

func init() {
	DatabaseName = os.Getenv("DB_NAME")
	if DatabaseName == "" {
		DatabaseName = "go_files"
	}

	FilesBucketName = os.Getenv("FB_NAME")
	if FilesBucketName == "" {
		FilesBucketName = "files"
	}
}
