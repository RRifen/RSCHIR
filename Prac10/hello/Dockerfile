FROM golang:latest

WORKDIR /app

COPY ./hello/go.mod ./hello/go.sum ./
RUN go mod download

COPY ./hello .
RUN mkdir main && go build -o main ./...

CMD ["./main/hello"]