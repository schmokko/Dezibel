package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.control.AdminControl;
import de.dezibel.control.LabelControl;
import de.dezibel.data.Album;
import de.dezibel.data.Application;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.User;
import de.dezibel.player.Player;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * Class for the Profile Panel of a Label.
 * @author Alexander, Henner
 */
public class LabelProfilPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private LabelControl profileControler;
    private AdminControl adminControler;
    private Label currentLabel;

    private JTabbedPane tabPanel;
    private JPanel pnProfile;
    private JPanel pnUploads;
    private JPanel pnFollower;
    private JPanel pnNews;
    private JPanel pnManagement;
    private JPanel pnArtists;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JButton btnFollow;
    private JButton btnEdit;

    private JTable tFollower;
    private FollowerTableModel followerModell;
    private JScrollPane tablePanel;
    private JTable tMedia;
    private JTable tAlbums;
    private JTable tNews;
    private JTextArea taNews;

    private JPopupMenu currentPopupMenu;
    private JScrollPane spMedia;
    private JScrollPane spAlbums;
    private JLabel lbMediaUpload;
    private JLabel lbAlbumsUpload;
    private MediaTableModel mediaModellUpload;
    private AlbumTableModel albumModellUpload;
    private NewsSideTableModel newsModell;
    private ArtistTableModel artistModell;
    private JTextField tfName;
    private JTextArea taCompanyDetails;
    private FollowerTableModel managerModell;
    private JTable tManager;
    private JPanel pnApplications;
    private ApplicationToLabelTableModel applicationsModell;
    private JTable tApplications;
    private JTextArea taApplications;
    private boolean isApplicationsVisible;
    private JButton btnLock;
    private JTable tArtists;
    private JScrollPane sptArtist;
    private int showTabNr;

    /**
     * Constructor of the LabelProfilPanel class.
     *
     * @param parent
     */
    public LabelProfilPanel(DezibelPanel parent) {
        super(parent);
        this.currentLabel = null;

        this.createComponents();
        // Create Layout
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(tabPanel, BorderLayout.CENTER);

        this.profileControler = new LabelControl();
        showTabNr = 0;
    }

    /**
     * Sets the tab which should be currently displayed.
     * 
     * @param tabNr nr of the tab which should be displayed
     */
    public void setTab(int tabNr){
        showTabNr = tabNr;
    }
    
    public void setUser(Label newLabel) {
        this.currentLabel = newLabel;
        showTabNr = 0;
        this.refresh();
    }

    public Label getLabel() {
        return this.currentLabel;
    }

    @Override
    public void refresh() {

        if (currentLabel != null) {

            if (!(currentLabel.isLocked() && !(profileControler.getLoggedInUser().isAdmin()))) {
                

                tfName.setText(profileControler.getName(currentLabel));
                taCompanyDetails.setText(profileControler.getCompanyDetails(currentLabel));

                btnFollow.setVisible(true);

                if (profileControler.getManagers(currentLabel).contains(profileControler.getLoggedInUser())) {
                    btnEdit.setVisible(true);
                } else {
                    btnEdit.setVisible(false);
                }

                if (profileControler.getFollowers(currentLabel).contains(profileControler.getLoggedInUser())) {
                    btnFollow.setText("Unfollow");
                } else {
                    btnFollow.setText("Follow");
                }

                if (taCompanyDetails.isEnabled()) {
                    btnEdit.setText("Speichern");
                } else {
                    btnEdit.setText("Bearbeiten");
                }

                if (!(profileControler.belongsToLoggedUser(currentLabel))) {
                    tabPanel.remove(pnApplications);
                    isApplicationsVisible = false;
                } else if (!(isApplicationsVisible)) {
                    tabPanel.addTab("Bewerbungen", null, pnApplications);
                }

                if (!(profileControler.getLoggedInUser().isAdmin())) {
                    btnLock.setVisible(false);
                } else {
                    btnLock.setVisible(true);
                }

                if (currentLabel.isLocked()) {
                    btnLock.setText("Entsperren");
                } else {
                    btnLock.setText("Sperren");
                }

                followerModell.setData(profileControler.getFollowers(currentLabel));
                mediaModellUpload.setData(profileControler.getAssociatedMediums(currentLabel));
                albumModellUpload.setData(profileControler.getCreatedAlbums(currentLabel));
                newsModell.setData(profileControler.getNews(currentLabel));
                managerModell.setData(profileControler.getManagers(currentLabel));
                applicationsModell.setData(profileControler.getApplications(currentLabel));
                artistModell.setData(profileControler.getArtists(currentLabel));
                
                tabPanel.setSelectedIndex(showTabNr);
            }
        }
    }

    /**
     * Method to create tabs.
     */
    private void createComponents() {
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();

        this.tabPanel = new JTabbedPane();

        this.pnProfile = new JPanel();
        this.createProfileComponents();
        tabPanel.addTab("Profil", null, pnProfile);
        this.pnUploads = new JPanel();
        this.createUploadsComponents();
        tabPanel.addTab("Uploads", null, pnUploads);
        this.pnFollower = new JPanel();
        this.createFollowerComponents();
        tabPanel.addTab("Follower", null, pnFollower);
        this.pnNews = new JPanel();
        this.createNewsComponents();
        tabPanel.addTab("News", null, pnNews);
        this.pnManagement = new JPanel();
        this.createManagementComponents();
        tabPanel.addTab("Manager", null, pnManagement);
        this.pnArtists = new JPanel();
        this.createArtistsComponents();
        tabPanel.addTab("Künstler", null, pnArtists);
        this.pnApplications = new JPanel();
        this.createApplicationsComponents();
        tabPanel.addTab("Bewerbungen", null, pnApplications);
    }

    /**
     * This method creates all components in the profile tab.
     */
    private void createProfileComponents() {
        pnProfile.setLayout(gbl);

        JLabel lbName = new JLabel("Name:");
        JLabel lbCompanyDetails = new JLabel("Impressum:");

        tfName = new JTextField(25);
        tfName.setEnabled(false);
        taCompanyDetails = new JTextArea();
        taCompanyDetails.setEnabled(false);
        JScrollPane sptCompanyDetails = new JScrollPane(taCompanyDetails);
        sptCompanyDetails.setPreferredSize(new Dimension(150, 250));

        btnEdit = new JButton("Bearbeiten");
        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (taCompanyDetails.isEnabled() && currentLabel != null) {
                    profileControler.setCompanyDetails(currentLabel, taCompanyDetails.getText());
                    taCompanyDetails.setEnabled(false);
                } else {
                    taCompanyDetails.setEnabled(true);
                }
                showTabNr = 0;
                refresh();
            }
        });

        btnFollow = new JButton("Follow");
        btnFollow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (profileControler.getFollowers(currentLabel).contains(
                        profileControler.getLoggedInUser())) {
                    profileControler.removeFollower(currentLabel);
                } else {
                    profileControler.addFollower(currentLabel);
                }
                showTabNr = 0;
                refresh();
                parent.refresh(UpdateEntity.FAVORITES);
            }
        });

        btnLock = new JButton("Sperren");
        btnLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(currentLabel.isLocked())) {
                    adminControler.lock(currentLabel, "");
                } else {
                    adminControler.unlock(currentLabel);
                }
                showTabNr = 0;
                refresh();
            }
        });

        addComponent(pnProfile, gbl, lbName, 0, 0);
        addComponent(pnProfile, gbl, tfName, 1, 0);
        addComponent(pnProfile, gbl, lbCompanyDetails, 0, 1);
        addComponent(pnProfile, gbl, sptCompanyDetails, 1, 1);

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbl.setConstraints(btnEdit, gbc);
        pnProfile.add(btnEdit);
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbl.setConstraints(btnFollow, gbc);
        pnProfile.add(btnFollow);
    }

    /**
     * This method adds a components to a given GridBagLayout.
     *
     * @param cont
     * @param gbl
     * @param comp
     * @param x
     * @param y
     */
    private void addComponent(Container cont,
            GridBagLayout gbl,
            Component comp,
            int x, int y) {
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbl.setConstraints(comp, gbc);
        cont.add(comp);
    }

    /**
     * Follower Tab
     */
    private void createFollowerComponents() {
        followerModell = new FollowerTableModel();
        tFollower = new JTable(followerModell);
        tFollower.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tFollower.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    User u = (User) followerModell.getValueAt(
                            tFollower.getSelectedRow(), -1);
                    if (u != null) {
                        parent.showProfile(u);
                    }
                }
            }
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tFollower, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        tablePanel = new JScrollPane(tFollower);
        BorderLayout fLayout = new BorderLayout();
        pnFollower.setLayout(fLayout);
        pnFollower.add(tablePanel, BorderLayout.CENTER);
    }

    /**
     * News Tab
     */
    private void createNewsComponents() {

        newsModell = new NewsSideTableModel();
        tNews = new JTable(newsModell);
        tNews.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sptNews = new JScrollPane(tNews);
        sptNews.getViewport().setView(tNews);
        pnNews.add(sptNews);

        taNews = new JTextArea();
        taNews.setLineWrap(true);
        taNews.setWrapStyleWord(true);

        JScrollPane sptaNews = new JScrollPane(taNews);
        sptaNews.getViewport().setView(taNews);
        taNews.setEditable(false);
        pnNews.add(sptaNews);

        GroupLayout layout = new GroupLayout(pnNews);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER, true)
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(sptNews, 128, 128, 2000))
                .addGroup(
                        GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sptaNews, 128, 128, 2000))
        );

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                                .addComponent(sptNews, 100, 100, 1000))
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING, true)
                                .addComponent(sptaNews, 100, 100, 1000))
                )
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnNews.setLayout(layout);

        tNews.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                News n = (News) newsModell.getValueAt(tNews.getSelectedRow(), -1);
                taNews.setText(n.getText());
            }
        });
    }

    /**
     * Uploads Tab
     */
    private void createUploadsComponents() {

        mediaModellUpload = new MediaTableModel();
        tMedia = new JTable(mediaModellUpload);
        tMedia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lbMediaUpload = new JLabel("Media");
        lbMediaUpload.setHorizontalAlignment(JLabel.CENTER);

        spMedia = new JScrollPane(tMedia);
        spMedia.getViewport().setView(tMedia);
        pnUploads.add(spMedia);

        albumModellUpload = new AlbumTableModel();
        tAlbums = new JTable(albumModellUpload);
        tAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spAlbums = new JScrollPane();
        lbAlbumsUpload = new JLabel("Alben");
        lbAlbumsUpload.setHorizontalAlignment(JLabel.CENTER);
        spAlbums.getViewport().setView(tAlbums);
        pnUploads.add(spAlbums);

        GroupLayout layout = new GroupLayout(pnUploads);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lbMediaUpload, 128, 128, 200)
                .addComponent(spMedia, 128, 128, 1500)
                .addComponent(lbAlbumsUpload, 128, 128, 200)
                .addComponent(spAlbums, 128, 128, 1500)
        );

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(lbMediaUpload, 32, 32, 32)
                        .addComponent(spMedia, 100, 100, 700)
                        .addComponent(lbAlbumsUpload, 32, 32, 32)
                        .addComponent(spAlbums, 100, 100, 700)
                )
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnUploads.setLayout(layout);

        tMedia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Medium m = (Medium) mediaModellUpload.getValueAt(
                            tMedia.getSelectedRow(), -1);
                    if (m != null) {
                        Player.getInstance().addMediumAsNext(m);
                        Player.getInstance().next();
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tMedia, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });
        
        tAlbums.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Album a = (Album) albumModellUpload.getValueAt(
                            tAlbums.getSelectedRow(), -1);
                    if (a != null) {
                        parent.showAlbum(a);
                    }
                }
            }
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tAlbums, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });
    }

    /**
     * Manager Tab
     */
    private void createManagementComponents() {
        managerModell = new FollowerTableModel();
        tManager = new JTable(managerModell);
        tManager.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tManager.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    User u = (User) managerModell.getValueAt(
                            tManager.getSelectedRow(), -1);
                    if (u != null) {
                        parent.showProfile(u);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tManager, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        tablePanel = new JScrollPane(tManager);
        BorderLayout fLayout = new BorderLayout();
        pnManagement.setLayout(fLayout);
        pnManagement.add(tablePanel, BorderLayout.CENTER);
    }

    
    /**
     * Applications Tab
     */
    private void createApplicationsComponents() {

        applicationsModell = new ApplicationToLabelTableModel();
        tApplications = new JTable(applicationsModell);
        tApplications.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sptApplications = new JScrollPane(tApplications);
        sptApplications.getViewport().setView(tApplications);
        pnApplications.add(sptApplications);

        taApplications = new JTextArea();
        taApplications.setLineWrap(true);
        taApplications.setWrapStyleWord(true);

        JScrollPane sptaApplications = new JScrollPane(taApplications);
        sptaApplications.getViewport().setView(taApplications);
        taApplications.setEditable(false);
        pnApplications.add(sptaApplications);

        GroupLayout layout = new GroupLayout(pnApplications);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER, true)
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(sptApplications, 128, 128, 2000))
                .addGroup(
                        GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sptaApplications, 128, 128, 2000))
        );

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                                .addComponent(sptApplications, 100, 100, 1000))
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING, true)
                                .addComponent(sptaApplications, 100, 100, 1000))
                )
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnApplications.setLayout(layout);

        tApplications.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Application a = (Application) applicationsModell.getValueAt(
                tApplications.getSelectedRow(), -1);                
                if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if(a != null) {
                        if(a.getLabel() != null) {
                            parent.showProfile(a.getLabel());
                            return;
                        }
                    }
                }
                taApplications.setText(a.getText());
            }
        });

        tApplications.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tApplications, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

    /**
     * Artists Tab
     */
    private void createArtistsComponents() {
        artistModell = new ArtistTableModel();
        tArtists = new JTable(artistModell);
        tArtists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tArtists.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    User u = (User) artistModell.getValueAt(
                            tArtists.getSelectedRow(), -1);
                    if (u != null) {
                        parent.showProfile(u);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tArtists, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        sptArtist = new JScrollPane(tArtists);
        BorderLayout fLayout = new BorderLayout();
        pnArtists.setLayout(fLayout);
        pnArtists.add(sptArtist, BorderLayout.CENTER);
    }
    

}
