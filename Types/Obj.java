package Types;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import Util.*;
import java.util.Random;

public class Obj {

    public Vector[] vec = null;
    public Normal[] nor = null;
    public TexCoor[] tex = null;
    public Face[] fac = null;
    public String name;
    private float x = 0;
    private float y = 0;
    private float z = -10.0f;
    private float xR = 0;
    private float yR = 0;
    private float zR = 0;
    private long time = System.nanoTime();

    public Obj(String file) {
        ObjParse parse = new ObjParse(file);
        this.vec = parse.getVec();
        this.nor = parse.getNor();
        this.tex = parse.getTex();
        this.fac = parse.getFace();
    }

    public void move(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void rotate(float rX, float rY, float rZ) {
        this.xR += rX;
        this.yR += rY;
        this.zR += rZ;
    }

    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRot(float xR, float yR, float zR) {
        this.xR = xR;
        this.yR = yR;
        this.zR = zR;
    }

    public void render(GL gl) {
        Random r = new Random();
        gl.glColor3f(0.65f, 0.32f, 0.89f);
        if (time + 100000000 < System.nanoTime()) {
            gl.glTranslatef(x, y, z);
            gl.glRotatef(xR, yR, zR, 1);
            time = System.nanoTime();
        } else {
            gl.glTranslatef(x, y, z);
            gl.glRotatef(xR, yR, zR, 1);
        }
        time = System.nanoTime();

        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0; i < fac.length; i++) {
            gl.glColor3f(r.nextFloat(), r.nextFloat(), r.nextFloat());
            gl.glVertex3f(vec[fac[i].v1].x, vec[fac[i].v1].y, vec[fac[i].v1].z);
            gl.glVertex3f(vec[fac[i].v2].x, vec[fac[i].v2].y, vec[fac[i].v2].z);
            gl.glVertex3f(vec[fac[i].v3].x, vec[fac[i].v3].y, vec[fac[i].v3].z);
        }
        gl.glEnd();

    }
}
		
