package br.com.uel;

import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class TranslacaoRotacao {
	/*	[DEPRECIADA] Constrói e retorna a matrix
	 * 	|	cos(a)	-sen(a)	0	|	
	 * 	|	sen(a)	cos(a)	0	|
	 * 	|	0		0		1	|
	 */ 
	public static double[][] constroiMatrixAngulo(double angulo){
		double[][] matrix = { { Math.cos(angulo), -1*(Math.sin(angulo)), 0 }, {( Math.sin(angulo)), Math.cos(angulo), 0 }, { 0, 0, 1} };
		
		return matrix;
	}
	/*	Constrói a matrix de rotação
	 * 	|	alfa	beta	(1-alfa) * centroX - beta * centroY	|	
	 * 	|	-beta	alfa	beta * centroX + (1-alfa) * centroY	|
	 * 	alfa = cos(angulo)		beta = sen(angulo)
	 */ 
	public static double[][] constroiMatrixCM(int[] cm,double angulo){
		double a = (Math.PI/180) * (360-angulo);
		double alfa = Math.cos(a);
		double beta = Math.sin(a);
		double[][] matrix = { {alfa, beta, (1-alfa)*cm[0] - beta*cm[1]}, {-1 * beta, alfa, beta*cm[0] + (1-alfa) * cm[1] }};
		
		return matrix;
	}
	/*	Pega centro de massa e armazena em p(cmx,cmy) */
	public static int[] pegaCentroMassa(Mat im) {
		int[] ponto = new int[3];
		ponto[0] = im.width()/2;
		ponto[1] = im.height()/2;
		ponto[2] = 1;
		return ponto;
	}
	public static Mat transfRotacao(Mat im) {
		//  [Outra solução] - https://www.tutorialspoint.com/javaexamples/rotate_image.htm
		
		  Scanner sc = new Scanner(System.in);
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      
	      System.out.println("Digite o valor do angulo");
	      double angulo = sc.nextInt();
	      
	      String file = "C:\\Users\\ebrun\\Documents\\galinha.png";
	      Mat src = Imgcodecs.imread(file);

	      Mat dst = im.clone();
	    
	      int[] cm = pegaCentroMassa(src);	
	      double[][] rotationMatrix = constroiMatrixCM(cm,angulo);	      
	      
	      for(int i = 0 ; i < src.height() ; i++) {						// Colocaremos a imagem inteira com o cm no ponto (0,0)
				for(int j = 0 ; j < src.width() ; j++) {
					double []rgb = src.get(i, j);
					int []pLinha = new int[2];
					pLinha[0] = (int) (rotationMatrix[0][0] * i  + rotationMatrix[0][1] * j + rotationMatrix[0][2]);
					pLinha[1] = (int) (rotationMatrix[1][0] * i  + rotationMatrix[1][1] * j + rotationMatrix[1][2]);
					dst.put(pLinha[0] ,pLinha[1], rgb);
					if(angulo > 0 && angulo < 90) {
						dst.put(pLinha[0]+1 ,pLinha[1]+1, rgb);
						dst.put(pLinha[0]+2 ,pLinha[1]+2, rgb);
					}else if(angulo > 90 && angulo < 180) {
						dst.put(pLinha[0]-1 ,pLinha[1]-1, rgb);
						dst.put(pLinha[0]-2 ,pLinha[1]-2, rgb);
					}else if(angulo > 180 && angulo < 270) {
						dst.put(pLinha[0]-1 ,pLinha[1]-1, rgb);
						dst.put(pLinha[0]+2 ,pLinha[1]+2, rgb);
					}
					else if(angulo > 270 && angulo < 360) {
						dst.put(pLinha[0]+1 ,pLinha[1]+1, rgb);
						dst.put(pLinha[0]-2 ,pLinha[1]-2, rgb);
					}
				}
	      }
//	      Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\galinha_trans.png", dst);
	      return dst;
	}

	// Salva a imagem original para fazer a comparação visual
	public static void salvaImagemOriginal() {		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\galinha.png");
		
		Mat im_tran = new Mat(im.rows()*2 , im.cols()*2 , im.type());
		
		for(int i = 0 ; i < im.height() ; i++) {
			for(int j = 0 ; j < im.width() ; j++) {
				double []rgb = im.get(i, j);
				im_tran.put(i,j, rgb);			
			}				
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\galinha_original.png", im_tran);
	}
	/*	Constrói e retorna a matrix
	 * 	|	1	0	deltaX	|	
	 * 	|	0	1	deltaY	|
	 * 	|	0	0	1		|
	 */ 
	public static double[][] constroiMatrix(Mat im, int deltaX, int deltaY) {
		double[][] matrix = { { 1, 0, deltaX }, { 0, 1, deltaY }, { 0, 0, 1} };
		
		return matrix;
	}
	/*	Multiplica a matrix pelo vetor [x	y	1] e armazena o resultado em um vetor	[x'	y'	1] */
	public static double[] multiplicaMatrix(double[][] matMult, int[] vet, int i, int j) {

		double x = 0;
		int cont = 0;
		double[] vetResp = new double[3];
		
		// Realiza a multiplicação da matrix com o vetor
		for(int l = 0 ; l < 3; l++) {
				x = (matMult[l][0] * vet[0]) +  (matMult[l][1] * vet[1]) +  (matMult[l][2] * vet[2]) ;
			vetResp[cont] = x;
			x = 0;
			cont++;
		}
		
		return vetResp;
	}
	public static Mat transfTranslacao(Mat im) {
		
		Scanner sc = new Scanner(System.in);
		salvaImagemOriginal();
		Mat im_tran = new Mat(im.rows()*2 , im.cols()*2 , im.type());
		
		System.out.println("Selecione o delta X :");
		int deltaX = sc.nextInt();
		System.out.println("Selecione o delta Y :");
		int deltaY = sc.nextInt();
		
		double[][] matMult = constroiMatrix(im,deltaY,deltaX);			// Constrói a matrix para fazer a multiplicação
		
		for(int i = 0 ; i < im.height() ; i++) {					// Percorre a imagem inteira
			for(int j = 0 ; j < im.width() ; j++) {
				
				int[] vet = { i, j, 1 };								// Vetor correspondente ao p = (x,y)
				double []rgb = im.get(i, j);							// Pega o RGB
				
				double[] pLinha = multiplicaMatrix(matMult,vet,i,j);		// Pega a posição do p'.       p' = (x',y')
				im_tran.put((int)pLinha[0] ,(int) pLinha[1], rgb);				// Salva o novo ponto na nova imagem
			}				
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\galinha_trans.png", im_tran);	// Salva a imagem já transladada
		return im_tran;
	}
}
