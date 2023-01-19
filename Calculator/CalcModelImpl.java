package Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel{
	
	private boolean editable=true;
	private boolean positive=true;
	private String string="";
	private double value=0;
	private String frozenValue;
	private Double activeOperand;
	private DoubleBinaryOperator pendingBinaryOperation;
	private List<CalcValueListener> listeners = new ArrayList<>();


	@Override
	public void addCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		listeners.add(l);
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return positive ? value : value*-1;
	}

	@Override
	public void setValue(double value) {
		this.value = Math.abs(value);
		editable = false;
		positive = value>=0;
		string = Double.toString(this.value);
		frozenValue = null;
		listeners.forEach((l) -> l.valueChanged(this));

		
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		string = "";
		value = 0;
		positive = true;
		frozenValue = null;
		editable = true;
		listeners.forEach((l) -> l.valueChanged(this));
		
	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = null;
		pendingBinaryOperation = null;
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException("Calculator is not editable");
		}
		positive = !positive;
		frozenValue = null;
		listeners.forEach((l) -> l.valueChanged(this));
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException("Calculator is not editable");
		}
		if (string.equals(""))throw new CalculatorInputException();
		
		if (string.contains(".")) {
			throw new CalculatorInputException("Already contains decimal point");
		}
		string+=".";
		frozenValue = null;
		listeners.forEach((l) -> l.valueChanged(this));
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) {
			throw new CalculatorInputException("Calculator is not editable");
		}
		if(string.equals("0") && digit==0) return;
		
		if(string.equals("0")) string="";
		
		if(string.length()==308) throw new CalculatorInputException();
		
		String temp = string;
		string+=digit;
		try {
			value = Double.parseDouble(string);
		} catch (NumberFormatException e) {
			string = temp;
			throw new CalculatorInputException("");
		}

		listeners.forEach((l) -> l.valueChanged(this));
		frozenValue = null;
		
	}

	@Override
	public boolean isActiveOperandSet() {
		// TODO Auto-generated method stub
		return activeOperand!=null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Active operand is not set");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		// TODO Auto-generated method stub
		this.activeOperand = activeOperand;
		
	}

	@Override
	public void clearActiveOperand() {
		// TODO Auto-generated method stub
		activeOperand = null;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		// TODO Auto-generated method stub
		return pendingBinaryOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		// TODO Auto-generated method stub
		this.pendingBinaryOperation = op;
	}

	@Override
	public String toString() {
		if (frozenValue!=null) return frozenValue;

		if (string.equals("")) {
			if (positive) {
				return "0";
			} else {
				return "-0";
			}
		}

		if (positive) {
			return string;
		} else {
			return "-"+string;
		}
	}
	
	@Override
	public
	void freezeValue(String value){
		frozenValue = value;

	}
	@Override
	public
	boolean hasFrozenValue(){
		return frozenValue!=null;
	}


}
