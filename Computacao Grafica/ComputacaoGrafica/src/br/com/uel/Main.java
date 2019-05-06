package br.com.uel;

import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {

	public static String path = "C:\\Users\\ebrun\\Documents\\";
	private static Scanner sc;
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\cara.png");
		boolean printa = true;
		
		sc = new Scanner(System.in);
		System.out.println("1. Zoom in linear");
		System.out.println("2. Zoom out linear");
		
		System.out.println("3. Zoom in quadrado");
		System.out.println("4. Zoom out quadrado");
		
		System.out.println("5. Histograma - preto e branco");
		System.out.println("6. Histograma - colorido");
		
		System.out.println("7. Operacao - E");
		System.out.println("8. Operacao - OU");
		
		System.out.println("9. Inverte cores");
		
		System.out.println("10. Translação");
		System.out.println("11. Rotação");
		
		System.out.println("12. Separação de canais");
		System.out.println("13. Esqueletização");
		
		int esc = sc.nextInt();
		
		switch(esc) {
		case(1):
			im = Zoom.zoom_in(im, path);
			path = path + "zoom_in_linear.jpg";
		break;
		case(2):
			im = Zoom.zoom_out(im, path);
			path = path + "zoom_out_linear.jpg";
		break;
		case(3):
			im = Zoom.zoom_in_quadrado(im, path);
			path = path + "zoom_in_quadrado.jpg";
		break;
		case(4):
			im = Zoom.zoom_out_quadrado(im, path);
			path = path + "zoom_out_quadrado.jpg";
		break;
		case(5):
			im = Histograma.contrastePretoBranco(im,path);
			path = path + "contraste_preto_e_branco.png";
		break;
		case(6):
			im = Histograma.contrasteColorido(im, path);
			path = path + "contraste_colorido.jpg";
		break;
		case(7):
			Mat im2 = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\im2.png");
			im = OperacaoE_OU.operacaoE(im, im2);
		break;
		case(8):
			Mat img2 = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\im2.png");
			im = OperacaoE_OU.operacaoOU(im, img2);
		break;
		case(9):
			im = InverteImagem.inverte_cores(im, path);
			path = path + "cores_invertidas.jpg";
		break;
		case(10):
			TranslacaoRotacao.transfTranslacao(im);
			printa = false;
//			path = path + "translacao.png";
		break;
		case(11):
			im = TranslacaoRotacao.transfRotacao(im);
			path = path + "rotacao.png";
		break;
		case(12):
			SeparacaoCanais.separaCanais(im);
			printa = false;
		break;
		case(13):
			im = Esqueletizacao.executaEsqueletizacao(im);
			path = path + "esqueletizacao.png";
		break;
		}
		if(printa == true)
			Imgcodecs.imwrite(path, im);
		System.out.println("Feito!");
	}

}
