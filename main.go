package main

import (
	"fmt"
	"net/http"
	"time"
)

func getIPAddress(r *http.Request) string {
	ipAddress := r.RemoteAddr
	fwdAddress := r.Header.Get("X-Forwarded-For")
	if fwdAddress != "" {
		ipAddress = fwdAddress
	}
	return ipAddress
}

func handlerPing(w http.ResponseWriter, r *http.Request) {

	_, err := w.Write([]byte("pong"))
	if err != nil {
		fmt.Println("Fail to respond")
	}
	fmt.Println(time.Now(), getIPAddress(r), r.Method, r.RequestURI, r.UserAgent())
}

func Auth(w http.ResponseWriter, r *http.Request) {

	username := "test.me"
	pass := "test.me"

	u, p, ok := r.BasicAuth()

	if !ok {
		fmt.Println("Error in basic auth")
		w.WriteHeader(401)
		return
	}
	if u != username {
		fmt.Println("Incorrect username")
		w.WriteHeader(401)
		return
	}
	if p != pass {
		fmt.Println("Incorrect pass")
		w.WriteHeader(401)
		return
	}
	w.WriteHeader(200)
	fmt.Println("Successful login")
	return
}

func main() {
	http.HandleFunc("/", handlerPing)
	http.HandleFunc("/auth", Auth)

	fmt.Println("Ping listening on port 3000")
	err := http.ListenAndServe(":3000", nil)

	if err != nil {
		fmt.Println("Error starting ping server: ", err)
	}
}
