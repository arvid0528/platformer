class EnemyWalking{
  
    /*
    walking enemy that paces back and forth on a platform
    Make invisible objects at edges that turn enemy around
    aggro player when player enters invisble objects
    */

  float x,y,w,h;
  float velX,velY;
  int health;
  float speedX;
  
  EnemyWalking(float x_, float y_, float w_, float h_){
     
    x = x_;
    y = y_;
    w = w_;
    h = h_;
    
    health = 100;
    speedX = 3;


  }
  
  //Normal pace when not chasing
  void pacing(){
    
    x += speedX;
    
  }
  
  //Enemy can see and is chasing player 
  void following(){
    
    if(x > playerX)velX = -5;
    else if(x < playerX)velX = 5;
    
    if(y > playerY)velY = -5;
    else if(y < playerY)velY=5;
   
    x += velX;
    y += velY;
    
  }
  
  void collissionDetection(){
     
    if(velX > 0){
      for(int i = 0; i < objs.length; i++){
        if(x+w/2 + velX > objs[i].left && x <= objs[i].left && y-h/2 < objs[i].bottom && y+h/2 > objs[i].top){
          x = objs[i].left - w/2;
          velX *= -0.75;

        }
      }
    }
    if(velX < 0){
      for(int i = 0; i < objs.length; i++){
        if(x-w/2 + velX < objs[i].right && x >= objs[i].right && y-h/2 < objs[i].bottom && y+h/2 > objs[i].top){
          x = objs[i].right + w/2;
          velX *= -0.75;
        }
      }
    }
    if(velY > 0){
      for(int i = 0; i < objs.length; i++){
        if(y+h/2 + velY > objs[i].top && y <= objs[i].top && x-w/2 < objs[i].right && x+w/2 > objs[i].left){
          y = objs[i].top - h/2;
          velY *= -0.75;
        }
      }
    }
    if(velY < 0){
      for(int i = 0; i < objs.length; i++){
        if(y-h/2 + velY < objs[i].bottom && y >= objs[i].bottom && x-w/2 < objs[i].right && x+w/2 > objs[i].left){
          y = objs[i].bottom + h/2;
          velY *= -0.75;
        }
      }
    }

  }
  
  void playerCollide(){
    
    if(playerX + player1.d/2 > x-w/2 && playerX - player1.d/2 < x+w/2 && playerY + player1.d/2 > y-h/2 && playerY - player1.d/2 < y+h/2){
      death = true;
    }else{
      death = false; 
    }
    
  }
  
  //Collission of Enemy and Player Bullet
  void bulletCollide(){
    
    for(int i = 0; i < bullets.length; i++){
       if(bullets[i].x > x-w/2 && bullets[i].x < x+w/2 && bullets[i].y > y-h/2 && bullets[i].y < y+h/2){
         bullets[i].kill(i);
         
         health -= player1.damage;
         
       }
    }
    
    
  }
  
  //Check if enemy can see the player
  void playerDetection(){
    
    
    boolean colX = false;
    boolean colY = false;
    float tempX;
    float tempY;
    //FloatList xlist;
    //xlist = new FloatList();
    //FloatList ylist;
    //ylist = new FloatList(); 
    float difX = abs(playerX-x);
    float difY = abs(playerY-y);
    
    //Divide line between playerX and enemyX into 100 bits
    for(int i = 0; i < 100; i++){
      //tempX and tempY plotted on their distance 100 times
      //Point (tempX,tempY) travels across the line
      if(x > playerX){
        tempX = x - (difX/100)*(i+1);
      }else{
        tempX = x + (difX/100)*(i+1); 
      }
      if(y > playerY){
        tempY = y - (difY/100)*(i+1); 
      }else{
        tempY = y + (difY/100)*(i+1); 
      }
      //fill(0,255,0);
      //noStroke();
      //ellipse(tempX,tempY,2,2);
      
      //Check if any of the 100 points collide with any object
      for(int j = 0; j < objs.length; j++){
        if(tempX > objs[j].left && tempX < objs[j].right && tempY > objs[j].top && tempY < objs[j].bottom){
          colX = true;
          break;
        }else{
        }
      } 
    }

    //Line of sight is not blocked by object
    if(colX == false && colY == false){
      playerDetected = true;
    }else{
      playerDetected = false; 
    }

  }
  
  void display(){
    
    rectMode(CENTER);
    if(playerDetected){
      fill(200,0,0);
    } else{
      fill(200,100,0);
    }
    stroke(0);
    ellipse(x,y,w,h);
    
    noFill();
    rect(x,y-h/2-10,w,5);
    
    if(health > 0){
      float healthbar = map(health,0,100,0,w);
      rectMode(CORNER);
      fill(255,0,0);
      rect(x-w/2,y-h/2-12.5,healthbar,5);
    }
    
  }
  
  
  
  
  
  
}
