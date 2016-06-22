package karta;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import rendering.Kamera;
import rendering.Model;
import rendering.Shader;
import rendering.Textur;

public class BrickRenderare {

	private HashMap<String,Textur> brick_texturer;
	private Model model;
	
	public BrickRenderare() {
		
		brick_texturer = new HashMap<String,Textur>();
		 
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
		
		model = new Model(vertices,texturen,indices);
		
		for(int a = 0; a < Bricka.brickor.length; a++){
			if(Bricka.brickor[a] != null){
				if(!brick_texturer.containsKey(Bricka.brickor[a].getTexturen())){
					String t = Bricka.brickor[a].getTexturen();
					brick_texturer.put(t,new Textur(t + ".png"));
				}
			}
		}
		
	}
	
	public void rendereraBricka(Bricka bricka, int x, int y,Shader shader, Matrix4f varld, Kamera kamera){
	
		shader.bind();
		
		if(brick_texturer.containsKey(bricka.getTexturen())){
			brick_texturer.get( bricka.getTexturen()).bind(0);
		}
		
		Matrix4f brick_position = new Matrix4f().translate(new Vector3f(x*2,y*2,0));
		Matrix4f mal = new Matrix4f();
		kamera.getProjektion().mul(varld,mal);
		mal.mul(brick_position);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projektion", mal);
		model.render();
	}
	
}
