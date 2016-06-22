package io;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	private long fonster;
	
	private boolean[] tangenter;
	
	
	public Input(long fonster ) {
		this.fonster = fonster;
		this.tangenter = new boolean[GLFW_KEY_LAST]; //key last är antalet tangenter som glfw registrerar
		
	}
	
	public boolean arTangentNere(int tangent){
		
		return (glfwGetKey(fonster, tangent) == GLFW_TRUE);
			
	}
	
	public boolean arMusKnappNere(int knapp){
		
		return (glfwGetMouseButton(fonster, knapp) == GLFW_TRUE);
		
	}
	
	public boolean arTangentTryckt(int knapp){
		return (arTangentNere(knapp) && !tangenter[knapp]);
	}
	
	public boolean arTangentSlappt(int knapp){
		return (!arTangentNere(knapp) && tangenter[knapp]);
	}
	
	public void updatera(){
		for(int a = 0; a < GLFW_KEY_LAST; a++) tangenter[a] = arTangentNere(a);
	}
}
