package com.danwink.convolutionsurface;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.ToDoubleBiFunction;

import com.danwink.convolutionsurface.primitive.LineSegment;
import com.danwink.convolutionsurface.primitive.Primitive;
import com.danwink.convolutionsurface.primitive.Triangle;

public class MainTest {
	public static void main(String[] args) {
		ArrayList<Primitive> prims = new ArrayList<>();

		// Line
		// prims.add( new LineSegment( new Vector( 0, 0, 0 ), new Vector( 1, 1, 1 ), 10f
		// ) );
		// Field field = FieldGenerator.getField( prims, new Vector( -2, -2, -2 ), new
		// Vector( 3, 3, 3 ), .03f );

		// Triangle
		float areaSize = 20;

		BiFunction<Float, Float, Float> xyToZ = (x, y) -> (float) (Math.sin(x * .4f) + Math.sin((y + Math.PI) * .8f));
		float s = 4f;
		for (float x = 0; x < areaSize; x++) {
			for (float y = 0; y < areaSize; y++) {
				Vector x1y1 = new Vector(x, y, xyToZ.apply(x, y));
				Vector x2y1 = new Vector(x + 1, y, xyToZ.apply(x + 1, y));
				Vector x1y2 = new Vector(x, y + 1, xyToZ.apply(x, y + 1));
				Vector x2y2 = new Vector(x + 1, y + 1, xyToZ.apply(x + 1, y + 1));
				prims.add(new Triangle(x1y1, x2y1, x2y2, s));
				prims.add(new Triangle(x1y1, x1y2, x2y2, s));
			}
		}
		Field field = FieldGenerator.getField(prims, new Vector(-1, -1, -3), new Vector(areaSize + 1, areaSize + 1, 3),
				.2f);

		MarchingCubesPolygonizer mcp = new MarchingCubesPolygonizer();
		ArrayList<Triangle> tris = mcp.polygonize(field, .1f);
		System.out.println("Saving");
		// saveTriangles(tris, "test.scad");
		saveAsStl(tris, "test.stl");
	}

	public static void saveTriangles(ArrayList<Triangle> tris, String file) {
		ArrayList<Vector> points = new ArrayList<Vector>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (Triangle t : tris) {
			indices.add(points.size());
			indices.add(points.size() + 1);
			indices.add(points.size() + 2);
			points.add(t.p0);
			points.add(t.p1);
			points.add(t.p2);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("polyhedron( points=[");
		for (int i = 0; i < points.size(); i++) {
			Vector p = points.get(i);
			sb.append("[" + p.x + "," + p.y + "," + p.z + "]");
			if (i + 1 < points.size()) {
				sb.append(",");
			}
		}
		sb.append("],  faces=[");
		for (int i = 0; i < indices.size(); i += 3) {
			sb.append("[" + indices.get(i) + "," + indices.get(i + 1) + "," + indices.get(i + 2) + "]");
			if (i + 4 < points.size()) {
				sb.append(",");
			}
		}
		sb.append("]);");

		try {
			Files.write(FileSystems.getDefault().getPath(file), sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copied from
	 * https://stackoverflow.com/questions/29971199/export-javafx-model-to-stl-file
	 * 
	 * @param tris
	 * @param file
	 */
	public static void saveAsStl(ArrayList<Triangle> tris, String file) {
		StringBuilder sb = new StringBuilder();
		sb.append("solid meshFX\n");

		// convert faces to polygons
		for (Triangle t : tris) {
			Vector normal = t.getNormal();
			sb.append("  facet normal ").append(normal.toSpaceDelitedString()).append("\n");
			sb.append("    outer loop\n");
			sb.append("      vertex ").append(t.p0.toSpaceDelitedString()).append("\n");
			sb.append("      vertex ").append(t.p1.toSpaceDelitedString()).append("\n");
			sb.append("      vertex ").append(t.p2.toSpaceDelitedString()).append("\n");
			sb.append("    endloop\n");
			sb.append("  endfacet\n");
		}

		sb.append("endsolid meshFX\n");

		// write file
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file), Charset.forName("UTF-8"),
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write(sb.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
