package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;

public class LocalizationActions {
	private Action croatian;
	private Action english;
	private Action german;

	public LocalizationActions() {
		createActions();
	}

	private void createActions() {

		english = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
				LocalizationProvider.getInstance().fire();

			}
		};
		english.putValue(Action.NAME, "English");

		german = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
				LocalizationProvider.getInstance().fire();

			}
		};

		german.putValue(Action.NAME, "Deutsch");

		croatian = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
				LocalizationProvider.getInstance().fire();

			}
		};
		
		croatian.putValue(Action.NAME, "Hrvatski");
		}

	public Action getCroatian() {
		return croatian;
	}

	public Action getEnglish() {
		return english;
	}

	public Action getGerman() {
		return german;
	}
	
	
		
	}

