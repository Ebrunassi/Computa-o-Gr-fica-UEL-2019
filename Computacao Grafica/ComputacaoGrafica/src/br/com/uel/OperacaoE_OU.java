package br.com.uel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class OperacaoE_OU {
	public static boolean comparaVetor(double[] rgb, double[] rgb1) {
		if(rgb[0] == rgb1[0] && rgb[1] == rgb1[1] && rgb[2] == rgb1[2])
			return true;
		return false;
	}
	public static Mat operacaoOU(Mat im1, Mat im2){
		double[] rgbB = {255,255,255};
		Mat im_dest = im1.clone();
		
		for(int i = 0; i < im1.height(); i++) {
			for(int j = 0; j < im1.width(); j++) {
				double []rgb1 = im1.get(i, j);
				double []rgb2 = im2.get(i, j);
				if(comparaVetor(rgb1,rgbB) == false) {
					im_dest.put(i,j, rgb1);
				}
				if(comparaVetor(rgb2,rgbB) == false){
					im_dest.put(i,j, rgb2);
				}
			}
		}
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\operacaoOU.png", im_dest);
		return im_dest;
	}
	public static Mat operacaoE(Mat im1, Mat im2){
		double[] rgbB = {255,255,255};
		Mat im_dest = im1.clone();
		
		for(int i = 0; i < im1.height(); i++) {
			for(int j = 0; j < im1.width(); j++) {
				double []rgb1 = im1.get(i, j);
				double []rgb2 = im2.get(i, j);
				if(comparaVetor(rgb1,rgb2) == true) {
					im_dest.put(i,j, rgb1);
				}else {
					im_dest.put(i,j,rgbB);
				}
				
			}
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\operacaoE.png", im_dest);
		return im_dest;
	}
}
