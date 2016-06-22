package karta;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.Fonster;
import rendering.Kamera;
import rendering.Shader;

public class Varld {

	private final int vy = 32;
	private byte[] brickor;
	private int width;
	private int height;
	private int skala;
	
	private Matrix4f varld;
	
	public Varld() {
		
		width = 128;
		height = 128;
		skala = 16;
		
		brickor = new byte[width * height];
		
		varld = new Matrix4f().setTranslation(new Vector3f(0));
		
		varld.scale(skala);
	}
	
	public void render(BrickRenderare brickRenderare,Shader shader,Kamera kamera,Fonster fonster ){

		int posX = ((int)kamera.getPosition().x + (fonster.getWidth()/2)) / (skala * 2);
		int posY = ((int)kamera.getPosition().y - (fonster.getHeight()/2)) / (skala * 2);
		
		for(int a = 0; a < vy; a++){
			for(int b = 0; b < vy; b++){
				Bricka bricka = getBricka(a-posX,b+posY);
				if(bricka != null)	brickRenderare.rendereraBricka(bricka, a-posX, -b-posY, shader, varld, kamera);
			}
		}
		
	}
	
	public void setBricka(Bricka bricka, int x, int y){
		brickor[x + y * width] = bricka.getId(); 
	}

	public byte[] getBrickor() {
		return brickor;
	}

	public void setBrickor(byte[] brickor) {
		this.brickor = brickor;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Matrix4f getVarld() {
		return varld;
	}

	public void setVarld(Matrix4f varld) {
		this.varld = varld;
	}
	
	public void korrigeraKamera(Kamera kamera,Fonster fonster){
		
		Vector3f position = kamera.getPosition();
		
		int w = -width * skala * 2;
		int h = height * skala * 2;
		
		if(position.x > -((fonster.getWidth() /2)-skala)){
			position.x = -((fonster.getWidth() /2)-skala);
		}
		
		if(position.x < w +(fonster.getWidth() / 2) + skala){
			position.x = ( w+ (fonster.getWidth() / 2) + skala);
		}
		
		if(position.y < ((fonster.getHeight() /2)-skala)){
			position.y = ((fonster.getHeight() /2)-skala);
		}

		if(position.y > h - ((fonster.getHeight() /2)+skala)){
			position.y = h - ((fonster.getHeight() /2)+skala);
		}
	}
	
	public Bricka getBricka(int x, int y){
		
		try{
			return Bricka.brickor[brickor[x + y * width]];
		}catch(ArrayIndexOutOfBoundsException ex){
			return null;
		}
		
	}

	public int getSkala() {
		return skala;
	}

	public void setSkala(int skala) {
		this.skala = skala;
	}

	public int getVy() {
		return vy;
	}
	
	
}
