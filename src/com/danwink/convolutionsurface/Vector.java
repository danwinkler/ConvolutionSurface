package com.danwink.convolutionsurface;

public class Vector {
	public float x, y, z;

	public Vector() {

	}

	public Vector(Vector p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(Vector v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}

	public void sub(Vector v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}

	public void scale(float s) {
		x *= s;
		y *= s;
		z *= s;
	}

	public void set(Vector v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public Vector cross(Vector v) {
		Vector r = new Vector();
		r.x = y * v.z - z * v.y;
		r.y = z * v.x - x * v.z;
		r.z = x * v.y - y * v.x;
		return r;
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float dot(Vector v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public void sub(Vector a, Vector b) {
		x = a.x - b.x;
		y = a.y - b.y;
		z = a.z - b.z;
	}

	public void normalize() {
		scale(1.f / length());
	}

	public float distance(Vector b) {
		Vector tmp = new Vector();
		tmp.sub(b, this);
		return tmp.length();
	}

	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}

	public String toSpaceDelitedString() {
		return String.format("%f %f %f", x, y, z);
	}
}
