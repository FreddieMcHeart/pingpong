FROM golang:buster AS builder

RUN apt-get install -y git ca-certificates && update-ca-certificates

ENV GO111MODULE=on \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

RUN mkdir /pong
WORKDIR /pong

COPY go.mod .

RUN go mod download

COPY . .

RUN go build

LABEL label=builder

FROM scratch

LABEL maintainer="(C) FreddieMcHeart | iwonnapapper@gmail.com | https://github.com/FreddieMcHeart"

COPY --from=builder /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/
COPY --from=builder /pong/gomain /

EXPOSE 3000

LABEL app=pong

ENTRYPOINT ["/gomain"]
