package Calculator;

import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

public class Operations extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DoubleBinaryOperator operator;
	
	public Operations(CalcModel model, String s) {
		super(s);
		
		if (s.equals("+")) operator = (left, right) -> left+right;
		else if (s.equals("-")) operator = (left, right) -> left-right;
		else if (s.equals("*")) operator = (left, right) -> left*right;
		else if (s.equals("/")) operator = (left, right) -> left/right;
		else throw new IllegalArgumentException();
		
		ActionListener listener = e -> {
			if(model.hasFrozenValue())
				throw new CalculatorInputException();
			
			DoubleBinaryOperator operation = model.getPendingBinaryOperation();
			if(operation!=null && model.isActiveOperandSet()) {
				double left = model.getActiveOperand();
				double right = model.getValue();
				model.setValue(operation.applyAsDouble(left, right));
				
			}
			
			model.freezeValue(model.toString());
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation(operator);
			model.clear();
		};
		addActionListener(listener);
	}


}
