/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import javax.media.opengl.GL;

public class EAnti {

    public void accPerspective(GL gl, double fovy, double aspect, double near, double far, double pixdx, double pixdy, double eyedx, double eyedy, double focus) {
        double fov2, left, right, bottom, top;

        fov2 = ((fovy * Math.PI) / 180.0) / 2.0;

        top = near / (Math.cos(fov2) / Math.sin(fov2));
        bottom = -top;

        right = top * aspect;
        left = -right;

        accFrustum(gl, left, right, bottom, top, near, far, pixdx, pixdy, eyedx,
                eyedy, focus);
    }

    private void accFrustum(GL gl, double left, double right, double bottom, double top, double near, double far, double pixdx, double pixdy, double eyedx, double eyedy, double focus) {
        double xwsize, ywsize;
        double dx, dy;
        int viewport[] = new int[4];

        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        xwsize = right - left;
        ywsize = top - bottom;

        dx = -(pixdx * xwsize / (double) viewport[2] + eyedx * near / focus);
        dy = -(pixdy * ywsize / (double) viewport[3] + eyedy * near / focus);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustum(left + dx, right + dx, bottom + dy, top + dy, near, far);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef((float) -eyedx, (float) -eyedy, 0.0f);
    }
    
    public final int ACSIZE = 8;
    /* 2 jitter points */
    JitterPoint j2[] = {new JitterPoint(0.246490f, 0.249999f),
        new JitterPoint(-0.246490f, -0.249999f)
    };

    /* 3 jitter points */
    JitterPoint j3[] = {new JitterPoint(-0.373411, -0.250550),//
        new JitterPoint(0.256263, 0.368119), //
        new JitterPoint(0.117148, -0.117570)};

    /* 4 jitter points */
    JitterPoint j4[] = {new JitterPoint(-0.208147, 0.353730),
        new JitterPoint(0.203849, -0.353780),
        new JitterPoint(-0.292626, -0.149945),
        new JitterPoint(0.296924, 0.149994)
    };

    /* 8 jitter points */
    JitterPoint j8[] = {new JitterPoint(-0.334818, 0.435331),
        new JitterPoint(0.286438, -0.393495),
        new JitterPoint(0.459462, 0.141540),
        new JitterPoint(-0.414498, -0.192829),
        new JitterPoint(-0.183790, 0.082102),
        new JitterPoint(-0.079263, -0.317383),
        new JitterPoint(0.102254, 0.299133), new JitterPoint(0.164216, -0.054399)
    };

    /* 15 jitter points */
    JitterPoint j15[] = {new JitterPoint(0.285561, 0.188437),
        new JitterPoint(0.360176, -0.065688),
        new JitterPoint(-0.111751, 0.275019),
        new JitterPoint(-0.055918, -0.215197),
        new JitterPoint(-0.080231, -0.470965),
        new JitterPoint(0.138721, 0.409168), new JitterPoint(0.384120, 0.458500),
        new JitterPoint(-0.454968, 0.134088),
        new JitterPoint(0.179271, -0.331196),
        new JitterPoint(-0.307049, -0.364927),
        new JitterPoint(0.105354, -0.010099),
        new JitterPoint(-0.154180, 0.021794),
        new JitterPoint(-0.370135, -0.116425),
        new JitterPoint(0.451636, -0.300013),
        new JitterPoint(-0.370610, 0.387504)
    };

    /* 24 jitter points */
    JitterPoint j24[] = {new JitterPoint(0.030245, 0.136384),
        new JitterPoint(0.018865, -0.348867),
        new JitterPoint(-0.350114, -0.472309),
        new JitterPoint(0.222181, 0.149524),
        new JitterPoint(-0.393670, -0.266873),
        new JitterPoint(0.404568, 0.230436), new JitterPoint(0.098381, 0.465337),
        new JitterPoint(0.462671, 0.442116),
        new JitterPoint(0.400373, -0.212720),
        new JitterPoint(-0.409988, 0.263345),
        new JitterPoint(-0.115878, -0.001981),
        new JitterPoint(0.348425, -0.009237),
        new JitterPoint(-0.464016, 0.066467),
        new JitterPoint(-0.138674, -0.468006),
        new JitterPoint(0.144932, -0.022780),
        new JitterPoint(-0.250195, 0.150161),
        new JitterPoint(-0.181400, -0.264219),
        new JitterPoint(0.196097, -0.234139),
        new JitterPoint(-0.311082, -0.078815),
        new JitterPoint(0.268379, 0.366778),
        new JitterPoint(-0.040601, 0.327109),
        new JitterPoint(-0.234392, 0.354659),
        new JitterPoint(-0.003102, -0.154402),
        new JitterPoint(0.297997, -0.417965)
    };

    /* 66 jitter points */
    JitterPoint j66[] = {new JitterPoint(0.266377, -0.218171),
        new JitterPoint(-0.170919, -0.429368),
        new JitterPoint(0.047356, -0.387135),
        new JitterPoint(-0.430063, 0.363413),
        new JitterPoint(-0.221638, -0.313768),
        new JitterPoint(0.124758, -0.197109),
        new JitterPoint(-0.400021, 0.482195),
        new JitterPoint(0.247882, 0.152010),
        new JitterPoint(-0.286709, -0.470214),
        new JitterPoint(-0.426790, 0.004977),
        new JitterPoint(-0.361249, -0.104549),
        new JitterPoint(-0.040643, 0.123453),
        new JitterPoint(-0.189296, 0.438963),
        new JitterPoint(-0.453521, -0.299889),
        new JitterPoint(0.408216, -0.457699),
        new JitterPoint(0.328973, -0.101914),
        new JitterPoint(-0.055540, -0.477952),
        new JitterPoint(0.194421, 0.453510), //
        new JitterPoint(0.404051, 0.224974), //
        new JitterPoint(0.310136, 0.419700),
        new JitterPoint(-0.021743, 0.403898),
        new JitterPoint(-0.466210, 0.248839),
        new JitterPoint(0.341369, 0.081490),
        new JitterPoint(0.124156, -0.016859),
        new JitterPoint(-0.461321, -0.176661),
        new JitterPoint(0.013210, 0.234401),
        new JitterPoint(0.174258, -0.311854),
        new JitterPoint(0.294061, 0.263364),
        new JitterPoint(-0.114836, 0.328189),
        new JitterPoint(0.041206, -0.106205),
        new JitterPoint(0.079227, 0.345021),
        new JitterPoint(-0.109319, -0.242380),
        new JitterPoint(0.425005, -0.332397),
        new JitterPoint(0.009146, 0.015098),
        new JitterPoint(-0.339084, -0.355707),
        new JitterPoint(-0.224596, -0.189548),
        new JitterPoint(0.083475, 0.117028),
        new JitterPoint(0.295962, -0.334699),
        new JitterPoint(0.452998, 0.025397),
        new JitterPoint(0.206511, -0.104668),
        new JitterPoint(0.447544, -0.096004),
        new JitterPoint(-0.108006, -0.002471),
        new JitterPoint(-0.380810, 0.130036),
        new JitterPoint(-0.242440, 0.186934),
        new JitterPoint(-0.200363, 0.070863),
        new JitterPoint(-0.344844, -0.230814),
        new JitterPoint(0.408660, 0.345826),
        new JitterPoint(-0.233016, 0.305203),
        new JitterPoint(0.158475, -0.430762),
        new JitterPoint(0.486972, 0.139163),
        new JitterPoint(-0.301610, 0.009319),
        new JitterPoint(0.282245, -0.458671),
        new JitterPoint(0.482046, 0.443890),
        new JitterPoint(-0.121527, 0.210223),
        new JitterPoint(-0.477606, -0.424878),
        new JitterPoint(-0.083941, -0.121440),
        new JitterPoint(-0.345773, 0.253779),
        new JitterPoint(0.234646, 0.034549),
        new JitterPoint(0.394102, -0.210901),
        new JitterPoint(-0.312571, 0.397656),
        new JitterPoint(0.200906, 0.333293),
        new JitterPoint(0.018703, -0.261792),
        new JitterPoint(-0.209349, -0.065383),
        new JitterPoint(0.076248, 0.478538),
        new JitterPoint(-0.073036, -0.355064),
        new JitterPoint(0.145087, 0.221726)
    };
}
