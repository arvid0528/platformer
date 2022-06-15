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
  
  void display(){
    
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
