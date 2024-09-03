package view;

import java.awt.EventQueue;
import javax.swing.JTable;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class Variables extends JPanel{

	private JPanel panel, panel_1, panel_2, panel_3;
	private JFrame frame;
	private JTable tableMutaciones, tablaCantidades;
	private JScrollPane scrollPane, scrollPane2;
	private JScrollBar verticalScrollBar1, verticalScrollBar2;
	private JLabel lblIndBase;
	private JButton btnOk, btnCancel;
	private FlowLayout flowLayout;
	private JSplitPane splitPane;
	
	int Nmutaciones, CantEInicial, Niteraciones, frecC;
	String txt, direc;
	
	private DefaultTableModel model, model2;

	public Variables(int fCantEInicial, int fNmutaciones, int nIt, String mtxt, String dir, int frec) {
		this.Nmutaciones=fNmutaciones;
		this.CantEInicial=fCantEInicial;
		this.Niteraciones=nIt;
		this.txt=mtxt;
		this.direc=dir;
		this.frecC=frec;
		initialize(CantEInicial, Nmutaciones, Niteraciones, txt, direc, frecC);
	}

	private void initialize(int fCantEInicial, int fNmutaciones, int fNiteraciones, String txt, String dir, int frec) {
		setLayout(new BorderLayout(0, 0));
		
		Short[][] data = new Short[fCantEInicial][fNmutaciones];
		for (int i = 0; i < fCantEInicial; i++) {
			for (int j = 0; j < fNmutaciones; j++) {
                    data[i][j] = 0;
            }
        }
		
		String[] columnNames = new String[fNmutaciones];
        for (int i = 0; i < fNmutaciones; i++) {
            columnNames[i] = String.valueOf(i + 1);
        }
        
        Short[][] data2=new Short[fCantEInicial][2];
        for (int i = 0; i < fCantEInicial; i++) {
			for (int j = 0; j < 2; j++) {
                data2[i][j] = 20;
			}
        }
        
        String[] columnNames2 = {"CI", "CA"};
        
		model=new DefaultTableModel(data, columnNames);
		tableMutaciones = new JTable(model);
		tableMutaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int columnWidth = 20; // set your desired fixed width here
        for (int i = 0; i < tableMutaciones.getColumnCount(); i++) {
            TableColumn column = tableMutaciones.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
        }
		
		scrollPane=new JScrollPane(tableMutaciones);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		model2= new DefaultTableModel(data2, columnNames2);
		tablaCantidades=new JTable(model2);
		tablaCantidades.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnWidth=30;
        for (int i = 0; i < tablaCantidades.getColumnCount(); i++) {
            TableColumn column = tablaCantidades.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
        }
        
        scrollPane2=new JScrollPane(tablaCantidades);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, scrollPane2);        
        add(splitPane, BorderLayout.CENTER);
        splitPane.setDividerLocation(600);
        verticalScrollBar1 = scrollPane.getVerticalScrollBar();
        verticalScrollBar2 = scrollPane2.getVerticalScrollBar();
        verticalScrollBar1.addAdjustmentListener(e -> verticalScrollBar2.setValue(e.getValue()));
        verticalScrollBar2.addAdjustmentListener(e -> verticalScrollBar1.setValue(e.getValue()));        		
		
		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		lblIndBase = new JLabel("Código genético de los Individuos (En Binario)");
		panel.add(lblIndBase);
		
		panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);

		btnOk = new JButton("Ok");
		panel_1.add(btnOk);
		
		btnCancel = new JButton("Cancelar");
		panel_1.add(btnCancel);
		
		panel_2 = new JPanel();
		add(panel_2, BorderLayout.WEST);
		
		panel_3 = new JPanel();
		flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		add(panel_3, BorderLayout.EAST);
	}

	public JButton getBtnOk() {
        return btnOk;
    }
	
	public JButton getBtnCancel() {
		return btnCancel;
	}

	public void setVisible(boolean b) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Variables window = new Variables(CantEInicial, Nmutaciones, Niteraciones, txt, direc, frecC);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public int getCantEInicial() {
		return CantEInicial;
	}

	public int getNMutaciones() {
		return Nmutaciones;
	}

	public int getNIteraciones() {
		return Niteraciones;
	}

	public String getMatrixtxt() {
		return txt;
	}

	public String getDirect() {
		return direc;
	}

	public int getCatastrof() {
		return frecC;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public DefaultTableModel getModel2() {
		return model2;
	}

	public Component getTableMutaciones() {
		return tableMutaciones;
	}
	
	public int getRow(MouseEvent e) {
		return tableMutaciones.rowAtPoint(e.getPoint());
	}
	
	public int getColumn(MouseEvent e) {
		return tableMutaciones.columnAtPoint(e.getPoint());
	}

}
