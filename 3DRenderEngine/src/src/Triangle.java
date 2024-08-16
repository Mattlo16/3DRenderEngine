import java.awt.Color;

//This class stores coordinates of a single point in 3D space.
public class Triangle {
    //A triangle in 3D graphics is a basic shape that consists of 3 vertices. 
    //It's one of the simplest polygons and is commonly used in 3D models because any surface can be broken down into large or tiny triangle.
    
    Vertex v1; //First vertex of triangle
    Vertex v2; //Second vertex of triangle
    Vertex v3; //Third vertex of triangle
    Color color; //Delcares a variable color of type Color. This variable will store the color of the triangle using the Color class from java.awt package.

    Triangle(Vertex v1, Vertex v2, Vertex v3, Color color)
    {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }

}
