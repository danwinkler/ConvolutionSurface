package com.danwink.convolutionsurface.primitive;

import com.danwink.convolutionsurface.Vector;

public class Triangle implements Primitive {
	public Vector p0, p1, p2;
	private Vector b, u, v;
	private float s, a1, a2, h, s2, h2;

	public Triangle() {

	}

	public Triangle(Vector p0, Vector p1, Vector p2) {
		this(p0, p1, p2, 1);
	}

	public Triangle(Vector p0, Vector p1, Vector p2, float s) {
		// Make p0 - p1 the longest side
		// This could be a lot shorter, but at least its straightforward
		float p0p1 = p0.distance(p1);
		float p1p2 = p1.distance(p2);
		float p2p0 = p2.distance(p0);
		if (p1p2 > p0p1 && p1p2 > p2p0) // If p1p2 is longest
		{
			Vector p0t = p1;
			Vector p1t = p2;
			Vector p2t = p0;

			p0 = p0t;
			p1 = p1t;
			p2 = p2t;
		} else if (p2p0 > p0p1 && p2p0 > p1p2) // If p2p0 is longest
		{
			Vector p0t = p2;
			Vector p1t = p0;
			Vector p2t = p1;

			p0 = p0t;
			p1 = p1t;
			p2 = p2t;
		}

		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;

		this.s = s;

		Vector v01 = new Vector(p1);
		v01.sub(p0);
		float v01length = v01.length();
		Vector v02 = new Vector(p2);
		v02.sub(p0);

		Vector v01n = new Vector(v01);
		v01n.normalize();
		Vector v02n = new Vector(v02);
		v02n.normalize();

		Vector av = new Vector(v01);
		// TODO: comment explaining this
		av.scale(v01.dot(v02) / v01.dot(v01));

		b = new Vector(p0);
		b.add(av);

		u = new Vector(v01);
		u.normalize();

		v = new Vector(p2);
		v.sub(b);
		h = v.length();
		v.normalize();

		a1 = av.length();
		a2 = v01length - a1;

		h2 = h * h;
		s2 = s * s;
	}

	public Vector getNormal() {
		Vector d1 = new Vector(p1);
		d1.sub(p0);
		d1.normalize();

		Vector d2 = new Vector(p2);
		d1.sub(p0);
		d2.normalize();

		return d1.cross(d2);
	}

	public float compute(Vector x) {
		Vector d = new Vector(x);
		d.sub(b);

		float d2 = d.lengthSquared();

		float us = (float) d.dot(u);
		float vs = (float) d.dot(v);

		float g = vs - h;
		float q = 1 + s2 * (d2 - us * us - vs * vs);
		float C2 = 1 + s2 * (d2 - us * us);
		float w = C2 - 2 * h * s2 * vs + h2 * s2;
		float m = a2 * g + us * h;
		float n = us * h - a1 * g;
		float A2 = a1 * a1 * w + h2 * (q + s2 * us * us) - 2 * h * s2 * a1 * us * g;
		float B2 = a2 * a2 * w + h2 * (q + s2 * us * us) + 2 * h * s2 * a2 * us * g;

		float A = (float) Math.sqrt(A2);
		float B = (float) Math.sqrt(B2);
		float C = (float) Math.sqrt(C2);

		float numer0 = (s * (vs * h + a1 * (a1 + us)));
		float numer1 = (s * (g * h + a1 * us));
		float numer2 = (s * (vs * h + a2 * (a2 - us)));
		float numer3 = (s * (g * h - a2 * us));
		float numer4 = (s * (a1 + us));
		float numer5 = (s * (a2 - us));

		float T1 = (n / A) * (float) (Math.atan(numer0 / A) + Math.atan(numer1 / -A));
		float T2 = (m / B) * (float) (Math.atan(numer2 / -B) + Math.atan(numer3 / B));
		float T3 = (vs / C) * (float) (Math.atan(numer4 / C) + Math.atan(numer5 / C));

		return (1 / (2 * q * s)) * (T1 + T2 + T3);
	}

	public Vector getMin() {
		return new Vector(Math.min(Math.min(p0.x, p1.x), p2.x), Math.min(Math.min(p0.y, p1.y), p2.y),
				Math.min(Math.min(p0.z, p1.z), p2.z));
	}

	public Vector getMax() {
		return new Vector(Math.max(Math.max(p0.x, p1.x), p2.x), Math.max(Math.max(p0.y, p1.y), p2.y),
				Math.max(Math.max(p0.z, p1.z), p2.z));
	}
}
