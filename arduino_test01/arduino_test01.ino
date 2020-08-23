/*******************************************************************************/
#include <SoftwareSerial.h>
/*******************************************************************************/
SoftwareSerial ArduinoSerial(3, 2); // RX, TX
/*******************************************************************************/
int value_light = 0;

void setup() 
{
  pinMode(3, INPUT);
  pinMode(2, OUTPUT);
  Serial.begin(115200);
  ArduinoSerial.begin(9600);
}

void loop() 
{
  value_light = analogRead(A1);
  ArduinoSerial.print(value_light);
  ArduinoSerial.print("\n");
  Serial.println(value_light);   
  delay(1500);
}
