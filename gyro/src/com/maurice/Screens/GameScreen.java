package com.maurice.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.maurice.GameWorld.GameRenderer;
import com.maurice.GameWorld.GameWorld;
import com.maurice.GameWorld.GameWorld.GameState;
import com.maurice.GyroHelpers.MyInputGestureListener;
import com.maurice.gyro.GyroGame;

public class GameScreen implements Screen{

	GyroGame game;
    private GameWorld world;
    private GameRenderer renderer;
    private int score=0;//useful for final display of score after gameover
    //private Rectangle rect;
    
    // This is the constructor, not the class declaration
    public GameScreen(GyroGame game) {
        System.out.println("GameScreen Attached");
        this.game = game;
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = screenWidth;
		float gameHeight = screenHeight ;
		int midPointX = (int) (gameWidth / 2);
		int midPointY = (int) (gameHeight / 2);
        
		System.out.println("Screenmidpoint==="+midPointX+"=="+midPointY);
        world = new GameWorld(midPointX,midPointY,(int)gameHeight, (int)gameWidth);
        //rect = world.getRect();
        renderer = new GameRenderer(world,midPointX, midPointY, (int)gameHeight, (int)gameWidth);
        
        Gdx.input.setInputProcessor(new GestureDetector(new MyInputGestureListener(world)));
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
        if(world.getCurrentState()== GameState.GAMEOVER){
        	score=world.getScore();
        	game.restartScreen.setScore(score);
        	game.setScreen(game.restartScreen);
        	
        }
    }
    public int getScore() {
    	return score;
    }
    public void setScore(int score) {
    	world.setScore(score);
    }
    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen - resizing");
    }

    @Override
    public void show() {
        System.out.println("GameScreen - show called");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen - hide called");     
    }

    @Override
    public void pause() {
        System.out.println("GameScreen - pause called");        
    }

    @Override
    public void resume() {
        System.out.println("GameScreen - resume called");       
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}
