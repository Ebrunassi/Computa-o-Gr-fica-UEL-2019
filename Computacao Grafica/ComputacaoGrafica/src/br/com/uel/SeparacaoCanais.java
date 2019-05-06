package br.com.uel;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class SeparacaoCanais {
	public static int calculaMedia(double[] rgb) {
		double media = (rgb[0] + rgb[1] + rgb[2])/3;
		return (int) media;
	}
	public static Mat imCinza(Mat im) {
		Mat imC = im.clone();
		for(int i = 0 ; i < im.height(); i++) {
			for(int j = 0; j < im.width(); j++) {
				double []rgb = im.get(i, j);
				int media = calculaMedia(rgb);
				rgb[0] = media;
				rgb[1] = media;
				rgb[2] = media;
				imC.put(i,j, rgb);
			}
		}
		return imC;
	}
	public static void separaCanais(Mat im) {
		Mat imR = new Mat(im.rows() , im.cols() , CvType.CV_8UC1);
		Mat imG = new Mat(im.rows() , im.cols() , CvType.CV_8UC1);
		Mat imB = new Mat(im.rows() , im.cols() , CvType.CV_8UC1);
//		imR = imCinza(im);
		for(int i = 0 ; i < im.height(); i++) {
			for(int j = 0; j < im.width(); j++) {
				double []rgb = im.get(i, j);
//				int mediaR = calculaMedia(rgb);				
				
				imB.put(i,j, rgb[0]);
				imG.put(i,j, rgb[1]);
				imR.put(i,j, rgb[2]);
				
			}
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\lady_blue.jpg", imB);
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\lady_green.jpg", imG);
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\lady_red.jpg", imR);
	}
}
