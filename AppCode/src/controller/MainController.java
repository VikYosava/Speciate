package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableModel;

import model.Configuracion;
import model.Funciones;
import model.Generados;
import view.ConfigInicial;
import view.MainFrame;
import view.Variables;

public class MainController {

	private MainFrame mainFrame;
	private Configuracion configuracion;
	private ConfigInicial configInicial;
	private Variables variablesPanel;
	private DefaultTableModel model, model2;
	
	public MainController(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		
		initialize();
	}

	private void initialize() {
		mainFrame.getConfigInicialPanel().getBtnOkk().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	            configuracion=new Configuracion(
	            		Integer.parseInt(mainFrame.getConfigInicialPanel().getCantEInicial()),
	            		Integer.parseInt(mainFrame.getConfigInicialPanel().getNmutaciones()),
	            		Integer.parseInt(mainFrame.getConfigInicialPanel().getNIteraciones()),
	            		mainFrame.getConfigInicialPanel().getMatrixtxt(),
	            		mainFrame.getConfigInicialPanel().getDirect(),
	            		Integer.parseInt(mainFrame.getConfigInicialPanel().getCatastrof()));
	            
	            variablesPanel = new Variables(configuracion.getCantEInicial(), 
	            		configuracion.getNMutaciones(), configuracion.getNIteraciones(),
	            		configuracion.getMatrixtxt(), configuracion.getDirect(), configuracion.getCatastrof());
	            mainFrame.showVariablesPanel(variablesPanel);
	            
	            addVariablesPanelListeners();
			}
	    });
		
	}
	
	
	private void addVariablesPanelListeners() {
		
		variablesPanel=mainFrame.getVariablesPanel();
		
		variablesPanel.getTableMutaciones().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int inc = e.getButton() == MouseEvent.BUTTON1 ? 1 : -1;

                int row = variablesPanel.getRow(e);
                int col = variablesPanel.getColumn(e);
                if (row >= 0 && col >= 0) {
                    model = variablesPanel.getModel();
                    short newValue = (short) (Short.parseShort(model.getValueAt(row, col).toString()) + inc);
                    model.setValueAt(newValue, row, col);
                }
            }
        });
		
		variablesPanel.getBtnCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				configInicial=new ConfigInicial();
				mainFrame.goBackToMainFrame(configInicial);
				initialize();
			}
		});
		
		variablesPanel.getBtnOk().addActionListener(new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				// Obtén los datos de la tabla de mutaciones
				int fCantEInicial = configuracion.getCantEInicial();
				int fNmutaciones = configuracion.getNMutaciones();
				int fNiteraciones = configuracion.getNIteraciones();
				String txt = configuracion.getMatrixtxt();
				String dir = configuracion.getDirect();
				int frec = configuracion.getCatastrof();
				short[][] IndBase = new short[fCantEInicial][fNmutaciones];
				int[] NPoblacion = new int[fCantEInicial];
				int[] Alimento = new int[fCantEInicial];
				model = variablesPanel.getModel();
				model2 = variablesPanel.getModel2();

				for (int j = 0; j < fCantEInicial; j++) {
					NPoblacion[j] = Integer.parseInt(model2.getValueAt(j, 0).toString());
					Alimento[j] = Integer.parseInt(model2.getValueAt(j, 1).toString());
					for (int i = 0; i < fNmutaciones; i++) {
						Short cellVal = Short.parseShort(model.getValueAt(j, i).toString());
						IndBase[j][i] = cellVal;
					}
				}

				int ronda0 = 0;
			        
				float[][] ProbIndividuo= new float[4][fNmutaciones];

				float[][] ProbIndividuoAux= new float[4][fNmutaciones];
				
				float maxPMut=1;
				float maxCRep=NPoblacion[0]*5;
				float maxCAlim=maxCRep;
				float maxCMov=maxPMut;
			        
			        
				// agresividad del medio: valor por el que puede morir una especie aleatoria cada cierto tiempo
			        // ley de los grandes numeros - gauss, bioestadistica
			    
				ProbIndividuoAux[0]=Generados.RandomIntegerArray(fNmutaciones, 0.4f, maxPMut, 1.5f); // PROB MUTACIÓN
			    ProbIndividuoAux[1]=Generados.RandomIntegerArray(fNmutaciones, 0f, maxCRep, 0.0f); // CAPACIDAD REPARTO bajar mucho
				ProbIndividuoAux[2]=Generados.RandomIntegerArray(fNmutaciones, 1f, maxCAlim, 1f); // COSTE ALIMENTO
				ProbIndividuoAux[3]=Generados.RandomIntegerArray(fNmutaciones, 0.2f, maxCMov, 2f); // CAPACIDAD MOVIMIENTO inhibición por contacto
				
				try {
					ProbIndividuo=Funciones.readMatrixFromFile(txt);
					if(ProbIndividuo==null||ProbIndividuo.length!=4) {
				        throw new IllegalArgumentException("Formato incorrecto: número de filas inválido.");
					}
					for (int i = 0; i < ProbIndividuo.length; i++) {
				        if (ProbIndividuo[i] == null || ProbIndividuo[i].length != fNmutaciones) {
				            throw new IllegalArgumentException("Formato incorrecto: número de columnas inválido en la fila " + i);
				        }
				    }
				}
				catch(Exception e1){
					System.out.println("Error en el formato del archivo: " + e1.getMessage());
					ProbIndividuo=new float[4][];
					ProbIndividuo=ProbIndividuoAux;
				}
			        
			        Generados GrupoBase= new Generados();
			        for(int x=0; x<fCantEInicial;x++) {
			        	String padre=java.util.Arrays.toString(IndBase[x])+x;
			        	GrupoBase.addGenerado(NPoblacion[x], IndBase[x], padre, ronda0, Alimento[x]);
			        }

			        Generados NuevoGrupo1=GrupoBase.clone().Generacion(ProbIndividuo, 1);
			        
			        int nrondas=fNiteraciones;
			        
			        String directorio=dir;
			        int frecCat=frec;
			        			     
			        GrupoBaseWorker worker = new GrupoBaseWorker(NuevoGrupo1,ProbIndividuo,nrondas,fCantEInicial,directorio, frecCat);
			        worker.execute();

	            }
	        });
	    }

}
