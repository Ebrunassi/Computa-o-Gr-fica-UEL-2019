package br.com.uel;

import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Histograma {
/* Funções para a imagem cinza ----------------------------------------------------------- */
	
	/*	Recebe a imagem cinza como parâmetro, e conta em um vetor de 255 posições
	 * quantas vezes cada pixel aparece 
	 * RETORNO : um vetor de 255 elementos com o número de vezes que cada pixel aparece 
	 * em seu respectivo índice
	 */
	public static double[] vetor_histograma(Mat im_cinza) {
		double histograma[] = new double[255];
		for(int i = 0 ; i < im_cinza.height(); i++) {
			for(int j = 0 ; j < im_cinza.width(); j++) {
				double []cin = im_cinza.get(i, j);
				histograma[(int) cin[0]]++;
				
			}
		}
		return histograma;
	}
	
	/* Transforma o vetor de contagem de pixels, em um vetor semelhante, mas que mostra quantos % tem
	 * de cada pixel na imagem
	 * 
	 * Fórmula : (nº de vezes que o pixel aparece / tamanho da imagem)
	 * 
	 * RETORNO : um vetor contendo a % dos pixels
	 * 
	 */
	public static double[] calcula_porcentagem_pixels(double[] histograma, int tamanho_imagem) {
		
		for(int i = 0 ; i < 255 ; i++) {
			histograma[i] = histograma[i] / tamanho_imagem;
		}
		return histograma;
	}
	
	/* Pega o vetor de % de pixels e transforma em um outro vetor contendo a soma das %.
	 * 
	 * RETORNO : Um vetor no qual, de acordo com o crescimento dos índices, soma com as %
	 * de índices inferiores
	 * 
	 */
	public static double[] calcula_soma_porcentagem_pixels(double[] histograma) {
		for(int i = 1 ; i < 255 ; i++) {
			histograma[i] = histograma[i] + histograma[i-1];
		}
		return histograma;
	}
	
	/* Transforma o vetor que contém o somatório das probabilidades em um vetor que contem uma cor
	 *  
	 *  RETORNO : Vetor que contém cores
	 */
	public static double[] transforma_em_cor(double[] histograma) {
		
		for(int i = 0 ; i < 255 ; i++) {
			histograma[i] = histograma[i] * 255;
		}
		
		return histograma;
	}
	
	/* Atribui as cores contidas no histograma à imagem
	 *  
	 *  RETORNO : Imagem contrastada
	 */
	public static Mat atribui_cor_imagem(double[] histograma, Mat im, Mat contraste) {
		
		for(int i = 0 ; i < im.height() ; i++) {
			for(int j = 0 ; j < im.width() ; j++) {
				
				double []rgb = im.get(i, j);
				rgb[0] = histograma[(int) rgb[0]];
				rgb[1] = histograma[(int) rgb[1]];
				rgb[2] = histograma[(int) rgb[2]];
				contraste.put(i, j, rgb);
			}
		}
		return contraste;
	}
	
	
	/* Funções para a imagem colorida --------------------------------------------------------- */
	public static double[] gera_vetor_colorido(Mat img_yuv, int rows, int cols) {
		double[] histograma_color = new double[256];
			
		// Contando as cores
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				double[] yuv = img_yuv.get(i,j);				
				histograma_color[(int)yuv[0]]++;	
			}
		}			
		return histograma_color;
		
	}
	
	public static double[] calcula_porcentagem_pixels_colorido(double[] histograma_color, int N) {
		for(int i = 0; i < 256; i++) {
			histograma_color[i] = histograma_color[i]/N; 
		}

		return histograma_color;
	}
	
	public static double[] calcula_soma_porcentagem_pixels_colorido(double []histograma_color) {
		for(int i = 1; i < 256; i++) {
			histograma_color[i] = histograma_color[i] + histograma_color[i-1];
		}
		return histograma_color;
	}
	
	public static double[] transforma_em_cor_colorido(double[] histograma_color) {
		for(int i = 0; i < 256; i++) {
			histograma_color[i] = histograma_color[i] * 255;
		}
		return histograma_color;
	}
	
	public static Mat atribui_cor_imagem_colorido(Mat img, double[] histograma_color, int rows, int cols) {
		Mat newImage = new Mat(rows, cols, img.type());
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				
				double[] yuv = img.get(i,j);
				yuv[0] = histograma_color[(int) yuv[0]];
				newImage.put(i,j, yuv);	
			}
		}
		return newImage;
	}
	
	
	
	public static Mat contrastePretoBranco(Mat im, String path) {
		Mat im_cinza = im.clone();
		double histograma[] = new double[255];
		
		int tamanho_imagem = im.height() * im.width();
		Mat contraste = new Mat(im.height(), im.width(), im.type());

		
		Imgproc.cvtColor(im, im_cinza, Imgproc.COLOR_RGB2GRAY);

		histograma = vetor_histograma(im_cinza);	// Pega o vetor com a contagem dos pixels
		histograma = calcula_porcentagem_pixels(histograma,tamanho_imagem);	// Pega o vetor com as porcentagens dos pixels
		histograma = calcula_soma_porcentagem_pixels(histograma);
		histograma = transforma_em_cor(histograma);
		contraste = atribui_cor_imagem(histograma,im,contraste);			
			
		System.out.println("FEITO!");
		return contraste;
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\nascimento_contraste.png", contraste);
	}
	
	public static Mat contrasteColorido(Mat im, String path) {
		Mat img_colorida = im.clone();
		Mat img_colorida_contrastada = im.clone();
				
		// Transforma a imagem em YUV
		Imgproc.cvtColor(im, img_colorida, Imgproc.COLOR_BGR2YUV);
		
		int rows = img_colorida.rows(); 
		int cols = img_colorida.cols();
		int N = cols*rows;
		
		
		double[] histograma_color = gera_vetor_colorido(img_colorida, rows, cols);			
		histograma_color = calcula_porcentagem_pixels_colorido(histograma_color,N);
		histograma_color = calcula_soma_porcentagem_pixels_colorido(histograma_color);
		histograma_color = transforma_em_cor_colorido(histograma_color);
		
		img_colorida = atribui_cor_imagem_colorido(img_colorida, histograma_color, rows, cols);
		
		
		//transformando img em rgb
		Imgproc.cvtColor(img_colorida, img_colorida_contrastada, Imgproc.COLOR_YUV2BGR);
		
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\nascimento_contraste.png", img_colorida_contrastada);
		System.out.println("Feito!");
		return img_colorida_contrastada;
	}
}
