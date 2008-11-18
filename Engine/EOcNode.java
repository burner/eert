package Engine;

import Types.Obj;
import Types.ObjIns;
import Types.Vector;
import java.util.LinkedList;
import javax.media.opengl.GL;

public class EOcNode {

    public EOcNode[] childs;
    public ObjIns[] objs;
    private boolean[] drawn;
    private float xSize,  ySize,  zSize;
    private Vector middle;

    public EOcNode(ObjIns[] objs, Vector middle, float xSize, float ySize, float zSize, boolean[] drawn, int depth) {
        this.middle = middle;
        this.drawn = drawn;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;

        float xSizeH = xSize / 2;
        float ySizeH = ySize / 2;
        float zSizeH = zSize / 2;

        this.objs = checkAllObjects(objs);
        byte idx = 0;
        if (objs.length > 1 && depth < 5) {
            this.childs = new EOcNode[8];
            EOcNode ch1 = new EOcNode(objs, new Vector(middle.x - xSize / 2, middle.y + ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch1;
                ++idx;
            }

            EOcNode ch2 = new EOcNode(objs, new Vector(middle.x + xSize / 2, middle.y + ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch2;
                ++idx;
            }

            EOcNode ch3 = new EOcNode(objs, new Vector(middle.x - xSize / 2, middle.y - ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch3;
                ++idx;
            }

            EOcNode ch4 = new EOcNode(objs, new Vector(middle.x + xSize / 2, middle.y - ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch4;
                ++idx;
            }

            EOcNode ch5 = new EOcNode(objs, new Vector(middle.x - xSize / 2, middle.y + ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch5;
                ++idx;
            }

            EOcNode ch6 = new EOcNode(objs, new Vector(middle.x + xSize / 2, middle.y + ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch6;
                ++idx;
            }

            EOcNode ch7 = new EOcNode(objs, new Vector(middle.x - xSize / 2, middle.y - ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch7;
                ++idx;
            }

            EOcNode ch8 = new EOcNode(objs, new Vector(middle.x + xSize / 2, middle.y - ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch8;
            }
        }
    }

    public void draw(GL gl) {
        if (this.childs == null) {
            //frustum check
        } else {
            for (EOcNode child : this.childs) {
                child.draw(gl);
            }
        }

    }

    private ObjIns[] checkAllObjects(ObjIns[] objs) {
        LinkedList<ObjIns> objects = new LinkedList<ObjIns>();
        for (ObjIns obj : objs) {
            if (checkForObj(obj)) {
                objects.add(obj);
            }
        }
        return (ObjIns[]) objects.toArray();
    }

    boolean checkForObj(ObjIns obj) {
        //Cheated a bit 
        //Just check a sphear around the box against the sphear around the Obj
        //the error-margin shouldn't be to big but the check is much cheaper
        //and easier to understand

        float boxSph = (float) Math.sqrt(Math.pow(xSize / 2, 2) + Math.pow(ySize / 2, 2) + Math.pow(zSize / 2, 2));
        //Euclidean distance
        float dis = (float) Math.abs(Math.sqrt(Math.pow(this.middle.x - obj.origin.x, 2) + Math.pow(this.middle.y - obj.origin.y, 2) + Math.pow(this.middle.z - obj.origin.y, 2)));

        if (dis - boxSph - obj.boundSph < 0.0f) {
            return true;
        } else {
            return false;
        }
    }
}
