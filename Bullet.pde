class Bullet{
  
  float x, y, velX, velY, dir, vel, a, bounces;
  
  Bullet(){
    
    a = angle;
    x = playerX + 25*cos(a);
    y = playerY + 25*sin(a);
    vel = 15;
    
    velX = vel * cos(a);
    velY = vel * sin(a);    
    
  }
  
  void kill(int index){
    
    int i = index;
    
    //createExplosion(bullets[i].x,bullets[i].y);
    arrayCopy(bullets,i+1,bullets,i,bullets.length-i-1);
    bullets = (Bullet[]) shorten(bullets);
    
  }
  
  void display(){
    
    x += velX;
    y += velY;
        
    noStroke();
    fill(255,230,0);
    ellipse(x,y,5,5);
    
  }
  
}
