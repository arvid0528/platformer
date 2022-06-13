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
    
    float rest = left % 20;              
 
    if(rest >= 10){
      left += 20 - rest;
    }
    else if(rest > 0){
      left -= rest;
    }
    
    rest = right % 20;
    if(rest >= 10){
      right += 20 - rest;
    }
    else if(rest > 0){
      right -= rest;
    }
    
    rest = top % 20;
    if(rest >= 10){
      top += 20 - rest;
    }
    else if(rest > 0){
      top -= rest;
    }
   
    rest = bottom % 20;
    if(rest >= 10){
      bottom += 20 - rest;
    }
    else if(rest > 0){
      bottom -= rest;
    }
          
    w = right - left;
    h = bottom - top;
    
    if(w < 20)w = 20;
    if(h < 20)h = 20;
    
    
    
  }
  
  void display(){
    
    fill(0,0,0,0);
    stroke(255);
    rectMode(CORNER);
    rect(left,top,w,h);   

    float widthAmount = w/20;
    float heightAmount = h/20;
    
    for(int i = 0; i < widthAmount; i++){
      image(grassTextureTop, left + i*20, top);
      image(dirtTexture, left + i*20, top+20);
      for(int j = 0; j < heightAmount-2; j++){
        image(cobbleTexture, left + i*20, top + (j+2)*20);
      }
    }
    

  }
  
}
