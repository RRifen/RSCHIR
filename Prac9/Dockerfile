FROM golang:latest

WORKDIR /app

COPY ./prac_go/go.mod ./prac_go/go.sum ./
RUN go mod download

COPY ./prac_go .
RUN mkdir main && go build -o main ./...

CMD ["./main/cmd"]