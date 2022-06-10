boolean bulletCollission(float colX, float colY, float velX, float velY){
  
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
