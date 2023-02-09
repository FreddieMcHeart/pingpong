package server

import (
	"github.com/gin-gonic/gin"
	"gomain/handlers"
)

func Server() error {
	r := gin.Default()

	r.GET("/ping", handlers.HandlePing)

	err := r.Run("0.0.0.0:3000")
	if err != nil {
		return err
	}
	return err
}
