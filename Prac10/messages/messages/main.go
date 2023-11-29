package main

import (
	"context"
	"fmt"
	"log"
	"messages/pb"
	"net"
	"os"

	"google.golang.org/grpc"
)

var MessageById = make(map[uint64]string)

func store(id uint64, message string) {
	MessageById[id] = message
}

func retrieve(id uint64) string {
	return MessageById[id]
}

func deleteFromMap(id uint64) {
	delete(MessageById, id)
}

type server struct {
	pb.UnimplementedGatewayServer
}

func (s *server) PostMessage(ctx context.Context, in *pb.MessageText) (*pb.MessageText, error) {
	fmt.Println(in)
	store(in.Id, in.Message)
	return &pb.MessageText{Id: in.Id, Message: in.Message}, nil
}

func (s *server) GetMessage(ctx context.Context, in *pb.Message) (*pb.MessageText, error) {
	fmt.Println(in)

	message := retrieve(in.Id)
	if message == "" {
		return &pb.MessageText{Id: in.Id}, nil
	}
	return &pb.MessageText{Id: in.Id, Message: message}, nil
}

func (s *server) DeleteMessage(ctx context.Context, in *pb.Message) (*pb.MessageText, error) {
	fmt.Println(in)

	message := retrieve(in.Id)
	if message == "" {
		return &pb.MessageText{Id: in.Id}, nil
	}
	deleteFromMap(in.Id)
	return &pb.MessageText{Id: in.Id, Message: message}, nil
}

func runGrpc() {
	val, present := os.LookupEnv("MESSAGES_PORT")
	if !present {
		val = "12201"
	}
	lis, err := net.Listen("tcp", ":"+val)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterGatewayServer(s, &server{})
	log.Printf("server listening at %v", lis.Addr())
	if err := s.Serve(lis); err != nil {
		panic(err)
	}
}

func main() {
	runGrpc()
}
