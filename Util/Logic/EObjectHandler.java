
package Util.Logic;

import Engine.Engine;
import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Types.*;
import Util.Logic.Camera;
import Util.Prelude.EObjParse;
import javax.media.opengl.GL;

public class EObjectHandler {
    public Obj[] obj;
    public ObjIns[] objIns;
    private Camera cam;
    private GL gl;
    private String szene;
    private Engine engine;
    public String[] textures;
    public EObjectHandler(Camera cam, String szene, GL gl, Engine engine) {
        this.engine = engine;
        this.cam = cam;
        this.gl = gl;
        this.szene = szene;
        this.textures = new String[6];
        //open the szene file and get all objects and objectinstances
        EObjParse eObjParse = new EObjParse(this.cam, szene, this.gl, this.engine);
        this.obj = new Obj[eObjParse.objects.size()];
        this.obj = eObjParse.objects.toArray(this.obj);
        
        this.objIns = new ObjIns[eObjParse.objectIns.size()];
        this.objIns = eObjParse.objectIns.toArray(this.objIns);
    }
    
    

}
