import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class MediaMedianaColorido {
	
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
	public static Cor pegaMediana(ArrayList<Cor> cor) {
				
		double []vet = new double[9];
		Cor aux = new Cor();
		
		for(int i = 0; i < 9 ; i++) {
			cor.get(i).setMedia( (cor.get(i).getBlue() + cor.get(i).getGreen() + cor.get(i).getRed()) / 3);
			cor.get(i).setIndice(i);
		}
		for(int i = 0; i < 9 ; i++) {
			for(int j = 0 ; j < 9 ; j++) {
				if(cor.get(j).getMedia() < cor.get(i).getMedia()) {
					
					aux.setBlue(cor.get(j).getBlue());
					aux.setGreen(cor.get(j).getGreen());
					aux.setRed(cor.get(j).getRed());
					aux.setMedia(cor.get(j).getMedia());
					aux.setIndice(cor.get(j).getIndice());
					
					cor.get(j).setBlue(cor.get(i).getBlue());
					cor.get(j).setGreen(cor.get(i).getGreen());
					cor.get(j).setRed(cor.get(i).getRed());
					cor.get(j).setMedia(cor.get(i).getMedia());
					cor.get(j).setIndice(cor.get(i).getIndice());
					
					cor.get(i).setBlue(aux.getBlue());
					cor.get(i).setGreen(aux.getGreen());
					cor.get(i).setRed(aux.getRed());
					cor.get(i).setMedia(aux.getMedia());
					cor.get(i).setIndice(aux.getIndice());
				}
			}
		}		
		return cor.get(4);	
		
	}
	// Função main que calcula e salva uma imagem aplicando a 'media'
	public static void Mediana_Colorido(Mat im) {
		Mat imC = im.clone();
		Mat imMedia = imC.clone();
		
		double [][]mat = new double [3][3];
		Cor cor = new Cor();
		int indice = 0;
		
		int c = 0;
		
		for(int i = 1 ; i < im.height() -2 ; i++) {
			for(int j = 1 ; j < im.width() -2; j++) {
				
				
					ArrayList<Cor> corList = new ArrayList<Cor>();
					double []rgb = im.get(i-1, j-1);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					
					cor = new Cor();
					rgb = im.get(i-1, j);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					
					cor = new Cor();
					rgb = im.get(i-1, j+1);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					cor = new Cor();
					
					rgb = im.get(i, j-1);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					cor = new Cor();
					
					rgb = im.get(i, j);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					cor = new Cor();
					
					rgb = im.get(i, j+1);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					cor = new Cor();
					
					rgb = im.get(i+1, j-1);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					cor = new Cor();
					
					rgb = im.get(i+1, j);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					cor = new Cor();
					
					rgb = im.get(i+1, j+1);
					cor.setRed(rgb[0]);
					cor.setGreen(rgb[1]);
					cor.setBlue(rgb[2]);
					corList.add(cor);
					cor = new Cor();
					
					cor = pegaMediana(corList);
					
					corList.clear();

				rgb[0] = cor.getRed();
				rgb[1] = cor.getGreen();
				rgb[2] = cor.getBlue();

				imMedia.put(i,j, rgb);
				
			}
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\imagem_mediana.jpg", imMedia);
	}
	
	// Função main que calcula e salva uma imagem aplicando a 'media'
	public static void Media_Colorido(Mat im) {
		Mat imC = im.clone();
//		Imgproc.cvtColor(im, imC, Imgproc.COLOR_BGR2GRAY);
		Mat imMedia = imC.clone();
		
		double [][]mat = new double [3][3];
		double []rgb = new double[3];
		double []cor = new double[3];
		
		for(int i = 1 ; i < im.height() -2 ; i++) {
			for(int j = 1 ; j < im.width() -2; j++) {
				
				for(int c = 0 ; c < 3 ; c++) {
					rgb = im.get(i-1, j-1);
					mat[0][0] = rgb[c];			
					rgb = im.get(i-1, j);
					mat[0][1] = rgb[c];
					rgb = im.get(i-1, j+1);
					mat[0][2] = rgb[c];
					rgb = im.get(i, j-1);
					mat[1][0] = rgb[c];
					rgb = im.get(i, j);
					mat[1][1] = rgb[c];
					rgb = im.get(i, j+1);
					mat[1][2] = rgb[c];
					rgb = im.get(i+1, j-1);
					mat[2][0] = rgb[c];
					rgb = im.get(i+1, j);
					mat[2][1] = rgb[c];
					rgb = im.get(i+1, j+1);
					mat[2][2] = rgb[c];
					
					cor[c] = mediaPontos(mat);
					
				}
				
				imMedia.put(i,j, cor);
				
			}
		}
		Imgcodecs.imwrite("C:\\Users\\ebrun\\Documents\\imagem_media.jpg", imMedia);
	}
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\pedraoP.jpg");
		Media_Colorido(im);
		Mediana_Colorido(im);
		System.out.println("Feito!");

	}

}



