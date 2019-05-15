import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class MediaMedianaCinza {
	public static double matR1[][] = {{-1,-2,-1},{0,0,0},{1,2,1}};
	public static double matR2[][] = {{-1,0,1},{-2,0,2},{-1,0,1}};
	
	// Soma todos os valores da matriz[3][3], e retorna a média desse valor (valor/9)
	public static double mediaPontos(double [][]mat){		
		double r1 = 0;
		
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				r1 += mat[i][j];
			}
		}
		return (r1/9);
	}
	// Coloca todos os valores da matriz[3][3] em um vetor[9], ordena ele e retorna o 5º elemento
	public static double pegaMediana(double [][]mat) {
		double []vet = new double[9];
		int c = 0;
		double aux;
		
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				vet[c] = mat[i][j];
				c++;
			}
		}
		for(int i = 0; i < 9 ; i++) {
			for(int j = 0 ; j < 9 ; j++) {
				if(vet[j] < vet[i]) {
					aux = vet[i];
					vet[i] = vet[j];
					vet[j] = aux;
				}
			}
		}
		return vet[5];
	}
	// Função main que calcula e salva uma imagem aplicando a 'media'
	public static void Mediana_Cinza(Mat im) {
		Mat imC = im.clone();
		Imgproc.cvtColor(im, imC, Imgproc.COLOR_BGR2GRAY);
		Mat imMedia = imC.clone();
		
//		Imgproc.cvtColor(imSobel, imSobel, Imgproc.COLOR_BGR2GRAY);
		
		double [][]mat = new double [3][3];
		double []cinza = new double[3];
		double r1 = 0;
		double r2 = 0;
		
		for(int i = 1 ; i < im.height() -2 ; i++) {
			for(int j = 1 ; j < im.width() -2; j++) {
				
			
				cinza = im.get(i-1, j-1);
				mat[0][0] = cinza[0];			
				cinza = im.get(i-1, j);
				mat[0][1] = cinza[0];
				cinza = im.get(i-1, j+1);
				mat[0][2] = cinza[0];
				cinza = im.get(i, j-1);
				mat[1][0] = cinza[0];
				cinza = im.get(i, j);
				mat[1][1] = cinza[0];
				cinza = im.get(i, j+1);
				mat[1][2] = cinza[0];
				cinza = im.get(i+1, j-1);
//				System.out.println("mat["+i+"]+["+j+"]");
				mat[2][0] = cinza[0];
				cinza = im.get(i+1, j);
				mat[2][1] = cinza[0];
				cinza = im.get(i+1, j+1);
				mat[2][2] = cinza[0];
				
				r1 = pegaMediana(mat);
				
				
//				if(r > 1)
//					System.out.println("r : " + r);
				imMedia.put(i,j, r1);
				
			}
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\imagem_mediana.jpg", imMedia);
	}
	
	// Função main que calcula e salva uma imagem aplicando a 'media'
	public static void Media_Cinza(Mat im) {
		Mat imC = im.clone();
		Imgproc.cvtColor(im, imC, Imgproc.COLOR_BGR2GRAY);
		Mat imMedia = imC.clone();
		
		double [][]mat = new double [3][3];
		double []cinza = new double[3];
		double r1 = 0;
		double r2 = 0;
		
		for(int i = 1 ; i < im.height() -2 ; i++) {
			for(int j = 1 ; j < im.width() -2; j++) {
				
			
				cinza = im.get(i-1, j-1);
				mat[0][0] = cinza[0];			
				cinza = im.get(i-1, j);
				mat[0][1] = cinza[0];
				cinza = im.get(i-1, j+1);
				mat[0][2] = cinza[0];
				cinza = im.get(i, j-1);
				mat[1][0] = cinza[0];
				cinza = im.get(i, j);
				mat[1][1] = cinza[0];
				cinza = im.get(i, j+1);
				mat[1][2] = cinza[0];
				cinza = im.get(i+1, j-1);
				mat[2][0] = cinza[0];
				cinza = im.get(i+1, j);
				mat[2][1] = cinza[0];
				cinza = im.get(i+1, j+1);
				mat[2][2] = cinza[0];
				
				r1 = mediaPontos(mat);
				
				imMedia.put(i,j, r1);
				
			}
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\imagem_media.jpg", imMedia);
	}
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\pedraoP.jpg");
		Media_Cinza(im);
		Mediana_Cinza(im);
		System.out.println("Feito!");

	}

}



