package com.maurice.GameWorld;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.maurice.GameObjects.Ball;
import com.maurice.GameObjects.Cluster;
import com.maurice.GameObjects.Wheel;


public class GameWorld{
	
	private int midPointX;
    private int midPointY;
    private int gameHeight;
    private int gameWidth;
    
    private int nextBallTime=10;
    
    private GameState currentState;
    
    public enum GameState{
    	READY, RUNNING, GAMEOVER
    }
    
	private Rectangle rect = new Rectangle(0, 0, 17, 12);
	private Wheel wheel;
	private float velocity=2;
	Random rand = new Random();
	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	
	private int sensitivity=2;
	private int ballantispeed=10;
	private int score=0;
	private int[] health;
	private int centerRadius;
	public GameWorld(int midPointX,int midPointY, int gameHeight, int gameWidth) {
		currentState = GameState.READY;
		wheel=new Wheel(midPointX,midPointY);
		health=wheel.getHealth();
		centerRadius=wheel.getCenterRadius();
		this.midPointX=midPointX;
		this.midPointY=midPointY;
		this.gameHeight=gameHeight;
		this.gameWidth=gameWidth;
		shoot();	
		currentState = GameState.RUNNING;
	}
	public void update(float delta) {
        switch (currentState) {
        case READY:
            updateReady(delta);
            break;

        case RUNNING:
        default:
            updateRunning(delta);
            break;
        }

    }
	private void restartGame() {
		currentState = GameState.RUNNING;
		score=0;
    }
    private void updateReady(float delta) {
        // Do nothing for now
    }
	public void updateRunning(float delta) {
        //System.out.println("GameWorld - update");
        rect.x+=velocity;
        if (rect.x > 320) {
            rect.x = 0;
        }
        if (rect.x < 0) {
            rect.x = 320;
        }
        //check collision-----------------
        for (int i = 0; i < balls.size(); i++) {
			Ball p = (Ball) balls.get(i);
			switch(checkCollision(p)){
			case 1:
				p.setAlive(false);
				System.out.println("absorbed");
				break;
			case 2:
				p.setAlive(false);
				System.out.println("collided");
				createCluster((int)p.getPosition().x, (int)p.getPosition().y,p.getType());
				break;
			case 0:
				break;
			}
		}
        for(int i=0;i<3;i++){
        	if(health[i]<=0) currentState = GameState.GAMEOVER;//check gameover
        }
        
        //wheel----
        wheel.setVelocity((int)velocity*sensitivity*15);
        wheel.update(delta);
        //ArrayList balls = robot.gets();
		for (int i = 0; i < balls.size(); i++) {
			Ball p = (Ball) balls.get(i);
			if (p.isAlive() == true) {
				p.update();
			} else {
				balls.remove(i);
			}
		}
		//update clusters-------------
		for (int i = 0; i < clusters.size(); i++) {
			Cluster p = (Cluster) clusters.get(i);
			if (p.isAlive() == true) {
				p.update();
			} else {
				clusters.remove(i);
			}
		}
		//generating random balls------
		if(--nextBallTime==0){
			shoot();
			nextBallTime=(10)+rand.nextInt(60);
		}
		if(rand.nextInt(200)==1) shootHealth();//shoot health balls
	}
	public Rectangle getRect() {
	    return rect;
	}
	public Wheel getWheel() {
	    return wheel;
	}
	public float getVelocity() {
		return velocity;
	}
	public int getCenterRadius() {
		return centerRadius;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	public void move(float pixel) {
		rect.x+= pixel;
		wheel.setDirection(wheel.getDirection()+pixel*sensitivity);
	}
	public void shoot() {
		int posX=rand.nextInt(320);
		int posY=rand.nextInt(3)==0?0:gameHeight;
		//int posX=0;
		//int posY=0;
		//Ball p = new Ball(posX, posY, 0,0, rand.nextInt(3),rand.nextInt(5)+5 );
		Ball p = new Ball(posX, posY, (midPointX-posX)/ballantispeed,
				(midPointY-posY)/ballantispeed, 
				rand.nextInt(3),rand.nextInt(5)+5 );
		balls.add(p);
		System.out.println("midpoint==="+midPointX+"=="+midPointY);
		System.out.println("ball created at==="+posX+"=="+posY);
	}
	public void shootHealth() {
		int posX=rand.nextInt(320);
		int posY=rand.nextInt(3)==0?0:gameHeight;
		Ball p = new Ball(posX, posY, (midPointX-posX)/ballantispeed,
				(midPointY-posY)/ballantispeed, 
				3,rand.nextInt(5)+5 );//here 3 type is health
		balls.add(p);
	}
	public void createCluster(int x, int y,int type){
		Cluster p = new Cluster(x, y, type );
		clusters.add(p);
	}
	public ArrayList<Ball> getBalls() {
		return balls;
	}
	public ArrayList<Cluster> getClusters() {
		return clusters;
	}
	public int checkCollision(Ball p){//0=no collision 1==collide 2=absorb
		Vector2 distance=p.getPosition();
		Vector2 center=new Vector2(midPointX,midPointY);
		
		float radii=p.getRadius()+wheel.getRadius();
		if(distance.dst2(center)<(radii*radii)) {
			Vector2 dial=distance.cpy();
			dial.sub(center);
			System.out.println("dial angle==="+dial.angle());
			System.out.println("wheeldiraction===" + wheel.getDirection());
			int type=p.getType();
			int hitType=0;//wheel sector hit
			int extraAngle=0;
			if(type==1) extraAngle=120;
			else if(type==2) extraAngle=240;
			float temp2=dial.angle()-((wheel.getDirection()+360)%360);
			if(temp2<0) temp2+=360;
			hitType=(int) temp2/120;
			if(type==3){
				health[hitType]=wheel.getFullHealth();
				return 1;
			}
			System.out.println("type===" + type);
			System.out.println("hittype===" + hitType);
			
			if (type==hitType) {addScore(p.getRadius());return 1;}//absorbed
			 
			health[hitType]-=10;
			return 2;
			/*System.out.println("extraangle===" + extraAngle);
			float temp=(dial.angle()-wheel.getDirection()-extraAngle+360)%360;
			System.out.println("temp===" + temp);
			//if (temp<120) {addScore(12);return 1;}//absorbed
			System.out.println("dialangle===" + dial.angle());


			return 2;//return collided wheel sector +2;	*/	}
		return 0;
	}
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score=score;;
	}
	public GameState getCurrentState(){
		return currentState;
	}
	public void addScore(int x){
		score+=x;
	}
	public boolean isReady() {
        return currentState == GameState.READY;
    }
    public void start() {
        currentState = GameState.RUNNING;
    }
    public void restart() {
        currentState = GameState.READY;
        score = 0;
        balls.clear();
        currentState = GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

}
