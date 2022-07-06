class FireAnimation{

    float startTime = millis();
    float x, y;

    FireAnimation(float x_, float y_){

        x = x_;
        y = y_;

    }

    void display(){

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