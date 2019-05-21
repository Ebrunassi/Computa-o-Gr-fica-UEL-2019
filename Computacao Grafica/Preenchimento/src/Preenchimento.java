import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Preenchimento {
	
	public static boolean comparaCor(double []corImagem, double[] corComparacao, int limiar) {
		if(	(corImagem[0] >= corComparacao[0] - limiar) && (corImagem[0] <= corComparacao[0] + limiar)
			&& (corImagem[1] >= corComparacao[1] - limiar) && (corImagem[1] <= corComparacao[1] + limiar)
			&& (corImagem[2] >= corComparacao[2] - limiar) && (corImagem[2] <= corComparacao[2] + limiar) ) {
			return true;
		}
		else {
			return false;
		}
		
	}
	public static Mat preencheImagem(Mat im, int x, int y, double []rgb, double []corNova) {
		double[] rgbIm = im.get(x, y);
	
		if(rgbIm != null) {
			if(comparaCor(rgbIm,rgb,50)) {
				im.put(x,y,corNova);
			}else {
				return im;
			}
			im = preencheImagem(im,x-1,y,rgb,corNova);
			im = preencheImagem(im,x,y+1,rgb,corNova);
			im = preencheImagem(im,x,y-1,rgb,corNova);
			im = preencheImagem(im,x+1,y,rgb,corNova);
			
		}
			
		
		return im;
	}
	
	public static void preenchimento_centro(Mat im){
		int x = im.width() / 2;
		int y = im.height() / 2;
		Mat imR = im.clone();
		double []rgb = new double [3];
		double []corNova = new double [3];
		Scanner sc = new Scanner(System.in);
		
		corNova[0] = 0;		// Cor nova - Formato BGR
		corNova[1] = 255;
		corNova[2] = 0;
		
		System.out.println("-- Valores RGB da nova cor--");
	    System.out.println("R : ");
	    corNova[2] = sc.nextDouble();
	    System.out.println("G : ");
	    corNova[1] = sc.nextDouble();
	    System.out.println("B : ");
	    corNova[0] = sc.nextDouble();
	    
		
		rgb = im.get(x, y);
		
		imR = preencheImagem(im, x, y, rgb, corNova);
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\preenchimento_resultado.jpg", imR);
		
	}
	public static void preenchimento_cor(Mat im){
		Mat imR = im.clone();
		double []rgb = new double [3];
		double []corNova = new double [3];
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-- Valores RGB da cor a ser substituida --");
	    System.out.println("R : ");
	    rgb[2] = sc.nextDouble();
	    System.out.println("G : ");
	    rgb[1] = sc.nextDouble();
	    System.out.println("B : ");
	    rgb[0] = sc.nextDouble();
	    
		System.out.println("-- Valores RGB da nova cor--");
	    System.out.println("R : ");
	    corNova[2] = sc.nextDouble();
	    System.out.println("G : ");
	    corNova[1] = sc.nextDouble();
	    System.out.println("B : ");
	    corNova[0] = sc.nextDouble();
		
	    // A cor do squirte é RGB(135,202,218)
	    // Pode troca para amarela RGB(181,218,75)
	    
		for(int i = 0; i < im.height(); i++) {
			for(int j = 0 ; j < im.width() ; j++) {
				imR = preencheImagem(im, i, j, rgb, corNova);		
			}
		}
		
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\preenchimento_cor_resultado.png", imR);
		
	}
	public static void preenchimento_ponto_fixo(Mat im){
		Mat imR = im.clone();
		double []rgb = new double [3];
		double []corNova = new double [3];
		
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Informe o ponto no eixo X : ");
	    int x = sc.nextInt();
	    System.out.println("Informe o ponto no eixo Y : ");
	    int y = sc.nextInt();
		
	    System.out.println("-- Valores RGB da nova cor--");
	    System.out.println("R : ");
	    corNova[2] = sc.nextDouble();
	    System.out.println("G : ");
	    corNova[1] = sc.nextDouble();
	    System.out.println("B : ");
	    corNova[0] = sc.nextDouble();
		
	    // Pegue a posicao (20,100) , (130,100) , (130,50)
	    // Pode troca para amarela RGB(181,218,75)
	    rgb = im.get(x, y);
		
		
		imR = preencheImagem(im, x, y, rgb, corNova);		
		
		
		
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\preenchimento_cor_pontoFixo.png", imR);
		
	}
	

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\squirtle.png");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Preenchimento pelo centro");
		System.out.println("2. Preenchimento por cor");
		System.out.println("3. Preenchimento por ponto conhecido");
		int dec = sc.nextInt();
		
		if(dec == 1) {
			preenchimento_centro(im);			
		}else if(dec == 2) {
			preenchimento_cor(im);			
		}else if(dec == 3) {
			preenchimento_ponto_fixo(im);			
		}
		
		System.out.println("Feito!");

	}

}
