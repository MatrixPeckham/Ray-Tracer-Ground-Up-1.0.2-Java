/*
 * Copyright (C) 2015 William Matrix Peckham
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.matrixpeckham.raytracer.lights;

import com.matrixpeckham.raytracer.util.Point3D;
import com.matrixpeckham.raytracer.util.ShadeRec;
import com.matrixpeckham.raytracer.util.Utility;
import com.matrixpeckham.raytracer.util.Vector3D;

/**
 *
 * @author William Matrix Peckham
 */
public class FakeSphericalLight extends PointLight {
    double r = 1;

    public FakeSphericalLight(){}
    
    public FakeSphericalLight(FakeSphericalLight dl) {
        super(dl);
        r=dl.r;
    }

    @Override
    public Vector3D getDirection(ShadeRec sr) {
        Point3D nloc = new Point3D();
        nloc.x=location.x+r*(2*Utility.randDouble()-1);
        nloc.y=location.y+r*(2*Utility.randDouble()-1);
        nloc.z=location.z+r*(2*Utility.randDouble()-1);
        return nloc.sub(sr.hitPoint).hat();
    }

    public void setJitterAmount(double radius) {
        r=radius;
    }
    
    
    
}
