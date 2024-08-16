
//Matrices are a powerful tool in 3D graphics because they allow us to perform transformations in Computer Graphics like translation, scaling, and rotation on points in 3d space.
                                                                                                                                                                                                                                                                                                                                                                                                                                  
public class Matrix3 { //Will represent a 3x3 matrix

    double[] values; //array of double(decimal) values that will store the 9 elements of the 3x3 matrix in a flat, 1D array.

    Matrix3(double[] values) //contructor that takes an array of doubles values as input and assigns it to the 'values' attribute of the class. Array represents matrix elements.
    {
        this.values = values;
    }

    Matrix3 multiply(Matrix3 other) //This method multiplies the current matrix 'this' by another matrix 'other' and returns a new 'Matrix3' object, representing the result.
    {
        double[] result = new double[9]; //Creates a new array 'result' of decimal values to store the 9 elements of the resulting matrix after multiplication.

        for (int row = 0; row < 3; row++) //iterates over rows of matrix
        {
            for (int col = 0; col < 3; col++) //iterates over columns
            {
                for (int i = 0; i < 3; i++) //Handles the dot product betweent he corresponding row from the first matrix and the column from the second matrix.
                {
                    result[row * 3 + col] += this.values[row * 3 + i] * other.values[i * 3 + col]; //This line preforms the actual matrix multiplication.
                    //this.values[row * 3 + i] selects the appropriate element from the row of the first matrix.
                    //other.values[i * 3 + col] selects the appropriate element from the column of the second matrix.
                    //result[row * 3 + col] The result of the multiplication is added to the corresponding position in the new 'result' array.
                    //The 'result' array is the new matrix we are making by multiplying the two already existing matrices.
                }
            }
        }
        return new Matrix3(result);
    }

    //This method takes a 'Vertex' object 'in' as input and returns a new 'Vertex' object representing the transformed point after applying the matrix to it. 
    Vertex transform(Vertex in) //It essentially gives us the new vertex point when we rotate it using a rotation matrix.                              
    {
        return new Vertex( //A new vertex object is created and returned. The coordinates of the new vertex are calculated by multiplying the original
                           //vertex coordinates by the matrix elements.
            in.x * values[0] + in.y * values[3] + in.z * values[6], //This calculates the new 'x' coordinate of the transformed vertex.
            in.x * values[1] + in.y * values[4] + in.z * values[7], 
            in.x * values[2] + in.y * values[5] + in.z * values[8]
            );
    }
}
