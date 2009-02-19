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

public class Vector4 {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(Vector old) {
        this.x = old.x;
        this.y = old.y;
        this.z = old.z;
        this.w = 1f;
    }

    public Vector4(Vector4 old) {
        this.x = old.x;
        this.y = old.y;
        this.z = old.z;
        this.w = old.w;
    }

    public float[] toArray() {
        float[] foo = {
            this.x,
            this.y,
            this.z,
            this.w};
        return foo;
        
    }
}
