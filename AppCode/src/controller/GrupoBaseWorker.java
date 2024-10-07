package controller;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.images.Resolutions;

import model.Funciones;
import model.Generados;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;



public class GrupoBaseWorker extends SwingWorker<List<Generados>, Graph> {
    private Generados nuevoGrupo;
    private float[][] probIndividuo;
    private int nrondas, cantEIni, frecCat;
    private String outputDir;

    public GrupoBaseWorker(Generados nuevoGrupo1, float[][] probIndividuo, int nrondas, int fCantEInicial, String directorio, int frecCatastrof) {
    	this.nuevoGrupo=nuevoGrupo1;
    	this.probIndividuo=probIndividuo;
    	this.nrondas=nrondas;
    	this.cantEIni=fCantEInicial;
    	this.outputDir="./"+directorio+"/";
    	this.frecCat=frecCatastrof;
	}

	@Override
    protected List<Generados> doInBackground() throws Exception {
		
		System.setProperty("org.graphstream.ui", "swing");
		FileSinkImages f = FileSinkImages.createDefault();
        f.setOutputType(FileSinkImages.OutputType.PNG);
        f.setResolution(Resolutions.UHD_4K);
        f.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
        
		LinkedList<Generados> listOfGenerados=new LinkedList<>();
		
		int frecCatastrof=frecCat;
		
		Generados nGrupo=nuevoGrupo;
    	listOfGenerados.add(nGrupo.clone());
    	String filePath=null;
		// Recrea una generación por cada ronda
		for(int i=1;i<=nrondas;i++) {
        	//System.out.println("gen i:"+i);
			nGrupo.Generacion(probIndividuo,i);
        	
        	// Recorre cada población de una generación y guarda el número de individuos de la población

	        	if(i%frecCatastrof==0) {

	        		Random random = new Random();
	                int binario = random.nextInt(2);
	                int posicion=random.nextInt(nGrupo.getEspecieID(0).length);
	                for(int j=0;j<nGrupo.size();j++) {

	                	if(nGrupo.getEspecieID(j)[posicion]==binario) {
	                		nGrupo.setCantID(j, 0);
	                	}
	                	
	                }
	        	}
	        	
	        Generados prov=nGrupo.clone();
	        
	        Graph grafoprueba = Funciones.GenerarGrafoG(nGrupo, cantEIni);
	        publish(grafoprueba);
	        
	        // captura de las imagenes 1 a nrondas
	        filePath =outputDir+"graph"+i+".png";		
	        f.writeAll(grafoprueba, filePath);
	        filePath =outputDir+"Data"+i+".arff";
	     
            
	        listOfGenerados.add(prov);

        }
		nGrupo.imprimirDatosGenerados(filePath, probIndividuo, nrondas);
		return listOfGenerados;
    }

	protected void done() {
		try {
			
			Funciones.GenerarLineas((LinkedList<Generados>) get());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
