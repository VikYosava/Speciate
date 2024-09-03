package model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;



public class Generados {
	
	private static final float PMutBase = 0.5f; //	[0]	 Minimo 0, máximo 1
	private static final float CapAlimBase = 0.3f; //[1]	 Minimo 0, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
	private static final float CostAlimBase = 0.25f; //	[2]	 Mínimo 0, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
    private static final float CMovBase = 0.6f; //	[3]	 Minimo 0, máximo 1 (Capacidad de recolección, incluir comer comida dentro del movimiento)

	private LinkedList<PoblacionPorEspecie> elements; 
    private int cont;
    
    public Generados() {
    	elements= new LinkedList<>();
    	cont=1;
    }

    public void addGenerado(int number, short[] binaryCode, String padre, int generacion, float alimento) {
    	PoblacionPorEspecie element = new PoblacionPorEspecie(number, binaryCode, padre, generacion, alimento, cont);
        elements.add(element);
        cont++;
    }
    
    public void addGenerado(PoblacionPorEspecie element) {
        element.setPosicion(cont);
    	elements.addLast(element);
    	cont++;
    }

    public void imprimirDatosGenerados(String filePath, float[][] probIndividuo, int nGeneraciones) throws IOException {
        
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		writer.write("@relation poblaciones\n\n");
		for(int i=0;i<elements.get(0).getEspecie().length;i++) {
			writer.write("@attribute c"+i+" {0, 1}\n");
		}
		writer.write("@attribute mutationProb numeric\n");
		writer.write("@attribute refoodCap numeric\n");
		writer.write("@attribute alimentCost numeric\n");
		writer.write("@attribute movementCap numeric\n");
		writer.write("@attribute generation integer\n");
		writer.write("@attribute size integer\n");
		writer.write("@attribute success {no, yes}\n\n");

		writer.write("@data\n");		
    	for (PoblacionPorEspecie info : elements) {
            info.escribirDatos(writer, filePath, probIndividuo, nGeneraciones);
        }
    	
    	writer.close();
    }
    
    public Generados clone() {
        Generados cloned = new Generados();
        for (PoblacionPorEspecie poblacion : this.elements) {
            // Clona cada elemento lo agrega a la lista clonada
            PoblacionPorEspecie clonedPoblacion = new PoblacionPorEspecie(
                    poblacion.getCant(),
                    poblacion.getEspecie(),
                    poblacion.getPadre(),
                    poblacion.getGeneracion(),
                    poblacion.getAlimento(),
                    poblacion.getPosicion()
            );

            cloned.addGenerado(clonedPoblacion);
        }
        return cloned;
    }
    
    public void setCant(int i, int cant) {
    	elements.get(i).setCant(cant);
    }

    public PoblacionPorEspecie get(int i) {
        if (i >= 0 && i < elements.size()) {
            return elements.get(i);
        } else {
            return null;
        }
    }
    
    public int getTotal() {
    	int total=0;
    	for (PoblacionPorEspecie poblacion : this.elements) {
            total=total+poblacion.getCant();
        }
    	return total;
    }
    
    public int getPosicion(int randFromTotal) {
    	int pos=0;
    	while(pos<this.elements.size() && randFromTotal>0){
    		randFromTotal=randFromTotal-this.elements.get(pos).getCant();
    		if(randFromTotal>0) {
    			pos++;
    		}
    	}
    	return pos;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }    

    public int size() {
        return elements.size();
    }
    
    public int getCantID(int index) {
        return elements.get(index).getCant();
    }
    
    public void setCantID(int index, int cant) {
    	elements.get(index).setCant(cant);
    }
    
    public int[] getCant() {
    	int[] cantidades = new int[elements.size()];
    	for(int i=0;i<elements.size();i++) {
    		cantidades[i]=elements.get(i).getCant();
    	}
    	return cantidades;
    }
    
    public String getPadreID(int index) {
    	return elements.get(index).getPadre();
    }
    
    public float getAlimentoID(int index) {
        return elements.get(index).getAlimento();
    }
    
    public void setAlimentoID(int index, float cant) {
    	elements.get(index).setAlimento(cant);
    }
    
    public short[] getEspecieID(int index) {
    	return elements.get(index).getEspecie();
    }
    
    public int getGeneracionID(int index) {
    	return elements.get(index).getGeneracion();
    }
    
    public void setGeneracionID(int index, int gener) {
    	elements.get(index).setGeneracion(gener);
    }
    
    public int getPosicionID(int index) {
    	return elements.get(index).getPosicion();
    }
    
    
    public static float[] RandomIntegerArray(int nmutaciones, float base, float max, float deviation) {
        float[] array = new float[nmutaciones]; // nmutaciones también se utilizará para dividir cada valor
        //la suma de todos debe ser aproximadamente la media
        Random random = new Random();

        // Parámetros de ajuste para nextGaussian
        float nmean = base; // media

        for (int i = 0; i < nmutaciones; i++) {
            float value;
            do {
                // Generar un número aleatorio basado en una distribución gaussiana
                value = (float) (random.nextGaussian() * deviation + nmean);
            } while (value < -max || value > max); // Asegurar que el valor esté dentro del rango deseado
            array[i] = value;
        }
        return array;
    }
    
	public Generados Generacion(float[][] probIndividuo, int generacion) {
    	Generados nuevoGrupo= this;
    	int tamanioGen=nuevoGrupo.size(); // De esta forma los que nacen nuevos no atacan ni se reproducen hasta la siguiente generación
    	for (int i = 0; i<tamanioGen;i++) {
    		int cantInd=nuevoGrupo.getCantID(i);
    		float alimento=nuevoGrupo.getAlimentoID(i);
    		
    		if(cantInd>0) {
    			
    		float CMov=CMovBase;
    		float CapAlim=CapAlimBase;
    		float CostAlim=CostAlimBase;
    		float PMut=PMutBase;
    	
			for(int k = 0; k<probIndividuo[3].length; k++) {
				PMut=PMut+probIndividuo[0][k]*nuevoGrupo.getEspecieID(i)[k];
				CapAlim=CapAlim+probIndividuo[1][k]*nuevoGrupo.getEspecieID(i)[k];
				CostAlim=CostAlim+probIndividuo[2][k]*nuevoGrupo.getEspecieID(i)[k];
				CMov=CMov+probIndividuo[3][k]*nuevoGrupo.getEspecieID(i)[k];
			}
			if(CMov<0) {
				CMov=0;
			}
			else if(CMov>1) {
				CMov=1;
			}
			
			if(CapAlim<0) {
				CapAlim=0;
			}
			
			if(CostAlim<0) {
				CostAlim=0;
			}

			if(PMut<0) {
				PMut=0;
			}
    		// PROCESO DE VIDA, CALCULA CUANTOS SOBREVIVEN Y LA CANTIDAD DE ALIMENTO QUE OBTIENEN DE MUTACIONES
			
			float NuevoAlimento=cantInd*CMov*CapAlim; // Si cantmov es 0 significa que no han interactuado por cuenta propia
												// Debe haber un caso a parte si tienen forma de generar alimento por si mismos
											// Si reparto es 1, no se da alimento a los individuos que no lo obtengan
										// El núm max de reparto sería el de individuos de la población, pero en la práctica
									// este número no hará falta casi, y es dificil de calcular cada vez, por lo que 
								// es mejor fijar alguna heurística. Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
			float AlimentoConsumido=cantInd*CMov*CostAlim;
			float DiferenciaAlimento=NuevoAlimento-AlimentoConsumido;
			alimento=alimento+DiferenciaAlimento;
			if(DiferenciaAlimento<0) {
				cantInd=(int) (cantInd+DiferenciaAlimento);
				// El número de la cantidad de individuos solo se reduce en esta parte, debido a que la reproducción se
				// lleva a cabo siempre, sea la cantidad de alimento mayor o no, a menos que mueran todos los individuos
				// Y esta reproducción debe calcularse de manera más compleja
			}
			if(cantInd>0) {
				float PMuttot=PMut;
				if(PMut>1) {
					PMut=1;
				}
				// Se determina cuántos se van a reproducir, se asume que crear un individuo igual cuesta el 
				// mismo alimento que sobrevivir
				// Solo se puede reproducir cada individuo una vez, no puede haber
				// un número de individuos que se reproducen mayor del número de individuos que existen
				int Nreprod=cantInd;
				if(CostAlim>0) {
					Nreprod=(int) ((alimento/CostAlim));
				}
				if(Nreprod>cantInd){
					Nreprod=cantInd;
				}
				alimento=alimento-Nreprod*CostAlim;
				for (int j=0; j<((int)(Nreprod*PMut)); j++) {
	    			// LA REPRODUCCIÓN AQUI TIENE QUE SER SOLO DE LOS QUE SÍ MUTAN
					short[] Padre=nuevoGrupo.getEspecieID(i).clone();
					String padreStr=java.util.Arrays.toString(Padre)+i;
	    			short[] Hijo=Reproducir(nuevoGrupo.getEspecieID(i),PMuttot);
	    			nuevoGrupo.addGenerado(1, Hijo, padreStr, generacion, NuevoAlimento);
	    		}
				nuevoGrupo.setAlimentoID(i,alimento);
				nuevoGrupo.setCantID(i,(int) (cantInd+(Nreprod-Nreprod*PMut)));
			}
    		}
    		if(nuevoGrupo.getCantID(i)<0) {
    			nuevoGrupo.setCantID(i, 0);
    		}
    	}
		return nuevoGrupo;
    }
	public static short[] Reproducir(short[] especie, float PMut) {
    short[] hijo=especie.clone();
    Random random = new Random();
    	while(PMut>0) {
        // Generar un número aleatorio entre 0 y x (ambos inclusive)
        int locmutacion = random.nextInt(especie.length); // LA POSICIÓN DEPENDE DEL TAMAÑO DE LA MATRIZ DE 
        							// COSAS ALEATORIAS, CORTAR A LA ALTURA DE MUTACIONES ESPECÍFICAS
        hijo[locmutacion]=1;
        PMut--;
    	}
    	return hijo;
	}
}