import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Convolucao {
	public static double matR1[][] = {{-1,-2,-1},{0,0,0},{1,2,1}};
	public static double matR2[][] = {{-1,0,1},{-2,0,2},{-1,0,1}};
	
	
	public static double multiplicaMatrix(double [][]mat, double [][]matMult){
		double [][]mr1 = new double[3][3];
		double r1 = 0;
		mr1[0][0] = (mat[0][0] * matMult[0][0]) + (mat[0][1] * matMult[1][0]) + (mat[0][2] * matMult[2][0]);
		mr1[0][1] = (mat[0][0] * matMult[0][1]) + (mat[0][1] * matMult[1][1]) + (mat[0][2] * matMult[2][1]);
		mr1[0][2] = (mat[0][0] * matMult[0][2]) + (mat[0][1] * matMult[1][2]) + (mat[0][2] * matMult[2][2]);
		
		mr1[1][0] = (mat[1][0] * matMult[0][0]) + (mat[1][1] * matMult[1][0]) + (mat[1][2] * matMult[2][0]);
		mr1[1][1] = (mat[1][0] * matMult[0][1]) + (mat[1][1] * matMult[1][1]) + (mat[1][2] * matMult[2][1]);
		mr1[1][2] = (mat[1][0] * matMult[0][2]) + (mat[1][1] * matMult[1][2]) + (mat[1][2] * matMult[2][2]);
		
		mr1[2][0] = (mat[2][0] * matMult[0][0]) + (mat[2][1] * matMult[1][0]) + (mat[2][2] * matMult[2][0]);
		mr1[2][1] = (mat[2][0] * matMult[0][1]) + (mat[2][1] * matMult[1][1]) + (mat[2][2] * matMult[2][1]);
		mr1[2][2] = (mat[2][0] * matMult[0][2]) + (mat[2][1] * matMult[1][2]) + (mat[2][2] * matMult[2][2]);
		
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				r1 += mr1[i][j];
			}
		}
		return r1;
	}
	
	public static void Sobel(Mat im) {
		Mat imC = im.clone();
		Imgproc.cvtColor(im, imC, Imgproc.COLOR_BGR2GRAY);
		Mat imSobel = imC.clone();
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
				
				r1 = multiplicaMatrix(mat,matR1);
				r2 = multiplicaMatrix(mat,matR2);
				double r= Math.sqrt((r1*r1+ r2*r2));
				if(r > 50) {
					r = 255;					
				}else {
					r = 0;
				}
//				if(r > 1)
//					System.out.println("r : " + r);
				imSobel.put(i,j, r);
				
			}
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\imagem_borda.jpg", imSobel);
	}
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\lady.jpg");
		Sobel(im);
		System.out.println("Feito!");

	}

}
