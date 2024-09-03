package model;


import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.ViewPanel;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Funciones {

	
	
	public static Viewer GenerarGrafo(Generados grupo, int cantEini) {
        Graph grafoFinal=new SingleGraph("Poblaciones");
		System.setProperty("org.graphstream.ui", "swing");
		// Se crean los nodos padre
		int cantmax=40;	
		int cantmin=3;
	
		
		for(int i=0;i<cantEini;i++) {
			int cant=grupo.getCantID(i);
			short[] especie=grupo.getEspecieID(i);
			String especieString=java.util.Arrays.toString(especie)+i;
			Node node=grafoFinal.addNode(especieString);
			node.setAttribute("Cant", cant);
			if(cant<cantmin) {
				cant=cantmin;
			}else if(cant>cantmax) {
				cant=cantmax;
			}
			
			String color=chooseColor(grupo, i);
			node.setAttribute("ui.style", "size: " + cant + "; fill-color: "+color+";");
			
			node.setAttribute("x", i*50);
			node.setAttribute("y", -i*50);
		}
		double parte=2;
		for(int j=cantEini;j<grupo.size();j++) {
			// Se construye un nuevo nodo, determinamos su tamaño segun la cantidad de individuos
			// de la especie, y el nombre por el código genético+la posición en la lista de especies
			int cant=grupo.getCantID(j);
			short[] especie=grupo.getEspecieID(j);
			String especieString=java.util.Arrays.toString(especie)+j;
			Node node=grafoFinal.addNode(especieString);
			node.setAttribute("Cant", cant);
			if(cant<cantmin) {
				cant=cantmin;
			}else if(cant>cantmax) {
				cant=cantmax;
			}
			
			
			String color=chooseColor(grupo, j);
			node.setAttribute("ui.style", "size: " + cant + "; fill-color: "+color+";");			
			String padre=grupo.getPadreID(j);
			// Comprobamos si el nodo es padre o hijo
			if(padre!=null) {
				// si es hijo, buscamos a su padre y lo unimos con un edge
				Node parentNode=grafoFinal.getNode(padre);
				int cantPadre=((Number) parentNode.getAttribute("Cant")).intValue();
				
				double x=((Number)parentNode.getAttribute("x")).doubleValue();
				double y=((Number)parentNode.getAttribute("y")).doubleValue();
				double radius=cantmax/4;
				
				double angleInc=(j-cantEini)*parte*Math.PI/(grupo.size()-cantEini);
				double angle = angleInc;
				
				x=x+radius*Math.cos(angle);
				y=y+radius*Math.sin(angle);
				
				
				node.setAttribute("x", x);
				node.setAttribute("y", y);
				
				Edge edge=grafoFinal.addEdge(padre+especieString, padre, especieString);
				edge.setAttribute("ui.length", cantPadre+cant);
				edge.setAttribute("ui.style", "fill-color: " + color + ";");
			}

		}

		Viewer viewer = grafoFinal.display();
        viewer.getDefaultView().enableMouseOptions();
        return viewer;
	}
	
	public static Graph GenerarGrafoG(Generados grupo, int cantEini) {
        Graph grafoFinal=new SingleGraph("Poblaciones");
		System.setProperty("org.graphstream.ui", "swing");
		// Se crean los nodos padre
		int cantmax=100;	
		int cantmin=3;
	
		
		for(int i=0;i<cantEini;i++) {
			int cant=grupo.getCantID(i);
			short[] especie=grupo.getEspecieID(i);
			String especieString=java.util.Arrays.toString(especie)+i;
			Node node=grafoFinal.addNode(especieString);
			node.setAttribute("Cant", cant);
			if(cant<cantmin) {
				cant=cantmin;
			}else if(cant>cantmax) {
				cant=cantmax;
			}
			
			String color=chooseColor(grupo, i);
			node.setAttribute("ui.style", "size: " + cant + "; fill-color: "+color+";");
			
			node.setAttribute("x", i*50);
			node.setAttribute("y", -i*50);
		}
		double parte=2;
		for(int j=cantEini;j<grupo.size();j++) {
			// Se construye un nuevo nodo, determinamos su tamaño segun la cantidad de individuos
			// de la especie, y el nombre por el código genético+la posición en la lista de especies
			int cant=grupo.getCantID(j);
			short[] especie=grupo.getEspecieID(j);
			String especieString=java.util.Arrays.toString(especie)+j;
			Node node=grafoFinal.addNode(especieString);
			node.setAttribute("Cant", cant);
			if(cant<cantmin) {
				cant=cantmin;
			}else if(cant>cantmax) {
				cant=cantmax;
			}
			
			
			String color=chooseColor(grupo, j);
			node.setAttribute("ui.style", "size: " + cant + "; fill-color: "+color+";");			
			String padre=grupo.getPadreID(j);
			// Comprobamos si el nodo es padre o hijo
			if(padre!=null) {
				// si es hijo, buscamos a su padre y lo unimos con un edge
				Node parentNode=grafoFinal.getNode(padre);
				int cantPadre=((Number) parentNode.getAttribute("Cant")).intValue();
				
				double x=((Number)parentNode.getAttribute("x")).doubleValue();
				double y=((Number)parentNode.getAttribute("y")).doubleValue();
				double radius=cantmax/4;
				
				double angleInc=(j-cantEini)*parte*Math.PI/(grupo.size()-cantEini);
				double angle = angleInc;
				
				x=x+radius*Math.cos(angle);
				y=y+radius*Math.sin(angle);
				
				
				node.setAttribute("x", x);
				node.setAttribute("y", y);
				
				Edge edge=grafoFinal.addEdge(padre+especieString, padre, especieString);
				edge.setAttribute("ui.length", cantPadre+cant);
				edge.setAttribute("ui.style", "fill-color: " + color + ";");
			}

		}
        return grafoFinal;		        
	}
	
	public static void captureImage(Viewer viewer, String filePath, int i) {
        try {
        	
            ViewPanel view = (ViewPanel) viewer.getDefaultView();

            // Get the graph rendering as a BufferedImage
                	BufferedImage image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    view.paint(image.getGraphics());

                    // Save the image to a file
                    File file = new File(filePath);
					ImageIO.write(image, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void GenerarLineas(LinkedList<Generados> listOfGenerados) {
		
		JFrame frame = new JFrame("Simulación de Poblaciones");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(1, 1)); // Single panel layout

        // Assuming each Generados object has population data accessible
        MyChartMultiline chart = new MyChartMultiline(listOfGenerados);

        // cada lista listOfGenerados.get(i) contiene 1 valor de todas las poblaciones
        
        // cada linea del array debe contener todos los valores de 1 generación
		
        chart.updateList();

		JScrollPane scrollPane = new JScrollPane(chart);
        frame.add(scrollPane);
        frame.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    chart.handleMouseMove(e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    // No necesitamos manejar arrastrar en este caso
                }
            });
        frame.setVisible(true);
	}
	
	public static float[][] readMatrixFromFile(String filename) {
        float[][] matrix = new float[4][];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                if (row < 4) {
                    matrix[row] = new float[values.length];  // assuming square matrix for simplicity
                
                    for (int col = 0; col < values.length; col++) {
                    	matrix[row][col] = Float.parseFloat(values[col]);
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }
	
	private static String chooseColor(Generados grupo, int j){
		short[] esp=grupo.getEspecieID(j);
		int length=grupo.getEspecieID(0).length;
		float valmut=0;
		float valalim=0;
		float valmov=0;
		for(int z=0;z<length;z++) {
			if(z<length/3) {
				valmut+=esp[z];
			}
			else if(z<2*length/3) {
				valalim+=esp[z];
			}
			else {
				valmov+=esp[z];	
			}
		}
		
		int r = (int) (255 * valmut/length*2.5);
	    int g = (int) (255 * valalim/length*2.5);
	    int b = (int) (255 * valmov/length*2.5);
	    
	    String color = String.format("#%02x%02x%02x", r, g, b);

	    return color;
	}
	
}