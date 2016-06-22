package rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Model {
	
	private int draw_count;
	private int vertex_id;
	private int textur_id;
	private int index_id;
	
	public Model(float[] vertices,float[] textur_koordinater, int[] indices) {
		
		draw_count = indices.length;
		
		
		
		vertex_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertex_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices) ,GL_STATIC_DRAW);
		
		
		textur_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textur_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(textur_koordinater) ,GL_STATIC_DRAW);
		
		index_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,index_id);
		IntBuffer intbuffer = BufferUtils.createIntBuffer(indices.length);
		intbuffer.put(indices);
		intbuffer.flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, intbuffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	
	public void render(){
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER,vertex_id);
		
		glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
		
		glBindBuffer(GL_ARRAY_BUFFER,textur_id);
		glVertexAttribPointer(1,2,GL_FLOAT,false,0,0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,index_id);
		
		glDrawElements(GL_TRIANGLES, draw_count,GL_UNSIGNED_INT,0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	private FloatBuffer createBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		
		buffer.put(data);
		
		buffer.flip();
		
		return buffer;
	}

	protected void finalize(){
		glDeleteBuffers(vertex_id);
		glDeleteBuffers(index_id);
		glDeleteBuffers(textur_id);
	}
	
}
