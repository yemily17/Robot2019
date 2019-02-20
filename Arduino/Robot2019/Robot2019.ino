/*
 * Robot 2019
 * Utilizes the Arduino as an I2C slave of the RoboRio for handling some tasks
 * Andrew Sealing
 */

#include "I2C.h"
#include "LEDStrip.h"
#include "PixyCam.h"
#include "LineFollower.h"
#include "Ultrasonic.h"

void setup() {
  Serial.begin(9600);
  Serial.println("program started");
//  I2C::initialize(0);
  LEDStrip::initialize(6, 15, 15);
//  PixyCam::initialize();
//  LineFollower::initialize();
//  Ultrasonic::initialize();
}

void loop() {
  LEDStrip::updateDisplay(I2C::getAllianceColor(), I2C::getPattern(), I2C::getDiagnosticColor(), I2C::getDiagnosticPattern());
  PixyCam::refresh(2);    //I2C::getPixyCamState());
//  if (I2C::getLineFollowerState())
//    LineFollower::updateLineData();
//  if (I2C::getUltrasonicState())
    Ultrasonic::updateDistance();
  I2C::setWriteData(PixyCam::getObjInView(), PixyCam::getXValue(), Ultrasonic::getDistance());
}