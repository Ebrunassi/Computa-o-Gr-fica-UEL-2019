import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

public class Tela {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Scanner sc = new Scanner(System.in);

		Preenchimento pr = new Preenchimento();
		Mat im = Imgcodecs.imread("C:\\Users\\ebrun\\Documents\\squirtle.png");

		System.out.println("Feito!");

		// C:\\Users\\ebrun\\Documents\\squirtle.png
		String path = "C:\\Users\\ebrun\\Documents\\squirtle.png";
//		System.out.println("Digite o caminho da imagem contendo o seu nome e extensão : ");
//		String path = sc.next();
//		Mat im = Imgcodecs.imread(path);

		Mat matriz = im.clone();

		int largura = (int) matriz.size().width;
		int altura = (int) matriz.size().height;

		JLabel labelR = new JLabel("R:");
		JLabel labelG = new JLabel("G:");
		JLabel labelB = new JLabel("B:");
		JLabel labelL = new JLabel("Limiar: ");
		JTextField textR = new JTextField(3);
		JTextField textG = new JTextField(3);
		JTextField textB = new JTextField(3);
		JTextField limiar = new JTextField(3);

		// Leitura da imagem para o Painel
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e1) {
			System.out.println("Erro na leitura da imagem !");
		}

		// Adiciona a imagem ao JPanel
		ImageIcon icon = new ImageIcon(image);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);

		// Seta o painel que conterá as labels
		JPanel seletor;
		seletor = new JPanel();
		seletor.add(thumb);
		seletor.add(labelR);
		seletor.add(textR);
		seletor.add(labelG);
		seletor.add(textG);
		seletor.add(labelB);
		seletor.add(textB);
		seletor.add(labelL);
		seletor.add(limiar);
		seletor.setLayout(new BoxLayout(seletor, BoxLayout.Y_AXIS));

		// Painel Principal
		JPanel painel = new JPanel();
		painel.add(thumb);
		painel.add(seletor);

		// Trata o evento de clique
		thumb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int x = e.getX();
				int y = e.getY();

				if (x < largura && y < altura) {

					try {
						// Obtem as cores dadas no teclado
						int limiarR = Integer.parseInt(limiar.getText());
						int b = Integer.parseInt(textB.getText());
						int g = Integer.parseInt(textG.getText());
						int r = Integer.parseInt(textR.getText());

						double[] corPreenchimento = { b, g, r };
						double[] corPadrao = matriz.get(y, x);
						Preenchimento.preenchimento_ponto_fixo(matriz, largura, altura, y, x, corPreenchimento,
								limiarR);

						BufferedImage imagem = new BufferedImage(matriz.cols(), matriz.rows(),
								BufferedImage.TYPE_INT_RGB);

						// Muda a imagem apos o preenchimento
						for (int i = 0; i < matriz.rows(); i++) {
							for (int j = 0; j < matriz.cols(); j++) {
								double[] d = matriz.get(i, j);
								int azul = (int) d[0];
								int verde = (int) d[1];
								int vermelho = (int) d[2];

								Color color = new Color(vermelho, verde, azul);
								int rgb = color.getRGB();
								imagem.setRGB(j, i, rgb);

							}
						}
						// Muda a imagem na JLabel
						ImageIcon icon = new ImageIcon(imagem);
						thumb.setIcon(icon);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Entrada vazia ou no formato incorreto");

					}
				}
			}
		});

		// Junta os paineis no frame
		JFrame frame = new JFrame("Preenchimento - Cor");
		frame.add(painel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
