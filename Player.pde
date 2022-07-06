class Player{
  
  float d = pixelSize*2;
  float w = pixelSize*2;
  float h = pixelSize*2;
  float damage = 10;
  boolean facingRight = true;
  float attackCooldown;
  float attackDuration;
  
  void attack(){

    attackDuration = 10;
    attackCooldown = 40;
    createFireAnimation(playerX - 32, playerY - 32);
    
  }

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
    
    stroke(0,200,255);
    noFill();
    rect(playerX,playerY,w,h);
    
    if(facingRight){
      if(attackDuration > 0){
        image(blobRightAttackNoFire, playerX - 32, playerY - 32);
        image(fire0, playerX - 32, playerY - 32);
      } else {
        image(blobRight, playerX - 32, playerY - 32);
      }
    }else{
      if(attackDuration > 0){
        image(blobLeftAttack, playerX - 32, playerY - 32);
      } else {
        image(blobLeft, playerX - 32, playerY - 32);
      }
    }
  }

  void displayRoll(){
    stroke(0);
    fill(0,200,200);
    rect(playerX,playerY,w,w);
  }
  
}
