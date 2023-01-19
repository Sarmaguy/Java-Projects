package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.CaretListener;
import javax.swing.Action;
import javax.swing.JFileChooser;

import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class ModelActions {
	private CaretListener cl;
	private JFrame frame;
	private DefaultMultipleDocumentModel model;
	private Action newDocument;
	private Action loadDocument;
	private Action saveDocument;
	private Action saveAsDocument;
	private Action closeDocument;
	private Action exit;
    private Action stats;
    private Action copy;
    private Action paste;
    private Action cut;

    public Action getCut() {
        return cut;
    }

    public Action getCopy() {
        return copy;
    }

    public Action getPaste() {
        return paste;
    }

    public Action getStats() {
        return stats;
    }

    public Action getNewDocument() {
		return newDocument;
	}

	public Action getLoadDocument() {
		return loadDocument;
	}

	public Action getSaveDocument() {
		return saveDocument;
	}

	public Action getSaveAsDocument() {
		return saveAsDocument;
	}

	public Action getCloseDocument() {
		return closeDocument;
	}

	public Action getExit() {
		return exit;
	}


	public ModelActions(JFrame frame, DefaultMultipleDocumentModel model, CaretListener cl) {
		this.cl = cl;
		this.frame = frame;
		this.model = model;
		createActions();
	}

	private void createActions(){
		newDocument = new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel document = model.createNewDocument();
				document.getTextComponent().addCaretListener(cl);
			}

		};
		newDocument.putValue(Action.NAME, "Novi");
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.SHORT_DESCRIPTION, "Stvori novi dokument");

		loadDocument = new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Učitaj dokument");
				if(jfc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION){
					JOptionPane.showMessageDialog(frame, "Niste odabrali datoteku", "Upozorenje", JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path path = jfc.getSelectedFile().toPath();
				if(!Files.isReadable(path)){
					JOptionPane.showMessageDialog(frame, "Datoteka nije čitljiva", "Upozorenje", JOptionPane.WARNING_MESSAGE);
					return;
				}
				model.loadDocument(path);
				SingleDocumentModel document = model.loadDocument(path);
				document.getTextComponent().addCaretListener(cl);
			}
		};
		loadDocument.putValue(Action.NAME, "Učitaj");
		loadDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		loadDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		loadDocument.putValue(Action.SHORT_DESCRIPTION, "Učitaj dokument");

		saveDocument = new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if(model.getNumberOfDocuments() == 0)
					return;
				
				
				SingleDocumentModel current = model.getCurrentDocument();
				if(current.getFilePath() == null){
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Spremi dokument");
					if(jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION){
						JOptionPane.showMessageDialog(frame, "Niste odabrali datoteku", "Upozorenje", JOptionPane.WARNING_MESSAGE);
						return;
					}
					Path path = jfc.getSelectedFile().toPath();
					if(Files.exists(path)){
						int rez = JOptionPane.showConfirmDialog(frame, "Datoteka već postoji. Želite li je prepisati?", "Upozorenje", JOptionPane.YES_NO_OPTION);
						if(rez == JOptionPane.NO_OPTION){
							return;
						}
					}
					model.saveDocument(current, path);
				}else{
					model.saveDocument(current, current.getFilePath());
				}
			}
		};
		saveDocument.putValue(Action.NAME, "Spremi");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Spremi trenutni dokument");

		saveAsDocument = new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getNumberOfDocuments() == 0)
					return;
				SingleDocumentModel current = model.getCurrentDocument();
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Spremi dokument");
				if(jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION){
					JOptionPane.showMessageDialog(frame, "Niste odabrali datoteku", "Upozorenje", JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path path = jfc.getSelectedFile().toPath();
				if(Files.exists(path)){
					int rez = JOptionPane.showConfirmDialog(frame, "Datoteka već postoji. Želite li je prepisati?", "Upozorenje", JOptionPane.YES_NO_OPTION);
					if(rez == JOptionPane.NO_OPTION){
						return;
					}
				}
				model.saveDocument(current, path);
			}
		};
		saveAsDocument.putValue(Action.NAME, "Spremi kao");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Spremi trenutni dokument kao novu datoteku");

		closeDocument = new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getNumberOfDocuments() == 0)
					return;
				SingleDocumentModel current = model.getCurrentDocument();
				if(current.isModified()){
					int rez = JOptionPane.showConfirmDialog(frame, "Dokument je promijenjen. Želite li ga spremiti?", "Upozorenje", JOptionPane.YES_NO_CANCEL_OPTION);
					if(rez == JOptionPane.CANCEL_OPTION){
						return;
					}
					if(rez == JOptionPane.YES_OPTION){
							model.saveDocument(current, current.getFilePath());
						}
					}
				
				model.closeDocument(current);
			}
		};
		closeDocument.putValue(Action.NAME, "Zatvori");
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Z);
		closeDocument.putValue(Action.SHORT_DESCRIPTION, "Zatvori trenutni dokument");
		
		exit = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		exit.putValue(Action.NAME, "Izlaz");
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		exit.putValue(Action.SHORT_DESCRIPTION, "Zatvori program");

        stats = new AbstractAction(){
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getNumberOfDocuments() == 0)
                    return;
                SingleDocumentModel current = model.getCurrentDocument();
                int chars = 0;
                int notemptychars = 0;
                String text = current.getTextComponent().getText();
                int lines = text.split("\\n").length;
                for (char c : text.toCharArray()) {
                    if (!Character.isWhitespace(c)) {
                        notemptychars++;
                    }
                    chars++;
                }
                JOptionPane.showMessageDialog(frame, 
                "Dokument  sadrži " + lines + " linija, " + chars + " znakova te " + notemptychars + " koji nisu prazni.", 
                "Statistika", 
                JOptionPane.INFORMATION_MESSAGE);

            }
        };
        stats.putValue(Action.NAME, "Statistika");
        stats.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift I"));
        stats.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
        stats.putValue(Action.SHORT_DESCRIPTION, "Prikaži statistiku");

        copy = new AbstractAction(){
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getNumberOfDocuments() == 0)
                    return;
                SingleDocumentModel current = model.getCurrentDocument();
                current.getTextComponent().copy();
            }
        };
		copy.putValue(Action.NAME, "Kopiraj");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.SHORT_DESCRIPTION, "Kopiraj  tekst");

		cut = new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getNumberOfDocuments() == 0)
					return;
				SingleDocumentModel current = model.getCurrentDocument();
				current.getTextComponent().cut();
			}
		};
		cut.putValue(Action.NAME, "Izreži");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cut.putValue(Action.SHORT_DESCRIPTION, "Izreži tekst");

		paste = new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getNumberOfDocuments() == 0)
					return;
				SingleDocumentModel current = model.getCurrentDocument();
				current.getTextComponent().paste();
			}
		};

		paste.putValue(Action.NAME, "Zalijepi");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		paste.putValue(Action.SHORT_DESCRIPTION, "Zalijepi tekst");

		}

}