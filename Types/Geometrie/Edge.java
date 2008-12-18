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

public class Edge {
    public Vector x1;
    public Vector x2;
    public Vector dirX1;
    public Vector dirX2;

    public Edge(Vector x1, Vector x2, Vector dirX1, Vector dirX2) {
        this.x1 = x1;
        this.x2 = x2;
        this.dirX1 = dirX1;
        this.dirX2 = dirX2;
    }

    public Edge(Vector x1, Vector x2, Vector pos) {
        this.x1 = x1;
        this.x2 = x2;

        this.dirX1 = new Vector(x1.x - pos.x,
                                x1.y - pos.y,
                                x1.z - pos.z);

        this.dirX2 = new Vector(x2.x - pos.x,
                                x2.y - pos.y,
                                x2.z - pos.z);
    }

}
