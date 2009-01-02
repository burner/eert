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
package Util.Logic;

public final class UHPT {

    public static long currentTime = System.nanoTime();
    public static long startTime = System.nanoTime();
    public static long lastFrame = System.nanoTime();
    public static long timeDiff = System.nanoTime();
    public static long timeIntervalTimer = System.currentTimeMillis();
    public static int timer = 7500;    //15000 millisec = one timeslice
    public static int timeInterval = 0; //what timeslice

    public static final long getETime() {
        return UHPT.currentTime;
    }

    public static final long starTime() {
        return UHPT.startTime;
    }

    public static final void updateUHPT() {

        UHPT.currentTime = System.nanoTime();
        UHPT.timeDiff = UHPT.currentTime - UHPT.lastFrame;
        if (System.currentTimeMillis() > UHPT.timeIntervalTimer + timer) {
            System.out.println("timerInterval Update");
            UHPT.timeInterval++;
            UHPT.timeIntervalTimer = System.currentTimeMillis();
        }
        UHPT.lastFrame = System.nanoTime();
    }
}
