package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;

public class Grafo extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	
	/**
	 * Create the panel.
	 */
	public Grafo(JScrollPane scrollPane) {
		this.scrollPane=scrollPane;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(1, 1));
		
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	
}
