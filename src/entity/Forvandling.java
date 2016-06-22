package entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Forvandling {

	public Vector3f position;
	public Vector3f skala;
	
	public Forvandling() {
		position = new Vector3f();
		skala = new Vector3f(1,1,1);
	}
	
	public Matrix4f getProjektion(Matrix4f mal){
		mal.scale(skala);
		mal.translate(position);
		return mal;
	}
	
}
