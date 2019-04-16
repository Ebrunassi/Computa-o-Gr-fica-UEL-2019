import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {
	
	public static boolean comparaVetor(double[] rgb, double[] rgb1) {
		if(rgb[0] == rgb1[0] && rgb[1] == rgb1[1] && rgb[2] == rgb1[2])
			return true;
		return false;
	}
	public static void operacaoOU(Mat im1, Mat im2){
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
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\operacaoOU.png", im_dest);
	}
	public static void operacaoE(Mat im1, Mat im2){
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
	}

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im1 = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\im1.png");
		Mat im2 = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\im2.png");
	    
		System.out.println("Transforma��o E e OU ");

		operacaoE(im1,im2);
		operacaoOU(im1,im2);
		System.out.println("Feito!");
	}
}
