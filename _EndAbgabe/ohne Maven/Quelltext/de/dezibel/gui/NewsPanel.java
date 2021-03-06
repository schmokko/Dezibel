package de.dezibel.gui;

import de.dezibel.control.NewsControl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import de.dezibel.control.NewsControl;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.News;
import de.dezibel.data.User;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import javax.swing.GroupLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class for the Panel which contains all information about news the user can see.
 * @author Aris, Tristan
 */
public class NewsPanel extends DragablePanel {

    private News currentNews;
    private static final long serialVersionUID = 1L;
    private JLabel lbnews;
    private JLabel lbAutor ;
    private JLabel lbDatum;
    private JLabel lbTitel;
    private JLabel lbText;
        
    private JTextField tfAutor;  
    private JTextField tfDatum;    
    private JTextField tfTitel;           

    private JTextArea taText;
    
    private JTable tNews;
    private NewsPanelTableModel nptm;
    private JScrollPane spNews1;
    private JScrollPane spNews2;
    private NewsSideTableModel model;
    
    private LinkedList<News> allNews;
    private NewsControl nc;
    

    public NewsPanel(DezibelPanel parent , News currentNews) {
        super(parent);
        this.currentNews = currentNews;
        this.createNewsPanel();
        this.fillTable();
        this.showCurrentNews();
    } 
    public  void createNewsPanel(){ 
      lbnews = new JLabel("News");
      lbAutor = new JLabel("Autor" );
      lbDatum = new JLabel("Datum");
      lbTitel = new JLabel("Titel");
      tfAutor = new JTextField();  
      tfAutor.setEditable(false);
      tfDatum = new JTextField();  
      tfDatum.setEditable(false);
      tfTitel  = new JTextField(); 
      tfTitel.setEditable(false);
      taText = new JTextArea();  
      taText.setEditable(false);
      nptm = new NewsPanelTableModel();
      tNews = new JTable(nptm);
      
      tNews.getTableHeader().setVisible(false);
      
      // Table News scrollpane 
      spNews1 = new JScrollPane();
      spNews1.getViewport().setView(tNews);
      
      // scrollpane fur taNews
      spNews2 = new JScrollPane();
      spNews2.getViewport().setView(taText);
      
      
      
          
      
      tNews.addMouseListener(new MouseAdapter() {  
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                if (nptm.getValueAt(tNews.getSelectedRow(), -1) instanceof News) {
                    currentNews = (News) nptm.getValueAt(
                            tNews.getSelectedRow(), -1);
                    showCurrentNews();
                } 
            }
        }
      });
      
      
              
      GroupLayout layout = new GroupLayout(this);
      layout.setHorizontalGroup(layout
            .createSequentialGroup( )
            .addGroup(
                        true, layout.createSequentialGroup()
                        .addComponent(spNews1, 128, 128, 200)
                        .addGap(25)
                        
                        )
                              
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lbTitel, 128, 128, 128)
                        .addComponent(tfTitel, 128, 128, 1500))
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lbAutor, 128, 128, 128)
                        .addComponent(tfAutor, 128, 128, 1500))
                .addGroup(
                       GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lbDatum, 128, 128, 128)
                        .addComponent(tfDatum, 128, 128, 1500))
                 .addGroup(
                       GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(spNews2, 128, 128, 1500))              
                        
                )
      );
                
                

      layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.LEADING, true)
                 .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                            .addComponent(spNews1, 32, 32, 1000)
                            .addGap(25)

                 )
                                   
                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                 .addGroup(layout.createSequentialGroup()
                       
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                                .addComponent(lbTitel, 32, 32, 32)
                                .addComponent(tfTitel, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbAutor, 32, 32, 32)
                                .addComponent(tfAutor, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbDatum, 32, 32, 32)
                                .addComponent(tfDatum, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(spNews2, 32, 32, 1000))
          
                          
                 ) 
                 )
       ); 

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        this.setLayout(layout);
    }

    /**
     * Displays the currently selected news in detail.
     */
    public void showCurrentNews() {      
        tNews.setRowSelectionInterval(nptm.getNewsIndex(currentNews), nptm.getNewsIndex(currentNews));
        tfTitel.setText(currentNews.getTitle());
        if(currentNews.isAuthorLabel()){
            tfAutor.setText(currentNews.getLabel().getName());
        }
        else{
            tfAutor.setText(currentNews.getAuthor().getFirstname());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        tfDatum.setText(sdf.format(currentNews.getCreationDate()));
        taText.setText(currentNews.getText());
    }
    
    @Override
    public void reset() {
    }

    @Override
    public void refresh() {
        if (Database.getInstance().getLoggedInUser() != null) {
            /*LinkedList<News>  = Database.getInstance().getLoggedInUser()
                    .getFavorizedUsers();
            nptm.setDataNews(favorizedUsers);*/
        }
    }

    /**
     * Helpmethod to load and store the data in the tablemodel
     */
    private void fillTable() {
       allNews = new LinkedList<News>();
       nc = new NewsControl();
       
       allNews = nc.searchForNews();
       nptm.setDataNews(allNews);
        
    }
   

}
