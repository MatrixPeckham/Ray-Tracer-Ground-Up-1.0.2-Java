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
package com.matrixpeckham.raytracer.geometricobjects.beveledobjects;

import com.matrixpeckham.raytracer.geometricobjects.Instance;
import com.matrixpeckham.raytracer.geometricobjects.compound.*;
import com.matrixpeckham.raytracer.geometricobjects.parametric.Torus;
import com.matrixpeckham.raytracer.geometricobjects.partobjects.ConcavePartCylinder;
import com.matrixpeckham.raytracer.geometricobjects.partobjects.ConvexPartCylinder;
import com.matrixpeckham.raytracer.geometricobjects.primitives.Disk;
import com.matrixpeckham.raytracer.geometricobjects.primitives.OpenCylinder;
import com.matrixpeckham.raytracer.geometricobjects.primitives.Ring;
import com.matrixpeckham.raytracer.materials.Matte;
import com.matrixpeckham.raytracer.util.Normal;
import com.matrixpeckham.raytracer.util.Point3D;

/**
 *
 * @author William Matrix Peckham
 */
public class BeveledRing extends Compound {
    
    public BeveledRing(){
        this(-1,1,0.5,1,0.1);
    }
    
    public BeveledRing(double bottom, double top, double innerRadius, double outerRadius,double bevelRadius) {
        Ring topd = new Ring(new Point3D(0, top, 0), new Normal(0, 1,
                0), innerRadius+bevelRadius,outerRadius-bevelRadius);
        Ring bottomd = new Ring(new Point3D(0, bottom, 0), new Normal(0, -1,
                0), innerRadius+bevelRadius,outerRadius-bevelRadius);
        ConcavePartCylinder innercyl = new ConcavePartCylinder(bottom+bevelRadius, top-bevelRadius, innerRadius,0,360);
        ConvexPartCylinder outercyl = new ConvexPartCylinder(bottom+bevelRadius, top-bevelRadius, outerRadius,0,360);
        Torus inTor = new Torus(innerRadius+bevelRadius, bevelRadius);
        Torus outTor = new Torus(outerRadius-bevelRadius, bevelRadius);
        addObject(topd);
        addObject(bottomd);
        addObject(innercyl);
        addObject(outercyl);
        Instance inUp = new Instance(inTor);
        inUp.translate(0, top-bevelRadius, 0);
        addObject(inUp);
        Instance inDown = new Instance(inTor);
        inDown.translate(0, bottom+bevelRadius, 0);
        addObject(inDown);
        Instance outUp = new Instance(outTor);
        outUp.translate(0, top-bevelRadius, 0);
        addObject(outUp);
        Instance outDown = new Instance(outTor);
        outDown.translate(0, bottom+bevelRadius, 0);
        addObject(outDown);
    }

    public void setBottomMaterial(Matte mattePtr1) {
        objects.get(1).setMaterial(mattePtr1);
    }

    public void setTopMaterial(Matte mattePtr1) {
        objects.get(0).setMaterial(mattePtr1);
    }

    public void setWallMaterial(Matte mattePtr2) {
        objects.get(2).setMaterial(mattePtr2);
        objects.get(3).setMaterial(mattePtr2);
    }

    public void setInnerWallMaterial(Matte mattePtr2) {
        objects.get(2).setMaterial(mattePtr2);
    }

    public void setOuterWallMaterial(Matte mattePtr3) {
        objects.get(3).setMaterial(mattePtr3);
    }
    
}
