
package Engine;

import Types.Obj;
import Types.Vector;
import java.util.LinkedList;
import javax.media.opengl.GL;

public class EOcNode {
    public EOcNode[] childs;
    public Obj[] objs;
    private boolean[] drawn;
    private float xSize, ySize, zSize;
    private Vector middle;
    
    public EOcNode(Obj[] objs, Vector middle, float xSize, float ySize, float zSize, boolean[] drawn, int depth) {     
        this.middle = middle;
        this.drawn = drawn;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
        
        float xSizeH = xSize /2;
        float ySizeH = ySize /2;
        float zSizeH = zSize /2;
        
        this.objs = checkAllObjects(objs);
                
        if(objs.length > 1 && depth < 5) {
            this.childs = new EOcNode[8];
            EOcNode ch1 = new EOcNode(objs, new Vector(middle.x - xSize/2, middle.y + ySize/2, middle.z + zSize/2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if(ch1.objs != null)
                this.childs[0] = ch1;            
            
            EOcNode ch2 = new EOcNode(objs, new Vector(middle.x + xSize/2, middle.y + ySize/2, middle.z + zSize/2), xSizeH, ySizeH, zSizeH, this.drawn,depth++);
            if(ch2.objs != null)
                this.childs[1] = ch1;
            
            EOcNode ch3 = new EOcNode(objs, new Vector(middle.x - xSize/2, middle.y - ySize/2, middle.z + zSize/2), xSizeH, ySizeH, zSizeH, this.drawn,depth++);
            if(ch1.objs != null)
                this.childs[2] = ch1;            
            
            EOcNode ch4 = new EOcNode(objs, new Vector(middle.x + xSize/2, middle.y - ySize/2, middle.z + zSize/2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if(ch2.objs != null)
                this.childs[3] = ch1;
            
            EOcNode ch5 = new EOcNode(objs, new Vector(middle.x - xSize/2, middle.y + ySize/2, middle.z - zSize/2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if(ch1.objs != null)
                this.childs[4] = ch1;            
            
            EOcNode ch6 = new EOcNode(objs, new Vector(middle.x + xSize/2, middle.y + ySize/2, middle.z - zSize/2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if(ch2.objs != null)
                this.childs[5] = ch1;
            
            EOcNode ch7 = new EOcNode(objs, new Vector(middle.x - xSize/2, middle.y - ySize/2, middle.z - zSize/2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if(ch1.objs != null)
                this.childs[6] = ch1;            
            
            EOcNode ch8 = new EOcNode(objs, new Vector(middle.x + xSize/2, middle.y - ySize/2, middle.z - zSize/2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if(ch2.objs != null)
                this.childs[7] = ch1;
        }
    }

    public void draw(GL gl) {
        if(this.childs == null) {
            //frustum check
        } else {
            for(EOcNode child : this.childs) {
                child.draw(gl);
            }
        }
        
    }
    
    private Obj[] checkAllObjects(Obj[] objs) {
        LinkedList<Obj> objects = new LinkedList<Obj>();
        for(Obj obj : objs) {
            if(checkForObj(obj))
                objects.add(obj);
        }
        return (Obj[])objects.toArray();
    }
    
    boolean checkForObj(Obj obj) {
        //Cheated a bit 
        //Just check a sphear around the box against the sphear around the Obj
        //the error-margin shouldn't be to big but the check is much cheaper
        //and easier to understand
        
        float boxSph = (float) Math.sqrt(Math.pow(xSize/2, 2) + Math.pow(ySize/2, 2) + Math.pow(zSize/2, 2));
        //Euclidean distance
        float dis = (float) Math.abs(Math.sqrt(Math.pow(this.middle.x - obj.origin.x, 2) + Math.pow(this.middle.y - obj.origin.y, 2) + Math.pow(this.middle.z - obj.origin.y, 2)));
        
        if(dis - boxSph - obj.bR >= 0.0f)
            return true;
        else 
            return false;
    }
    
    
    

}
