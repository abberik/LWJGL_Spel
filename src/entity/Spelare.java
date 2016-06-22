package entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector3f;

import io.Fonster;
import karta.Varld;
import rendering.Kamera;
import rendering.Model;
import rendering.Shader;
import rendering.Textur;

public class Spelare {

	private Model model;
	private Textur textur;
	private Forvandling forvandling;
	
	public Spelare() {
		
		float[] vertices = new float[] {
				-1f, 1f , 0, //övre vänstra	 0
				1f, 1f, 0, //övre högra      1
				1f,-1f,0, //undre högra      2
				-1f, -1f, 0 //undre vänstra 3 
		};
		
		float[] texturen = new float[] {
				0,0,
				1,0,
				1,1,
				0,1
				
		};
		
		int[] indices = new int[] {
				0,1,2,
				2,3,0
		};
		
		model = new Model(vertices, texturen, indices);
		textur = new Textur("spelare.png");
		forvandling = new Forvandling();
		forvandling.skala = new Vector3f(16,16,1);
		
	}
	
	public void update(float delta, Fonster fonster, Kamera kamera, Varld varld){
		
		if(fonster.getInput().arTangentNere(GLFW_KEY_ESCAPE)){
			fonster.stang();
		}
		
		if(fonster.getInput().arTangentNere(GLFW_KEY_LEFT)){
			forvandling.position.add(new Vector3f(-7.5f * delta,0,0));
		}
		
		if(fonster.getInput().arTangentNere(GLFW_KEY_RIGHT)){
			forvandling.position.add(new Vector3f(7.5f * delta,0,0));
		}
		
		if(fonster.getInput().arTangentNere(GLFW_KEY_UP)){
			forvandling.position.add(new Vector3f(0,7.5f * delta,0));
		}
		
		if(fonster.getInput().arTangentNere(GLFW_KEY_DOWN)){
			forvandling.position.add(new Vector3f(0,-7.5f * delta,0));
		}
		
		kamera.setPosition(forvandling.position.mul(-varld.getSkala(),new Vector3f()));
		
	}
	
	public void render(Shader shader, Kamera kamera){
		
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projektion", forvandling.getProjektion(kamera.getProjektion()));
		textur.bind(0);
		model.render();
		
		
		
	}
	
}
