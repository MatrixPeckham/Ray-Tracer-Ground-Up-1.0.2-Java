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
package com.matrixpeckham.raytracer.geometricobjects.triangles;

import com.matrixpeckham.raytracer.geometricobjects.GeometricObject;
import com.matrixpeckham.raytracer.util.BBox;
import com.matrixpeckham.raytracer.util.DoubleRef;
import com.matrixpeckham.raytracer.util.Normal;
import com.matrixpeckham.raytracer.util.Point3D;
import com.matrixpeckham.raytracer.util.Ray;
import com.matrixpeckham.raytracer.util.ShadeRec;
import com.matrixpeckham.raytracer.util.Utility;

/**
 *
 * @author William Matrix Peckham
 */
public class SmoothTriangle extends GeometricObject {

    public final Normal n0 = new Normal(0, 1, 0);
    public final Normal n1 = new Normal(0, 1, 0);
    public final Normal n2 = new Normal(0, 1, 0);
    private final Point3D v0 = new Point3D(0);
    private final Point3D v1 = new Point3D(0, 0, 1);
    private final Point3D v2 = new Point3D(1, 0, 0);

    public SmoothTriangle() {
        super();
    }

    public SmoothTriangle(Point3D a, Point3D b, Point3D c) {
        super();
        v0.setTo(a);
        v1.setTo(b);
        v2.setTo(c);
    }

    public SmoothTriangle(SmoothTriangle s) {
        super(s);
        v0.setTo(s.v0);
        v1.setTo(s.v1);
        v2.setTo(s.v2);
        n0.setTo(s.n0);
        n1.setTo(s.n1);
        n2.setTo(s.n2);
    }

    @Override
    public GeometricObject clone() {
        return new SmoothTriangle(this);
    }

    private Normal interpolateNormal(double beta, double gamma) {
        Normal normal = n0.mul(1 - beta - gamma).add(n1.mul(beta)).add(n2.mul(
                gamma));
        return normal;
    }

    @Override
    public BBox getBoundingBox() {
        double delta = 0.0001;

        return (new BBox(Math.min(Math.min(v0.x, v1.x), v2.x) - delta, Math.max(
                Math.max(v0.x, v1.x), v2.x) + delta,
                Math.min(Math.min(v0.y, v1.y), v2.y) - delta, Math.max(Math.max(
                                v0.y, v1.y), v2.y) + delta,
                Math.min(Math.min(v0.z, v1.z), v2.z) - delta, Math.max(Math.max(
                                v0.z, v1.z), v2.z) + delta));
    }

    @Override
    public boolean hit(Ray ray, ShadeRec sr) {
        double a = v0.x - v1.x, b = v0.x - v2.x, c = ray.d.x, d = v0.x - ray.o.x;
        double e = v0.y - v1.y, f = v0.y - v2.y, g = ray.d.y, h = v0.y - ray.o.y;
        double i = v0.z - v1.z, j = v0.z - v2.z, k = ray.d.z, l = v0.z - ray.o.z;

        double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
        double q = g * i - e * k, s = e * j - f * i;

        double inv_denom = 1.0 / (a * m + b * q + c * s);

        double e1 = d * m - b * n - c * p;
        double beta = e1 * inv_denom;

        if (beta < 0.0) {
            return (false);
        }

        double r = r = e * l - h * i;
        double e2 = a * n + d * q + c * r;
        double gamma = e2 * inv_denom;

        if (gamma < 0.0) {
            return (false);
        }

        if (beta + gamma > 1.0) {
            return (false);
        }

        double e3 = a * p - b * r + d * s;
        double t = e3 * inv_denom;

        if (t < Utility.EPSILON) {
            return (false);
        }

        sr.lastT = t;
        sr.normal.setTo(interpolateNormal(beta, gamma));
        sr.localHitPosition.setTo(ray.o.add(ray.d.mul(t)));

        return (true);
    }

    @Override
    public boolean shadowHit(Ray ray, DoubleRef tr) {
        double a = v0.x - v1.x, b = v0.x - v2.x, c = ray.d.x, d = v0.x - ray.o.x;
        double e = v0.y - v1.y, f = v0.y - v2.y, g = ray.d.y, h = v0.y - ray.o.y;
        double i = v0.z - v1.z, j = v0.z - v2.z, k = ray.d.z, l = v0.z - ray.o.z;

        double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
        double q = g * i - e * k, s = e * j - f * i;

        double inv_denom = 1.0 / (a * m + b * q + c * s);

        double e1 = d * m - b * n - c * p;
        double beta = e1 * inv_denom;

        if (beta < 0.0) {
            return (false);
        }

        double r = r = e * l - h * i;
        double e2 = a * n + d * q + c * r;
        double gamma = e2 * inv_denom;

        if (gamma < 0.0) {
            return (false);
        }

        if (beta + gamma > 1.0) {
            return (false);
        }

        double e3 = a * p - b * r + d * s;
        double t = e3 * inv_denom;

        if (t < Utility.EPSILON) {
            return (false);
        }

        tr.d = t;

        return (true);
    }

}
