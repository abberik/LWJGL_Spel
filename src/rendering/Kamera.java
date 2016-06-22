package rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Kamera {

	private Vector3f position;
	private Matrix4f projektion;
	
	public Kamera(int width, int height) {
		
		position = new Vector3f(0,0,0);
		projektion = new Matrix4f().setOrtho2D(-width / 2, width/2, -height/2, height/2);
		
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void addPosition(Vector3f position) {
		this.position.add(position);
	}
	
	public void setProjektion(Matrix4f projektion) {
		this.projektion = projektion;
	}
	
	public Matrix4f getProjektion() { 
		return projektion.translate(position,new Matrix4f());		
	}

	
	
}
