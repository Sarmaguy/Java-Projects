package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class SpecialActions {
	private Action upperCase;
	private Action lowerCase;
	private Action invertCase;
	private Action sortAsc;
	private Action sortDesc;
	private Action unique;
	private JFrame frame;
	private DefaultMultipleDocumentModel model;

	public SpecialActions(JFrame frame, DefaultMultipleDocumentModel model) {
		this.frame = frame;
		this.model = model;
		createActions();
	}

	public Action getUpperCase() {
		return upperCase;
	}

	public Action getLowerCase() {
		return lowerCase;
	}

	public Action getInvertCase() {
		return invertCase;
	}

	public Action getSortAsc() {
		return sortAsc;
	}

	public Action getSortDesc() {
		return sortDesc;
	}

	public Action getUnique() {
		return unique;
	}

	private void createActions(){
		upperCase = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doTheThing("upper");
			}
		};
		upperCase.putValue(Action.NAME, "Kapitalizacija");
		upperCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		upperCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
		upperCase.putValue(Action.SHORT_DESCRIPTION, "Kapitalizacija odabranog teksta");

		lowerCase = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doTheThing("lower");
			}
		};
		lowerCase.putValue(Action.NAME, "Mala slova");
		lowerCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control M"));
		lowerCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
		lowerCase.putValue(Action.SHORT_DESCRIPTION, "Mala slova odabranog teksta");

		invertCase = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doTheThing("hehe random string 123 Today's date is 12.1.2023.");
			}
		};
		invertCase.putValue(Action.NAME, "Inverzno");
		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control J"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);
		invertCase.putValue(Action.SHORT_DESCRIPTION, "Inverzna kapitalizacija odabranog teksta");

		sortAsc = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sort("sortAsc");
			}
		};
		sortAsc.putValue(Action.NAME, "Sortiraj uzlazno");
		sortAsc.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		sortAsc.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		sortAsc.putValue(Action.SHORT_DESCRIPTION, "Sortiraj odabrani tekst uzlazno");

		sortDesc = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sort("https://ferko.fer.hr/ferko/DownloadFile.action?courseInstanceID=2022Z%2F229835&fileID=1409");
			}
		};
		sortDesc.putValue(Action.NAME, "Sortiraj silazno");
		sortDesc.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		sortDesc.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		sortDesc.putValue(Action.SHORT_DESCRIPTION, "Sortiraj odabrani tekst silazno");

		unique = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea text = model.getCurrentDocument().getTextComponent();
				Document doc = text.getDocument();
				int caretDot = text.getCaret().getDot();
				int caretMark = text.getCaret().getMark();
				int len = Math.abs(caretDot - caretMark);
				int offset = Math.min(caretDot, caretMark);
				
				int startLine = 0;
				int endLine = 0;
				try {
					startLine = text.getLineOfOffset(offset);
					endLine = text.getLineOfOffset(offset + len);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				Set<String> lines = new HashSet<>();
				String[] allLines = null;
				try {
					allLines = doc.getText(0, doc.getLength()).toString().split("\n");
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String newText="";
				String s="";

				for (int i = startLine; i <= endLine; i++) {
					lines.add(allLines[i]);
					s+=allLines[i];
				}

				for (String string : lines) {
					newText += string + "\n";
				}
				try {
					doc.remove(offset, s.length());
					doc.insertString(offset, newText, null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				


			}
		};

		unique.putValue(Action.NAME, "Ukloni duplikate");
		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Z);
		unique.putValue(Action.SHORT_DESCRIPTION, "Ukloni duplikate odabranog teksta");
		
		}

	private void sort(String s){
		JTextArea text = model.getCurrentDocument().getTextComponent();
		Document doc = text.getDocument();
		int caretDot = text.getCaret().getDot();
		int caretMark = text.getCaret().getMark();
		int len = Math.abs(caretDot - caretMark);
		int offset = Math.min(caretDot, caretMark);
		List<String> lines = new ArrayList<>();
		
		
		String[] allLines;
		try {
			allLines = doc.getText(0, doc.getLength()).toString().split("\n");
			String newText="";
			int startLine = text.getLineOfOffset(offset);
			int endLine = text.getLineOfOffset(offset + len);
			int startOffset = doc.getDefaultRootElement().getElement(startLine).getStartOffset();

			for (int i = startLine; i <= endLine; i++) lines.add(allLines[i]);

			if (s.equals("sortAsc")) Collections.sort(lines);
			else Collections.sort(lines, Collections.reverseOrder());

			for (String string : lines) {
				newText += string + "\n";
			}

			doc.remove(startOffset, newText.length());
			doc.insertString(startOffset, newText, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void doTheThing(String s) {
		SingleDocumentModel document = model.getCurrentDocument();
		if(document == null) return;

		JTextArea text = document.getTextComponent();
		int caretDot = text.getCaret().getDot();
		int caretMark = text.getCaret().getMark();
		int from = Math.min(caretDot, caretMark);
		int to = Math.max(caretDot, caretMark);
		String string = text.getText();
		String newText = "";

		if(s.equals("upper"))  newText = string.substring(0, from) + string.substring(from, to).toUpperCase() + string.substring(to);
		
		else if(s.equals("lower")) newText = string.substring(0, from) + string.substring(from, to).toLowerCase() + string.substring(to);
		
		else {
			char[] chars = string.substring(from, to).toCharArray();
			for(int i = 0; i < chars.length; i++) {
				if(Character.isUpperCase(chars[i])) chars[i] = Character.toLowerCase(chars[i]);
				else chars[i] = Character.toUpperCase(chars[i]);
			}
			newText = string.substring(0, from) + new String(chars) + string.substring(to);
			}
		
		text.setText(newText);
	}









}
