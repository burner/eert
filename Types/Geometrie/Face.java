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
	public int v1 = 0, vt1 = 0, vn1 = 0;
	public int v2 = 0, vt2 = 0, vn2 = 0;
	public int v3 = 0, vt3 = 0, vn3 = 0;
	public Face(int v1, int vt1, int  vn1, int v2, int vt2, int vn2, int v3, int vt3, int vn3) {
		this.v1 = v1;
		this.vt1 = vt1;
		this.vn1 = vn1;
		this.v2 = v2;
		this.vt2 = vt2;
		this.vn2 = vn2;
		this.v3 = v3;
		this.vt3 = vt3;
		this.vn3 = vn3;
	}
}
