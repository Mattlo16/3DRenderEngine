import javax.swing.*; //Imports buttons, sliders, etc
import java.awt.*; //Imports graphics for drawing

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
                //list tris add next time!
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER); //Adds the render panel to the center of the window.
        frame.setSize(400, 400);
        frame.setVisible(true);


    }
}