package main

import (
	"gomain/server"
	"log"
)

func main() {
	err := server.Server()
	if err != nil {
		log.Fatal()
	}
}
