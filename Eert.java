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

import Util.Logic.EFrame;
import Util.*;

public class Eert {

    private static EFrame frame;

    public static void main(String[] args) {
        String szene = args[0];
        String fullscreen;
        boolean boolScreen = false;
        if (args.length == 2) {
            fullscreen = args[1];
            if (fullscreen.contains("Fullscreen")) {
                boolScreen = true;
            }
        }
     
        frame = new EFrame(szene, boolScreen);
        Eert.frame.animator.start();
        System.out.println("Before leaving main");
    }
}