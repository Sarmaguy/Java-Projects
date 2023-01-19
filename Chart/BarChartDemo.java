package Chart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class BarChartDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChart chart;
	
	public BarChartDemo(BarChart chart) {
		this.chart=chart;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(20, 50);
		setSize(500, 500);
		initGUI();
	}

	private void initGUI() {
		List<XYValue> l = new ArrayList<>();
		l.add(new XYValue(1, 8));
		l.add(new XYValue(2, 5));
		l.add(new XYValue(3, 16));
		l.add(new XYValue(4, 13));
		l.add(new XYValue(5, 22));
		l.add(new XYValue(6, 2));
		
		BarChartComponent comp = new BarChartComponent(chart);
		getContentPane().add(comp);
	}
	
	public static void main(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Invalid number of arguments");
		}
		
		Scanner s = null;
		try {
			s = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xDesc = s.nextLine();
		String yDesc = s.nextLine();
		String[] dots = s.nextLine().split("\\s+");
		List<XYValue> l = new ArrayList<>();
		for (int i = 0; i < dots.length; i++) {
			String[] xy = dots[i].split(",");
			l.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		String min = s.nextLine();
		String max= s.nextLine();
		String diff = s.nextLine();
		int ymin = Integer.parseInt(min);
		int ymax = Integer.parseInt(max);
		int d = Integer.parseInt(diff);
		BarChart chart = new BarChart(l, xDesc, yDesc, ymin, ymax, d);
		

		
		
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(chart).setVisible(true);
		});
	}
	
	
}
