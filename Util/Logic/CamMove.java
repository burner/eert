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

import Types.Geometrie.Vector;
import Util.Geometrie.VectorUtil;


/* this class lets the cam move on a spline
 * and look a point on a spline
 */
public class CamMove {

    private Vector[] pOn;
    private Vector[] pTo;
    private long startTime;
    private long timeSlice;
    private int counter;
    private long interval;

    public Vector pos;
    public Vector lookAt;
    public boolean aktiv;

    public CamMove(int timeSlice, int interval, Vector[] pOn, Vector[] pTo) {
        this.aktiv = true;
        this.timeSlice = timeSlice;
        this.interval = interval;
        this.pOn = pOn;
        this.pTo = pTo;
        this.counter = 1;
    }

    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public void updatePos() {
        //check if the time passed another timeslice
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - ((this.timeSlice * this.counter) - this.startTime);

        if ((this.startTime + (this.timeSlice * this.counter)) < currentTime) {
            //this needs to be done because more than one timeSlice could have passed
            for (int i = 0; i < (int) timeDiff / this.timeSlice; i++) {
                this.counter++;
            }
        }

        //check if camera Move should still be updated
        if(counter*3 > this.pOn.length) {
            this.aktiv = false;
            return;
        } else {
            this.aktiv = true;
        }

        
        double lamda = timeDiff / this.timeSlice;

        //Quadratic Bézier curves
        //gone cheat a bit
        //only discrete curves meaning allways three points
        Vector p0 = VectorUtil.mult(this.pOn[3 * this.counter - 3], (float) Math.pow(1 - lamda, 2));

        Vector p1 = VectorUtil.mult(this.pOn[3 * this.counter - 2], (float) (2 * lamda * (1 - lamda)));

        Vector p2 = VectorUtil.mult(this.pOn[3 * this.counter - 1], (float) Math.pow(lamda, 2));

        this.pos = VectorUtil.add(p0, p1, p2);

        
        //same for the lookAt Vector
        p0 = VectorUtil.mult(this.pTo[3 * this.counter - 3], (float) Math.pow(1 - lamda, 2));

        p1 = VectorUtil.mult(this.pTo[3 * this.counter - 2], (float) (2 * lamda * (1 - lamda)));

        p2 = VectorUtil.mult(this.pTo[3 * this.counter - 1], (float) Math.pow(lamda, 2));

        this.pos = VectorUtil.add(p0, p1, p2);
    }
}
