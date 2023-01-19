package Calculator;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Calculator extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CalcModelImpl model = new CalcModelImpl();
	private Container container ;
	private Stack<Double> stack = new Stack<>();
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
	
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700,300);
		initGUI();
	}

	private void initGUI() {
		container = getContentPane();
		container.setLayout(new CalcLayout(3));
		
		JLabel result = new JLabel("0");
		result.setFont(result.getFont().deriveFont(30f));
		model.addCalcValueListener((m) -> {
			result.setText(m.toString()); //ovo
		});
		container.add(result, "1,1");
		
		container.add(new Numbers(model, 0), "5,3");
		container.add(new Numbers(model, 1), "4,3");
		container.add(new Numbers(model, 2), "4,4");
		container.add(new Numbers(model, 3), "4,5");
		container.add(new Numbers(model, 4), "3,3");
		container.add(new Numbers(model, 5), "3,4");
		container.add(new Numbers(model, 6), "3,5");
		container.add(new Numbers(model, 7), "2,3");
		container.add(new Numbers(model, 8), "2,4");
		container.add(new Numbers(model, 9), "2,5");
		container.add(new Operations(model, "-"), "4,6");
		container.add(new Operations(model, "+"), "5,6");
		container.add(new Operations(model, "/"), "2,6");
		container.add(new Operations(model, "*"), "3,6");
		container.add(new Functions(model, "1/x"), "2,1");
		container.add(new Functions(model, "log"), "3,1");
		container.add(new Functions(model, "ln"), "4,1");
		container.add(new Functions(model, "x^n"), "5,1");
		container.add(new Functions(model, "sin"), "2,2");
		container.add(new Functions(model, "cos"), "3,2");
		container.add(new Functions(model, "tan"), "4,2");
		container.add(new Functions(model, "ctg"), "5,2");
		
		
		
		JButton swap = new JButton("+/-");
		ActionListener swapListener = e ->  {
			if(model.isEditable())
				model.swapSign();
		};
		swap.addActionListener(swapListener);
		container.add(swap, "5,4");

		JButton dot = new JButton(".");
		ActionListener dotListener = e ->  {
			if(model.isEditable())
				model.insertDecimalPoint();
		};
		dot.addActionListener(dotListener);
		container.add(dot, "5,5");

		JButton equals = new JButton("=");
		ActionListener equalsListener = e ->  {
			if(model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.clearActiveOperand();
			}
		};
		equals.addActionListener(equalsListener);
		container.add(equals, "1,6");
		
		JButton clear = new JButton("clr");
		ActionListener clearListener = e ->  {
			model.clear();
		};
		clear.addActionListener(clearListener);
		container.add(clear, "1,7");

		JButton reset = new JButton("reset");
		ActionListener resetListener = e ->  {
			model.clearAll();
		};
		reset.addActionListener(resetListener);
		container.add(reset, "2,7");
		
		JCheckBox inv = new JCheckBox("Inv");
		ActionListener invListener = e ->  {
			for(Component c : container.getComponents()) {
				if(c instanceof Functions) {
					((Functions) c).invert();
				}
			}
		};
		inv.addActionListener(invListener);
		container.add(inv, "5,7");

		JButton push = new JButton("push");
		ActionListener pushListener = e ->  {
			stack.push(model.getValue());
		};
		push.addActionListener(pushListener);
		container.add(push, "3,7");

		JButton pop = new JButton("pop");
		ActionListener popListener = e ->  {
			if(!stack.isEmpty()) {
				model.setValue(stack.pop());
			}
		};
		pop.addActionListener(popListener);
		container.add(pop, "4,7");
		

	}


}
