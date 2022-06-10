boolean wDown,aDown,sDown,dDown;
boolean spaceDown;
float playerX,playerY;
float playerAcc;
float speedY;
float speedX; 
float vel;
float gravity;
boolean moveBool = false;
int wLoopFail = -1;
int aLoopFail = -1;
int sLoopFail = -1;
int dLoopFail = -1;
int wIndex;
int aIndex;
int sIndex;
int dIndex;
int createdObjects;
float savedX,savedY;
boolean drawLine = false;
float angle;
String mode;
boolean death;
boolean playerDetected = false;
boolean levelWon;
int level = 1; 
boolean grounded = false;
boolean jump;
boolean dblJumpAvail = true;
float spaceClickedTimer;

int[] xList = new int[1080/20];
int[] yList = new int[720/20];

Player player1 = new Player();
Enemy enemy1;

Explosion[] explosions = new Explosion[0];

void createExplosion(float x, float y){
  Explosion tempExplosion = new Explosion(x,y);
  explosions = (Explosion[]) append(explosions,tempExplosion);
}

Object[] objs = new Object[0];

void createObject(float x,float y,float w,float h){
  Object tempObj = new Object(x,y,w,h);
  objs = (Object[]) append(objs,tempObj);
}

Popup[] popups = new Popup[0];

void createPopup(String text, float x, float y, float time, int size){
  Popup tempPopup = new Popup(text,x,y,time,size);
  popups = (Popup[]) append(popups,tempPopup);
}

Bullet[] bullets = new Bullet[0];

void createBullet(){
  Bullet tempBullet = new Bullet();
  bullets = (Bullet[]) append(bullets,tempBullet);
}

Goal goal1;


void setup(){
  size(1080,720); 
  
  speedX = 8;
  speedY = 0;
  gravity = 0.5;
  
  rectMode(CENTER);
  textAlign(CENTER,CENTER);
  
  playerX = width/2; 
  playerY = height/2;
  
  mode = "play";
  //mode = "build";

  for(int i = 0; i < width/20; i++){
    xList[i] = i*20;
  }
  for(int i = 0; i < height/20; i++){
    yList[i] = i*20;
  }
  
  createObject(0,height/2,20,height);
  createObject(width-20,height/2,20,height);
  createObject(width/2,0,width,20);
  createObject(width/2,height-20,width,20);
  
  
  //Level 1
  
  enemy1 = new Enemy(850,height-200,50,50);
  goal1 = new Goal(width-100,100);
  
  createObject(200,height/2-150,50,height-150);
  createObject(400,height/2+150,50,height-150);
  createObject(600,height/2-150,50,height-150);
  
  playerX = 100;
  playerY = 100;
    
    
}

void draw(){
  background(50);
  
  stroke(80);
  strokeWeight(1);
  for(int x = 0; x < width/20; x++){
    line(x*20,0,x*20,height);
  }
  for(int y = 0; y < height/20; y++){
    line(0,y*20,width,y*20);
  }
  
  if(enemy1.health > 0){
    enemy1.collissionDetection();
    enemy1.playerCollide();
    enemy1.bulletCollide();
    if(playerDetected){
      enemy1.following();
    }else{
      enemy1.pacing();
    }
    enemy1.playerDetection();
    enemy1.display();
  }
  
  goal1.goalCheck();
  goal1.display();
  
  if(levelWon){
    //reset everything
    level++;
  } 


  
  textSize(30);
  fill(255);
  text("Mode: " + mode,width/2,50);
  textSize(20);
  text(bullets.length,width/2,80);
  
  for(int i = 0; i < bullets.length; i++){
    if(bulletCollission(bullets[i].x,bullets[i].y,bullets[i].velX,bullets[i].velY)==true){
      
      bullets[i].kill(i);
      break;
    }
    
    if(bullets[i].x > width || bullets[i].x < 0 || bullets[i].y > height || bullets[i].y < 0){
      arrayCopy(bullets,i+1,bullets,i,bullets.length-i-1);
      bullets = (Bullet[]) shorten(bullets);
    }
    
  }
  
  for(int i = 0; i < bullets.length; i++){
    bullets[i].display(); 
  }
  
  for(int i = 0; i < objs.length; i++){
    objs[i].display();
  }
  
  for(int i = 0; i < popups.length; i++){
    popups[i].display(); 
  }

  for(int i = 0; i < explosions.length; i++){
    explosions[i].display(); 
  }

  playerAcc = playerAcc + 0.4;
  for(int i = 0; i < objs.length; i++){
    if(playerX+player1.w/2 > objs[i].left && playerX-player1.w/2 < objs[i].right && 
    playerY+player1.h/2 + speedY > objs[i].top && playerY+player1.h/2 <= objs[i].bottom){
      sIndex = i;
      sLoopFail = sIndex;
    }
  }
  if(sLoopFail == -1){
    playerY += speedY; 
    speedY += gravity;
  }else{
    playerY = objs[sIndex].top - player1.h/2; 
    grounded = true;
    dblJumpAvail = true;
    //playerAcc = 0;
    sLoopFail = -1;
  }

  player1.display();
  
  if(death){
    textSize(50);
    fill(255);
    text("YOU DIED",width/2,height/2); 
  }
  
  if(drawLine){
    stroke(76,224,89,200);
    noFill();
    rectMode(CORNER);
    quad(savedX,savedY,mouseX,savedY,mouseX,mouseY,savedX,mouseY);
    rectMode(CENTER);
  }  
  
  if(wDown || !grounded){
    for(int i = 0; i < objs.length; i++){
      if(playerY-player1.h/2 + speedY < objs[i].bottom && playerY-player1.h/2 >= objs[i].bottom && 
      playerX+player1.w/2 > objs[i].left && playerX-player1.w/2 < objs[i].right){
        wIndex = i;
        wLoopFail = wIndex;
      }
    }
    if(wLoopFail == -1){
      playerY += speedY; 
    }else{
      playerY = objs[wIndex].bottom + player1.h/2;
      wLoopFail = -1;
    }
  }
  
  if(aDown){
    for(int i = 0; i < objs.length; i++){
      if(playerX-player1.w/2 - speedX < objs[i].right && playerX-player1.w/2 >= objs[i].right && 
      playerY+player1.h/2 > objs[i].top && playerY-player1.h/2 < objs[i].bottom){
        aIndex = i;
        aLoopFail = aIndex;
      }
    }
    if(aLoopFail == -1){
      playerX -= speedX; 
    }else{
      playerX = objs[aIndex].right + player1.w/2;
      aLoopFail = -1;
    }
  }
  
  if(sDown && !grounded){
    for(int i = 0; i < objs.length; i++){
      if(playerX+player1.w/2 > objs[i].left && playerX-player1.w/2 < objs[i].right && 
      playerY+player1.h/2 + speedY > objs[i].top && playerY+player1.h/2 <= objs[i].bottom){
        sIndex = i;
        sLoopFail = sIndex;
      }
    }
    if(sLoopFail == -1){
      playerY += speedY; 
    }else{
      playerY = objs[sIndex].top - player1.h/2; 
      sLoopFail = -1;
    }
  }
  
  if(dDown){
    //Loop through all objects in objs
    for(int i = 0; i < objs.length; i++){
      //contact with left side of object
      if(playerX+player1.w/2 + speedX > objs[i].left && playerX+player1.w/2 <= objs[i].right && 
      playerY+player1.h/2 > objs[i].top && playerY-player1.h/2 < objs[i].bottom){
        //x stopped at left side of object
        //save i for object x stops at
        dIndex = i;
        dLoopFail = dIndex;
      }
    }
    //no collission was found, carry on as usual
    if(dLoopFail == -1){
      playerX += speedX; 
    }else{
      //collission: x stops at saved object index
      playerX = objs[dIndex].left - player1.w/2;
      
      //playerX = objs[dIndex].x - objs[dIndex].w/2 - 25;
      dLoopFail = -1;
    }
  }
  
  if(spaceClickedTimer > 0){
    spaceClickedTimer -= 1;
  }
  
  if((spaceDown && grounded && spaceClickedTimer == 0)){
    grounded = false;
    jump = true;
    spaceClickedTimer = 5;
  }
  else if(spaceDown && !grounded && dblJumpAvail && spaceClickedTimer == 0){
    jump = true;
    dblJumpAvail = false;
  }
  
  if(jump){
    println("JUMP");
    playerY = playerY - 5;
    speedY = -10; 
    jump = false;
  }
    
    
  if(!grounded){
    //playerY += speedY; 
    //speedY += gravity;
  }
  
  textSize(30);
  text("grounded: " + grounded, width/2, height/2);
  text("jump: " + jump, width/2, height/2 + 50);
  text("dblJumpAvail: " + dblJumpAvail, width/2, height/2 + 100);
  text("spaceClickedTimer: " + spaceClickedTimer, width/2, height/2 + 150);
  
  spaceDown = false;
  
}
void keyPressed(){
  if(key=='w'||key=='W')wDown=true; 
  if(key=='a'||key=='A')aDown=true; 
  if(key=='s'||key=='S')sDown=true; 
  if(key=='d'||key=='D')dDown=true; 
  if(key==' ')spaceDown=true;
  
}

void keyReleased(){
  if(key=='w'||key=='W')wDown=false; 
  if(key=='a'||key=='A')aDown=false; 
  if(key=='s'||key=='S')sDown=false;
  if(key=='d'||key=='D')dDown=false;  
  if(key==' ')spaceDown=false;
  if(key==ENTER){
    if(mode == "play"){
      mode = "build"; 
    }else{
      mode = "play"; 
    }
  }
}
void mousePressed(){
  if(mode == "build"){
    savedX = mouseX;
    savedY = mouseY;
    drawLine = true;
  }
  if(mode == "play")createBullet();
}
void mouseReleased(){
  if(mode == "build"){
    float posX = savedX + (mouseX - savedX)*0.5;
    float posY = savedY + (mouseY - savedY)*0.5;
    float tempWidth = abs(mouseX - savedX);
    float tempHeight = abs(mouseY - savedY);
    if(tempWidth > 10 && tempHeight > 10){
      createObject(posX,posY,tempWidth,tempHeight);
    }else{
      createPopup("Too small",posX,posY,1,20);
    }
    drawLine = false;
  }
}
