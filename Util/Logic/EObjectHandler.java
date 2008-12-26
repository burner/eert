
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

    //Constuctor
    public EObjectHandler(Camera cam, String szene, GL gl, Engine engine) {
        //Save all the parameter
        this.engine = engine;
        this.cam = cam;
        this.gl = gl;
        this.szene = szene;

        //make space to save the texture url
        this.textures = new String[6];

        //parse the szene file
        EObjParse eObjParse = new EObjParse(this.cam, szene, this.gl, this.engine);

        //save the objects parse by EObjParse
        this.obj = new Obj[eObjParse.objects.size()];
        this.obj = eObjParse.objects.toArray(this.obj);

        //save the objInstances parsed by EObjParse
        this.objIns = new ObjIns[eObjParse.objectIns.size()];
        this.objIns = eObjParse.objectIns.toArray(this.objIns);
    }

    //update all the objInstances
    public void updateObjIns() {
        for(ObjIns toUpdate : this.objIns) {
            toUpdate.update();
        }
    }
    
    

}
