class Player{
  
  float d = pixelSize*2;
  float w = pixelSize*2;
  float h = 50;
  float damage = 10;
  boolean facingRight = true;

  
  void display(){
    
    rectMode(CENTER);
    /*
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
    */
    
    stroke(0);
    fill(0,200,255);
    rect(playerX,playerY,w,h);

    fill(200,0,0);
    if(facingRight){
      rect(playerX + w/2, playerY - h/2, pixelSize, pixelSize*2);
    }else{
      rect(playerX - w/2, playerY - h/2, pixelSize, pixelSize*2);
    }
     
  }

  void displayRoll(){
    stroke(0);
    fill(0,200,200);
    rect(playerX,playerY,w,w);
  }
  
}
