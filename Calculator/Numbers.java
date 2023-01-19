package Calculator;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Numbers extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Numbers(CalcModel model, int n) {
		super(""+n);
		this.setFont(this.getFont().deriveFont(30f));
		
		ActionListener listener = e -> { 
			if (model.isEditable()) model.insertDigit(n);
			};
		
		addActionListener(listener);
		
	}

}
