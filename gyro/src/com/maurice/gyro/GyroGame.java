package com.maurice.gyro;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.maurice.Screens.GameScreen;
import com.maurice.Screens.MainMenuScreen;
import com.maurice.Screens.RestartScreen;

public class GyroGame extends Game {
	public MainMenuScreen mainMenuScreen;
    public GameScreen gameScreen;
    public RestartScreen restartScreen;
	@Override
	public void create() {
		System.out.println("Game Created!");
		mainMenuScreen = new MainMenuScreen(this);
        gameScreen = new GameScreen(this);
        restartScreen = new RestartScreen(this);
        setScreen(mainMenuScreen);
	}
	
}
