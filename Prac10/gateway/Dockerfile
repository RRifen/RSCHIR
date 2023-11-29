FROM golang:latest

WORKDIR /app

COPY ./gateway-main/go.mod ./gateway-main/go.sum ./
RUN go mod download

COPY ./gateway-main .
RUN mkdir main && go build -o main ./...

CMD ["./main/gateway"]