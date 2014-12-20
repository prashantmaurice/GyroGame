package com.maurice.GyroHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.maurice.GameWorld.GameWorld;

public class MyInputGestureListener implements GestureListener  {
	GameWorld world;
    public MyInputGestureListener(GameWorld world ) {
    	this.world=world;
    }
    
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		System.out.println("Swiped=="+velocityX);
		world.setVelocity(velocityX*Gdx.graphics.getDeltaTime());
		return false;
	}
	
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}


	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		world.setVelocity(0);
		world.move(-deltaX);
		System.out.println("Dragged=="+deltaX);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
