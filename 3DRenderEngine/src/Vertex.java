
//This class stores coordinates of a single point in 3D space.
public class Vertex {
    //A vertex is a point in 3d space defined by three coordinates, X, Y, and Z. In 3D graphics, objects are made up of vertices connected together to form shapes.
    //X-Axis: Represents the left-right direction.
    //Y-Axis: Represents the up-down direction on the screen.
    //Z-Axis: Represents depth, with positive Z moving "towards the observer" (coming out of the screen) and negative Z moving "away" (going into the screen).

    double x;
    double y;
    double z;

    Vertex(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
