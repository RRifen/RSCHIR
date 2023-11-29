package main

import (
	"context"
	"net/http"
	"os"

	"gateway/logger"
	"gateway/pb"

	"github.com/grpc-ecosystem/grpc-gateway/v2/runtime"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

func runRest() {
	ctx := context.Background()
	logger.LogInfo("Background")
	ctx, cancel := context.WithCancel(ctx)
	defer cancel()
	mux := runtime.NewServeMux()
	opts := []grpc.DialOption{grpc.WithTransportCredentials(insecure.NewCredentials()), grpc.WithBlock()}

	mp, present := os.LookupEnv("MESSAGES_PORT")
	if !present {
		mp = "12201"
	}
	err := pb.RegisterGatewayHandlerFromEndpoint(ctx, mux, "messages:"+mp, opts)
	if err != nil {
		logger.LogError(err.Error())
		panic(err)
	}

	hp, present := os.LookupEnv("HELLO_PORT")
	if !present {
		hp = "50051"
	}
	err = pb.RegisterGreeterHandlerFromEndpoint(ctx, mux, "hello:"+hp, opts)
	if err != nil {
		logger.LogError(err.Error())
		panic(err)
	}

	gp, present := os.LookupEnv("GATEWAY_PORT")
	if !present {
		gp = "8081"
	}
	logger.LogInfo("server listening at " + gp)
	if err := http.ListenAndServe(":"+gp, mux); err != nil {
		panic(err)
	}
}

func main() {
	runRest()
}
