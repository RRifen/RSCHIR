FROM golang:latest

WORKDIR /app

COPY ./go/go.mod ./go/go.sum ./
RUN go mod download

COPY ./go .
RUN mkdir main && go build -o main ./...

CMD ["./main/cmd"]