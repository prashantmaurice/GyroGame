package com.maurice.GameWorld;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.maurice.GameObjects.Ball;
import com.maurice.GameObjects.Cluster;

public class GameRenderer {
    
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    
    private int midPointX;
    private int midPointY;
    private int gameHeight;
    private int gameWidth;
    //colors-light
    private Color color1=colorFromHex(0xE60000L);//0xaarrggbb
	private Color color2=colorFromHex(0xFF9900L);//orange
	private Color color3=colorFromHex(0x0099FFL);
	private Color color4=Color.WHITE;//health
	private Color[] colors = new Color[]{color1,color2,color3,color4};
	//colors-faded
	private Color color1d=colorFromHex(0xA10000L);//0xaarrggbb
	private Color color2d=colorFromHex(0xB26B00L);
	private Color color3d=colorFromHex(0x005C99L);
	private Color[] colorsfaded = new Color[]{color1d,color2d,color3d};
	
	private SpriteBatch batch;
    private BitmapFont font;
    private int centerRadius ;
    private boolean isCritical=false;
    
    public GameRenderer(GameWorld world, int midPointX,int midPointY,int gameHeight,int gameWidth) {
        myWorld = world;
        this.midPointX=midPointX;
        this.midPointY=midPointY;
        this.gameHeight=gameHeight;
        this.gameWidth=gameWidth;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWidth, gameHeight);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        batch = new SpriteBatch();  //text display
        font = new BitmapFont(Gdx.files.internal("data/devgothic.fnt"));
        font.setColor(Color.WHITE);
        //font.setScale(2);
        centerRadius=myWorld.getCenterRadius();
    }

    public void render() {
    	//System.out.println("GameRenderer - render");

        // 1. We draw a black background. This prevents flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL10.GL_BLEND);//enable alpha
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
        //add score
        batch.begin();
        font.draw(batch, Integer.toString(myWorld.getScore()), 10, gameHeight-10);//text display
    	//font.draw(batch, "Health Critical...!", 10, gameHeight-40);//text display
        if(isCritical) {
        	font.setColor(Color.RED);
        	font.draw(batch, "Health Critical...!", 10, gameHeight-40);//text display
        	font.setColor(Color.WHITE);
        }
        batch.end();
        
        // Tells shapeRenderer to begin drawing filled shapes
        shapeRenderer.begin(ShapeType.Filled);

        // Chooses RGB Color of 87, 109, 120 at full opacity
        shapeRenderer.setColor(87 / 255.0f, 109 / 255.0f, 120 / 255.0f, (float) 0.5);
        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y,
                myWorld.getRect().width, myWorld.getRect().height);
        
        
        //draw wheel---------------
        /*Color color1=myWorld.getWheel().get
        Color color2=myWorld.getWheel().getColor2();
        Color color3=myWorld.getWheel().getColor3();*/
        int radius=myWorld.getWheel().getRadius();
        float direction = myWorld.getWheel().getDirection();
        int[] health=myWorld.getWheel().getHealth();
        if((health[0]<20)|(health[1]<20)|(health[2]<20)) isCritical=true;
        else isCritical=false;
      //draw clusters-------------------
        ArrayList clusters = myWorld.getClusters();
		for (int i = 0; i < clusters.size(); i++) {
			Cluster c = (Cluster) clusters.get(i);
			shapeRenderer.setColor(colorsfaded[c.getType()]);
	        shapeRenderer.circle(c.getPosition().x, c.getPosition().y , c.getOutRadius());
	        shapeRenderer.setColor(Color.BLACK);
	        shapeRenderer.circle(c.getPosition().x, c.getPosition().y , c.getInRadius());
		}
        
        
        
        //faded part-------
        shapeRenderer.setColor(color1d);
        shapeRenderer.arc(midPointX, midPointY , radius, direction+0, 120, 60);
        shapeRenderer.setColor(color2d);
        shapeRenderer.arc(midPointX, midPointY , radius, direction+120, 120, 60);
        shapeRenderer.setColor(color3d);
        shapeRenderer.arc(midPointX, midPointY , radius, direction+240, 120, 60);
        //healthy part---
        shapeRenderer.setColor(color1);
        shapeRenderer.arc(midPointX, midPointY , health[0]+centerRadius, direction+0, 120, 60);
        shapeRenderer.setColor(color2);
        shapeRenderer.arc(midPointX, midPointY , health[1]+centerRadius, direction+120, 120, 60);
        shapeRenderer.setColor(color3);
        shapeRenderer.arc(midPointX, midPointY , health[2]+centerRadius, direction+240, 120, 60);
        //center circle---------------
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(midPointX, midPointY , myWorld.getWheel().getCenterRadius());
        //draw balls-------------------
        ArrayList balls = myWorld.getBalls();
		for (int i = 0; i < balls.size(); i++) {
			Ball p = (Ball) balls.get(i);
			shapeRenderer.setColor(colors[p.getType()]);
	        shapeRenderer.circle(p.getPosition().x, p.getPosition().y , p.getRadius());
		}
		//check health critical
		
        
        
        shapeRenderer.end();


    }
    private Color colorFromHex(long hex){
            //float a = (hex & 0xFF000000L) >> 24;
            float r = (hex & 0xFF0000L) >> 16;
            float g = (hex & 0xFF00L) >> 8;
            float b = (hex & 0xFFL);
                            
            return new Color(r/255f, g/255f, b/255f, 255f/255f);
    }
}