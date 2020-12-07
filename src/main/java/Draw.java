import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Draw {

    public static void line(Mat image, Point point1, Point point2, Scalar color, int thickness){
        Imgproc.line(image, point1, point2, color, thickness);
    }

    public static void contour(Mat image, Quadrangle quadrangle, Scalar color, int thickness){
        Point topLeft = quadrangle.getTopLeft();
        Point topRight = quadrangle.getTopRight();
        Point bottomRight = quadrangle.getBottomRight();
        Point bottomLeft = quadrangle.getBottomLeft();

        Imgproc.line(image, topLeft, topRight, color, thickness);
        Imgproc.line(image, topRight, bottomRight, color, thickness);
        Imgproc.line(image, bottomRight, bottomLeft, color, thickness);
        Imgproc.line(image, bottomLeft, topLeft, color, thickness);
    }

    public static void polygons(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, Scalar color, int thickness, boolean noContour){
        if(!noContour){
            contour(image, quadrangle, color, thickness);
        }
        int verticalLines = verticalNumber < 1? 0 : verticalNumber-1;
        int horizontalLines = horizontalNumber < 1? 0 : horizontalNumber-1;

        for (int i = 1; i<=verticalLines; i++){
            double l = (double)i/(verticalLines-i+1);
            Imgproc.line(image,
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getTopLeft().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getTopLeft().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomRight().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getBottomRight().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    color, thickness);
        }
        for (int i = 1; i<=horizontalLines; i++){
            double l = (double)i/(horizontalLines-i+1);
            Imgproc.line(image,
                    new Point(
                            (quadrangle.getTopLeft().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getTopLeft().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getBottomRight().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getBottomRight().y*l))/(1+l)),
                    color, thickness);
        }
    }

    public static void polygons(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, Scalar color, int thickness){
        polygons(image, quadrangle, verticalNumber, horizontalNumber, color, thickness, false);
    }

    public static void polygons(Mat image, Quadrangle quadrangle, int number, Scalar color, int thickness, boolean noContour){
        polygons(image, quadrangle, number, number, color, thickness, noContour);
    }

    public static void polygons(Mat image, Quadrangle quadrangle, int number, Scalar color, int thickness){
        polygons(image, quadrangle, number, number, color, thickness, false);
    }

    public static void polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, int crossThickness, boolean noContour){
        if(!noContour){
            contour(image, quadrangle, mainColor, mainThickness);
        }
        int verticalLines = verticalNumber < 1? 0 : verticalNumber-1;
        int horizontalLines = horizontalNumber < 1? 0 : horizontalNumber-1;

        for (int i = 1; i<=verticalLines; i++){
            double l = (double)i/(verticalLines-i+1);
            Imgproc.line(image,
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getTopLeft().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getTopLeft().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomRight().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getBottomRight().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    i==verticalOrdinal?crossColor:mainColor, i==verticalOrdinal?crossThickness:mainThickness);
        }
        for (int i = 1; i<=horizontalLines; i++){
            double l = (double)i/(horizontalLines-i+1);
            Imgproc.line(image,
                    new Point(
                            (quadrangle.getTopLeft().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getTopLeft().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getBottomRight().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getBottomRight().y*l))/(1+l)),
                    i==horizontalOrdinal?crossColor:mainColor, i==horizontalOrdinal?crossThickness:mainThickness);
        }
    }

    public static void polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, int crossThickness){
        polygonsCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, crossThickness, false);
    }

    public static void polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, boolean noContour){
        polygonsCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, mainThickness*3, noContour);
    }

    public static void polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness){
        polygonsCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, mainThickness*3, false);
    }

}
