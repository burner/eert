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
    String name;

    public Obj(String file) {
        ObjParse parse = new ObjParse(file);
        this.vec = parse.getVec();
        this.nor = parse.getNor();
        this.tex = parse.getTex();
        this.fac = parse.getFace();
    }

    public void render(GL gl) {
        gl.glColor3f(0.65f, 0.32f, 0.89f);
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0; i < fac.length; i++) {
            gl.glVertex3f(vec[fac[i].v1].x, vec[fac[i].v1].y, vec[fac[i].v1].z);
            gl.glVertex3f(vec[fac[i].v2].x, vec[fac[i].v2].y, vec[fac[i].v2].z);
            gl.glVertex3f(vec[fac[i].v3].x, vec[fac[i].v3].y, vec[fac[i].v3].z);
        }
        gl.glEnd();
    }
}
		
