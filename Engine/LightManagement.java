
package Engine;

import Types.*;
import javax.media.opengl.GL;

public class LightManagement {
    private boolean[] active;
    private Light[] lights;
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
            
    
    private Light[] fillLight() {
        Light[] tmpLight = new Light[8];
        
        for(Light tmp : tmpLight) {
            tmp = new Light();
        }
        return tmpLight;
    }
}
