#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>
#include <SPI.h>
#include <SD.h>

Adafruit_MPU6050 mpu;

float acc_x[100], acc_y[100], acc_z[100];
float gy_x[100], gy_y[100], gy_z[100];
float temp[100];

const int CS_pin = 10;


int idx = 0;
unsigned long pre_time = 0;
const long interval = 50;

int flag = 0;
int file_idx = 0;
String filename ="0.csv";
const int LED_pin = 13;


// button
const int BTN_pin= A1;
boolean preBtn = LOW;
boolean curBtn = LOW;
boolean state = false;
boolean debounce(boolean last);

void saveData();
boolean debounce(boolean last);
void mpu_setup();


// LoRa 
#define RF95_CS 8


void setup(void){

    Serial.begin(115200);
    while (!Serial) ;
    delay(10);

    pinMode(LED_pin, OUTPUT);
    digitalWrite(LED_pin, LOW);
    
    pinMode(BTN_pin, INPUT_PULLUP);

    pinMode(RF95_CS, OUTPUT);
    digitalWrite(RF95_CS, HIGH);
    pinMode(CS_pin, OUTPUT);
    digitalWrite(CS_pin, LOW);
    delay(100);
    
    
    // see if the card is present and can be initialized:
//    if (!SD.begin(CS_pin)){
//        Serial.println("fail1");
//        while (1);
//    }
//    Serial.println("SD init success");
    mpu_setup();
    Serial.println("mpu init success");
    delay(100);
}


void loop() {
    
    curBtn = debounce(preBtn);
    
    if (curBtn == HIGH && preBtn == LOW){
        state = !state;
        if (state == true){
            idx = 0;
            pre_time = millis();
            //Serial.println("start");
            digitalWrite(LED_pin, HIGH);
        }
        else{
            file_idx++;
            //Serial.println("quit");
            digitalWrite(LED_pin, LOW);
            filename = String(file_idx) + ".csv";
            //Serial.println(filename);
        }
    }
    preBtn = curBtn;

    if(state){
        if(millis() - pre_time > interval){
            pre_time = millis();
            sensors_event_t a, g, t;
            mpu.getEvent(&a, &g, &t);

            acc_x[idx] = a.acceleration.x;
            acc_y[idx] = a.acceleration.y;
            acc_z[idx] = a.acceleration.z;
            temp[idx] = t.temperature;
            gy_x[idx] = g.gyro.x;
            gy_y[idx] = g.gyro.y;
            gy_z[idx++] = g.gyro.z;

            if(idx >= 100){
                saveData();
                idx = 0;
            }
        }
    }
}



void mpu_setup(){
    //Serial.println("test2");
    if (!mpu.begin(0x69)){
        Serial.println("mpu fail");
        while (1){
            delay(10);
        }
    }
    //Serial.println("test3");

    mpu.setAccelerometerRange(MPU6050_RANGE_2_G);
    mpu.setGyroRange(MPU6050_RANGE_250_DEG);
    mpu.setFilterBandwidth(MPU6050_BAND_5_HZ);
}


boolean debounce(boolean last){
  boolean current = digitalRead(BTN_pin);
  if(current != last){
    delay(5);
    current = digitalRead(BTN_pin);
  }
  return current;
}



void saveData(){
    filename = String(file_idx) + ".csv";
    File dataFile = SD.open(filename, FILE_WRITE);
    if (dataFile) {
        for (int i = 0; i < 100; i++) {
            dataFile.print(acc_x[i]); dataFile.print(",");
            dataFile.print(acc_y[i]); dataFile.print(",");
            dataFile.print(acc_z[i]); dataFile.print(",");
            dataFile.print(gy_x[i]); dataFile.print(",");
            dataFile.print(gy_y[i]); dataFile.print(",");
            dataFile.print(gy_z[i]); dataFile.println();
        }
        dataFile.close();
        //Serial.println("save");
    }
}
