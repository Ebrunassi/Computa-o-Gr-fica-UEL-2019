package br.com.uel;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class InverteImagem {
	public static Mat inverte_cores(Mat og, String path) {
		int rows = og.rows();
        int cols = og.cols();
        System.out.println("invertendo [2] ");
        Mat inv = new Mat(rows,cols,CvType.CV_32FC1);
//        Mat inv = og.clone();

        
        System.out.println(rows + " > " + cols);
        double rgb_inv[] = new double[3];
        
        int i,j = 0;
        for ( i = 0; i < og.height(); i++) {
            for ( j = 0; j < og.width(); j++) {
            	            	
            	double rgb[] = og.get(i, j);
//                System.out.println("1 : " + rgb[0] + "2 : " + rgb[1] + "3 : " + rgb[2]);
            	rgb_inv[0] = 255 - rgb[0] ;
            	rgb_inv[1] = 255 - rgb[1];
            	rgb_inv[2] = 255 - rgb[2];
            	
//            	for(int l = 0 ; l < 3 ; l++)
//            		System.out.println(rgb_inv[0] + " > " + rgb_inv[l] + " > " +rgb_inv[2] );
                inv.put(i, j, rgb_inv);


                          
            }
        }
//        System.out.println(inv.dump());
//        System.out.println(i + " > " + j);
//        ShowWindow.showWindow("teste",inv);
//        return inv;
        
//        System.out.println("rows : " + og.rows() + "cols : " + og.cols());
//        System.out.println("invertendo [3] ");
//        Imgcodecs.imwrite(path, inv);
//        System.out.println(path);
        return inv;
	}
}
