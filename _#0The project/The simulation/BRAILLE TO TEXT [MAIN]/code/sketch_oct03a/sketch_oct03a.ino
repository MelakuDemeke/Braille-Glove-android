//#include <Keyboard.h>

String msg = "";
const int firstButton = 2;
const int secondButton = 3;
const int thirdButton = 4;
const int fourthButton = 5;
const int fifthButton = 6;
const int sixth = 7;
const int keyenter = 8;
const int keyclear = 9;
const int keyFullSend = 10;

int firstKey = 0;
int secondKey = 0;
int thirdKey = 0;
int fourthKey = 0;
int fifthKey = 0;
int sixthKey = 0;

void setup() {
   Serial.begin(9600);
  pinMode(firstButton, INPUT);
  pinMode(secondButton, INPUT);
  pinMode(thirdButton, INPUT);
  pinMode(fourthButton, INPUT);
  pinMode(fifthButton, INPUT);
  pinMode(sixth, INPUT);
  pinMode(keyenter, INPUT);
  pinMode(keyclear, INPUT);
  pinMode(keyFullSend, INPUT);
  
  Serial.begin(9600);
//  Keyboard.begin();
}

void resetInput(){
  firstKey = 0;
  secondKey = 0;
  thirdKey = 0;
  fourthKey = 0;
  fifthKey = 0;
  sixthKey = 0;
}

void loop() {
  if (digitalRead(firstButton) == HIGH) {
    firstKey = 1;
  }  
  if (digitalRead(secondButton) == HIGH) {
    secondKey = 1;
  }
  if (digitalRead(thirdButton) == HIGH) {
    thirdKey = 1;
  }
  if (digitalRead(fourthButton) == HIGH) {
    fourthKey = 1;
  }
  if (digitalRead(fifthButton) == HIGH) {
    fifthKey = 1;
  }
  if (digitalRead(sixth) == HIGH) {
    sixthKey = 1;
  }

  if (digitalRead(keyenter) == HIGH) {
    if (firstKey == 1 && secondKey == 0 && thirdKey == 0 && fourthKey == 0 && fifthKey == 0 && sixthKey == 0) {
   //  Keyboard.write('a');
     Serial.println('a');
     msg += "a";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 0 && fourthKey == 0 && fifthKey == 0 && sixthKey == 0) {
    // Keyboard.write('b');
     Serial.println('b');
     msg += "b";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 0 && fourthKey == 1 && fifthKey == 0 && sixthKey == 0) {
     //Keyboard.write('c');
     Serial.println('c');
     msg += "c";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 0 && fourthKey == 1 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('d');
     Serial.println('d');
     msg += "d";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 0 && fourthKey == 0 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('e');
     Serial.println('e');
     msg += "e";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 0 && fourthKey == 1 && fifthKey == 0 && sixthKey == 0) {
     //Keyboard.write('f');
     Serial.println('f');
     msg += "f";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 0 && fourthKey == 1 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('g');
     Serial.println('g');
     msg += "g";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 0 && fourthKey == 0 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('h');
     Serial.println('h');
     msg += "h";
     resetInput(); 
    }
    if (firstKey == 0 && secondKey == 1 && thirdKey == 0 && fourthKey == 1 && fifthKey == 0 && sixthKey == 0) {
    // Keyboard.write('i');
     Serial.println('i');
     msg += "i";
     resetInput();
    }
    if (firstKey == 0 && secondKey == 1 && thirdKey == 0 && fourthKey == 1 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('j');
     Serial.println('j');
     msg += "j";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 0 && fifthKey == 0 && sixthKey == 0) {
    // Keyboard.write('k');
     Serial.println('k');
     msg += "k";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 1 && fourthKey == 0 && fifthKey == 0 && sixthKey == 0) {
     //Keyboard.write('l');
     Serial.println('l');
     msg += "l";
     resetInput();  
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 1 && fifthKey == 0 && sixthKey == 0) {
     //Keyboard.write('m');
     Serial.println('m');
     msg += "m";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 1 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('n');
     Serial.println('n');
     msg += "n";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 0 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('o');
     Serial.println('o');
     msg += "o";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 1 && fourthKey == 1 && fifthKey == 0 && sixthKey == 0) {
    // Keyboard.write('p');
     Serial.println('p');
     msg += "p";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 1 && fourthKey == 1 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('q');
     Serial.println('q');
     msg += "q";
     resetInput();
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 1 && fourthKey == 0 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('r');
     Serial.println('r');
     msg += "r";
     resetInput();
    }
    if (firstKey == 0 && secondKey == 1 && thirdKey == 1 && fourthKey == 1 && fifthKey == 0 && sixthKey == 0) {
    // Keyboard.write('s');
     Serial.println('s');
     msg += "s";
     resetInput();
    }
    if (firstKey == 0 && secondKey == 1 && thirdKey == 1 && fourthKey == 1 && fifthKey == 1 && sixthKey == 0) {
    // Keyboard.write('t');
     Serial.println('t');
     msg += "t";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 0 && fifthKey == 0 && sixthKey == 1) {
     //Keyboard.write('u');
     Serial.println('u');
     msg += "u";
     resetInput();  
    }
    if (firstKey == 1 && secondKey == 1 && thirdKey == 1 && fourthKey == 0 && fifthKey == 0 && sixthKey == 1) {
    // Keyboard.write('v');
     Serial.println('v');
     msg += "v";
     resetInput(); 
    }
    if (firstKey == 0 && secondKey == 1 && thirdKey == 0 && fourthKey == 1 && fifthKey == 1 && sixthKey == 1) {
    // Keyboard.write('w');
     Serial.println('w');
     msg += "w";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 1 && fifthKey == 0 && sixthKey == 1) {
    // Keyboard.write('x');
     Serial.println('x');
     msg += "x";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 1 && fifthKey == 1 && sixthKey == 1) {
    // Keyboard.write('y');
     Serial.println('y');
     msg += "y";
     resetInput(); 
    }
    if (firstKey == 1 && secondKey == 0 && thirdKey == 1 && fourthKey == 0 && fifthKey == 1 && sixthKey == 1) {
     // Keyboard.write('z');
     Serial.println('z');
     msg += "z";
     resetInput();
    }
    if (firstKey == 0 && secondKey == 1 && thirdKey == 0 && fourthKey == 0 && fifthKey == 0 && sixthKey == 0) {
     // Keyboard.write(' ');
     Serial.println(' ');
     msg += " ";
     resetInput();
    }
    if (firstKey == 0 && secondKey == 0 && thirdKey == 1 && fourthKey == 0 && fifthKey == 0 && sixthKey == 0) {
     // Keyboard.write('.');
     Serial.println('.');
     msg += ".";
     resetInput();
    }
    if (firstKey == 0 && secondKey == 0 && thirdKey == 0 && fourthKey == 0 && fifthKey == 1 && sixthKey == 0) {
     // Keyboard.write('?');
     Serial.println('?');
     msg += "?";
     resetInput();
    }
    else{
      resetInput();
    }
  }
  if(digitalRead(keyclear) == HIGH){
    resetInput();
    }
  if(digitalRead(keyFullSend) == HIGH){
    Serial.print(msg);
    msg = "";
    }
}
