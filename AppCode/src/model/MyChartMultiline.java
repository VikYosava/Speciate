package model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

public class MyChartMultiline extends JComponent {
        private Map <Integer, ChartLine> chartMatrix;
        //Clave: identificado de la especie
        // Chartline guarda el color de la linea de esa especie y los puntos
        
        private LinkedList<Generados> listOfGenerados;
        private List<String> codigos;
        private int steps; 
        private int maxValue;
                
        MyChartMultiline(LinkedList<Generados> listOfGenerados){
        	chartMatrix = new HashMap<>();
        	this.listOfGenerados=listOfGenerados;
        	this.steps = listOfGenerados.size();
        	this.maxValue = 0;
        	this.codigos=new ArrayList<>();
        	for(int z=0;z<listOfGenerados.get(steps-1).size();z++) {
        		
        		int especiesGeneracion =listOfGenerados.get(steps-1).getGeneracionID(z);
        		String especiesText = " Pos. Mut.: ";
        		for(int h=0;h<listOfGenerados.get(steps-1).getEspecieID(z).length;h++) {
        			if(listOfGenerados.get(steps-1).getEspecieID(z)[h]==1){
            			int hnum=h+1;
        				especiesText+=hnum+" ";
        			}
        		}
        		
        		this.codigos.add("Gen.: "+especiesGeneracion+ especiesText);
        	}
        	
            addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    handleMouseMove(e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    // No necesitamos manejar arrastrar en este caso
                }
            });
        	
        }
        
        public void updateList() {
        	for (int j = 0; j < listOfGenerados.get(steps-1).size(); j++) {
          		 List<Integer> populationData = new ArrayList<>();

              	 for (int i = 0; i < steps; i++) {
              		 
              		 if(j<listOfGenerados.get(i).size()) {
                  		 populationData.add(listOfGenerados.get(i).getCantID(j));
              		 }

              	 }
              	ChartLine aux = chartMatrix.get(j);
                if(aux == null) {
                	float r = (float) Math.random();
                    float g = (float) Math.random();
                    float b = (float) Math.random();
                	
                	Color col = new Color(r, g, b);
                    aux = new ChartLine(col, populationData);
                }else {
                	aux.setPoints(populationData);
                	
                }
                chartMatrix.put(j, aux);
                
                int temp = Collections.max(populationData);
                if(maxValue< temp)
            		maxValue = temp;
                repaint();
               }
        }
        
        /*public void updateList(int id, List<Integer> line){
            //System.out.println("updateList()");
            ChartLine aux = chartMatrix.get(id);
            if(aux == null) {
            	float r = (float) Math.random();
                float g = (float) Math.random();
                float b = (float) Math.random();
            	
            	Color col = new Color(r, g, b);
                aux = new ChartLine(col, line);
            }else {
            	aux.setPoints(line);
            	
            }
            chartMatrix.put(id, aux);
            
            int temp = Collections.max(line);
            if(maxValue< temp)
        		maxValue = temp;
            repaint();
        }*/

        @Override
        public void paint(Graphics g) {
            //System.out.println("paint()"); 
        	Graphics2D graphics2d = (Graphics2D)g;
        	
        	graphics2d.setColor(Color.DARK_GRAY);
        	graphics2d.fillRect(0, 0, getWidth(), getHeight());
        	
        	graphics2d.setColor(Color.LIGHT_GRAY);
        	int width = getWidth();
            int height = getHeight();
        	
        	graphics2d.drawLine(0, height-30, width, height-30);
            graphics2d.drawLine(50,0,50,height);
            
            graphics2d.drawString("Generaciones", width / 2, height - 5);
            graphics2d.drawString("Cantidad de Individuos", 10, 10);
            
            drawGrid(graphics2d);
            
            for(ChartLine l : chartMatrix.values()) {             	
            	paintMe(graphics2d, l);            	
            }
            
            graphics2d.setColor(Color.LIGHT_GRAY);
            float hDiv = (float) (width - 50) / (float) (steps - 1);
            for (int i = 0; i < steps; i++) {
                graphics2d.drawString(String.valueOf(i), (int) (i * hDiv) + 50, height - 15);
            }
            
            graphics2d.setColor(Color.LIGHT_GRAY);
            float vDiv = (float) (height - 50) / (float) maxValue;
            float yStep = maxValue/10;
            yStep+=50-yStep%10;
            for (int i = 0; i <= maxValue; i += yStep) {
                int yPosition = height - 30 - (int) (i * vDiv);
                if (yPosition >= height) {
                    yPosition = height - 1; // Ajuste para evitar valores fuera de los límites
                }
                graphics2d.drawString(String.valueOf(i), 10, yPosition);
            }
            
        }
        
        private void drawGrid(Graphics2D graphics2d) {
            graphics2d.setColor(Color.LIGHT_GRAY);
            int width = getWidth();
            int height = getHeight();
            
            // Dibuja líneas verticales
            float hDiv = (float) (width - 50) / (float) (steps - 1);
            for (int i = 0; i < steps; i++) {
                int xPosition = (int) (i * hDiv) + 50;
                graphics2d.drawLine(xPosition, 0, xPosition, height - 30);
            }

            // Dibuja líneas horizontales cada 50 unidades
            float vDiv = (float) (height - 50) / (float) maxValue;
            float yStep = maxValue/10;
            yStep+=50-yStep%10;
            for (int i = 0; i <= maxValue; i += yStep) {
                int yPosition = height - 30 - (int) (i * vDiv);
                graphics2d.drawLine(50, yPosition, width, yPosition);
            }
        }
        
        private void paintMe(Graphics g, ChartLine line){
            g.setColor(line.getColor());
            
            int width = getWidth()-50;
            int height = getHeight()-50;
            
            float hDiv = (float)width/(float)(line.size()-1);
            float vDiv = (float)height/(float)maxValue;
            
            for(int i=0; i<line.size()-1; i++){
            	
            	int value1 = line.get(i) != null ? line.get(i) : 0;
            	int value2 = line.get(i + 1) != null ? line.get(i + 1) : 0;
            	
            	g.drawLine(
            			(int) (i*hDiv)+50,
            			getHeight()-30-(int)(value1*vDiv),
            			(int)((i+1)*hDiv)+50,
            			getHeight()-30-(int)(value2*vDiv)
            		);

            }
            g.drawRect(50, 0, getWidth() - 50, getHeight() - 30);
        }
        
        public void handleMouseMove(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            for (Map.Entry<Integer, ChartLine> entry : chartMatrix.entrySet()) {
                ChartLine line = entry.getValue();

                if (isNearLine(mouseX, mouseY, line)) {
                    setToolTipText(codigos.get(entry.getKey()));
                    return;
                }
            }

            setToolTipText(null);
        }
        
        private boolean isNearLine(int mouseX, int mouseY, ChartLine line) {
            int width = getWidth() - 50;
            int height = getHeight() - 50;

            float hDiv = (float) width / (float) (line.size() - 1);
            float vDiv = (float) height / (float) maxValue;

            int tolerance = 5; // Tolerancia para considerar que el ratón está cerca de la línea

            for (int i = 0; i < line.size() - 1; i++) {
                int value1 = line.get(i) != null ? line.get(i) : 0;
                int value2 = line.get(i + 1) != null ? line.get(i + 1) : 0;

                int x1 = (int) (i * hDiv) + 50;
                int y1 = getHeight() - 30 - (int) (value1 * vDiv);
                int x2 = (int) ((i + 1) * hDiv) + 50;
                int y2 = getHeight() - 30 - (int) (value2 * vDiv);

                // Calcular la distancia desde el punto del ratón a la línea
                double distance = Math.abs((y2 - y1) * mouseX - (x2 - x1) * mouseY + x2 * y1 - y2 * x1)
                        / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

                if (distance <= tolerance) {
                    return true; // El ratón está cerca de esta línea
                }
            }

            return false; // El ratón no está cerca de ninguna línea
        }
    }