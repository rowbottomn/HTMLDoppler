import java.util.ArrayList;

boolean paused =false;
boolean doppler = true;
Source source;
Source source2;

void setup(){
   size(1500,1000);
   
   //source = new Source(5, 3, 20);
   source = new Source(3, 12, 20);// Sonic Boom!
   source2 = new Source(new PVector(width*0.75, height/2),0, 12, 20);
   textSize(25);
}

void keyPressed(){
  if (key == ' '){
   paused = !paused; 
  }
  else if( key == '1'){
    doppler = !doppler;
    source = new Source(0, 12, 20);
  }
  else if (key == 'a'){
   source.vel.x -=0.1; 
  }
  else if( key == 'd'){
    source.vel.x +=0.1; 
  }
  else if (key == 'w'){
   source.vel.y -=0.1; 
  }
  else if( key == 's'){
    source.vel.y +=0.1; 
  }
}

void mousePressed(){
   source.pos = new PVector(mouseX, mouseY); 
}

void mouseWheel(MouseEvent event) {
  float e = event.getCount();
  source.vel.x +=e;
  print(e + ", " +source.vel.x )
}

void draw(){
  fill(0,0,0);
  
  if(!paused){
    background(200);
    source.update();
    source.check();
    source.draw();
    if (!doppler){
      source2.update();
      source2.check();
      source2.draw();
    }
  }
  text("speed: "+source.vel.x, 20, 35);
  text("frequency: "+source.freq, 20, 65);

 
}

class Crest{
   
  PVector pos;
  float radius;
  float speed = 3;
  
  public Crest(PVector _pos){
    pos = new PVector( _pos.x, _pos.y);
    radius = 10;
  }
  
  public Crest(PVector _pos, float _speed){
    pos = new PVector( _pos.x, _pos.y);
    radius = 10;
    speed = _speed;
  }
  
  void update(){
    radius += speed;
    
  }
  
  void check(){

  }
  
  void draw(){
     strokeWeight(20);
     stroke(255,255,0,100);
     noFill();
     ellipse(pos.x, pos.y, radius, radius);
  }
}

class Source{
  
  PVector pos;
  PVector vel;
  ArrayList <Crest>waves;
  color col = color(200, 200, 0, 100);
  float period = 100;
  float freq = 60/period;
  float speed_sound = 3; 
  
  public Source(){
    pos = new PVector(0, height/2);
    vel = new PVector(1, 0);
    waves = new ArrayList<Crest>();
  }

  public Source(float speed, float period ){
    pos = new PVector(0, height/2);
    vel = new PVector(speed, 0);
    waves = new ArrayList<Crest>();
    period = period;
    freq = 60/period;
  }
  
  public Source(float speed, float _speed, float _period ){
    pos = new PVector(width/4, height/2);
    vel = new PVector(speed, 0);
    waves = new ArrayList<Crest>();
    period = _period;
    freq = 60/period;
    speed_sound = _speed;
  }
   
  public Source(PVector _pos, float speed, float _speed, float _period ){
    pos = _pos;
    vel = new PVector(speed, 0);
    waves = new ArrayList<Crest>();
    period = _period;
    freq = 60/period;
    speed_sound = _speed;
  }
  void update(){
    pos.add(vel);  
    for(int i = 0; i < waves.size(); i++){
        waves.get(i).update();
    }
  }
   
  void check(){
    if(pos.x > width+10){
      pos.x = -10;
    }
    if (frameCount%period==0){
        waves.add(new Crest(pos, speed_sound));
        println("making wave"+waves.size());
    }
    for(int i = 0; i < waves.size(); i++){
      Crest wave = waves.get(i);
      if (wave.radius>3*width){
       waves.remove(wave); 
      }
    }
  }
  
  void draw(){
    //draw the source
    noStroke();
    fill(0);
    ellipse(pos.x, pos.y, 20, 20);
    for(int i = 0; i < waves.size(); i++){
        waves.get(i).draw();
    }

  }
}