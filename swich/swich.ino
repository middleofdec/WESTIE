#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
//#include <FirebaseArduino.h>
#include <FirebaseESP8266.h>

// Config Firebase
#define FIREBASE_HOST "westie-project.firebaseio.com"
#define FIREBASE_AUTH "BnfvtmbGeOuivnwiqiQugvU7JNafNpGGpI0ZKekq"

// Config connect WiFi
#define WIFI_SSID "1stFirst"
#define WIFI_PASSWORD "tnsr1512"

FirebaseData firebaseData;

//#define LED_lamp D1

void setup() 
{
  Serial.begin(115200);
  pinMode(D1, OUTPUT);
  
   // Connect to wifi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  // Connect to Firebase
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() 
{
  lamp();
}

void lamp()
{
  Firebase.getString(firebaseData, "LDR/Lamp");
  String status_lamp = firebaseData.stringData();
  if (status_lamp == "ON")
  {
    Serial.println("LED ON");
    digitalWrite(D1,HIGH);
  }
  else
  {
    Serial.println("LED OFF");
    digitalWrite(D1,LOW);
  }
}
