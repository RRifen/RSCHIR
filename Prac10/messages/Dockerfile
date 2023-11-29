FROM golang:latest

WORKDIR /app

COPY ./messages/go.mod ./messages/go.sum ./
RUN go mod download

COPY ./messages .
RUN mkdir main && go build -o main ./...

CMD ["./main/messages"]