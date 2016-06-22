package spel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import entity.Spelare;
import io.Fonster;
import io.Timer;
import karta.BrickRenderare;
import karta.Bricka;
import karta.Varld;
import rendering.Kamera;
import rendering.Shader;


public class Spel {

	public static void main(String[] args) {
		new Spel();
	}
	
	private final boolean printProgramInfo = true;
	private Fonster fonster; //fonstret spelet visas i
	private Kamera kamera;	//kameran som visar spelet
	private BrickRenderare brickRendererare;
	private Spelare spelare;
	
	public Spel() {
		
		if( glfwInit()/*Initiera biblioteket*/ ){
			//it loads fine
			
			fonster = new Fonster();
			
			fonster.createWindow("Abberixs lwjgl spel.");
			GL.createCapabilities();
			
			kamera = new Kamera(fonster.getWidth(),fonster.getHeight());
			
			glEnable(GL_TEXTURE_2D);
			
			brickRendererare = new BrickRenderare();
			

			Shader shader = new Shader("shader");
	

			Varld varld = new Varld();
			
			spelare = new Spelare();
			
			for(int a = 0; a < varld.getHeight(); a++){
				for(int b = 0; b < varld.getWidth(); b++){
					varld.setBricka(Bricka.gras, b, a);
				}
			}
			
			for(int a = 0; a < varld.getHeight(); a++){
				varld.setBricka(Bricka.stenblock, a, 1);
				varld.setBricka(Bricka.stenblock, a, 2);
			}
			

			
			double frame_cap = 1.0/60.0;
			double frame_time = 0; 
			double frames = 0;
				
			double time = Timer.getTime();
			double unprocessed = 0;
			
			while( !fonster.skallStangas()){
				
				boolean can_render = false;
				
				double time_2 = Timer.getTime();
				
				double passed = time_2 - time;
				unprocessed += passed;
				
				frame_time += passed;
				
				time = time_2;
								
				while(unprocessed >= frame_cap){
				
					unprocessed -= frame_cap;
					can_render = true;

					
					
					spelare.update((float)frame_cap, fonster, kamera, varld);
					
					varld.korrigeraKamera(kamera, fonster);
					
					fonster.updatera();

					if(frame_time >= 1.0){
						frame_time = 0;
						if(printProgramInfo){
							System.out.println("FPS = " + frames);
							System.out.println("MEM = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024) + "MB");
						}
						frames = 0;
					}
					
					
				}
				
				if(can_render){
				
					
					
					glClear(GL_COLOR_BUFFER_BIT);
					
					varld.render(brickRendererare, shader, kamera,fonster);
					
					spelare.render(shader, kamera);
					
					fonster.swapBuffers();
					
					frames++;
					
				}
				
				
			}
			
			glfwTerminate();
			
		}else{
			System.out.println("false");
		}
		
		
		
	}
	
}
