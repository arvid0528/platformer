class Goal{
  
  
  float x,y;
  float d = 100;


  Goal(float x_, float y_){
    
  x = x_;
  y = y_;
  

  }
  
  void display(){
    
    stroke(0);
    fill(0,255,0,150);
    ellipse(x,y,d,d); 
    
  }
  
  void goalCheck(){
    
    if(dist(playerX,playerY,x,y) < (player1.d + d)/2){
      levelWon = true;
    } 
    
    
    
    
  }
  
  
  
  
  
  
  
  
}
