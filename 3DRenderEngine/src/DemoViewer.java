import javax.swing.*; //Imports buttons, sliders, etc
import java.awt.*; //Imports graphics for drawing
import java.util.ArrayList; //Imports arraylist
import java.util.List; //Imports list
import java.awt.geom.Path2D; //Imports geometry package

public class DemoViewer {

    public static void main(String[] args) {
        JFrame frame = new JFrame(); //Represents the window in the GUI.
        Container pane = frame.getContentPane(); //This gets the content pane of the Jframe so we can add components to it.
        pane.setLayout(new BorderLayout()); //Sets layout manager to border layout. Divides container into 5 regions. North, South, East, West, and Center.

        //slider to control horizontal rotation.
        JSlider headingSlider = new JSlider(0, 360, 180); //Slider can go from 0 -> 360. Starts at 180 (middle of slider).
        pane.add(headingSlider, BorderLayout.SOUTH); //We add the slider to our pane at the BOTTOM of the pane (SOUTH).

        //slider to control vertical rotation.
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0); //Slider can go from -90 -> 90. Starts at 0 (middle of slider).
        pane.add(pitchSlider, BorderLayout.EAST); //We add the slider to out pane at the RIGHT of the pane (EAST).

        //panel to display render results
        JPanel renderPanel = new JPanel() //Create new panel that will be used to display the rendered 3d image. 
                                          //The {...} part defines an anonymous inner class that overrides the 'paintComponent' method. (alters a method to our liking)
        {
            public void paintComponent(Graphics g) //Will be called whenever the panel needs to be redrawn. The graphics object g is used to perform the drawing.
            {
                Graphics2D g2 = (Graphics2D) g; //Casts Graphics object to Graphics2D which provides more sophisticated control over geomertry,
                                                //coordinate transformations, color management, and text layout.
                g2.setColor(Color.BLACK); //Sets drawing color to black.
                g2.fillRect(0, 0, getWidth(), getHeight()); //Fills the entire panels with a black rectangle, essentially clearing the screen.

                //rendering magic will happen here.
                List<Triangle> tris = new ArrayList<>(); //declares variable 'tris' of type 'List' that will hold objects of type Triangle.
                //This will create an instantce of an arraylist which will store the Triangle objects.
                tris.add(new Triangle(new Vertex(100, 100, 100), //Creates a triangle object. In the declaration we create 3 vertexes with coordinates.
                                      new Vertex(-100, -100, 100),  //tris.add adds the triangle to the arraylist of Triangles.
                                      new Vertex(-100, 100, -100), 
                                      Color.WHITE)); //Sets color of triangle to white.
                tris.add(new Triangle(new Vertex(100, 100, 100), 
                                      new Vertex(-100, -100, 100), 
                                      new Vertex(-100, -100, 100), 
                                      Color.RED));
                tris.add(new Triangle(new Vertex(-100, 100, -100), 
                                      new Vertex(100, -100, -100), 
                                      new Vertex(100, 100, 100), 
                                      Color.GREEN));
                tris.add(new Triangle(new Vertex(-100, 100, -100), 
                                      new Vertex(100, -100, -100), 
                                      new Vertex(-100, -100, 100), 
                                      Color.BLUE));
                //The four triangles put together will form a tetrahedron, a 3D shape with 4 triangular faces.
                //The vertices are chosen so that the shape is centered at the origin '(0, 0, 0)' making it easier to perform rotations.
                //We are using orthographic projection here.
                g2.translate(getWidth() / 2, getHeight() / 2); //This line shifts the origin of the coordinate system from the top left of the screen to the origin (middle)
                //getWidth() returns the width of the drawing in pixels
                //getHeight() returns the height of the drawing in pixels
                //Dividing the 2 gets us the center point.
                //translate method shifts the entire coordinatew system by this amount so (0,0) is now the center of the screen instead of top left corner.
                //This will allow us to see the shape better.
                g2.setColor(Color.WHITE); //Triangles will be drawn in white.
                for (Triangle t : tris) //Starts a loop iterating over each Triangle object in the tris arraylist.
                {
                    Path2D path = new Path2D.Double(); //Creates a Path2D object which will help us outline the triangle.
                    path.moveTo(t.v1.x, t.v1.y); //This moves the pen to the starting point of the triangles first vertex.
                    //moveTo(x,y) sets the starting point for the drawing without drawing anything yet.
                    //The coordinates t.v1.x and t.v1.y get the first vertex x and y of the triangle t.
                    //Since we are using orthographic projection, we dont need z yet.
                    path.lineTo(t.v2.x, t.v2.y); //Draws a straight line from current point to specified (x,y) point. Line from 1 vertex to another.
                    path.lineTo(t.v3.x, t.v3.y); //Draws a line from the second vertex to the third vertex of the triangle.
                    path.closePath(); //Draws a line from the current point (last point) to the first point. Completes the outline for the triangle.
                    g2.draw(path); //Draws the completes path on the screen.
                }
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER); //Adds the render panel to the center of the window.
        frame.setSize(400, 400);
        frame.setVisible(true);


    }
}