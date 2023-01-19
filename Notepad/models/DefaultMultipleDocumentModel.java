package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.SingleDocumentModel;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SingleDocumentModel currentDocument;
	private List<SingleDocumentModel> documents;
	private List<MultipleDocumentListener> listeners;

	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();

		addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				int index = getSelectedIndex();
				if(index == -1) {
					currentDocument = null;
					return;
				}
				currentDocument = documents.get(index);
				listeners.forEach(l -> l.currentDocumentChanged(currentDocument, documents.get(index)));
				currentDocument = documents.get(index);
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		// TODO Auto-generated method stub
		return documents.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		// TODO Auto-generated method stub
		return  createNewDocument(null,"");
	}

    public SingleDocumentModel createNewDocument(Path path, String text) {
        SingleDocumentModel doc = new DefaultSingleDocumentModel(path, text);
        doc.addSingleDocumentListener(new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
               // setIconAt(documents.indexOf(model), model.isModified() ? Utils.red : Utils.green);
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                int index = documents.indexOf(model);
                if (index == -1) return;
                String title = model.getFilePath() == null ? "unnamed" : model.getFilePath().getFileName().toString();
                if (model.isModified()) title += "*";
                setTitleAt(index, title);
            }
        });
        documents.add(doc);

        listeners.forEach(l -> l.documentAdded(doc));
        listeners.forEach(l -> l.currentDocumentChanged(currentDocument, doc));

        currentDocument = doc;


        addTab(path == null ? "unnamed" : path.getFileName().toString(), doc.getTextComponent());
        setSelectedIndex(documents.size() - 1);
        return doc;
    }

	@Override
	public SingleDocumentModel getCurrentDocument() {
		// TODO Auto-generated method stub
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {

        if(!Files.isReadable(path)) {
            JOptionPane.showMessageDialog(
                    this, 
                    "Datoteka ne postoji!", 
                    "Pogreška", 
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
		
        for(SingleDocumentModel d :  documents) {
            if (d.getFilePath() != null && d.getFilePath().equals(path)) {
                setSelectedIndex(documents.indexOf(d));
                currentDocument = d;
                return d;
            }
        }

        byte[] okteti;
        try {
            okteti = Files.readAllBytes(path);
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(this, 
			"Pogreška prilikom čitanja datoteke "+path.toAbsolutePath()+".", 
			"Pogreška", 
			JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String text = new String(okteti, StandardCharsets.UTF_8);
		return  createNewDocument(path, text);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
        if(newPath == null) {
            newPath = model.getFilePath();
        }
        
        if(newPath==null) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save document");
            if(jfc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(
                        this, 
                        "Ništa nije snimljeno.", 
                        "Upozorenje", 
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }


        
        byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(newPath, data);
        } catch (IOException e) {
           JOptionPane.showMessageDialog(this, "Pogreška prilikom spremanja datoteke " + newPath.toAbsolutePath() + ".",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
		    return;
        }
        JOptionPane.showMessageDialog(
					this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
        model.setFilePath(newPath);
        model.setModified(false);
		
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
        // TODO Auto-generated method stub
        int index = documents.indexOf(model);
        if (index == -1) return;

        documents.remove(index);
        remove(index);

        listeners.forEach(l -> l.documentRemoved(model));

        if (documents.isEmpty()) {
            currentDocument = null;
            return;
        }

        if (index == documents.size()) index--;
        currentDocument = documents.get(index);
        listeners.forEach(l -> l.currentDocumentChanged(model, currentDocument));
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		// TODO Auto-generated method stub
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		// TODO Auto-generated method stub
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		// TODO Auto-generated method stub
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		// TODO Auto-generated method stub
		return documents.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		// TODO Auto-generated method stub
		for (SingleDocumentModel doc : documents) {
            if (doc.getFilePath() != null && doc.getFilePath().equals(path)) {
                return doc;
            }
        }
        return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		// TODO Auto-generated method stub
		return documents.indexOf(doc);
	}

}
