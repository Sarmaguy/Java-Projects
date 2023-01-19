package Calculator;

import java.util.Objects;

public class RCPosition {
	
	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		this.row=row;
		this.column=column;
	}
	
	public static RCPosition parse(String text) {
		String[] s = text.split(",");
		
		return new RCPosition(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
	
	
}
