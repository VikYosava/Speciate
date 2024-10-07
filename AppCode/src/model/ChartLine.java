package model;
import java.awt.Color;
import java.util.List;

public class ChartLine {	
	private Color color;
	private List<Integer> points;
	
	ChartLine (Color c, List<Integer> points){
		this.color = c;
		this.points = points;
	}	
	
	public Color getColor() {		
		return color;
	}
	
	public Integer get(int i) {
		return points.get(i);
	}
	
	public List<Integer> getPoints(){
		return points;
	}
	public void setPoints(List<Integer> points) {
		this.points = points;
	}
	
	public int size() {
		return points.size();
	}
}
