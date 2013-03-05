import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.imgscalr.Scalr;

public class IconResizer {

	private JFrame frmAndroidIconResizer;
	private JTextField filePath;
	private JTextField folderPath;
	private JButton folderButton;

	private final String WEB_URL = "http://wireshock.com/android-icon-resizer/";
	private final String DONATE_LINK = "https://www.paypal.com/us/cgi-bin/webscr?cmd=_flow&SESSION=TsJY3Q-Kiw0dn7H3jdp_MvUVAdzQMf7lpM4o4vovX01EOoxSzqA6e2yHviO&dispatch=5885d80a13c0db1f8e263663d3faee8dd75b1e1ec3ad97b7af62835dd81d5d52";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IconResizer window = new IconResizer();
					window.frmAndroidIconResizer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IconResizer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAndroidIconResizer = new JFrame();
		frmAndroidIconResizer.setTitle("Android Icon Resizer");
		frmAndroidIconResizer.setBounds(100, 100, 329, 356);
		frmAndroidIconResizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAndroidIconResizer.getContentPane().setLayout(null);

		filePath = new JTextField();
		filePath.setEditable(false);
		filePath.setBounds(25, 51, 187, 28);
		frmAndroidIconResizer.getContentPane().add(filePath);
		filePath.setColumns(10);

		JButton fileButton = new JButton("Browse");
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
						"PNG Icon", "png"));
				fileChooser.setDialogTitle("Choose PNG Icon To Open");
				fileChooser.showOpenDialog(fileChooser);
				filePath.setText(fileChooser.getSelectedFile().toString());
			}
		});
		fileButton.setBounds(227, 52, 74, 29);
		frmAndroidIconResizer.getContentPane().add(fileButton);

		folderPath = new JTextField();
		folderPath.setEditable(false);
		folderPath.setColumns(10);
		folderPath.setBounds(25, 117, 187, 28);
		frmAndroidIconResizer.getContentPane().add(folderPath);

		folderButton = new JButton("Browse");
		folderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser folderChooser = new JFileChooser();
				folderChooser
						.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				folderChooser
						.setDialogTitle("Choose Your Android App Res Folder");
				folderChooser.showOpenDialog(folderChooser);
				folderPath.setText(folderChooser.getSelectedFile().toString());
			}
		});
		folderButton.setBounds(227, 118, 74, 29);
		frmAndroidIconResizer.getContentPane().add(folderButton);

		JLabel lblxIconPath = new JLabel("512x512 Icon Path");
		lblxIconPath.setBounds(25, 28, 187, 16);
		frmAndroidIconResizer.getContentPane().add(lblxIconPath);

		final JLabel lblMessage = new JLabel("Press Generate");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(35, 164, 266, 16);
		frmAndroidIconResizer.getContentPane().add(lblMessage);

		JLabel lblOutputFolder = new JLabel("Output Folder");
		lblOutputFolder.setBounds(25, 97, 187, 16);
		frmAndroidIconResizer.getContentPane().add(lblOutputFolder);

		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!filePath.getText().isEmpty()
						&& !folderPath.getText().isEmpty()) {
					File imageFile = new File(filePath.getText());
					String dirPath = folderPath.getText();
					if (imageFile.exists()) {
						try {
							lblMessage.setText("Resizing Images...");

							// Use createResizedCopy method to create resized
							// BufferedImage
							BufferedImage myImage = ImageIO.read(imageFile);
							BufferedImage xxhdpi = createResizedCopy(myImage,
									144, 144);
							BufferedImage xhdpi = createResizedCopy(myImage,
									96, 96);
							BufferedImage hdpi = createResizedCopy(myImage, 72,
									72);
							BufferedImage mdpi = createResizedCopy(myImage, 48,
									48);
							BufferedImage ldpi = createResizedCopy(myImage, 36,
									36);

							// Create output files
							File xxhdpiFile = new File(dirPath
									+ "/drawable-xxhdpi/ic_launcher.png");
							File xhdpiFile = new File(dirPath
									+ "/drawable-xhdpi/ic_launcher.png");
							File hdpiFile = new File(dirPath
									+ "/drawable-hdpi/ic_launcher.png");
							File mdpiFile = new File(dirPath
									+ "/drawable-mdpi/ic_launcher.png");
							File ldpiFile = new File(dirPath
									+ "/drawable-ldpi/ic_launcher.png");

							lblMessage.setText("Creating Files...");

							// Create directories if they do not exist
							xxhdpiFile.mkdirs();
							xhdpiFile.mkdirs();
							hdpiFile.mkdirs();
							mdpiFile.mkdirs();
							ldpiFile.mkdirs();

							// Write bitmaps to files
							ImageIO.write(xxhdpi, "PNG", xxhdpiFile);
							ImageIO.write(xhdpi, "PNG", xhdpiFile);
							ImageIO.write(hdpi, "PNG", hdpiFile);
							ImageIO.write(mdpi, "PNG", mdpiFile);
							ImageIO.write(ldpi, "PNG", ldpiFile);

							lblMessage.setText("Icons successfully generated!");
						} catch (IOException e1) {
							lblMessage
									.setText("Failed! Could not open source icon!");
							e1.printStackTrace();
						}
					} else {
						lblMessage
								.setText("Sorry! Selected icon does not exist anymore.");
					}
				} else {
					lblMessage
							.setText("Please browse source Icon and res folder!");
				}
			}
		});
		btnGenerate.setBounds(88, 192, 152, 50);
		frmAndroidIconResizer.getContentPane().add(btnGenerate);

		JLabel aboutLabel = new JLabel(
				"\u00A92013 Kaushal Subedi, All Rights Reserved");
		aboutLabel.setBounds(25, 280, 276, 16);
		frmAndroidIconResizer.getContentPane().add(aboutLabel);

		JButton btnWebsite = new JButton("Website");
		btnWebsite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI(WEB_URL));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnWebsite.setBounds(25, 299, 117, 29);
		frmAndroidIconResizer.getContentPane().add(btnWebsite);

		JButton btnDonate = new JButton("Donate");
		btnDonate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(DONATE_LINK));
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		});
		btnDonate.setBounds(184, 299, 117, 29);
		frmAndroidIconResizer.getContentPane().add(btnDonate);
	}

	BufferedImage createResizedCopy(BufferedImage originalImage,
			int scaledWidth, int scaledHeight) {

		return Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY,
				scaledWidth, scaledHeight, Scalr.OP_ANTIALIAS);

	}

}
