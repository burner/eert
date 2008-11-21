
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
        this.obj = new Obj[eObjParse.objects.size()];
        this.obj = eObjParse.objects.toArray(this.obj);
        
        this.objIns = new ObjIns[eObjParse.objectIns.size()];
        this.objIns = eObjParse.objectIns.toArray(this.objIns);
    }
    
    

}
