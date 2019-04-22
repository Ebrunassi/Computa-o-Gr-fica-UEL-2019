import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {
	
	public static boolean imagemTotalmenteCorroida = false;
	public static Mat imResultante;
	
	/* Função que verificará se a imagem foi totalmente corroida */
	public static boolean verificaImagemCorroida(Mat im) {
		boolean pixelBranco = false;
		for(int i = 1; i < im.height()-1; i++) {
			for(int j = 0 ; j < im.width(); j++) {
				double []rgb = im.get(i, j);
				pixelBranco = difRGB(rgb,255);				// Se retornar FALSE, significa que o pixel é branco
				if(pixelBranco == true)
					return false;
			}
		}
		return true;
	}
	public static void somaImagens(Mat im) {
		double []black = {0,0,0};
		
		for(int i = 0; i < im.height(); i++) {
			for(int j = 0 ; j < im.width(); j++) {
				double []rgb = im.get(i, j);
//				double []rgbR = imResultante.get(i, j);
				if(rgb[0] != 255 )
//				if(difCores(rgb,rgbR))
					imResultante.put(i, j, black);
			}
		}
	}
	public static boolean difCores(double[] rgb, double[] rgbAb) {
		if(rgb[0] != rgbAb[0] ) {
			return true;
		}else {
			return false;
		}
	}
	public static boolean difRGB(double[] rgb, int valorCor) {
		if(rgb[0] != valorCor || rgb[1] != valorCor || rgb[2] != valorCor) {
			return true;			// É diferente de branco!
		}else {
			return false;			// É igual a branco!
		}
	}
	/*	A erosão será feita utilizando esta figura âncore
	 * 			X
	 * 		  X X X
	 * 			X
	 */
	public static Mat erosao(Mat im) {
//		Mat imErosao = im.clone();
		Mat imErosao = new Mat(im.rows() , im.cols() , im.type());
		double []white = {255,255,255};
		double []black = {0,0,0};
		for(int i = 0; i < im.height(); i++) {
			for(int j = 0 ; j < im.width(); j++) {
				imErosao.put(i, j, white);
			}
		}

		for(int i = 1; i < im.height()-1; i++) {
			for(int j = 1 ; j < im.width()-1; j++) {
				double []rgb1 = im.get(i-1, j);
				double []rgb2 = im.get(i, j);
				double []rgb3 = im.get(i+1, j);
				double []rgb4 = im.get(i, j-1);
				double []rgb5 = im.get(i, j+1);
				
				
//				System.out.println(rgb2[0] + "," +rgb2[1] + "," +rgb2[2] + "" );
				if(difRGB(rgb1,255) == true && difRGB(rgb2,255) == true && difRGB(rgb3,255) == true && difRGB(rgb4,255) == true && difRGB(rgb5,255) == true && i-1 != 0 && i+1 != im.height() && j-1 != 0 && j+1 != im.width()) {
					imErosao.put(i,j, black);
					
//					imErosao.put(i-1,j, white);
//					imErosao.put(i+1,j, white);
//					imErosao.put(i,j-1, white);
//					imErosao.put(i,j+1, white);
//					imErosao.put(i-1,j-1, white);
//					imErosao.put(i+1,j+1, white);
//					imErosao.put(i-1,j+1, white);
//					imErosao.put(i+1,j-1, white);
						
				}else {
					imErosao.put(i,j, white);	
				}
			}
		}
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\folhaCorroida.jpg", imErosao);
		return imErosao;
	}
	public static Mat dilatacao(Mat im) {
		Mat imErosao = new Mat(im.rows() , im.cols() , im.type());
		double []white = {255,255,255};
		double []black = {0,0,0};
//		Mat imErosao  = im.clone();
		for(int i = 0; i < im.height(); i++) {
			for(int j = 0 ; j < im.width(); j++) {
				imErosao.put(i, j, white);
			}
		}
		
		for(int i = 1; i < im.height()-1; i++) {
			for(int j = 1 ; j < im.width()-1; j++) {
				double []rgb2 = im.get(i, j);				
//				System.out.println(rgb2[0] + "," +rgb2[1] + "," +rgb2[2] + "" );
				
//				if(difRGB(rgb1,255) == true || difRGB(rgb2,255) == true || difRGB(rgb3,255) == true || difRGB(rgb4,255) == true || difRGB(rgb5,255) == true || difRGB(rgb6,255) == true || difRGB(rgb7,255) == true || difRGB(rgb8,255) == true || difRGB(rgb9,255) == true) {
				if(difRGB(rgb2,255) == true) {
					
					imErosao.put(i,j, black);
					
					imErosao.put(i-1,j, black);
					imErosao.put(i+1,j, black);
					imErosao.put(i,j-1, black);
					imErosao.put(i,j+1, black);
//					imErosao.put(i+1,j+1, black);
//					imErosao.put(i-1,j+1, black);
//					imErosao.put(i-1,j-1, black);
//					imErosao.put(i+1,j-1, black);
//					
//					imErosao.put(i+1,j+1, black);
//					imErosao.put(i-1,j-1, black);
//					imErosao.put(i-1,j+1, black);
//					imErosao.put(i+1,j-1, black);
//					
//					imErosao.put(i,j-2, black);
//					imErosao.put(i,j+2, black);
//					imErosao.put(i-2,j, black);
//					imErosao.put(i+2,j, black);
					
				}else {
//					imErosao.put(i,j, white);
//					imErosao.put(i-1,j, white);
//					imErosao.put(i+1,j, white);
//					imErosao.put(i,j-1, white);
//					imErosao.put(i,j+1, white);
//					imErosao.put(i+1,j+1, white);
//					imErosao.put(i-1,j+1, white);
//					imErosao.put(i-1,j-1, white);
//					imErosao.put(i+1,j-1, white);
				}
			}
		}
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\estrelaDilatado.png", imErosao);
		return imErosao;
	}
	
	/* Realiza a corrosão e depois a dilatação da imagem */
	public static Mat abertura(Mat im) {
		Mat imErosao = erosao(im);
		Mat imAbertura = dilatacao(imErosao);
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\folhaAbertura.jpg", imAbertura);
		return imAbertura;
	}
	
	/* Faz a diferença entre a imagem gerada após o processo de abertura com a imagem original e salva em imDif */
	public static void diferencaImagem(Mat im,Mat imAb) {
//		Mat imDif = im.clone();
		Mat imDif = new Mat(im.rows() , im.cols() , im.type());
		double []black = {0,0,0};
		double []white = {255,255,255};
		for(int i = 0; i < im.height(); i++) {
			for(int j = 0 ; j < im.width(); j++) {
				imDif.put(i, j, white);
			}
		}
		
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\coelhoDif.jpg", imDif);
		
		for(int i = 0; i < imAb.height(); i++) {
			for(int j = 0 ; j < imAb.width(); j++) {
				double []rgb = im.get(i, j);
				double []rgbAb = imAb.get(i, j);
				if(rgb[0] != 255 && rgbAb[0] == 255 ) {
					imDif.put(i, j, black);
				}else {
					imDif.put(i, j, white);
				}
					

			}	
		}
		somaImagens(imDif);
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\caraR.png", imResultante);
	}
	
	public static void fazEsqueletizacao(Mat im) {
		Mat imErosao = im.clone();		
		int i = 0;
//		imErosao = erosao(im);
//		for(int i = 0 ; i < 10 ; i++) {
		while(imagemTotalmenteCorroida == false) {
			i++;
			System.out.println(i+".");
			Mat imAbertura = abertura(imErosao);
			diferencaImagem(imErosao,imAbertura);
			imErosao = erosao(imErosao);				
			imagemTotalmenteCorroida = verificaImagemCorroida(imErosao);			// Atualiza o status que mostrará se a matrix está em branco (totalmente corroida)
		}
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\folhaDif.jpg", imErosao);
	}
	
	/* Torna a imagem preta e branca */
	public static Mat ajustaImagem(Mat im) {
		Mat imNova = im.clone();
		
		double[] white = {255,255,255};
		
		for(int i = 0; i < im.height(); i++) {
			for(int j = 0 ; j < im.width(); j++) {
				double []rgb = im.get(i, j);
				if(rgb[0] < 100) {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
					imNova.put(i,j, rgb);
				}else {
					imNova.put(i,j, white);					
				}
			}	
		}
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\chegou.jpg", imNova);
		return imNova;
	}
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\cara.png");
		double []white = {255,255,255};
		double []black = {0,0,0};
		Mat imBordaBranca = im.clone();
				
		imResultante = im.clone();
		
		for(int i = 0; i < im.height(); i++) {
			for(int j = 0 ; j < im.width(); j++) {
				imResultante.put(i, j, white);
			}
		}
		
		Mat imNova = ajustaImagem(im);
		
//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\chegou.jpg", imNova);
		fazEsqueletizacao(imNova);			
		

//		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\arvR.jpg", imResultante);
		System.out.println("Feito!");

	}

}
