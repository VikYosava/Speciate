package view;


import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Font;

public class ConfigInicial extends JPanel{

	private JFormattedTextField fNmutaciones;
	private JFormattedTextField fCantEInicial;
	private JFormattedTextField fNIteraciones;
	private JFormattedTextField fMatrixtxt;
	private JFormattedTextField fDirect;
	private JFormattedTextField ffCatastrof;
	private JButton btnOkk;
	private JLabel lblNmutaciones, lblNespeciesIni, lblNIteraciones, 
	lblIntrdoduceLasVariables,
	lblTablaMut, lblCarpetaSalida, lblFrecuenciaCatstrofesn;

	public ConfigInicial() {;
		initialize();
	}
	
	private void initialize() {
		setLayout(null);
		
		lblNmutaciones = new JLabel("Posibles Mutaciones (Nº entero)");
		lblNmutaciones.setBounds(100, 70, 175, 13);
		add(lblNmutaciones);
		
		lblNespeciesIni = new JLabel("Especies Iniciales (Nº entero)");
		lblNespeciesIni.setBounds(300, 70, 175, 13);
		add(lblNespeciesIni);
		
		fNmutaciones = new JFormattedTextField();
		fNmutaciones.setText("10");
		fNmutaciones.setBounds(100, 90, 175, 19);
		add(fNmutaciones);
		
		fCantEInicial = new JFormattedTextField();
		fCantEInicial.setText("1");
		fCantEInicial.setBounds(300, 90, 175, 19);
		add(fCantEInicial);
		
		fNIteraciones = new JFormattedTextField();
		fNIteraciones.setText("10");
		fNIteraciones.setBounds(100, 170, 175, 19);
		add(fNIteraciones);
		
		fMatrixtxt = new JFormattedTextField();
		fMatrixtxt.setText("matrix.txt");
		fMatrixtxt.setBounds(500, 90, 175, 19);
		add(fMatrixtxt);
		
		fDirect = new JFormattedTextField();
		fDirect.setText("Imagenes");
		fDirect.setBounds(500, 170, 175, 19);
		add(fDirect);
		
		ffCatastrof = new JFormattedTextField();
		ffCatastrof.setText("500");
		ffCatastrof.setBounds(300, 170, 175, 19);
		add(ffCatastrof);
		
		btnOkk = new JButton("OK");
		btnOkk.setBounds(360, 300, 80, 25);
		add(btnOkk);
		
		lblNIteraciones = new JLabel("Cantidad Iteraciones (Nº entero)");
		lblNIteraciones.setBounds(100, 150, 175, 13);
		add(lblNIteraciones);
		
		lblIntrdoduceLasVariables = new JLabel("Intrdoduce los parámetros de la simulación y nombra la carpeta para almacenar los resultados");
		lblIntrdoduceLasVariables.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblIntrdoduceLasVariables.setBounds(136, 30, 521, 18);
		add(lblIntrdoduceLasVariables);
		
		lblTablaMut = new JLabel("Tabla Mutaciones (\"nombre.txt\")");
		lblTablaMut.setBounds(500, 70, 175, 13);
		add(lblTablaMut);
		
		lblCarpetaSalida = new JLabel("Carpeta para Imágenes (\"nombre\")");
		lblCarpetaSalida.setBounds(500, 150, 175, 13);
		add(lblCarpetaSalida);
		
		lblFrecuenciaCatstrofesn = new JLabel("Frecuencia Catástrofes (Nº entero)");
		lblFrecuenciaCatstrofesn.setBounds(300, 150, 175, 13);
		add(lblFrecuenciaCatstrofesn);
		
		
	}

	public JButton getBtnOkk() {
        return btnOkk;
    }

    public String getNmutaciones() {
        return fNmutaciones.getText();
    }

    public String getCantEInicial() {
        return fCantEInicial.getText();
    }

    public String getNIteraciones() {
        return fNIteraciones.getText();
    }

    public String getMatrixtxt() {
        return fMatrixtxt.getText();
    }

    public String getDirect() {
        return fDirect.getText();
    }

    public String getCatastrof() {
        return ffCatastrof.getText();
    }
    
	
	public void addBtnOkkListener(ActionListener listener) {
        btnOkk.addActionListener(listener);
    }
}
