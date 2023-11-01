package types

import "os"

type UserData struct {
	Data string
}

var CookieName string = os.Getenv("COOKIE")
