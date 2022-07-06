/* autogenerated by Processing revision 1283 on 2022-07-06 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class platformer extends PApplet {

boolean wDown,aDown,sDown,dDown;
boolean spaceDown;
boolean shiftDown;
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
float rollTimer = 0;
String playerAnimation;
float rollCooldown; 

PImage standImg; 
PImage cobbleTexture;
PImage grassTextureTop;
PImage dirtTexture;
PImage zweihander;
PImage blobRight;
PImage blobLeft;
PImage blobRightAttack;
PImage blobRightAttackNoFire;
PImage blobLeftAttack;
PImage fire0;
PImage fire1;
PImage fire2;

PImage[] imageList = new PImage[3];

int pixelSize = 32;

int[] xList = new int[1440/pixelSize];
int[] yList = new int[810/pixelSize];

Player player1 = new Player();
Enemy enemy1;
EnemyWalking walker;

Explosion[] explosions = new Explosion[0];

 public void createExplosion(float x, float y){
  Explosion tempExplosion = new Explosion(x,y);
  explosions = (Explosion[]) append(explosions,tempExplosion);
}

Object[] objs = new Object[0];

 public void createObject(float x,float y,float w,float h){
  Object tempObj = new Object(x,y,w,h);
  objs = (Object[]) append(objs,tempObj);
}

InvisObject[] invisObjs = new InvisObject[0];

 public void createInvisObject(float x, float y, float w, float h){
  InvisObject tempInvisObj = new InvisObject(x, y, w, h);
  invisObjs = (InvisObject[]) append(invisObjs, tempInvisObj);
}

Popup[] popups = new Popup[0];

 public void createPopup(String text, float x, float y, float time, int size){
  Popup tempPopup = new Popup(text,x,y,time,size);
  popups = (Popup[]) append(popups,tempPopup);
}

Bullet[] bullets = new Bullet[0];

 public void createBullet(){
  Bullet tempBullet = new Bullet();
  bullets = (Bullet[]) append(bullets,tempBullet);
}

FireAnimation[] fireAnimations = new FireAnimation[0];

 public void createFireAnimation(float x, float y){
  FireAnimation tempFire = new FireAnimation(x, y);
  fireAnimations = (FireAnimation[]) append(fireAnimations, tempFire);
}

Goal goal1;


 public void setup(){
  /* size commented out by preprocessor */; 
  
  speedX = 6;
  speedY = 0;
  gravity = 1.5f;
  
  rectMode(CENTER);
  textAlign(CENTER,CENTER);
  
  playerX = width/2; 
  playerY = height/2;
  
  mode = "play";
  playerAnimation = "stand";

  standImg = loadImage("dude.png");
  cobbleTexture = loadImage("cobbleTexture.png");
  grassTextureTop = loadImage("grassTextureTop.png");
  dirtTexture = loadImage("dirtTexture.png");
  zweihander = loadImage("zweihander.png");
  blobRight = loadImage("blobRight.png");
  blobLeft = loadImage("blobLeft.png");
  blobRightAttack = loadImage("blobRightAttack.png");
  blobRightAttackNoFire = loadImage("blobRightAttackNoFire.png");
  blobLeftAttack = loadImage("blobLeftAttack.png");
  fire0 = loadImage("fire0.png");
  fire1 = loadImage("fire1.png");
  fire2 = loadImage("fire2.png");

  imageList[0] = fire0;
  imageList[1] = fire1;
  imageList[2] = fire2;

  standImg.resize(pixelSize*2, pixelSize*2);
  grassTextureTop.resize(30, 30);


  for(int i = 0; i < width/pixelSize; i++){
    xList[i] = i*pixelSize;
  }
  for(int i = 0; i < height/pixelSize; i++){
    yList[i] = i*pixelSize;
  }
  
  createObject(0,height/2,pixelSize,height);
  createObject(width-pixelSize,height/2,pixelSize,height);
  createObject(width/2,0,width,pixelSize);
  createObject(width/2,height-pixelSize,width,pixelSize);
  
  
  //Level 1
  
  enemy1 = new Enemy(850,height-230,50,60);
  goal1 = new Goal(width-100,100);
  walker = new EnemyWalking(1000, height - 335, 30, 70);
  
  //createObject(200,height/2-150,50,height-150);
  //createObject(400,height/2+150,50,height-150);
  //createObject(600,height/2-150,50,height-150);
  
  createObject(1000, height-280, 500, 40);
  createInvisObject(750, height-320, 20, 20);
  createInvisObject(1270, height-320, 20, 20);

  playerX = 100;
  playerY = 100;
    
}

 public void draw(){
  background(120,170,180,50);
  
  stroke(80);
  strokeWeight(1);
  
  for(int x = 0; x < width/pixelSize; x++){
    line(x*pixelSize,0,x*pixelSize,height);
  }
  for(int y = 0; y < height/pixelSize; y++){
    line(0,y*pixelSize,width,y*pixelSize);
  }
  

  /*
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
  */

  walker.pacing();
  walker.playerCollide();
  walker.collissionDetection();
  walker.display();
  
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

  for(int i = 0; i < invisObjs.length; i++){
    invisObjs[i].display();
  }
  
  for(int i = 0; i < popups.length; i++){
    popups[i].display(); 
  }

  for(int i = 0; i < explosions.length; i++){
    explosions[i].display(); 
  }
  for(int i = 0; i < fireAnimations.length; i++){
    fireAnimations[i].display();
  }

  // PLAYERS VERTICAL COLLISION
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

  for(int i = 0; i < objs.length; i++){
    if(playerX+player1.w/2 > objs[i].left && playerX-player1.w/2 < objs[i].right && 
    playerY+player1.h/2 > objs[i].top && playerY-player1.h/2 + speedY <= objs[i].bottom){
      sIndex = i;
      sLoopFail = sIndex;
    }
  }
  if(sLoopFail == -1){
    //playerY += speedY; 
    //speedY += gravity;
  }else{
    playerY = objs[sIndex].bottom + player1.h/2; 
    speedY *= -0.8f;
    //grounded = true;
    //dblJumpAvail = true;
    //playerAcc = 0;
    sLoopFail = -1;
  }

  if(playerAnimation == "stand"){
    player1.display();
    //image(standImg, playerX-player1.w/2, playerY-player1.h/2);
  } 
  else if(playerAnimation == "roll"){
    player1.displayRoll();
  }

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

  /*
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
  */
  
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
      player1.facingRight = false;
    }else{
      playerX = objs[aIndex].right + player1.w/2;
      aLoopFail = -1;
    }
  }
  
  /*
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
  */
  
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
      player1.facingRight = true;
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
    playerY = playerY - 5;
    speedY = -20; 
    jump = false;
  }
    
  if(shiftDown && rollCooldown == 0){
    // roll dodge
    rollTimer = 15;
  }

  if(rollTimer > 0){
    playerAnimation = "roll";
    rollTimer -= 1;
    rollCooldown = 30;
  } else {
    playerAnimation = "stand";
  }

  if(rollCooldown > 0){
    rollCooldown -= 1;
  }
  
  if(!grounded){
    //playerY += speedY; 
    //speedY += gravity;
  }
  
  if(player1.attackDuration > 0){
    noFill();
    stroke(255,0,0);
    //rect(playerX + pixelSize, playerY, pixelSize, pixelSize);
    rectMode(CENTER);
    player1.attackDuration -= 1;
  }


  if(player1.attackCooldown > 0){
    player1.attackCooldown -= 1;
  }


  /*
  textSize(30);
  text("grounded: " + grounded, width/2, height/2);
  text("jump: " + jump, width/2, height/2 + 50);
  text("playerAnimation: " + playerAnimation, width/2, height/2 + 100);
  text("rollTimer: " + rollTimer, width/2, height/2 + 150);
  text("rollCooldown: " + rollCooldown, width/2, height/2 + 200);
  */

  spaceDown = false;
  
}
 public void keyPressed(){
  if(key=='w'||key=='W')wDown=true; 
  if(key=='a'||key=='A')aDown=true; 
  if(key=='s'||key=='S')sDown=true; 
  if(key=='d'||key=='D')dDown=true; 
  if(key==' ')spaceDown=true;
  if(keyCode == SHIFT)shiftDown=true;
  if(keyCode == UP && player1.attackCooldown == 0){
    player1.attack();
  }
  
}

 public void keyReleased(){
  if(key=='w'||key=='W')wDown=false; 
  if(key=='a'||key=='A')aDown=false; 
  if(key=='s'||key=='S')sDown=false;
  if(key=='d'||key=='D')dDown=false;  
  if(key==' ')spaceDown=false;
  if(keyCode == SHIFT)shiftDown=false;
  if(key==ENTER){
    if(mode == "play"){
      mode = "build"; 
    }else{
      mode = "play"; 
    }
  }
}
 public void mousePressed(){
  if(mode == "build"){
    savedX = mouseX;
    savedY = mouseY;
    drawLine = true;
  }
  if(mode == "play"){
    //createBullet();
    
  }
}
 public void mouseReleased(){
  if(mode == "build"){
    float posX = savedX + (mouseX - savedX)*0.5f;
    float posY = savedY + (mouseY - savedY)*0.5f;
    float tempWidth = abs(mouseX - savedX);
    float tempHeight = abs(mouseY - savedY);
    if(tempWidth > 10 && tempHeight > 10){
      if(mouseButton == LEFT){
        createObject(posX,posY,tempWidth,tempHeight);
      }else if(mouseButton == RIGHT){
        createInvisObject(posX, posY, tempWidth, tempHeight);
      }
    }else{
      createPopup("Too small",posX,posY,1,20);
    }
    drawLine = false;
  }
}
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
  
   public void kill(int index){
    
    int i = index;
    
    //createExplosion(bullets[i].x,bullets[i].y);
    arrayCopy(bullets,i+1,bullets,i,bullets.length-i-1);
    bullets = (Bullet[]) shorten(bullets);
    
  }
  
   public void display(){
    
    x += velX;
    y += velY;
        
    noStroke();
    fill(255,230,0);
    ellipse(x,y,5,5);
    
  }
  
}
 public boolean bulletCollission(float colX, float colY, float velX, float velY){
  
  //calculate position next frame
  float newX = colX + velX;
  float newY = colY + velY;

  for(int i = 0; i < objs.length; i++){
    if(newX > objs[i].left && newX < objs[i].right && newY > objs[i].top && newY < objs[i].bottom){
      return true;
    }
  }
  return false;
  
}
class Enemy{
  
  float x,y,w,h;
  float velX,velY;
  int health;
  
  Enemy(float x_, float y_, float w_, float h_){
     
    x = x_;
    y = y_;
    w = w_;
    h = h_;
    
    health = 100;

  }
  
  //Normal pace when not chasing
   public void pacing(){
    
    float maxSpeed = 3;
    
    velX += random(-0.5f,0.5f);
    velY += random(-1,1);
    
    if(velX > maxSpeed)velX=maxSpeed;
    if(velX < -maxSpeed)velX=-maxSpeed;
    if(velY > maxSpeed)velY=maxSpeed;
    if(velY < -maxSpeed)velY=-maxSpeed;
    
    x += velX;
    y += velY;
     
  }
  
  //Enemy can see and is chasing player 
   public void following(){
    
    if(x > playerX)velX = -5;
    else if(x < playerX)velX = 5;
    
    if(y > playerY)velY = -5;
    else if(y < playerY)velY=5;
   
    x += velX;
    y += velY;
    
  }
  
   public void collissionDetection(){
     
    if(velX > 0){
      for(int i = 0; i < objs.length; i++){
        if(x+w/2 + velX > objs[i].left && x <= objs[i].left && y-h/2 < objs[i].bottom && y+h/2 > objs[i].top){
          x = objs[i].left - w/2;
          velX *= -0.75f;

        }
      }
    }
    if(velX < 0){
      for(int i = 0; i < objs.length; i++){
        if(x-w/2 + velX < objs[i].right && x >= objs[i].right && y-h/2 < objs[i].bottom && y+h/2 > objs[i].top){
          x = objs[i].right + w/2;
          velX *= -0.75f;
        }
      }
    }
    if(velY > 0){
      for(int i = 0; i < objs.length; i++){
        if(y+h/2 + velY > objs[i].top && y <= objs[i].top && x-w/2 < objs[i].right && x+w/2 > objs[i].left){
          y = objs[i].top - h/2;
          velY *= -0.75f;
        }
      }
    }
    if(velY < 0){
      for(int i = 0; i < objs.length; i++){
        if(y-h/2 + velY < objs[i].bottom && y >= objs[i].bottom && x-w/2 < objs[i].right && x+w/2 > objs[i].left){
          y = objs[i].bottom + h/2;
          velY *= -0.75f;
        }
      }
    }

  }
  
   public void playerCollide(){
    
    if(playerX + player1.d/2 > x-w/2 && playerX - player1.d/2 < x+w/2 && playerY + player1.d/2 > y-h/2 && playerY - player1.d/2 < y+h/2){
      death = true;
    }else{
      death = false; 
    }
    
  }
  
  //Collission of Enemy and Player Bullet
   public void bulletCollide(){
    
    for(int i = 0; i < bullets.length; i++){
       if(bullets[i].x > x-w/2 && bullets[i].x < x+w/2 && bullets[i].y > y-h/2 && bullets[i].y < y+h/2){
         bullets[i].kill(i);
         
         health -= player1.damage;
         
       }
    }
    
    
  }
  
  //Check if enemy can see the player
   public void playerDetection(){
    
    
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
  
   public void display(){
    
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
      rect(x-w/2,y-h/2-12.5f,healthbar,5);
    }
    
  }
  
  
  
  
  
  
}
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
    velX = 3;

  }
  
  //Normal pace when not chasing
   public void pacing(){
    
    x += velX;

    if(velX > 0){
      for(int i = 0; i < invisObjs.length; i++){
        if(x+w/2 + velX > invisObjs[i].left && x <= invisObjs[i].left && y-h/2 < invisObjs[i].bottom && y+h/2 > invisObjs[i].top){
          x = invisObjs[i].left - w/2;
          velX *= -1;

        }
      }
    }
    if(velX < 0){
      for(int i = 0; i < invisObjs.length; i++){
        if(x-w/2 + velX < invisObjs[i].right && x >= invisObjs[i].right && y-h/2 < invisObjs[i].bottom && y+h/2 > invisObjs[i].top){
          x = invisObjs[i].right + w/2;
          velX *= -1;
        }
      }
    }

    
  }
  
  //Enemy can see and is chasing player 
   public void following(){
    
    if(x > playerX)velX = -5;
    else if(x < playerX)velX = 5;
    
    if(y > playerY)velY = -5;
    else if(y < playerY)velY=5;
   
    x += velX;
    y += velY;
    
  }
  
   public void collissionDetection(){
     
    if(velX > 0){
      for(int i = 0; i < objs.length; i++){
        if(x+w/2 + velX > objs[i].left && x <= objs[i].left && y-h/2 < objs[i].bottom && y+h/2 > objs[i].top){
          x = objs[i].left - w/2;
          velX *= -1;

        }
      }
    }
    if(velX < 0){
      for(int i = 0; i < objs.length; i++){
        if(x-w/2 + velX < objs[i].right && x >= objs[i].right && y-h/2 < objs[i].bottom && y+h/2 > objs[i].top){
          x = objs[i].right + w/2;
          velX *= -1;
        }
      }
    }
    if(velY > 0){
      for(int i = 0; i < objs.length; i++){
        if(y+h/2 + velY > objs[i].top && y <= objs[i].top && x-w/2 < objs[i].right && x+w/2 > objs[i].left){
          y = objs[i].top - h/2;
          velY *= -0.75f;
        }
      }
    }
    if(velY < 0){
      for(int i = 0; i < objs.length; i++){
        if(y-h/2 + velY < objs[i].bottom && y >= objs[i].bottom && x-w/2 < objs[i].right && x+w/2 > objs[i].left){
          y = objs[i].bottom + h/2;
          velY *= -0.75f;
        }
      }
    }

  }
  
   public void playerCollide(){
    
    if(playerX + player1.d/2 > x-w/2 && playerX - player1.d/2 < x+w/2 && playerY + player1.d/2 > y-h/2 && playerY - player1.d/2 < y+h/2){
      death = true;
    }else{
      death = false; 
    }
    
  }
  
  //Collission of Enemy and Player Bullet
   public void bulletCollide(){
    
    for(int i = 0; i < bullets.length; i++){
       if(bullets[i].x > x-w/2 && bullets[i].x < x+w/2 && bullets[i].y > y-h/2 && bullets[i].y < y+h/2){
         bullets[i].kill(i);
         
         health -= player1.damage;
         
       }
    }
    
    
  }
  
  //Check if enemy can see the player
   public void playerDetection(){
    
    
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
  
   public void display(){
    
    rectMode(CENTER);
    if(playerDetected){
      fill(200,0,0);
    } else{
      fill(200,100,0);
    }
    stroke(0);
    rect(x,y,w,h);
    
    noFill();
    rect(x,y-h/2-10,w,5);
    
    if(health > 0){
      float healthbar = map(health,0,100,0,w);
      rectMode(CORNER);
      fill(255,0,0);
      rect(x-w/2,y-h/2-12.5f,healthbar,5);
      }
    }
    
  }
  
  
  
  
  
  

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
  
   public void display(){
    
    if(millis() < timeUp + savedTime){
      fill(0);
      image(explosionPic,tempX-25,tempY-25,50,50);
    }

  }
  
  
}
class Goal{
  
  
  float x,y;
  float d = 100;


  Goal(float x_, float y_){
    
  x = x_;
  y = y_;
  

  }
  
   public void display(){
    
    stroke(0);
    fill(0,255,0,150);
    ellipse(x,y,d,d); 
    
  }
  
   public void goalCheck(){
    
    if(dist(playerX,playerY,x,y) < (player1.d + d)/2){
      levelWon = true;
    } 
    
    
    
    
  }
  
  
  
  
  
  
  
  
}
class InvisObject{
  
  float x,y,w,h,cornerX,cornerY,newX,newY;
  float left;
  float right;
  float top;
  float bottom;
  float tempWidth;
  float tempHeight;
  
  InvisObject(float obj_x,float obj_y,float obj_w,float obj_h){
    
    x = obj_x;
    y = obj_y;
    w = obj_w;
    h = obj_h;
    
    cornerX = obj_x - w/2;
    cornerY = obj_y - h/2;
    
    left = cornerX;
    right = cornerX + w;
    top = cornerY;
    bottom = cornerY + h;
    
    float rest = left % pixelSize;              
 
    if(rest >= pixelSize/2){
      left += pixelSize - rest;
    }
    else if(rest > 0){
      left -= rest;
    }
    
    rest = right % pixelSize;
    if(rest >= pixelSize/2){
      right += pixelSize - rest;
    }
    else if(rest > 0){
      right -= rest;
    }
    
    rest = top % pixelSize;
    if(rest >= pixelSize/2){
      top += pixelSize - rest;
    }
    else if(rest > 0){
      top -= rest;
    }
   
    rest = bottom % pixelSize;
    if(rest >= pixelSize/2){
      bottom += pixelSize - rest;
    }
    else if(rest > 0){
      bottom -= rest;
    }
          
    w = right - left;
    h = bottom - top;
    
    if(w < pixelSize)w = pixelSize;
    if(h < pixelSize)h = pixelSize;
    
  }
  
   public void display(){
    
    fill(0,0,0,0);
    stroke(255,0,0);
    rectMode(CORNER);
    rect(left,top,w,h);   

  }
  
  
}
class Player{
  
  float d = pixelSize*2;
  float w = pixelSize*2;
  float h = pixelSize*2;
  float damage = 10;
  boolean facingRight = true;
  float attackCooldown;
  float attackDuration;
  
   public void attack(){

    attackDuration = 10;
    attackCooldown = 40;
    createFireAnimation(playerX - 32, playerY - 32);
    
  }

   public void display(){
    
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

   public void displayRoll(){
    stroke(0);
    fill(0,200,200);
    rect(playerX,playerY,w,w);
  }
  
}
class FireAnimation{

    float startTime = millis();
    float x, y;

    FireAnimation(float x_, float y_){

        x = x_;
        y = y_;

    }

     public void display(){

        if((millis() - startTime) < 200){
            image(fire0, x, y);
        }
        else if((millis() - startTime) < 400){
            image(fire1, x, y);
        }
        else if(millis() - startTime < 600){
            image(fire2, x, y);
        }
        
    }
    

}
class Object{
  
  float x,y,w,h,cornerX,cornerY,newX,newY;
  float left;
  float right;
  float top;
  float bottom;
  float tempWidth;
  float tempHeight;
  
  Object(float obj_x,float obj_y,float obj_w,float obj_h){
    
    x = obj_x;
    y = obj_y;
    w = obj_w;
    h = obj_h;
    
    cornerX = obj_x - w/2;
    cornerY = obj_y - h/2;
    
    left = cornerX;
    right = cornerX + w;
    top = cornerY;
    bottom = cornerY + h;
    
    float rest = left % pixelSize;              
 
    if(rest >= pixelSize/2){
      left += pixelSize - rest;
    }
    else if(rest > 0){
      left -= rest;
    }
    
    rest = right % pixelSize;
    if(rest >= pixelSize/2){
      right += pixelSize - rest;
    }
    else if(rest > 0){
      right -= rest;
    }
    
    rest = top % pixelSize;
    if(rest >= pixelSize/2){
      top += pixelSize - rest;
    }
    else if(rest > 0){
      top -= rest;
    }
   
    rest = bottom % pixelSize;
    if(rest >= pixelSize/2){
      bottom += pixelSize - rest;
    }
    else if(rest > 0){
      bottom -= rest;
    }
          
    w = right - left;
    h = bottom - top;
    
    if(w < pixelSize)w = pixelSize;
    if(h < pixelSize)h = pixelSize;
    
    
    
  }
  
   public void display(){
    
    fill(0,0,0,0);
    stroke(255);
    rectMode(CORNER);
    rect(left,top,w,h);   

    float widthAmount = w/pixelSize;
    float heightAmount = h/pixelSize;
    
    for(int i = 0; i < widthAmount; i++){
      image(grassTextureTop, left + i*pixelSize, top);
      image(dirtTexture, left + i*pixelSize, top+pixelSize);
      for(int j = 0; j < heightAmount-2; j++){
        image(cobbleTexture, left + i*pixelSize, top + (j+2)*pixelSize);
      }
    }
    

  }
  
}
class Popup{
  
  float timeUp,x,y,size;
  String text;
  float savedTime = millis();
 
  Popup(String popup_text, float popup_x, float popup_y, float popup_timeUp, int popup_size){

    text = popup_text;
    x = popup_x;
    y = popup_y;
    timeUp = popup_timeUp;
    size = popup_size;
    
    
  } 
  
   public void display(){
      
    if(millis() < timeUp*1000+savedTime){
      fill(255,0,0);
      text(text,x,y);
    }
    
  }
  
  
  
  
  
}


  public void settings() { size(1440, 810); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "platformer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
