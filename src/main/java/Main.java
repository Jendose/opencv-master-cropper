import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

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

    public static void saveFlatCross(Mat image, Quadrangle testQuadrangle, int verticalOrdinal, int horizontalOrdinal, String path){
        Mat resImage = image.clone();
        // Color: BRG
        resImage = Draw.polygonsFlatCross(resImage, testQuadrangle, 20, 42, verticalOrdinal, horizontalOrdinal, new Scalar(255,255,255), new Scalar(0,255,255), 5);
        saveImage(resImage, path);
    }

    public static void saveCross(Mat image, Quadrangle testQuadrangle, int horizontalOrdinal, int verticalOrdinal, String path){
        Mat resImage = image.clone();
        // Color: BRG
        resImage = Draw.polygonsCross(resImage, testQuadrangle, 20, 42, verticalOrdinal, horizontalOrdinal, new Scalar(255,255,255), new Scalar(0,255,255), 5);
        saveImage(resImage, path);
    }

    public static void main(String[] args) {

        Quadrangle verticalObject = new Quadrangle(
                new Point(378, 490),
                new Point(3672, 1405),
                new Point(3154, 2770),
                new Point(783, 1602)
        );

        Mat verticalObjectImageSource = loadImage("./src/main/resources/img/examples/vertical/vertical.jpg");
        Mat verticalObjectImage = verticalObjectImageSource.clone();

        Quadrangle horizontalObject = new Quadrangle(
                new Point(482, 1517),
                new Point(2606, 546),
                new Point(3581, 885),
                new Point(1335, 2542)
        );

        Mat horizontalObjectImageSource = loadImage("./src/main/resources/img/examples/horizontal/horizontal.jpg");
        Mat horizontalObjectImage = horizontalObjectImageSource.clone();



        // Vertical flat
        saveFlatCross(verticalObjectImage, verticalObject, 10, 21,"./src/main/resources/img/examples/vertical/vertical_polygonsFlat-20-42_cross-3.jpg");
        saveFlatCross(verticalObjectImage, verticalObject, 10, 20,"./src/main/resources/img/examples/vertical/vertical_polygonsFlat-20-42_cross-2.jpg");
        saveFlatCross(verticalObjectImage, verticalObject, 9, 19,"./src/main/resources/img/examples/vertical/vertical_polygonsFlat-20-42_cross-1.jpg");
        saveFlatCross(verticalObjectImage, verticalObject, 9, 18,"./src/main/resources/img/examples/vertical/vertical_polygonsFlat-20-42_cross-0.jpg");

        verticalObjectImage = Draw.contour(verticalObjectImage, verticalObject, new Scalar(255,0,77), 5);
        verticalObjectImage = Draw.polygonsFlat(verticalObjectImage, verticalObject, 2, new Scalar(255,255,255), 5, true);

        saveImage(verticalObjectImage, "./src/main/resources/img/examples/vertical/vertical_central-lines_1-incorrect.jpg");
        verticalObjectImage = verticalObjectImageSource.clone();

        // Vertical correct
        saveCross(verticalObjectImage, verticalObject, 21, 10,"./src/main/resources/img/examples/vertical/vertical_polygons-20-42_cross-0.jpg");

        verticalObjectImage = Draw.contour(verticalObjectImage, verticalObject, new Scalar(255,0,77), 5);
        verticalObjectImage = Draw.polygons(verticalObjectImage, verticalObject, 2, new Scalar(255,255,255), 5, true);

        saveImage(verticalObjectImage, "./src/main/resources/img/examples/vertical/vertical_central-lines_2-correct.jpg");
        verticalObjectImage = verticalObjectImageSource.clone();



        // Horizontal flat
        saveFlatCross(horizontalObjectImage, horizontalObject, 10, 21,"./src/main/resources/img/examples/horizontal/horizontal_polygonsFlat-20-42_cross-3.jpg");
        saveFlatCross(horizontalObjectImage, horizontalObject, 10, 22,"./src/main/resources/img/examples/horizontal/horizontal_polygonsFlat-20-42_cross-2.jpg");
        saveFlatCross(horizontalObjectImage, horizontalObject, 11, 23,"./src/main/resources/img/examples/horizontal/horizontal_polygonsFlat-20-42_cross-1.jpg");
        saveFlatCross(horizontalObjectImage, horizontalObject, 11, 24,"./src/main/resources/img/examples/horizontal/horizontal_polygonsFlat-20-42_cross-0.jpg");

        horizontalObjectImage = Draw.contour(horizontalObjectImage, horizontalObject, new Scalar(255,0,77), 5);
        horizontalObjectImage = Draw.polygonsFlat(horizontalObjectImage, horizontalObject, 2, new Scalar(255,255,255), 5, true);

        saveImage(horizontalObjectImage, "./src/main/resources/img/examples/horizontal/horizontal_central-lines_1-incorrect.jpg");
        horizontalObjectImage = horizontalObjectImageSource.clone();

        // Horizontal correct
        saveCross(verticalObjectImage, verticalObject, 21, 10,"./src/main/resources/img/examples/vertical/vertical_polygons-20-42_cross-0.jpg");

        horizontalObjectImage = Draw.contour(horizontalObjectImage, horizontalObject, new Scalar(255,0,77), 5);
        horizontalObjectImage = Draw.polygons(horizontalObjectImage, horizontalObject, 2, new Scalar(255,255,255), 5, true);

        saveImage(horizontalObjectImage, "./src/main/resources/img/examples/horizontal/horizontal_central-lines_2-correct.jpg");
        horizontalObjectImage = horizontalObjectImageSource.clone();
    }
}
