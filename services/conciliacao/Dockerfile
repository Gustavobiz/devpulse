# Estágio 1: Build do binário estático
FROM golang:1.18-alpine AS builder
WORKDIR /app
COPY go.mod ./
# COPY go.sum ./ # Descomente se tiver go.sum
RUN go mod download
COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -o conciliacao main.go

# Estágio 2: Imagem final leve
FROM alpine:3.18
WORKDIR /
COPY --from=builder /app/conciliacao /conciliacao
EXPOSE 8080
CMD ["/conciliacao"]