package Chart;

import java.util.List;

public class BarChart {
	private List<XYValue> values;
	private String xDescription;
	private String yDescription;
	private int yMin;
	private int yMax;
	private int yDiff;

	public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int yDiff) {
		
		if (yMin < 0) {
			throw new IllegalArgumentException("yMin must be greater than 0");
		}

		if (yMax < yMin) {
			throw new IllegalArgumentException("yMax must be greater than yMin");
		}

		if (yDiff < 1) {
			throw new IllegalArgumentException("Difference must be greater than 1");
		}

		values.forEach(value -> {
			if (value.getY() < yMin) 
				throw new IllegalArgumentException("Value must be greater than yMin");
			
		});

		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDiff = yDiff;
	}


	public List<XYValue> getValues() {
		return values;
	}

	public String getxDescription() {
		return xDescription;
	}

	public String getyDescription() {
		return yDescription;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getyDiff() {
		return yDiff;
	}
}
