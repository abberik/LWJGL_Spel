package io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;


public class Fonster {

	private long fonster;			//GLFWs referens till fönstret
	private int width,height;
	private boolean helskarm;
	private Input input;
	
	public Fonster() {
		
		width = 640; height = 480; //skapa ursprungliga värden
		setHelskarm(false);	//slå av helskärm
	}

	public long getFonster() {
		return fonster;
	}

	public void setFonster(long fonster) {
		this.fonster = fonster;
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
	
	public void setSize(int width, int height){
		
		setWidth(width); setHeight(height);
		
	}
	
	public void createWindow(String titel){
		
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		setWidth(width); setHeight(height);
		
		
		fonster = glfwCreateWindow(width,height,titel,helskarm ? glfwGetPrimaryMonitor() : 0,0);
		
		if(fonster == 0){
			//the creation of the window failed
			throw new IllegalStateException("Misslyckades med att skapa fönstret.");
		}
		
		
		
		glfwSetWindowPos(fonster, ((vid.width() - width) / 2), ((vid.height() - height) / 2));
		
		glfwShowWindow(fonster);
		
		glfwMakeContextCurrent(fonster);
		
		input = new Input(fonster);
		
	}
	
	public boolean skallStangas(){
		return glfwWindowShouldClose(getFonster());
	}
	
	public void swapBuffers(){
		glfwSwapBuffers(getFonster());
	}

	public boolean isHelskarm() {
		return helskarm;
	}

	public void setHelskarm(boolean helskarm) {
		this.helskarm = helskarm;

	}
	
	public static void setCallbacks(){
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}
	
	public void stang(){
		glfwSetWindowShouldClose(fonster, true);
	}
	
	public void updatera(){
		input.updatera();
		glfwPollEvents();
	}

	
}
