package com.bondisim.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.bondisim.elementos.Texto;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;
import com.bondisim.vehiculos.ColectivoJugador;

public class PantallaJuego implements Screen {
	
    private SpriteBatch b;
    private ColectivoJugador colectivoJugador;
    private int anchoPantalla, altoPantalla;
    
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderizador;

    private Sprite velocimetro_s;
    private Texto velocidad, ayuda;
    private Texto[] transmisionT = new Texto[3];
    private float posYVelocidad = 0;
    private float posXVelocidad;
    private float textoVelocidadX, textoVelocidadY;
    
    public float tiempo = 0;
    
    private String[] transmision = {"D", "N", "R"};
    
    private OrthographicCamera camJugador, camHUD;
    
    Entrada entrada = new Entrada(this);
    
    @Override
    public void show() {
        b = Render.batch;
        anchoPantalla = Gdx.graphics.getWidth();
        altoPantalla = Gdx.graphics.getHeight();
        
        // cargar mapa
        TmxMapLoader cargadorMapa = new TmxMapLoader();
        mapa = cargadorMapa.load(Recursos.MAPA1);
        renderizador = new OrthogonalTiledMapRenderer(mapa);

        // configurar colectivo
        Texture bondi = new Texture(Recursos.SP_34_BIMET);
        TextureRegion bondiRegion = new TextureRegion(bondi, 235, 192);
        TextureRegion[][] temp = bondiRegion.split(235, 64);
        TextureRegion[] bondiFrames = new TextureRegion[temp.length * temp[0].length];
        
        // configurar velocimetro
        Texture velocimetro = new Texture(Recursos.VELOCIMETRO);
        TextureRegion velocimetroRegion = new TextureRegion(velocimetro, 512, 256);
        velocimetro_s = new Sprite(velocimetroRegion);
        float altoVelocimetro = 128, anchoVelocimetro = 256;
        velocimetro_s.setSize(anchoVelocimetro, altoVelocimetro);
        posXVelocidad = anchoPantalla / 2 - velocimetro_s.getWidth() / 2;
        
        for (int frame = 0; frame < temp.length; frame++) {
            bondiFrames[frame] = temp[frame][0];
        }

        // cargar colectivo y velocimetro
        colectivoJugador = new ColectivoJugador(bondi, bondiFrames);

        velocidad = new Texto(Recursos.FUENTE_DIGITAL, 120, Recursos.CELESTE, false);
        
        ayuda = new Texto(Recursos.FUENTE_RETRO_GAMING, 20, Color.WHITE, false);
        
        for (int i = 0; i < transmision.length; i++) {
        	transmisionT[i] = new Texto(Recursos.FUENTE_DIGITAL, 40, Recursos.CELESTE, false);
        	transmisionT[i].setTexto(transmision[i]);
        }

		camJugador = new OrthographicCamera(anchoPantalla, altoPantalla);
        camHUD 	   = new OrthographicCamera(anchoPantalla, altoPantalla);
        
        Gdx.input.setInputProcessor(entrada);
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0.3f, 0.3f, 0.3f);
        
        camJugador.position.set(colectivoJugador.getX() + colectivoJugador.getWidth() / 2,
                colectivoJugador.getY() + colectivoJugador.getHeight() / 2, 0);
        camJugador.update();
        
        renderizador.setView(camJugador);
        renderizador.render();
        
        b.setProjectionMatrix(camJugador.combined);

        b.begin();
            colectivoJugador.procesarEntrada();
            colectivoJugador.draw(b);
            if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            	System.out.println("Pos X: " + colectivoJugador.getX() + "\nPos Y: " + colectivoJugador.getY() + "\nRotacion: " + colectivoJugador.getRotation());
            }
        b.end();

        camHUD.update();
        b.setProjectionMatrix(camHUD.combined);

        b.begin();        
            velocimetro_s.setPosition(posXVelocidad, posYVelocidad);
            velocimetro_s.draw(b);

            textoVelocidadX = posXVelocidad + velocimetro_s.getHeight() + 50;
            textoVelocidadY = posYVelocidad + 100;
            int velocidadBondi = (int) colectivoJugador.getVelocidad() / 5;
            int velocidadAbs = Math.abs(velocidadBondi);
            String velocidadTexto = String.format("%02d", velocidadAbs);
            velocidad.setTexto(velocidadTexto);
            velocidad.setX((int) (textoVelocidadX - velocidad.getAncho()));
            velocidad.setY((int) textoVelocidadY);

            float textoTransmisionX = anchoPantalla / 2 - 75;
            float textoTransmisionY = posYVelocidad + 60;
            
            for (int i = 0; i < transmisionT.length; i++) {
            	transmisionT[i].setX((int) (textoTransmisionX - transmisionT[i].getAncho()));
            	transmisionT[i].setY((int) textoTransmisionY);
            }
            
            if (velocidadBondi == 0) {
            	transmisionT[1].dibujar();
            } else if (velocidadBondi > 0) {
            	transmisionT[0].dibujar();
            } else {
            	transmisionT[2].dibujar();
            }
            
            velocidad.dibujar();
            
            tiempo += delta;
            
            if (!colectivoJugador.isMotorEncendido()) {
            	ayuda.setTexto("Presiona la G para encender el motor.");
            	ayuda.setPosicion(textoTransmisionX - 150, textoTransmisionY + 100);
            	if(!(Math.round(tiempo) % 2 == 0)) {
            		ayuda.dibujar();
            	}
            }            
        b.end();
        
        if (entrada.isEscape()) {
			Render.app.setScreen(new PantallaOpcionesDesdeJuego());
			Gdx.app.log("Pantalla", "Cambio a PantallaOpcionesDesdeJuego");
		}
        
=======
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.bondisim.elementos.Texto;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;
import com.bondisim.vehiculos.ColectivoJugador;

public class PantallaJuego implements Screen {
    private SpriteBatch b;
    private ColectivoJugador colectivoJugador;
    private int anchoPantalla, altoPantalla;
    
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderizador;

    private Sprite velocimetro_s;
    private Texto velocidad;
    private Texto[] transmisionT = new Texto[3];
    private float posYVelocidad = 0;
    private float posXVelocidad;
    private float textoVelocidadX, textoVelocidadY;
    
    private String[] transmision = {"D", "N", "R"};
    
    private OrthographicCamera camJugador, camHUD;
    
    Entrada entrada = new Entrada(this);
    
    @Override
    public void show() {
        b = Render.batch;
        anchoPantalla = Gdx.graphics.getWidth();
        altoPantalla = Gdx.graphics.getHeight();
        
        // cargar mapa
        TmxMapLoader cargadorMapa = new TmxMapLoader();
        mapa = cargadorMapa.load(Recursos.MAPA1);
        renderizador = new OrthogonalTiledMapRenderer(mapa);

        // configurar colectivo
        Texture bondi = new Texture(Recursos.SP_34_BIMET);
        TextureRegion bondiRegion = new TextureRegion(bondi, 235, 192);
        TextureRegion[][] temp = bondiRegion.split(235, 64);
        TextureRegion[] bondiFrames = new TextureRegion[temp.length * temp[0].length];
        
        // configurar velocimetro
        Texture velocimetro = new Texture(Recursos.VELOCIMETRO);
        TextureRegion velocimetroRegion = new TextureRegion(velocimetro, 512, 256);
        velocimetro_s = new Sprite(velocimetroRegion);
        float altoVelocimetro = 128, anchoVelocimetro = 256;
        velocimetro_s.setSize(anchoVelocimetro, altoVelocimetro);
        posXVelocidad = anchoPantalla / 2 - velocimetro_s.getWidth() / 2;
        
        for (int frame = 0; frame < temp.length; frame++) {
            bondiFrames[frame] = temp[frame][0];
        }

        // cargar colectivo y velocimetro
        colectivoJugador = new ColectivoJugador(bondi, bondiFrames);

        velocidad = new Texto(Recursos.FUENTE_DIGITAL, 120, Recursos.CELESTE, false);
        
        for (int i = 0; i < transmision.length; i++) {
        	transmisionT[i] = new Texto(Recursos.FUENTE_DIGITAL, 40, Recursos.CELESTE, false);
        	transmisionT[i].setTexto(transmision[i]);
        }

		camJugador = new OrthographicCamera(anchoPantalla, altoPantalla);
        camHUD 	   = new OrthographicCamera(anchoPantalla, altoPantalla);
        
        Gdx.input.setInputProcessor(entrada);
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0.3f, 0.3f, 0.3f);
        
        camJugador.position.set(colectivoJugador.getX() + colectivoJugador.getWidth() / 2,
                colectivoJugador.getY() + colectivoJugador.getHeight() / 2, 0);
        camJugador.update();
        
        renderizador.setView(camJugador);
        renderizador.render();
        
        b.setProjectionMatrix(camJugador.combined);

        b.begin();
            colectivoJugador.procesarEntrada();
            colectivoJugador.draw(b);
            if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            	System.out.println("Pos X: " + colectivoJugador.getX() + "\nPos Y: " + colectivoJugador.getY() + "\nRotacion: " + colectivoJugador.getRotation());
            }
        b.end();

        camHUD.update();
        b.setProjectionMatrix(camHUD.combined);

        b.begin();        
            velocimetro_s.setPosition(posXVelocidad, posYVelocidad);
            velocimetro_s.draw(b);

            textoVelocidadX = posXVelocidad + velocimetro_s.getHeight() + 50;
            textoVelocidadY = posYVelocidad + 100;
            int velocidadBondi = (int) colectivoJugador.getVelocidad() / 5;
            int velocidadAbs = Math.abs(velocidadBondi);
            String velocidadTexto = String.format("%02d", velocidadAbs);
            velocidad.setTexto(velocidadTexto);
            velocidad.setX((int) (textoVelocidadX - velocidad.getAncho()));
            velocidad.setY((int) textoVelocidadY);

            float textoTransmisionX = anchoPantalla / 2 - 75;
            float textoTransmisionY = posYVelocidad + 60;
            
            for (int i = 0; i < transmisionT.length; i++) {
            	transmisionT[i].setX((int) (textoTransmisionX - transmisionT[i].getAncho()));
            	transmisionT[i].setY((int) textoTransmisionY);
            }
            
            if (velocidadBondi == 0) {
            	transmisionT[1].dibujar();
            } else if (velocidadBondi > 0) {
            	transmisionT[0].dibujar();
            } else {
            	transmisionT[2].dibujar();
            }
            
            velocidad.dibujar();
        b.end();
>>>>>>> branch 'main' of file:///C:\Users\gabi\git\BondiSim
    }

    @Override
    public void resize(int width, int height) {
        camJugador.setToOrtho(false, width, height);
        camHUD.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        b.dispose();
        colectivoJugador.getTexture().dispose();
        mapa.dispose();
        renderizador.dispose();
    }
}
