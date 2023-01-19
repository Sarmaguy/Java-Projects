package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.LocalizationActions;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ModelActions;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SpecialActions;
import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.interfaces.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class JNotepadPP extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private DefaultMultipleDocumentModel model;
    private ModelActions mAction;
    private SpecialActions sAction;
    private LocalizationActions lAction;
	private JLabel length;
	private JLabel ln;
	private JLabel col;
	private JLabel sel;
	private FormLocalizationProvider flp;
	private JMenu fileMenu;
	private  JMenu tools;
	private JMenu changeCase;
	private JMenu sort;
	private JMenu lang;
	private  JToolBar toolBar;
	
	

	
	
public JNotepadPP() {
		
    model = new DefaultMultipleDocumentModel();
    
    CaretListener cl = new CaretListener(){
        @Override
        public void caretUpdate(CaretEvent e) {
            int dot = e.getDot();
            int mark = e.getMark();
            int len = 0;
            int line=0;
            int column=0;
            JTextArea textComp = model.getCurrentDocument().getTextComponent();
            try {
                int caretpos = textComp.getCaretPosition();
                line = textComp.getLineOfOffset(caretpos);
                column = caretpos - textComp.getLineStartOffset(line);
                line++;
                len = Math.abs(dot - mark);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            JNotepadPP.this.length.setText("length: " + textComp.getText().length());
            JNotepadPP.this.ln.setText("Ln: " + line);
            JNotepadPP.this.col.setText("Col: " + (column+1));
            JNotepadPP.this.sel.setText("Sel: " + len);
        }
    };

    mAction = new ModelActions(this, model,cl);
    sAction = new SpecialActions(this, model);
    lAction = new LocalizationActions();
    

    model.addMultipleDocumentListener(new MultipleDocumentListener() {
        @Override
        public void documentAdded(SingleDocumentModel model) {
             JOptionPane.showMessageDialog(
                JNotepadPP.this, 
                "Datoteka je dodana.", 
                "Informacija", 
                JOptionPane.INFORMATION_MESSAGE);
        }

        @Override
        public void documentRemoved(SingleDocumentModel model) {
            JOptionPane.showMessageDialog(
            	JNotepadPP.this, 
                "Datoteka je odstranjena.", 
                "Informacija", 
                JOptionPane.INFORMATION_MESSAGE);
            //actions
        }

        @Override
        public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
            JOptionPane.showMessageDialog(
            	JNotepadPP.this, 
                "Datoteka je promjenjena.", 
                "Informacija", 
                JOptionPane.INFORMATION_MESSAGE);
            
            if (model.getNumberOfDocuments() == 0) {
                setTitle("JNotepad++");
            } else {
                setTitle(currentModel.getFilePath() == null ? "(unnamed) - JNotepad++" : (currentModel.getFilePath() + " - JNotepad++"));
            }
        }
    });

    flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	flp.addLocalizationListener(new ILocalizationListener() {
		@Override
		public void localizationChanged() {
			mAction.getNewDocument().putValue(Action.NAME, flp.getString("new"));
			mAction.getLoadDocument().putValue(Action.NAME, flp.getString("open"));
			mAction.getSaveDocument().putValue(Action.NAME, flp.getString("save"));
			mAction.getSaveAsDocument().putValue(Action.NAME, flp.getString("saveAs"));
			mAction.getCloseDocument().putValue(Action.NAME, flp.getString("close"));
			mAction.getStats().putValue(Action.NAME, flp.getString("stats"));
			mAction.getExit().putValue(Action.NAME, flp.getString("exit"));
			mAction.getCut().putValue(Action.NAME, flp.getString("cut"));
			mAction.getCopy().putValue(Action.NAME, flp.getString("copy"));
			mAction.getPaste().putValue(Action.NAME, flp.getString("paste"));
			sAction.getUpperCase().putValue(Action.NAME, flp.getString("toUpper"));
			sAction.getLowerCase().putValue(Action.NAME, flp.getString("toLower"));
			sAction.getInvertCase().putValue(Action.NAME, flp.getString("invert"));
			sAction.getSortDesc().putValue(Action.NAME, flp.getString("descending"));
			sAction.getSortAsc().putValue(Action.NAME, flp.getString("ascending"));
			sAction.getUnique().putValue(Action.NAME, flp.getString("unique"));
			lAction.getEnglish().putValue(Action.NAME, flp.getString("english"));
			lAction.getCroatian().putValue(Action.NAME, flp.getString("croatian"));
			lAction.getGerman().putValue(Action.NAME, flp.getString("german"));

			fileMenu.setText(flp.getString("file"));
			tools.setText(flp.getString("tools"));
			changeCase.setText(flp.getString("capitalization"));
			sort.setText(flp.getString("sort"));
			lang.setText(flp.getString("language"));

		}
	});

    addWindowListener( new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            for (int i = 0; i < model.getNumberOfDocuments(); i++) {
                if (model.getDocument(i).isModified()) {
                    int result = JOptionPane.showOptionDialog(
                            JNotepadPP.this,
                            "Datoteka " + model.getDocument(i).getFilePath() + " je izmjenjena. Želite li spremiti promjene?",
                            "Upozorenje",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, 
                            new String[] { "Da", "Ne", "Otkaži" }, 
                            null);

                    if (result == JOptionPane.YES_OPTION) {
                    	Path path = model.getDocument(i).getFilePath();
                    	model.saveDocument(model.getDocument(i),path);
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
            }

            dispose();
        }
    });

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setLocation(0, 0);
    setSize(600, 600);
    setTitle("JNotepad++");
		
		initGUI();
	}
	
	private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(model, BorderLayout.CENTER);

        createMenuBar();
		createToolBar();
		createStatusBar();
	}

	private void createStatusBar() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		length = new JLabel("length: 0");
		JPanel panel2 = new JPanel(new GridLayout(1, 3));
		JLabel date = new JLabel();
		ln = new JLabel("Ln: 1");
		col = new JLabel("Col: 1");
		sel = new JLabel("Sel: 0");

		length.setHorizontalAlignment(SwingConstants.LEFT);
		date.setHorizontalAlignment(SwingConstants.RIGHT);

		panel2.add(ln);
		panel2.add(col);
		panel2.add(sel);
		panel.add(length);
		panel.add(panel2);
		panel.add(date);

		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				date.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			}
		});
		t.start();

		add(panel, BorderLayout.SOUTH);
	}

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        fileMenu = new JMenu("Dokument");
        menuBar.add(fileMenu);

	    fileMenu.add(new JMenuItem(mAction.getNewDocument()));
	    fileMenu.add(new JMenuItem(mAction.getLoadDocument()));
        fileMenu.add(new JMenuItem(mAction.getSaveDocument()));
        fileMenu.add(new JMenuItem(mAction.getSaveAsDocument()));
        fileMenu.add(new JMenuItem(mAction.getCloseDocument()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(mAction.getStats()));
        fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(mAction.getCopy()));
		fileMenu.add(new JMenuItem(mAction.getCut()));
		fileMenu.add(new JMenuItem(mAction.getPaste()));
		fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(mAction.getExit()));
        
        tools = new JMenu("Tools");
        menuBar.add(tools);
        
        changeCase = new JMenu("Change case");
        tools.add(changeCase);
        
        changeCase.add(sAction.getUpperCase());
        changeCase.add(sAction.getLowerCase());
        changeCase.add(sAction.getInvertCase());
        
        sort = new JMenu("Sort");
        tools.add(sort);
        sort.add(sAction.getSortAsc());
        sort.add(sAction.getSortDesc());
        
        tools.add(sAction.getUnique());
        
        lang = new JMenu("Localization");
        menuBar.add(lang);
        lang.add(lAction.getCroatian());
        lang.add(lAction.getEnglish());
        lang.add(lAction.getGerman());
        




    }

    private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(true);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);

        toolBar.add(new JMenuItem(mAction.getNewDocument()));
        toolBar.add(new JMenuItem(mAction.getLoadDocument()));
        toolBar.add(new JMenuItem(mAction.getSaveDocument()));
        toolBar.add(new JMenuItem(mAction.getSaveAsDocument()));
        toolBar.add(new JMenuItem(mAction.getCloseDocument()));
        toolBar.addSeparator();
		toolBar.add(new JMenuItem(mAction.getStats()));
		toolBar.addSeparator();
		toolBar.add(new JMenuItem(mAction.getCopy()));
		toolBar.add(new JMenuItem(mAction.getCut()));
		toolBar.add(new JMenuItem(mAction.getPaste()));
		toolBar.addSeparator();
        toolBar.add(new JMenuItem(mAction.getExit()));



}
    
    public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
