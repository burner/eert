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
package Types.Geometrie;

public class Face {

    public Vector v1,  v2,  v3;
    public Vector vn1,  vn2,  vn3;
    public Vector faceNormal;
    public TexCoor vt1,  vt2,  vt3;
    public Face fr1, fr2, fr3;      //friend1 is v1 & v2 and so on
    public Edge ed1, ed2, ed3;
    public float angle;
    public boolean lit;

    public Face(Vector v1, Vector v2, Vector v3, Vector vn1, Vector vn2, Vector vn3, TexCoor vt1, TexCoor vt2, TexCoor vt3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

        this.vn1 = vn1;
        this.vn2 = vn2;
        this.vn3 = vn3;

        this.vt1 = vt1;
        this.vt2 = vt2;
        this.vt3 = vt3;
        makeEdges();
    }

     public void makeEdges() {
        this.ed1 = new Edge(v1,v2);
        this.ed2 = new Edge(v2,v3);
        this.ed3 = new Edge(v3,v1);
    }
}
