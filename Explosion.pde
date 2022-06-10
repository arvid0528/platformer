class Explosion{
  
  PImage explosionPic;
  float tempX,tempY;
  float timeUp;
  float savedTime = millis();
  
  Explosion(float expX, float expY){
    
    explosionPic = loadImage("explosion.png");
    tempX = expX;
    tempY = expY;
    timeUp = 1000;
    
  }
  
  void display(){
    
    if(millis() < timeUp + savedTime){
      fill(0);
      image(explosionPic,tempX-25,tempY-25,50,50);
    }

  }
  
  
}
