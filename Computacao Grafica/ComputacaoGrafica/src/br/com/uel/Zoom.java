package br.com.uel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Zoom {
	
	private Mat im = null;
	
	public Zoom (Mat imagem) {
		this.im = imagem;
	}
	
	/*	Funções auxiliares para os Zoons Lineares*/
	public static double [] Soma_zoom(double []rgb, double []rgb2) {
		rgb[0] = (rgb[0] + rgb2[0]) / 2;
		rgb[1] = (rgb[1] + rgb2[1]) / 2;
		rgb[2] = (rgb[2] + rgb2[2]) / 2;
		return rgb;
	}
	public static double [] Divide_zoom(double []rgb, double []rgb2, int grau_divisao) {
		rgb[0] = (rgb[0] + rgb2[0]) / grau_divisao;
		rgb[1] = (rgb[1] + rgb2[1]) / grau_divisao;
		rgb[2] = (rgb[2] + rgb2[2]) / grau_divisao;
		return rgb;
	}
	
	/*	Quadrado	*/
	public static Mat zoom_in_quadrado(Mat im, String path) {
		int rows = im.rows();
		int cols = im.cols();
		
		Mat zi = new Mat(rows * 2, cols * 2, im.type());
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < cols ; j++) {
				double []rgb = im.get(i, j);

				zi.put(2 * i ,  2 * j , rgb);
                zi.put(2 * i , (2*j) + 1 , rgb);
                zi.put((2*i) + 1, 2 * j, rgb);
                zi.put((2*i) + 1, 2 * j + 1, rgb);
			}
		}
		System.out.println("Zoom in quadrado!");
		return zi;
//		Imgcodecs.imwrite(path, zi);
		
	}
	public static Mat zoom_out_quadrado(Mat im, String path) {
		int rows = im.rows();
		int cols = im.cols();
		
		Mat zi = new Mat(rows / 2, cols / 2, im.type());
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < cols ; j++) {
				
				double []rgb = im.get(i, j);
				
				zi.put(i/2,j/2, rgb);
				j++;
			}
			i++;
		}
		System.out.println("Zoom out quadrado!");
		return zi;
//		Imgcodecs.imwrite(path, zi);  
		
	}
	
	/*	Linear	*/
	public static Mat zoom_in(Mat im, String path) {
		int rows = im.rows();
		int cols = im.cols();
		
		Mat zi = new Mat(rows*2, cols*2, im.type());
		
		for(int i = 0; i < rows ; i++) {
			for(int j = 0 ; j < cols ; j++) {
				
				if(i < rows-1 && j < cols-1) {
					
					// Direita
					double []rgb = im.get(i, j);
					double []rgb2 = im.get(i, j+1);
					zi.put(i*2, j*2, rgb);
					rgb = Soma_zoom(rgb,rgb2);
					zi.put(i*2, j*2+1, rgb);
					
					// Baixo
					rgb = im.get(i, j);
					rgb2 = im.get(i+1, j);
					rgb = Soma_zoom(rgb,rgb2);
					zi.put(i*2+1, j*2, rgb);
					
					// "Diagonal"
					rgb = zi.get(i*2, j*2);
					rgb2 = zi.get(i*2, j*2+1);
					rgb = Soma_zoom(rgb,rgb2);
					zi.put(i*2+1, j*2+1, rgb);		
				}
				
			}
		}
		System.out.println("Zoom in linear!");
		return zi;
//		Imgcodecs.imwrite(path, zi);
	}
	public static Mat zoom_out(Mat im, String path) {
		int rows = im.rows();
		int cols = im.cols();
		
		Mat zo = new Mat(rows/2, cols/2, im.type());
		
		for(int i = 0 ; i < rows-1 ; i++) {
			for(int j = 0 ; j < cols-1 ; j++) {
			
				double []rgb = im.get(i, j);
				double []rgb2 = im.get(i, j+1);	// Direita					
				rgb = Soma_zoom(rgb,rgb2);
				rgb2 = im.get(i+1, j);	// Baixo
				rgb = Soma_zoom(rgb,rgb2);
				rgb2 = im.get(i+1, j+1);	// Diagonal
				rgb = Soma_zoom(rgb,rgb2);
				zo.put(i/2, j/2, rgb);	
				
			}
		}
		System.out.println("Zoom out linear!");
		return zo;
//		Imgcodecs.imwrite(path, zo);		
	}
}
