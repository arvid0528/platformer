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
  
  void display(){
      
    if(millis() < timeUp*1000+savedTime){
      fill(255,0,0);
      text(text,x,y);
    }
    
  }
  
  
  
  
  
}
