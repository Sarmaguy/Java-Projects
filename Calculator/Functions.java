package Calculator;

import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

public class Functions extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DoubleUnaryOperator operator;
	private DoubleUnaryOperator inverted;
	private boolean invert;
	private String function;
	private String function_inverse;
	
	public void invert() {
		invert = !invert;
		if (invert) setText(function_inverse);
		else setText(function);
	}
	
	public Functions(CalcModel model, String operation) {
		super(operation);
		function=operation;
		
		if (operation.equals("sin")) {
			operator = Math::sin;
			inverted = Math::asin;
			function_inverse="arcsin";
			
		}
		else if (operation.equals("cos")) {
			operator = Math::cos;
			inverted = Math::acos;
			function_inverse="arccos";
		}
		else if (operation.equals("tan")) {
			operator = Math::tan;
			inverted = Math::atan;
			function_inverse="arctan";
		}
		else if (operation.equals("ctg")){
			operator = (x) -> 1 / Math.tan(x);
			inverted = (x) -> Math.atan(1 / x);
			function_inverse="arcctg";
		}
		else if (operation.equals("log")){
			operator = Math::log10;
			inverted = (x) -> Math.pow(10, x);
			function_inverse="10^x";
		}
		else if (operation.equals("ln")){
			operator = Math::log;
			inverted = Math::exp;
			function_inverse="e^x";
		}
		else if (operation.equals("1/x")){
			operator = (x) -> 1 / x;
			inverted = (x) -> 1 / x;
			function_inverse=function;
		}
		else if (operation.equals("x^n")){
			operator = (x) -> Math.pow(model.getValue(), x);
			inverted = (x) -> Math.pow(model.getValue(), 1/x);
			function_inverse="x^(1/n)";


		}
		else throw new IllegalArgumentException();
		
		
		ActionListener listener=null;
		if(operation.equals("x^n")) {
			listener = e -> {
				if(model.hasFrozenValue())
					throw new CalculatorInputException();
				DoubleBinaryOperator oper = model.getPendingBinaryOperation();
				if(oper!=null && model.isActiveOperandSet()) {
					double left = model.getActiveOperand();
					double right = model.getValue();
					model.setValue(oper.applyAsDouble(left, right));
				}
				model.freezeValue(model.toString());
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation((a,b) -> Math.pow(a, invert ? 1/b : b));
				model.clear();
			};
		} 
		else {
			listener = e -> {
				if(model.hasFrozenValue())
					throw new CalculatorInputException();
				
				if (invert) model.setValue(inverted.applyAsDouble(model.getValue()));
				else model.setValue(operator.applyAsDouble(model.getValue()));
				
			};
		}
		addActionListener(listener);
		
		
		
		
		
	}

}
