package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.SingleDocumentModel;

public class DefaultSingleDocumentModel implements SingleDocumentModel{

    private JTextArea textArea;
    private Path path;
    private boolean modified;
    private List<SingleDocumentListener> listeners;

    public DefaultSingleDocumentModel(Path path, String textContent) {
        this.path = path;
        this.textArea = new JTextArea(textContent);
        this.modified = false;
        this.listeners = new ArrayList<>();

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(true);
            }
        });
        


    }


	@Override
	public JTextArea getTextComponent() {
		// TODO Auto-generated method stub
		return textArea;
	}

	@Override
	public Path getFilePath() {
		// TODO Auto-generated method stub
		return path;
	}

	@Override
	public void setFilePath(Path path) {
        // TODO Auto-generated method stub
        this.path = path;
		
	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;

        listeners.forEach(l -> l.documentModifyStatusUpdated(this));
		
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		// TODO Auto-generated method stub
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		// TODO Auto-generated method stub
		listeners.remove(l);
	}

}
