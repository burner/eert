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
package Util.Geometrie;

import Types.Geometrie.Vector4;
import Types.Geometrie.Face;
import Types.Geometrie.TexCoor;
import Types.Geometrie.Vector;

public class VectorUtil {

    public static float dotProduct(Vector v1, Vector v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }

    public static float angleVec(Vector v1, Vector v2) {
        return (float) Math.acos((v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z));
    }

    public static float angle(Face f1, Vector v1) {
        /*
         * acos(dotProduct(faceNormal, dirPointToFaceMiddle)
         *      --------------------------------------------
         *      lenght(faceNormal) * length(dir))
         */

        //make middle of triangle
        float xF = (f1.v1.x / 3) + (f1.v2.x / 3) + (f1.v3.x / 3);
        float yF = (f1.v1.y / 3) + (f1.v2.y / 3) + (f1.v3.y / 3);
        float zF = (f1.v1.z / 3) + (f1.v2.z / 3) + (f1.v3.z / 3);

        //make vector from v1 to faceMiddle
        Vector dir = new Vector(xF - v1.x,
                yF - v1.y,
                zF - v1.z);


        float x = (float) Math.sqrt((f1.faceNormal.x * f1.faceNormal.x) +
                (f1.faceNormal.y * f1.faceNormal.y) +
                (f1.faceNormal.z * f1.faceNormal.z));


        float dot = (xF * dir.x) + (yF * dir.y) + (zF * dir.z);

        float y = (float) Math.sqrt((dir.x * dir.x) +
                (dir.y * dir.y) +
                (dir.z * dir.z));

        return (float) Math.acos(dot / (x * y));
    }

    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z);
    }

    public static Vector mult(Vector v, float scalar) {
        return new Vector(v.x * scalar,
                v.y * scalar,
                v.z * scalar);
    }

    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x,
                v1.y + v2.y,
                v1.z + v2.z);
    }

    public static Vector crossProduct(Vector vector1, Vector vector2) {
        return new Vector(
                vector1.y * vector2.z - vector1.z * vector2.y,
                vector1.z * vector2.x - vector1.x * vector2.z,
                vector1.x * vector2.y - vector1.y * vector2.x);
    }

    public static Vector threePlaneIntersec(Vector n1, float d1, Vector n2, float d2, Vector n3, float d3) {
        /*
        d1 ( N2 * N3 ) + d2 ( N3 * N1 ) + d3 ( N1 * N2 )
        P = 	-------------------------------------------------------------------------
        N1 . ( N2 * N3 )
         */
        n1.normalize();
        n2.normalize();
        n3.normalize();

        Vector n2n3C = crossProduct(n2, n3);
        Vector n3n1C = crossProduct(n3, n1);
        Vector n1n2C = crossProduct(n1, n2);

        Vector copyN2N3C = new Vector(n2n3C);

        n2n3C.mult(d1);
        n3n1C.mult(d2);
        n1n2C.mult(d3);

        Vector divisor = add(n2n3C, n3n1C);
        divisor.add(n1n2C);

        float divident = dotProduct(n1, copyN2N3C);

        divisor.div(divident);

        return divisor;
    }

    public static float distance(Vector x, Vector y) {
        return (float) Math.sqrt(Math.pow((x.x - y.x), 2) +
                Math.pow((x.y - y.y), 2) +
                Math.pow((x.z - y.z), 2));
    }

    public static float determinant4f(float m[]) {
        return m[12] * m[9] * m[6] * m[3] - m[8] * m[13] * m[6] * m[3] - m[12] * m[5] * m[10] * m[3] +
                m[4] * m[13] * m[10] * m[3] + m[8] * m[5] * m[14] * m[3] - m[4] * m[9] * m[14] * m[3] -
                m[12] * m[9] * m[2] * m[7] + m[8] * m[13] * m[2] * m[7] + m[12] * m[1] * m[10] * m[7] -
                m[0] * m[13] * m[10] * m[7] - m[8] * m[1] * m[14] * m[7] + m[0] * m[9] * m[14] * m[7] +
                m[12] * m[5] * m[2] * m[11] - m[4] * m[13] * m[2] * m[11] - m[12] * m[1] * m[6] * m[11] +
                m[0] * m[13] * m[6] * m[11] + m[4] * m[1] * m[14] * m[11] - m[0] * m[5] * m[14] * m[11] -
                m[8] * m[5] * m[2] * m[15] + m[4] * m[9] * m[2] * m[15] + m[8] * m[1] * m[6] * m[15] -
                m[0] * m[9] * m[6] * m[15] - m[4] * m[1] * m[10] * m[15] + m[0] * m[5] * m[10] * m[15];
    }

    public static float[] invertFFMatrix(float[] m) {
        float x = determinant4f(m);
        float[] i = new float[16];
        i[0] = (-m[13] * m[10] * m[7] + m[9] * m[14] * m[7] + m[13] * m[6] * m[11] - m[5] * m[14] * m[11] - m[9] * m[6] * m[15] + m[5] * m[10] * m[15]) / x;
        i[4] = (m[12] * m[10] * m[7] - m[8] * m[14] * m[7] - m[12] * m[6] * m[11] + m[4] * m[14] * m[11] + m[8] * m[6] * m[15] - m[4] * m[10] * m[15]) / x;
        i[8] = (-m[12] * m[9] * m[7] + m[8] * m[13] * m[7] + m[12] * m[5] * m[11] - m[4] * m[13] * m[11] - m[8] * m[5] * m[15] + m[4] * m[9] * m[15]) / x;
        i[12] = (m[12] * m[9] * m[6] - m[8] * m[13] * m[6] - m[12] * m[5] * m[10] + m[4] * m[13] * m[10] + m[8] * m[5] * m[14] - m[4] * m[9] * m[14]) / x;
        i[1] = (m[13] * m[10] * m[3] - m[9] * m[14] * m[3] - m[13] * m[2] * m[11] + m[1] * m[14] * m[11] + m[9] * m[2] * m[15] - m[1] * m[10] * m[15]) / x;
        i[5] = (-m[12] * m[10] * m[3] + m[8] * m[14] * m[3] + m[12] * m[2] * m[11] - m[0] * m[14] * m[11] - m[8] * m[2] * m[15] + m[0] * m[10] * m[15]) / x;
        i[9] = (m[12] * m[9] * m[3] - m[8] * m[13] * m[3] - m[12] * m[1] * m[11] + m[0] * m[13] * m[11] + m[8] * m[1] * m[15] - m[0] * m[9] * m[15]) / x;
        i[13] = (-m[12] * m[9] * m[2] + m[8] * m[13] * m[2] + m[12] * m[1] * m[10] - m[0] * m[13] * m[10] - m[8] * m[1] * m[14] + m[0] * m[9] * m[14]) / x;
        i[2] = (-m[13] * m[6] * m[3] + m[5] * m[14] * m[3] + m[13] * m[2] * m[7] - m[1] * m[14] * m[7] - m[5] * m[2] * m[15] + m[1] * m[6] * m[15]) / x;
        i[6] = (m[12] * m[6] * m[3] - m[4] * m[14] * m[3] - m[12] * m[2] * m[7] + m[0] * m[14] * m[7] + m[4] * m[2] * m[15] - m[0] * m[6] * m[15]) / x;
        i[10] = (-m[12] * m[5] * m[3] + m[4] * m[13] * m[3] + m[12] * m[1] * m[7] - m[0] * m[13] * m[7] - m[4] * m[1] * m[15] + m[0] * m[5] * m[15]) / x;
        i[14] = (m[12] * m[5] * m[2] - m[4] * m[13] * m[2] - m[12] * m[1] * m[6] + m[0] * m[13] * m[6] + m[4] * m[1] * m[14] - m[0] * m[5] * m[14]) / x;
        i[3] = (m[9] * m[6] * m[3] - m[5] * m[10] * m[3] - m[9] * m[2] * m[7] + m[1] * m[10] * m[7] + m[5] * m[2] * m[11] - m[1] * m[6] * m[11]) / x;
        i[7] = (-m[8] * m[6] * m[3] + m[4] * m[10] * m[3] + m[8] * m[2] * m[7] - m[0] * m[10] * m[7] - m[4] * m[2] * m[11] + m[0] * m[6] * m[11]) / x;
        i[11] = (m[8] * m[5] * m[3] - m[4] * m[9] * m[3] - m[8] * m[1] * m[7] + m[0] * m[9] * m[7] + m[4] * m[1] * m[11] - m[0] * m[5] * m[11]) / x;
        i[15] = (-m[8] * m[5] * m[2] + m[4] * m[9] * m[2] + m[8] * m[1] * m[6] - m[0] * m[9] * m[6] - m[4] * m[1] * m[10] + m[0] * m[5] * m[10]) / x;
        return i;
    }

    public static Vector4 vecMultMatrix(Vector4 in, float[] mIn) {
        Vector4 ret = new Vector4();
        ret.x = in.x * mIn[0] + in.x * mIn[1] + in.x * mIn[2] + in.x * mIn[3];
        ret.y = in.y * mIn[4] + in.y * mIn[5] + in.y * mIn[6] + in.y * mIn[7];
        ret.z = in.z * mIn[8] + in.z * mIn[9] + in.z * mIn[10] + in.z * mIn[11];
        ret.w = in.w * mIn[12] + in.w * mIn[13] + in.w * mIn[14] + in.w * mIn[15];

        return ret;
    }

    public static float[] invertModelView(float[] in) {
        float[] tmp = new float[16];
        //Rotation
        tmp[1] = in[4];
        tmp[2] = in[8];
        tmp[4] = in[1];
        tmp[6] = in[9];
        tmp[8] = in[2];
        tmp[9] = in[6];
        //Translation
        tmp[12] = -tmp[12];
        tmp[13] = -tmp[14];
        tmp[14] = -tmp[15];
        return tmp;
    }

    public static Vector multWithGLMatrix(float[] m, Vector in) {
        Vector tmp = new Vector();
        tmp.x = m[0] * in.x + m[1] * in.x + m[2] * in.x;
        tmp.y = m[4] * in.y + m[5] * in.y + m[6] * in.y;
        tmp.z = m[8] * in.x + m[9] * in.x + m[10] * in.x;

        tmp.x += m[12];
        tmp.y += m[13];
        tmp.z += m[14];

        return tmp;
    }

    public static Vector[] calcTBN(Face foo) {
        Vector v2v1 = VectorUtil.sub(foo.v2, foo.v1);
        Vector v3v1 = VectorUtil.sub(foo.v3, foo.v1);

        TexCoor uv21 = TexCoorUtil.sub(foo.vt2, foo.vt1);
        TexCoor uv31 = TexCoorUtil.sub(foo.vt3, foo.vt1);

        Vector[] ret = new Vector[3];
        //tangent
        ret[0] = VectorUtil.sub(VectorUtil.mult(v2v1, uv31.t), VectorUtil.mult(v3v1, uv21.t));
        ret[0].normalize();
        
        //binormal
        ret[1] = VectorUtil.sub(VectorUtil.mult(v3v1, uv21.s), VectorUtil.mult(v2v1, uv31.s));
        ret[1].normalize();

        //normal
        ret[2] = VectorUtil.crossProduct(ret[0], ret[1]);
        ret[2].normalize();

        //Gram-Schmidt orthogonalization
        ret[0].sub(VectorUtil.mult(ret[2], VectorUtil.dotProduct(ret[2], ret[0])));
        ret[0].normalize();

        //check if right handed TBN
        boolean right = VectorUtil.dotProduct(VectorUtil.crossProduct(ret[0], ret[1]), ret[2]) >= 0;
        ret[1] = VectorUtil.crossProduct(ret[2], ret[0]);

        if(!right) {
            ret[1].mult(-1);
        }

        return ret;
    }
}
