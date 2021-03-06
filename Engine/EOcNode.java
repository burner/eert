 /*
 *EERT = EERT enhanced rendering technology
 *
 *Copyright (C) [2008] [Robert "BuRnEr" Schadek]

 *This program is free software; you can redistribute it and/or modify it under 
 *the terms of the GNU General Public License as published by the Free Software
 *Foundation; either version 3 of the License, 
 *or (at your option) any later version.

 *This program is distributed in the hope that it will be useful, but WITHOUT 
 *ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 *FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 *You should have received a copy of the GNU General Public License along with 
 *this program; if not, see <http://www.gnu.org/licenses/>.
 */
package Engine;

import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Types.Geometrie.Vector;
import Util.Geometrie.VectorUtil;
import java.util.LinkedList;
import javax.media.opengl.GL;

public class EOcNode {

    private EOcMaster root;
    public EOcNode[] childs;
    public ObjIns[] objs;
    private boolean[] drawn;
    private Vector middle;
    private float radius;
    private int depth;
    private float bSize;
    private float bHalf;

    public EOcNode(EOcMaster root, ObjIns[] objs, Vector middle, float radius, float bSize, boolean[] drawn, int depth) {
        this.root = root;
        this.middle = middle;
        this.depth = depth;
        this.radius = radius;
        this.drawn = drawn;
        this.bSize = bSize;
        this.bHalf = this.bSize / 2;

        this.objs = checkAllObjects(objs);

        //Octree Depth
        if (this.depth < this.root.treeDepth) {
            this.childs = makeChilds();
        }

    }

    public void draw(GL gl) {
        //If this node has no more child
        //draw all objIns not yet drawn

        if (this.childs == null) {
            float dis;
            //If the distance is 0.0
            //the node is not within
            //the frustun
            if(this.root.engine.frame.octree)drawBox(gl);
            if (0.0f == (dis = SphereInFrustum(this.middle.x, this.middle.y, this.middle.z, this.radius))) {
                return;
            } else {
                this.root.engine.eInfo.drawnNodes++;
                for (ObjIns obIns : this.objs) {
                    if (!this.root.drawn[obIns.objInsNumber]) {
                        dis = (float) Math.sqrt(Math.pow(this.root.cam.loc.x - obIns.origin.x, 2) +
                                Math.pow(this.root.cam.loc.y - obIns.origin.y, 2) +
                                Math.pow(this.root.cam.loc.z - obIns.origin.z, 2));

                        //below this the actually rendering take place
                        obIns.parent.render(obIns.objInsNumber, dis);
                        this.root.drawn[obIns.objInsNumber] = true;
                    } else {
                        continue;
                    }
                }
            }
        } else {
            //drawBox(gl);
            for (EOcNode child : this.childs) {
                child.draw(gl);
            }
            return;
        }
    }

 /*   public void drawLight(GL gl) {
        //If this node has no more child
        //draw all objIns not yet drawn
        if (this.childs == null) {
            if(sphereInSphere()) {
                for (ObjIns obIns : this.objs) {
                    if (!this.root.drawn[obIns.objInsNumber]) {
                        float dis = VectorUtil.distance(obIns.origin, this.root.cam.loc);
                        obIns.parent.findFacesFacingLight(dis, obIns.objInsNumber);
                        obIns.parent.renderShadow(obIns.objInsNumber);
                        this.root.drawn[obIns.objInsNumber] = true;
                    } else {
                        continue;
                    }
                }
            } else {
                return;
            }
        } else {
            for (EOcNode child : this.childs) {
                child.drawLight(gl);
            }
            return;
        }
    }

    //this one tests if a sphere namely
    //a objIns is in the viewSphere
    private boolean sphereInSphere() {
        float dis = Math.abs(VectorUtil.distance(this.root.cam.frustMiddle, this.middle));
        if(dis - this.radius > this.root.cam.frustRadius) {
            return false;
        }
        return true;
    }*/

    private float SphereInFrustum(float x, float y, float z, float radius) {
        int p;
        float d = 0.0f;

        for (p = 0; p < 6; p++) {
            d = this.root.frustum[p][0] * x + this.root.frustum[p][1] * y + this.root.frustum[p][2] * z + this.root.frustum[p][3];
            if (d <= -radius) {
                return 0;
            }
        }
        return d + radius;
    }

    private ObjIns[] checkAllObjects(ObjIns[] objs) {
        LinkedList<ObjIns> objects = new LinkedList<ObjIns>();
        for (ObjIns obj : objs) {
            if (checkForObj(obj)) {
                objects.add(obj);
            }
        }
        ObjIns[] ret = new ObjIns[objects.size()];
        return ret = objects.toArray(ret);
    }

    private boolean checkForObj(ObjIns obj) {
        //Cheated a bit 
        //Just check a sphear around the box against the sphear around the Obj
        //the error-margin shouldn't be to big, but the check is much cheaper
        //and easier to understand

        float dis = (float) Math.sqrt((obj.origin.x - this.middle.x) * (obj.origin.x - this.middle.x) +
                (obj.origin.y - this.middle.y) * (obj.origin.y - this.middle.y) +
                (obj.origin.z - this.middle.z) * (obj.origin.z - this.middle.z));

        if (dis - obj.boundSph < this.radius) {
            return true;
        } else {
            return false;
        }
    }

    private void drawBox(GL gl) {
        gl.glPushMatrix();
        gl.glTranslatef(this.middle.x, this.middle.y, this.middle.z);
        gl.glColor3f(0.4f, 0.2f, 0.7f);

        //Back
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(-this.radius, -this.radius, -this.radius);
        gl.glVertex3f(this.radius, -this.radius, -this.radius);
        gl.glVertex3f(this.radius, this.radius, -this.radius);
        gl.glVertex3f(-this.radius, this.radius, -this.radius);
        gl.glEnd();

        //Front
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(-this.radius, -this.radius, this.radius);
        gl.glVertex3f(this.radius, -this.radius, this.radius);
        gl.glVertex3f(this.radius, this.radius, this.radius);
        gl.glVertex3f(-this.radius, this.radius, this.radius);
        gl.glEnd();

        //Left
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(-this.radius, -this.radius, -this.radius);
        gl.glVertex3f(-this.radius, -this.radius, this.radius);
        gl.glVertex3f(-this.radius, this.radius, this.radius);
        gl.glVertex3f(-this.radius, this.radius, -this.radius);
        gl.glEnd();

        //Right
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(this.radius, -this.radius, -this.radius);
        gl.glVertex3f(this.radius, -this.radius, this.radius);
        gl.glVertex3f(this.radius, this.radius, this.radius);
        gl.glVertex3f(this.radius, this.radius, -this.radius);
        gl.glEnd();
        gl.glColor3f(1f,1f,1f);

        gl.glPopMatrix();
    }

    private EOcNode[] makeChilds() {
        LinkedList<EOcNode> tmpChilds = new LinkedList<EOcNode>();
        //important needs to be created within this contidional execution
        //otherwise the child test in draw does not work
        EOcNode ch1 = new EOcNode(this.root, this.objs, new Vector(middle.x - this.bHalf,
                middle.y - this.bHalf,
                middle.z - this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch1.objs.length > 0) {
            tmpChilds.add(ch1);
        }

        EOcNode ch2 = new EOcNode(this.root, this.objs, new Vector(middle.x - this.bHalf,
                middle.y - this.bHalf,
                middle.z + this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch2.objs.length > 0) {
            tmpChilds.add(ch2);
        }

        EOcNode ch3 = new EOcNode(this.root, this.objs, new Vector(middle.x - this.bHalf,
                middle.y + this.bHalf,
                middle.z - this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch3.objs.length > 0) {
            tmpChilds.add(ch3);
        }

        EOcNode ch4 = new EOcNode(this.root, this.objs, new Vector(middle.x - this.bHalf,
                middle.y + this.bHalf,
                middle.z + this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch4.objs.length > 0) {
            tmpChilds.add(ch4);
        }
        EOcNode ch5 = new EOcNode(this.root, this.objs, new Vector(middle.x + this.bHalf,
                middle.y - this.bHalf,
                middle.z - this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch5.objs.length > 0) {
            tmpChilds.add(ch5);
        }

        EOcNode ch6 = new EOcNode(this.root, this.objs, new Vector(middle.x + this.bHalf,
                middle.y - this.bHalf,
                middle.z + this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch6.objs.length > 0) {
            tmpChilds.add(ch6);
        }

        EOcNode ch7 = new EOcNode(this.root, this.objs, new Vector(middle.x + this.bHalf,
                middle.y + this.bHalf,
                middle.z - this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch7.objs.length > 0) {
            tmpChilds.add(ch7);
        }

        EOcNode ch8 = new EOcNode(this.root, this.objs, new Vector(middle.x + this.bHalf,
                middle.y + this.bHalf,
                middle.z + this.bHalf), this.radius / 2, this.bHalf, this.drawn, depth + 1);
        if (ch8.objs.length > 0) {
            tmpChilds.add(ch8);
        }

        if (tmpChilds.size() == 0) {
            return null;
        } else {
            EOcNode[] retChilds = new EOcNode[tmpChilds.size()];
            this.root.numNodes += retChilds.length;
            return retChilds = tmpChilds.toArray(retChilds);
        }
    }
}
