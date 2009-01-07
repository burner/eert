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

import Types.Geometrie.Face;
import Types.Geometrie.Vector;

public class VectorUtil {

    public static float dotProduct(Vector v1, Vector v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
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
}
