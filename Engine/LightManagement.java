
package Engine;

import Types.Light.ELight;
import Types.Geometrie.Vector;
import Types.*;
import javax.media.opengl.GL;

public class LightManagement {
    private boolean[] active;
    private ELight[] lights;
    private GL gl;
    
    public LightManagement(GL gl) {
        this.gl = gl;
        this.active = new boolean[8];
        this.lights = fillLight();
    }
    
    public void activateLight(int num) {
        this.active[num] = true;
    }
    
    public void deActivateLight(int num) {
        this.active[num] = false;
    }
    
    public void setLightPos(int num, Vector newPos) {
        this.lights[num].pos = newPos;
    }
    
    public void setAmbiant(int num, float[] ambiant) {
        
    }
    
    public void setDirec(int num, float[] dir) {
        
    }
            
    
    private ELight[] fillLight() {
        ELight[] tmpLight = new ELight[8];
        
        for(ELight tmp : tmpLight) {
            tmp = new ELight();
        }
        return tmpLight;
    }
}
