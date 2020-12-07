import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static {
        OpenCV.loadShared();
    }

    public static Mat loadImage(String imagePath) {
        return Imgcodecs.imread(imagePath);
    }

    public static void saveImage(Mat imageMatrix, String targetPath) {
        Imgcodecs.imwrite(targetPath, imageMatrix);
    }

    public static void saveVariant(Mat input, Quadrangle testQuadrangle, int horizontalOrdinal, int verticalOrdinal, int testNumber){
        Mat resImage = input.clone();
        Draw.contour(resImage, testQuadrangle, new Scalar(255,0,77), 5);
        Draw.polygonsCross(resImage, testQuadrangle, 20, 42, verticalOrdinal, horizontalOrdinal, new Scalar(255,255,255), new Scalar(0,255,255), 5);
        saveImage(resImage, "./src/main/resources/img/test_opencv_poligons-20-42+"+ testNumber +".jpg");
    }

    public static void main(String[] args) {

        Quadrangle testQuadrangle = new Quadrangle(
                new Point(378, 490),
                new Point(3672, 1405),
                new Point(3154, 2770),
                new Point(783, 1602)
        );

        Mat loadedImage = loadImage("./src/main/resources/img/test.jpg");
        Mat newImage = loadedImage.clone();
                                                       // brg
        Draw.contour(newImage, testQuadrangle, new Scalar(255,0,77), 5);
//        Draw.polygons(newImage, testQuadrangle, 20, 42, new Scalar(255,255,255), 5, true);

        saveVariant(newImage, testQuadrangle, 21, 10,0);
        saveVariant(newImage, testQuadrangle, 20, 10,1);
        saveVariant(newImage, testQuadrangle, 19, 9,2);
        saveVariant(newImage, testQuadrangle, 18, 9,3);
    }
}
