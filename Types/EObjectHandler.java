
package Types;

import Util.Camera;
import Util.EObjParse;
import javax.media.opengl.GL;

public class EObjectHandler {
    public Obj[] obj;
    public ObjIns[] objIns;
    private Camera cam;
    private GL gl;
    private String szene;
    public EObjectHandler(Camera cam, String szene, GL gl) {
        this.cam = cam;
        this.gl = gl;
        this.szene = szene;
        
        //open the szene file and get all objects and objectinstances
        EObjParse eObjParse = new EObjParse(this.cam, szene, this.gl);
        this.obj = (Obj[])eObjParse.objects.toArray();
        this.objIns = (ObjIns[])eObjParse.objectIns.toArray();
    }
    
    

}
