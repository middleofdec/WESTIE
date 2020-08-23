#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
#include <FirebaseArduino.h>
#include <DHTesp.h>
/*******************************************************************************/
SoftwareSerial NodeSerial(D2, D3); // RX | TX
DHTesp dht;
/*******************************************************************************/
// Config Firebase
#define FIREBASE_HOST "westie-project.firebaseio.com"
#define FIREBASE_AUTH "BnfvtmbGeOuivnwiqiQugvU7JNafNpGGpI0ZKekq"

// Config connect WiFi
#define WIFI_SSID "1stFirst"
#define WIFI_PASSWORD "tnsr1512"
/*******************************************************************************/
String humi,temp;
String LPG, CO, Smoke;
/*******************************************************************************/
#define         MQ_PIN                       (0)
/*******************************************************************************/
#define         RL_VALUE                     (5)     //กำหนดความต้านทานโหลดเป็นกิโลโอห์ม
#define         RO_CLEAN_AIR_FACTOR          (9.83)  //RO_CLEAR_AIR_FACTOR=(Sensor resistance in clean air)/RO,RS/RO
                                                     //which is derived from the chart in datasheet
/*******************************************************************************/
#define         CALIBARAION_SAMPLE_TIMES     (50)    //define how many samples you are going to take in the calibration phase
#define         CALIBRATION_SAMPLE_INTERVAL  (500)   //define the time interal(in milisecond) between each samples in the
                                                     //cablibration phase
#define         READ_SAMPLE_INTERVAL         (50)    //define how many samples you are going to take in normal operation
#define         READ_SAMPLE_TIMES            (5)     //define the time interal(in milisecond) between each samples in 
                                                     //normal operation                                                   
/*******************************************************************************/                                                 
#define         GAS_LPG                      (0)
#define         GAS_CO                       (1)
#define         GAS_SMOKE                    (2)
/*******************************************************************************/  
float           LPGCurve[3]  =  {2.3,0.21,-0.47};   //two points are taken from the curve. 
                                                    //with these two points, a line is formed which is "approximately equivalent"
                                                    //to the original curve. 
                                                    //data format:{ x, y, slope}; point1: (lg200, 0.21), point2: (lg10000, -0.59) 
float           COCurve[3]  =  {2.3,0.72,-0.34};    //two points are taken from the curve. 
                                                    //with these two points, a line is formed which is "approximately equivalent" 
                                                    //to the original curve.
                                                    //data format:{ x, y, slope}; point1: (lg200, 0.72), point2: (lg10000,  0.15) 
float           SmokeCurve[3] ={2.3,0.53,-0.44};    //two points are taken from the curve. 
                                                    //with these two points, a line is formed which is "approximately equivalent" 
                                                    //to the original curve.
                                                    //data format:{ x, y, slope}; point1: (lg200, 0.53), point2: (lg10000,  -0.22)                                                     
float           Ro           =  10;                 //Ro is initialized to 10 kilo ohms
/*******************************************************************************/
void setup() 
{
  pinMode(D2,INPUT);
  pinMode(D3,OUTPUT);
  
  Serial.begin(115200);
  NodeSerial.begin(9600);

  dht.setup(D0, DHTesp::DHT22);
  Ro = MQCalibration(MQ_PIN);                       //Calibrating the sensor. Please make sure the sensor is in clean air 
                                                    //when you perform the calibration

  // Connect to wifi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  // Connect to Firebase
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}
/*******************************************************************************/
void loop() 
{
  LDR();
  dht22();
  MQ2();
  delay(500);
}
/*******************************************************************************/
void LDR()
{
  
  while (NodeSerial.available()>0)
  {
    float value_light = NodeSerial.parseFloat();
    if(NodeSerial.read() == '\n')
    {
      Serial.println(value_light);
       if (value_light <= 200)
      {
        Firebase.setString("LDR/Light","แสงสว่างมาก");
      }
      else if ((value_light > 200)&&(value_light <= 350))
      {
        Firebase.setString("LDR/Light","แสงปกติ");
      }
      else if ((value_light > 350)&&(value_light <= 500))
      {
        Firebase.setString("LDR/Light","แสงน้อย");
      }
      else {Firebase.setString("LDR/Light","ไม่มีแสง");}
    }
  }
  delay(500);
}
/*******************************************************************************/
void dht22()
{ 
  int hu = dht.getHumidity();
  int te = dht.getTemperature();
  
  humi = hu;
  temp = te;
  
  Firebase.setString("DHT22/Humidity",humi);
  Firebase.setString("DHT22/Temperature",temp);
  delay(500);
}
/*******************************************************************************/
void MQ2()
{
  int l = MQGetGasPercentage(MQRead(MQ_PIN)/Ro, GAS_LPG);
  int c = MQGetGasPercentage(MQRead(MQ_PIN)/Ro, GAS_CO);
  int s = MQGetGasPercentage(MQRead(MQ_PIN)/Ro, GAS_SMOKE);

  LPG   = l;
  CO    = c;
  Smoke = s;
  
  Firebase.setString("MQ-2/CO",CO);
  Firebase.setString("MQ-2/LPG",LPG);
  Firebase.setString("MQ-2/Smoke",Smoke);

  delay(500);
}
/****************** MQResistanceCalculation ****************************************
Input:   raw_adc - raw value read from adc, which represents the voltage
Output:  the calculated sensor resistance
Remarks: The sensor and the load resistor forms a voltage divider. Given the voltage
         across the load resistor and its resistance, the resistance of the sensor
         could be derived.
************************************************************************************/ 
float MQResistanceCalculation(int raw_adc)
{
  return ( ((float)RL_VALUE*(1023-raw_adc)/raw_adc));
}

/***************************** MQCalibration ****************************************
Input:   mq_pin - analog channel
Output:  Ro of the sensor
Remarks: This function assumes that the sensor is in clean air. It use  
         MQResistanceCalculation to calculates the sensor resistance in clean air 
         and then divides it with RO_CLEAN_AIR_FACTOR. RO_CLEAN_AIR_FACTOR is about 
         10, which differs slightly between different sensors.
************************************************************************************/ 
float MQCalibration(int mq_pin)
{
  int i;
  float val=0;

  for (i=0;i<CALIBARAION_SAMPLE_TIMES;i++) {            //take multiple samples
    val += MQResistanceCalculation(analogRead(mq_pin));
    delay(CALIBRATION_SAMPLE_INTERVAL);
  }
  val = val/CALIBARAION_SAMPLE_TIMES;                   //calculate the average value

  val = val/RO_CLEAN_AIR_FACTOR;                        //divided by RO_CLEAN_AIR_FACTOR yields the Ro 
                                                        //according to the chart in the datasheet 

  return val; 
}
/*****************************  MQRead *********************************************
Input:   mq_pin - analog channel
Output:  Rs of the sensor
Remarks: This function use MQResistanceCalculation to caculate the sensor resistenc (Rs).
         The Rs changes as the sensor is in the different consentration of the target
         gas. The sample times and the time interval between samples could be configured
         by changing the definition of the macros.
************************************************************************************/ 
float MQRead(int mq_pin)
{
  int i;
  float rs=0;

  for (i=0;i<READ_SAMPLE_TIMES;i++) {
    rs += MQResistanceCalculation(analogRead(mq_pin));
    delay(READ_SAMPLE_INTERVAL);
  }

  rs = rs/READ_SAMPLE_TIMES;

  return rs;  
}
/*****************************  MQGetGasPercentage **********************************
Input:   rs_ro_ratio - Rs divided by Ro
         gas_id      - target gas type
Output:  ppm of the target gas
Remarks: This function passes different curves to the MQGetPercentage function which 
         calculates the ppm (parts per million) of the target gas.
************************************************************************************/ 
int MQGetGasPercentage(float rs_ro_ratio, int gas_id)
{
  if ( gas_id == GAS_LPG ) {
     return MQGetPercentage(rs_ro_ratio,LPGCurve);
  } else if ( gas_id == GAS_CO ) {
     return MQGetPercentage(rs_ro_ratio,COCurve);
  } else if ( gas_id == GAS_SMOKE ) {
     return MQGetPercentage(rs_ro_ratio,SmokeCurve);
  }    

  return 0;
}
/*****************************  MQGetPercentage **********************************
Input:   rs_ro_ratio - Rs divided by Ro
         pcurve      - pointer to the curve of the target gas
Output:  ppm of the target gas
Remarks: By using the slope and a point of the line. The x(logarithmic value of ppm) 
         of the line could be derived if y(rs_ro_ratio) is provided. As it is a 
         logarithmic coordinate, power of 10 is used to convert the result to non-logarithmic 
         value.
************************************************************************************/ 
int  MQGetPercentage(float rs_ro_ratio, float *pcurve)
{
  return (pow(10,( ((log(rs_ro_ratio)-pcurve[1])/pcurve[2]) + pcurve[0])));
}
