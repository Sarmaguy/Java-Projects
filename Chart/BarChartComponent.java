package Chart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChart chart;
	
	public BarChartComponent(BarChart chart) { 
		this.chart = chart;
	}

	public BarChart getChart() {
		return chart;
	}

	public void setChart(BarChart chart) {
		this.chart = chart;
	}


	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		
		g2d.setColor(Color.BLACK);
		g2d.drawLine(50, getHeight() - 50, getWidth() - 50, getHeight() - 50);
		g2d.drawLine(50, 20, 50, getHeight() - 50);
		
		g2d.fillPolygon(new int[] {getWidth() - 50, getWidth() - 50, getWidth() - 45}, new int[] {getHeight() - 50-5, getHeight() - 50+5, getHeight() - 50}, 3);
		g2d.fillPolygon(new int[] {45, 50, 55}, new int[] {20,15,20}, 3);
		
		
		g2d.drawString(chart.getxDescription(), getWidth() / 2 - chart.getxDescription().length()*4, getHeight() - 20);
		
		AffineTransform defaultAT = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		g2d.drawString(chart.getyDescription(), -getHeight()/2 - chart.getyDescription().length()*4, 20);
		g2d.setTransform(defaultAT);
		
		//g2d.drawString(chart, 35, getHeight() - 35); smisli nes s ovim

		
		int x = 50;
		int xStep = (getWidth() - 100) / chart.getValues().size();
		for (int i = 0; i < chart.getValues().size(); i++) {
			g2d.drawString(""+chart.getValues().get(i).getX(), x+xStep/2, getHeight() - 35);
			x += xStep;
		}
		
		
		int max=chart.getyMax();
		List<Integer> yOs= new ArrayList<>();
		int min=chart.getyMin();
		int current=min;
		int increment = chart.getyDiff();
		while (current<max) {
			yOs.add(current);
			current+= increment;
		}
		yOs.add(max);
		
		int y = getHeight() - 50;
		int yStep = (getHeight() - 50) / yOs.size();
		
		for (int i = 0; i < yOs.size(); i++) {
			g2d.drawString(""+yOs.get(i), 30, y);
			y -= yStep;
		}
		
		int yMax = getHeight() - 50;
		int xMax = 50;
		Color purple = new Color(160, 32, 240);
		for (int i = 0; i < chart.getValues().size(); i++) {
			int height = (int) ((double) chart.getValues().get(i).getY() / max * (getHeight() - 100));
			g2d.setColor(purple);
			g2d.fillRect(xMax, yMax - height, xStep, height);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(xMax, yMax - height, xStep, height);
			xMax += xStep;
		}

		
	}
	

}
