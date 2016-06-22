package rendering;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {

	private int program;
	private int vertex_shader;
	private int fragment_shader;
	
	public Shader(String filnamn) {
		
		program = glCreateProgram();
		
		vertex_shader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex_shader,readFile(filnamn + ".vs"));
		glCompileShader(vertex_shader);
		if(glGetShaderi(vertex_shader, GL_COMPILE_STATUS) != 1){
			System.err.println(glGetShaderInfoLog(vertex_shader));
			System.out.println(glGetShaderInfoLog(vertex_shader));
			System.exit(1);
		}
		
		fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragment_shader,readFile(filnamn + ".fs"));
		glCompileShader(fragment_shader);
		if(glGetShaderi(fragment_shader, GL_COMPILE_STATUS) != 1){
			System.err.println(glGetShaderInfoLog(fragment_shader));
			System.out.println(glGetShaderInfoLog(fragment_shader));
			System.exit(1);
		}
		
		
		glAttachShader(program, vertex_shader);
		glAttachShader(program, fragment_shader);
		
		glBindAttribLocation(program,0,"vertices");
		glBindAttribLocation(program,1,"textures");

		glLinkProgram(program);
		
		if(glGetProgrami(program, GL_VALIDATE_STATUS) != 1){
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		
	}
	
	
	private String readFile(String filnamn){
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader("./shaders/" + filnamn));
			String rad;
			while((rad = br.readLine()) != null){
				string.append(rad + "\n");
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		return string.toString();
	}
	
	public void setUniform(String name, int value){
		
		int location = glGetUniformLocation(program, name);

		if(location != -1 ){
		
			glUniform1i(location,value);
			
		}
		
	}
	
	public void setUniform(String name, Matrix4f value){
		
		int location = glGetUniformLocation(program, name);
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		
		value.get(buffer);
		
		
		if(location != -1 ){
		
			glUniformMatrix4fv(location,false,buffer);
			
		}
		
	}
	
	public void bind(){
		glUseProgram(program);
	}
	
	public void finalize(){
		glDetachShader(program, vertex_shader);
		glDetachShader(program, fragment_shader);
		glDeleteShader(vertex_shader);
		glDeleteShader(fragment_shader);
		glDeleteProgram(program);
	}
}
