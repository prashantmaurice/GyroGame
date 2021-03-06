package com.maurice.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.maurice.GameWorld.GameRenderer;
import com.maurice.GameWorld.GameWorld;
import com.maurice.GyroHelpers.MyInputGestureListener;
import com.maurice.gyro.GyroGame;

public class RestartScreen implements Screen {
	 

    GyroGame game; // Note it's "GyroGame" not "Game"
    private SpriteBatch batch;
    private BitmapFont font;
    float screenWidth;
    float screenHeight;
    String yourScore;
    private int score=0;//useful for final display of score only
    // constructor to keep a reference to the main Game class
     public RestartScreen(GyroGame game){
            this.game = game;
            System.out.println("text");
            screenWidth = Gdx.graphics.getWidth();
     		screenHeight = Gdx.graphics.getHeight();
     		batch = new SpriteBatch();  //text display
            font = new BitmapFont(Gdx.files.internal("data/devgothic.fnt"));
            font.setColor(Color.GRAY);
            font.setScale((float) 1);
     }
     
     @Override
     public void render(float delta) {
             // update and draw stuff
    	 //score=game.gameScreen.getScore();
    	 yourScore="YOURSCORE = "+score;
          if (Gdx.input.justTouched()) // use your own criterion here
              {
        	  	game.gameScreen.setScore(0);
        	  	game.setScreen(new GameScreen(game));
        	  	System.out.println("just touched....!");
              }
          Gdx.gl.glClearColor(220, 220, 220, 1);
          Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
          //add score
          batch.begin();
          font.draw(batch, "GYRO", 10, screenHeight-20);
          font.draw(batch, yourScore, 10, screenHeight-80);//text display
          font.draw(batch, "TAP SCREEN TO RESTART GAME", 10, screenHeight/2);//text display
          font.draw(batch, "DESIGNED BY : MAURICE", 10, (screenHeight/2)-40);//text display
          batch.end();
     }
     public void setScore(int score){
    	 this.score=score;
     }

    @Override
     public void resize(int width, int height) {
     }


    @Override
     public void show() {
          // called when this screen is set as the screen with game.setScreen();
    	System.out.println("mainmenu screen set");
     }


    @Override
     public void hide() {
          // called when current screen changes from this to a different screen
     }


    @Override
     public void pause() {
     }


    @Override
     public void resume() {
     }


    @Override
     public void dispose() {
             // never called automatically
     }
}