import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Draw {

    public static Mat line(Mat image, Point point1, Point point2, Scalar color, int thickness){
        Mat resImage = image.clone();
        Imgproc.line(resImage, point1, point2, color, thickness);
        return resImage;
    }

    public static Mat contour(Mat image, Quadrangle quadrangle, Scalar color, int thickness){
        Mat resImage = image.clone();

        Point topLeft = quadrangle.getTopLeft();
        Point topRight = quadrangle.getTopRight();
        Point bottomRight = quadrangle.getBottomRight();
        Point bottomLeft = quadrangle.getBottomLeft();

        Imgproc.line(resImage, topLeft, topRight, color, thickness);
        Imgproc.line(resImage, topRight, bottomRight, color, thickness);
        Imgproc.line(resImage, bottomRight, bottomLeft, color, thickness);
        Imgproc.line(resImage, bottomLeft, topLeft, color, thickness);

        return resImage;
    }

    // polygons строят секту прямоугольного объекта по законам перспективы

    public static Mat polygons(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, Scalar color, int thickness, boolean noContour){
        Mat resImage = image.clone();

        if(!noContour){
            contour(resImage, quadrangle, color, thickness);
        }

        Point A = quadrangle.getTopLeft();
        Point B = quadrangle.getTopRight();
        Point C = quadrangle.getBottomRight();
        Point D = quadrangle.getBottomLeft();

        // Деление объекта на сегменты по вертикали происходит из точки пересечения двух прямых:
        // 1) Проходящей через точки A, B
        // 2) Проходящей через точки D, C

        // Угловой коэффициент AB
        double k_AB = (B.y-A.y)/(B.x-A.x);
        // Свободный член
        double b_AB = A.y-((A.x*(B.y-A.y))/(B.x-A.x));

        // Угловой коэффициент DC
        double k_DC = (C.y-D.y)/(C.x-D.x);
        // Свободный член
        double b_DC = D.y-((D.x*(C.y-D.y))/(C.x-D.x));

        // Точка пересечения AB и DC
        double point_AB_DC_x = (b_AB-b_DC)/(k_DC-k_AB);
        Point point_AB_DC = new Point(point_AB_DC_x,k_AB*point_AB_DC_x+b_AB);

        // Тангенс угла между AB и DC
        double tg_AB_DC = (k_DC-k_AB)/(1+k_AB*k_DC);

        // Угол между AB и DC - используется для вычисления угла, равного 1/verticalNumber части данного (шага по вертикали)
        double angle_AB_DC = Math.atan(tg_AB_DC);

        // Деление объекта на сегменты по горизонтали происходит из точки пересечения двух прямых:
        // 1) Проходящей через точки A, D
        // 2) Проходящей через точки B, C

        // Угловой коэффициент AD
        double k_AD = (D.y-A.y)/(D.x-A.x);
        // Свободный член
        double b_AD = A.y-((A.x*(D.y-A.y))/(D.x-A.x));

        // Угловой коэффициент BC
        double k_BC = (C.y-B.y)/(C.x-B.x);
        // Свободный член
        double b_BC = B.y-((B.x*(C.y-B.y))/(C.x-B.x));

        // Точка пересечения AD и BC
        double point_AD_BC_x = (b_AD-b_BC)/(k_BC-k_AD);
        Point point_AD_BC = new Point(point_AD_BC_x,k_AD*point_AD_BC_x+b_AD);

        // Тангенс угла между AD и BC
        double tg_AD_BC = (k_BC-k_AD)/(1+k_AD*k_BC);

        // Угол между AD и BC - используется для вычисления угла, равного 1/horizontalNumber части данного (шага по горизонтали)
        double angle_AD_BC = Math.atan(tg_AD_BC);

        int widthLines = verticalNumber < 1? 0 : verticalNumber-1;
        int heightLines = horizontalNumber < 1? 0 : horizontalNumber-1;

//        for (int i = 1; i<=widthLines; i++){
//            double l = (double)i/(widthLines-i+1);
//            Imgproc.line(resImage,
//                    new Point(
//                            (quadrangle.getBottomLeft().x+(quadrangle.getTopLeft().x*l))/(1+l),
//                            (quadrangle.getBottomLeft().y+(quadrangle.getTopLeft().y*l))/(1+l)),
//                    new Point(
//                            (quadrangle.getBottomRight().x+(quadrangle.getTopRight().x*l))/(1+l),
//                            (quadrangle.getBottomRight().y+(quadrangle.getTopRight().y*l))/(1+l)),
//                    color, thickness);
//        }
//        for (int i = 1; i<=heightLines; i++){
//            double l = (double)i/(heightLines-i+1);
//            Imgproc.line(resImage,
//                    new Point(
//                            (quadrangle.getTopLeft().x+(quadrangle.getTopRight().x*l))/(1+l),
//                            (quadrangle.getTopLeft().y+(quadrangle.getTopRight().y*l))/(1+l)),
//                    new Point(
//                            (quadrangle.getBottomLeft().x+(quadrangle.getBottomRight().x*l))/(1+l),
//                            (quadrangle.getBottomLeft().y+(quadrangle.getBottomRight().y*l))/(1+l)),
//                    color, thickness);
//        }

        return resImage;
    }

    public static Mat polygons(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, Scalar color, int thickness){
        return polygons(image, quadrangle, verticalNumber, horizontalNumber, color, thickness, false);
    }

    public static Mat polygons(Mat image, Quadrangle quadrangle, int number, Scalar color, int thickness, boolean noContour){
        return polygons(image, quadrangle, number, number, color, thickness, noContour);
    }

    public static Mat polygons(Mat image, Quadrangle quadrangle, int number, Scalar color, int thickness){
        return polygons(image, quadrangle, number, number, color, thickness, false);
    }

    // polygonsCross позволяют выделить пересечение двух линий на сетке polygons

    public static Mat polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, int crossThickness, boolean noContour){
        Mat resImage = image.clone();

        if(!noContour){
            contour(resImage, quadrangle, mainColor, mainThickness);
        }
        int widthLines = verticalNumber < 1? 0 : verticalNumber-1;
        int heightLines = horizontalNumber < 1? 0 : horizontalNumber-1;

        for (int i = 1; i<=widthLines; i++){
            double l = (double)i/(widthLines-i+1);
            Imgproc.line(resImage,
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getTopLeft().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getTopLeft().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomRight().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getBottomRight().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    i==verticalOrdinal?crossColor:mainColor, i==verticalOrdinal?crossThickness:mainThickness);
        }
        for (int i = 1; i<=heightLines; i++){
            double l = (double)i/(heightLines-i+1);
            Imgproc.line(resImage,
                    new Point(
                            (quadrangle.getTopLeft().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getTopLeft().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getBottomRight().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getBottomRight().y*l))/(1+l)),
                    i==horizontalOrdinal?crossColor:mainColor, i==horizontalOrdinal?crossThickness:mainThickness);
        }

        return resImage;
    }

    public static Mat polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, int crossThickness){
        return polygonsCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, crossThickness, false);
    }

    public static Mat polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, boolean noContour){
        return polygonsCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, mainThickness*3, noContour);
    }

    public static Mat polygonsCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness){
        return polygonsCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, mainThickness*3, false);
    }



    //----------------------------------------------------------------------------------------------------------------//
    // polygonsFlat строят сетку, разделяя стороны четырехугольника на отрезки

    public static Mat polygonsFlat(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, Scalar color, int thickness, boolean noContour){
        Mat resImage = image.clone();

        if(!noContour){
            contour(resImage, quadrangle, color, thickness);
        }
        int widthLines = verticalNumber < 1? 0 : verticalNumber-1;
        int heightLines = horizontalNumber < 1? 0 : horizontalNumber-1;

        for (int i = 1; i<=widthLines; i++){
            double l = (double)i/(widthLines-i+1);
            Imgproc.line(resImage,
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getTopLeft().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getTopLeft().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomRight().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getBottomRight().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    color, thickness);
        }
        for (int i = 1; i<=heightLines; i++){
            double l = (double)i/(heightLines-i+1);
            Imgproc.line(resImage,
                    new Point(
                            (quadrangle.getTopLeft().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getTopLeft().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getBottomRight().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getBottomRight().y*l))/(1+l)),
                    color, thickness);
        }

        return resImage;
    }

    public static Mat polygonsFlat(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, Scalar color, int thickness){
        return polygonsFlat(image, quadrangle, verticalNumber, horizontalNumber, color, thickness, false);
    }

    public static Mat polygonsFlat(Mat image, Quadrangle quadrangle, int number, Scalar color, int thickness, boolean noContour){
        return polygonsFlat(image, quadrangle, number, number, color, thickness, noContour);
    }

    public static Mat polygonsFlat(Mat image, Quadrangle quadrangle, int number, Scalar color, int thickness){
        return polygonsFlat(image, quadrangle, number, number, color, thickness, false);
    }

    // polygonsFlatCross позволяют выделить пересечение двух линий на сетке polygonsFlat

    public static Mat polygonsFlatCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, int crossThickness, boolean noContour){
        Mat resImage = image.clone();

        if(!noContour){
            contour(resImage, quadrangle, mainColor, mainThickness);
        }
        int widthLines = verticalNumber < 1? 0 : verticalNumber-1;
        int heightLines = horizontalNumber < 1? 0 : horizontalNumber-1;

        for (int i = 1; i<=widthLines; i++){
            double l = (double)i/(widthLines-i+1);
            Imgproc.line(resImage,
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getTopLeft().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getTopLeft().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomRight().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getBottomRight().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    i==verticalOrdinal?crossColor:mainColor, i==verticalOrdinal?crossThickness:mainThickness);
        }
        for (int i = 1; i<=heightLines; i++){
            double l = (double)i/(heightLines-i+1);
            Imgproc.line(resImage,
                    new Point(
                            (quadrangle.getTopLeft().x+(quadrangle.getTopRight().x*l))/(1+l),
                            (quadrangle.getTopLeft().y+(quadrangle.getTopRight().y*l))/(1+l)),
                    new Point(
                            (quadrangle.getBottomLeft().x+(quadrangle.getBottomRight().x*l))/(1+l),
                            (quadrangle.getBottomLeft().y+(quadrangle.getBottomRight().y*l))/(1+l)),
                    i==horizontalOrdinal?crossColor:mainColor, i==horizontalOrdinal?crossThickness:mainThickness);
        }

        return resImage;
    }

    public static Mat polygonsFlatCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, int crossThickness){
        return polygonsFlatCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, crossThickness, false);
    }

    public static Mat polygonsFlatCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness, boolean noContour){
        return polygonsFlatCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, mainThickness*3, noContour);
    }

    public static Mat polygonsFlatCross(Mat image, Quadrangle quadrangle, int verticalNumber, int horizontalNumber, int verticalOrdinal, int horizontalOrdinal, Scalar mainColor, Scalar crossColor, int mainThickness){
        return polygonsFlatCross(image, quadrangle, verticalNumber, horizontalNumber, verticalOrdinal, horizontalOrdinal, mainColor, crossColor, mainThickness, mainThickness*3, false);
    }

}
