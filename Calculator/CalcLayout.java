package Calculator;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

public class CalcLayout implements LayoutManager2{
	private int spacing;
	private HashMap<RCPosition, Component> components = new HashMap<RCPosition, Component>();
	
	public CalcLayout(int spacing) {
		this.spacing=spacing;
	}
	
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		throw new  UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (RCPosition rcp : components.keySet()) {
			if (components.get(rcp).equals(comp)) {
				components.remove(rcp);
				break;
			}
		}
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int maxWidth=0;
		int maxHeight=0;
		
		for (Entry<RCPosition, Component> e : components.entrySet()) {
			Dimension d= e.getValue().getPreferredSize();
			if(d != null) {
				int width=d.width;
				int height = (int) d.getHeight();
				if(e.getKey().getRow() == 1 && e.getKey().getColumn() == 1) {
					width= (width-4*spacing)/5 +1;
				}
				if (width>maxWidth) maxWidth=width;

				if (height>maxHeight) maxHeight=height;
			}
		}
		Insets inset = parent.getInsets();
		maxHeight = maxHeight*5 + spacing*4 + inset.top + inset.bottom;
		maxWidth = maxWidth*7 + spacing*6 + inset.left + inset.right;
		return new Dimension(maxWidth, maxHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int maxWidth=0;
		int maxHeight=0;
		
		for (Entry<RCPosition, Component> e : components.entrySet()) {
			Dimension d= e.getValue().getMinimumSize();
			if(d != null) {
				int width=d.width;
				int height = (int) d.getHeight();
				if(e.getKey().getRow() == 1 && e.getKey().getColumn() == 1) {
					width= (width-4*spacing)/5 +1;
				}
				if (width>maxWidth) maxWidth=width;

				if (height>maxHeight) maxHeight=height;
			}
		}
		Insets inset = parent.getInsets();
		maxHeight = maxHeight*5 + spacing*4 + inset.top + inset.bottom;
		maxWidth = maxWidth*7 + spacing*6 + inset.left + inset.right;
		return new Dimension(maxWidth, maxHeight);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		int maxWidth=0;
		int maxHeight=0;
		
		for (Entry<RCPosition, Component> e : components.entrySet()) {
			Dimension d= e.getValue().getMaximumSize();
			if(d != null) {
				int width=d.width;
				int height = (int) d.getHeight();
				if(e.getKey().getRow() == 1 && e.getKey().getColumn() == 1) {
					width= (width-4*spacing)/5 +1;
				}
				if (width>maxWidth) maxWidth=width;

				if (height>maxHeight) maxHeight=height;
			}
		}
		Insets inset = target.getInsets();
		maxHeight = maxHeight*5 + spacing*4 + inset.top + inset.bottom;
		maxWidth = maxWidth*7 + spacing*6 + inset.left + inset.right;
		return new Dimension(maxWidth, maxHeight);
	}


	@Override
	public void layoutContainer(Container parent) {
		Insets inset = parent.getInsets();
		double width = parent.getSize().getWidth() - 6*spacing - inset.left - inset.right;
		double height = parent.getSize().getHeight() - 4*spacing - inset.top - inset.bottom;
		int columnSize = (int) (width/7);
		int rowSize = (int) (height/5);
		int columnAnomaly = (int) (width%7);
		int rowAnomaly = (int) (height%5);
		int x = inset.left;
		int y = inset.top;
		
		for (int i = 1; i < 6; i++) {
			int boxHeight = rowSize;
			x=inset.left;
			
			if (i==1 || i==5) 
				if (rowAnomaly >=3) boxHeight++;
			
			else if (i==2 || i==4) {
				if (rowAnomaly == 2 || rowAnomaly == 4) boxHeight++;
			}
			else if (rowAnomaly == 1 || rowAnomaly == 3) boxHeight++;
			
			for(int j = 1; j < 8; j++) {
				int boxWidth = columnSize;
				Component comp = components.get(new RCPosition(i, j));
				
				if (i==1 && j==1 ) {
					boxWidth = columnSize*5 + 4*spacing;
					if (columnAnomaly==1) boxWidth+=1;
					else if (columnAnomaly==2 || columnAnomaly==3) boxWidth+=2;
					else if (columnAnomaly==4) boxWidth+=3;
					else if (columnAnomaly==5 || columnAnomaly==6) boxWidth+=4;
					
				}
				else {
					if (j==1 || j==7) {
						if (columnAnomaly==3 || columnAnomaly==6 || columnAnomaly==4) 
								boxWidth++;
					}
					else if (j==2 || j==6) {
						if (columnAnomaly==5 || columnAnomaly==6)
							boxWidth++;
					}
					else if (j==3 || j==5) {
						if (columnAnomaly==6 || columnAnomaly==4)
							boxWidth++;
					}
					else {
						if(columnAnomaly%2==1) boxWidth++;
						
					}
					
				}
				if(comp != null && comp.isVisible()) {
					comp.setBounds(x,y,boxWidth,boxHeight);
				}
				if (!(i==1 && j>1 && j<6)) x+= boxWidth + spacing;
				
				
				
			}
			y+= boxHeight + spacing;
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition rcp;
		
		if (constraints instanceof RCPosition) rcp = (RCPosition) constraints;
		
		else if (constraints instanceof String) rcp = RCPosition.parse((String) constraints);
		
		else throw new IllegalArgumentException();
		
		int row = rcp.getRow();
		int column = rcp.getColumn();
		
		if (row < 1 || row > 5) throw new CalcLayoutException();
		
		if (column < 1 || column > 7) throw new CalcLayoutException();
		
		if (row == 1 && column > 1 && column < 6) throw new CalcLayoutException();
		
		if (components.containsKey(rcp) || components.containsValue(comp)) throw new CalcLayoutException();
		
		components.put(rcp, comp);
		
	}


	@Override
	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}

}
