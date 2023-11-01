package cookie

import (
	"encoding/json"
	"github.com/gorilla/securecookie" // Используем библиотеку для безопасного хранения cookie
	"net/http"

	. "Prac8/pkg/types"
)

// Инициализируем secure cookie
var cookieHandler = securecookie.New(
	securecookie.GenerateRandomKey(64),
	securecookie.GenerateRandomKey(32),
)

func SetDataInCookie(w http.ResponseWriter, data UserData) error {
	encoded, err := json.Marshal(data)
	strData := string(encoded[:])
	if err != nil {
		return err
	}

	value := make(map[string]interface{})
	value["data"] = encoded

	strData, err = cookieHandler.Encode(CookieName, value)
	if err != nil {
		return err
	}

	cookie := &http.Cookie{
		Name:  CookieName,
		Value: strData,
		Path:  "/",
	}

	http.SetCookie(w, cookie)
	return nil
}

func GetDataFromCookie(r *http.Request) (UserData, error) {
	cookie, err := r.Cookie(CookieName)
	if err != nil {
		return UserData{}, err
	}
	value := make(map[string]interface{})
	if err = cookieHandler.Decode(CookieName, cookie.Value, &value); err != nil {
		return UserData{}, err
	}

	encodedData := (string)(value["data"].([]uint8))

	var userData UserData
	if err := json.Unmarshal([]byte(encodedData), &userData); err != nil {
		return UserData{}, err
	}

	return userData, nil
}
