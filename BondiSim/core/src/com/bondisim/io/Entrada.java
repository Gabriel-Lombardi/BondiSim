package com.bondisim.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.bondisim.utiles.Config;

public class Entrada implements InputProcessor {

	private boolean enter = false, escape = false, click = false, ctrl_l = false, ctrl_r = false,
					abajo = false, arriba = false, izquierda = false, derecha = false,
					plus = false, minus = false;
	private int mouseX = 0, mouseY = 0;
	Screen app;
	
	public boolean isEnter() {
		return enter;
	}
	
	public boolean isEscape() {
		return escape;
	}
	
	public boolean isClick() {
		return click;
	}
	
	public Entrada(Screen app) {
		this.app = app;
	}
	
	public boolean isAbajo() {
		return abajo;
	}

	public boolean isArriba() {
		return arriba;
	}
	
	public boolean isIzquierda() { 
		return izquierda;
	}
	
	public boolean isDerecha() { 
		return derecha;
	}
	
	public boolean isCtrlL() {
		return ctrl_l;
	}

	public boolean isCtrlR() {
		return ctrl_r;
	}
	
	public boolean isPlus() {
		return plus;
	}
	
	public boolean isMinus() {
		return minus;
	}
	
	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.ENTER) {
			enter = true;
			System.out.println("Enter");
		}
		
		if (keycode == Keys.ESCAPE) {
			escape = true;
			System.out.println("Escape");
		}
		
		if (keycode == Keys.CONTROL_LEFT) {
			ctrl_l = true;
		}

		if (keycode == Keys.CONTROL_RIGHT) {
			ctrl_r = true;
		}
		
		if (keycode == Keys.DOWN) {
			abajo = true;
			System.out.println("Abajo");
		}
		
		if (keycode == Keys.UP) {
			arriba = true;
			System.out.println("Arriba");
		}
		
		if (keycode == Keys.LEFT) {
			izquierda = true;
			System.out.println("Izquierda");
		}
		
		if (keycode == Keys.RIGHT) {
			derecha = true;
			System.out.println("Derecha");
		}
		
		if (keycode == Keys.RIGHT_BRACKET) {
			plus = true;
			System.out.println("+");
		}
		
		if (keycode == Keys.SLASH) {
			minus = true;
			System.out.println("-");
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.ENTER) {
			enter = false;
		}
		
		if (keycode == Keys.ESCAPE) {
			escape = false;
		}
		
		if (keycode == Keys.CONTROL_LEFT) {
			ctrl_l = false;
		}

		if (keycode == Keys.CONTROL_RIGHT) {
			ctrl_r = false;
		}
		
		if (keycode == Keys.DOWN) {
			abajo = false;
		}
		
		if (keycode == Keys.UP) {
			arriba = false;
		}
		
		if (keycode == Keys.LEFT) {
			izquierda = false;
		}
		
		if (keycode == Keys.RIGHT) {
			derecha = false;
		}
		
		if (keycode == Keys.PLUS) {
			plus = false;
		}
		
		if (keycode == Keys.MINUS) {
			minus = false;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		click = true;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		click = false;
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseX = screenX;
		mouseY = Config.ALTO - screenY;
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
}
