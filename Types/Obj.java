package Types;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import Util.*;

public class Obj {
	Vector[] vec = null;
	Normal[] nor = null;
	TexCoor[] tex = null;
	Face[] fac = null;
	GL gl;
	public Obj(String file, GL gl) {
		this.gl = gl;
		ObjParse parse = new ObjParse(file);
		this.vec = parse.getVec();
		this.nor = parse.getNor();
		this.tex = parse.getTex();
		this.fac = parse.getFace();
	}

	public void render() {
		gl.glColor3f(0.65f, 0.32f, 0.89f);
		for(int i = 0; i < fac.length; i++) {
			gl.glBegin(GL.GL_TRIANGLES);
			gl.glVertex3f(vec[fac[i].v1].x, vec[fac[i].v1].y, vec[fac[i].v1].z);
			gl.glVertex3f(vec[fac[i].v2].x, vec[fac[i].v2].y, vec[fac[i].v2].z);
			gl.glVertex3f(vec[fac[i].v3].x, vec[fac[i].v3].y, vec[fac[i].v3].z);
			gl.glEnd();
		}
	}
}
		
