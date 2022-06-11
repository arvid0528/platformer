class Player{
  
  float d = 60;
  float w = 20;
  float h = 50;
  float damage = 10;
  
  void display(){
    
    rectMode(CENTER);
    
    pushMatrix();
    translate(playerX,playerY);
    angle = atan2(mouseY-playerY,mouseX-playerX);
    popMatrix(); 
  
    pushMatrix();
    translate(playerX,playerY);  
    rotate(angle);
    fill(50);
    stroke(0);
    rect(20,0,50,5);
    popMatrix(); 
    
    stroke(0);
    fill(0,200,255);
    rect(playerX,playerY,w,h);
     
  }

  void displayRoll(){
    stroke(0);
    fill(0,200,200);
    rect(playerX,playerY,w,w);
  }
  
}
