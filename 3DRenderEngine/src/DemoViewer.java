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
          

                //Heading Slider (bottom slider): Controls rotation in XZ plane (left-right)
                //Pitch Slider (right side slider): Controls rotation in YZ plane (up-down)
                double heading = Math.toRadians(headingSlider.getValue()); //This line reads the current value of 'headingSlider' which controls left-right rotation.
                //The 'getValue' method retrieves the slider's current value, which is in degrees.
                //'Math.toRadians' converts the degree value into radians since trig functions use radians.
                Matrix3 headingTransform = new Matrix3(new double[] { //initializes a new rotation matrix. The values of the matrix correspond to the XZ rotation matrix.
                    Math.cos(heading), 0, Math.sin(heading),
                    0, 1, 0,
                    -Math.sin(heading), 0, Math.cos(heading)
                }); //This matrix handles the rotation of the Y-acis, which affects the X and Z coordinates while leaving the Y unchanged.

                double pitch = Math.toRadians(pitchSlider.getValue()); //This line reads the current value of 'pitchSlider' which controls the up-down rotation.
                Matrix3 pitchTransform = new Matrix3(new double[] { //Initializes a new rotation matrix. The values of the matrix correspond to the YZ rotation matrix.
                    1, 0, 0,
                    0, Math.cos(pitch), Math.sin(pitch),
                    0, -Math.sin(pitch), Math.cos(pitch)
                });

                Matrix3 transform = headingTransform.multiply(pitchTransform); //Creates a matrix named transform which represents the two matrices being multiplied by eachother. (headingTransform * pitchTransform)
                //This new 'transform' matrix applies both XZ and YZ rotation matrices into once matrix. 



                g2.setColor(Color.WHITE); //Sets color of lines to be drawn white.
                for (Triangle t : tris)
                {
                    Vertex v1 = transform.transform(t.v1); //Applies rotation matrix to first vertex 'v1' of triangle 't', transforming it according to the current rotation of angle ('heading')
                    Vertex v2 = transform.transform(t.v2); //same as above, transforms vertex into newly rotated vertex.
                    Vertex v3 = transform.transform(t.v3);
                    Path2D path = new Path2D.Double(); //Creates a new Path2D object to draw the triangle.
                    path.moveTo(v1.x, v1.y); //Moves to the first new vertex that was just created.
                    path.lineTo(v2.x, v2.y); //Draws a line to the second vertex. (we are at the first vertex rn)
                    path.lineTo(v3.x, v3.y); //Draws a line to third vertex.
                    path.closePath(); //Closes the path, forming the triangle. Essentially this draws the line from the current point to the first point, completing the triangle.
                    g2.draw(path); //Draws the trangle outline on the screen.
                }
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER); //Adds the render panel to the center of the window.

        //A change listener in java is a type of event listener that responds to changes in a component's state. In this case, it's used with sliders to detect when the slider's value changes.
        headingSlider.addChangeListener(e -> renderPanel.repaint()); //headingSlider is the slider that controls the left right rotation. 'addChangeListener' attaches a change listener to this slider.
        //Whenever the slider's value changes, the code inside the listener will be executed.
        //'e -> renderPanel.repaint' is a lambda expression. Its a short way of writing a method that takes an event 'e' as input and then calls 'renderPanel.repaint()'.
        //'e' is the event object representing the change event (the slider being moved).
        //'renderPanel.repaint()' is a method that redraws the renderpanel (everything displayed on screen). This will redraw the screen with the new vertices being applied.
        pitchSlider.addChangeListener(e -> renderPanel.repaint());

        frame.setSize(400, 400);
        frame.setVisible(true);


    }
}
