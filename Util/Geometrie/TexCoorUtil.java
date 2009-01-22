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

import Types.Geometrie.TexCoor;

public class TexCoorUtil {
    public static TexCoor sub(TexCoor t1, TexCoor t2) {
        return new TexCoor(t1.s - t2.s, t1.t - t1.t);
    }

    public static TexCoor add(TexCoor t1, TexCoor t2) {
        return new TexCoor(t1.s + t2.s, t1.t + t1.t);
    }
}