# ConvolutionSurface
Java Implementation of convolution primitives from "Creating and Rendering Convolution Surfaces" (1997), 
by Jon McCormack and Andrei Sheerstyuk. Currently only the only primitive supported are line segments.

Includes functions for:
* Generating scalar fields from list of primitives
* Generating triangles from scalar fields using marching cubes

Example:
```java
ArrayList<Primitive> prims = new ArrayList<>();
prims.add( new LineSegment( new Vector( 0, 0, 0 ), new Vector( 1, 1, 1 ), 10f ) );
Field field = FieldGenerator.getField( prims, new Vector( -1, -1, -1 ), new Vector( 2, 2, 2 ), .02f );

MarchingCubesPolygonizer mcp = new MarchingCubesPolygonizer();
ArrayList<Triangle> tris = mcp.polygonize( field, .1f );
```

## References
1. Mccormack, Jon, and Andrei Sherstyuk. "Creating and Rendering Convolution Surfaces." Computer Graphics Forum 17.2 (1998): 113-20.
