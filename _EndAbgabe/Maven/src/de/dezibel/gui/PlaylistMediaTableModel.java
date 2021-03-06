package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 * TableModel for mediaobject in the PlaylistPanel.
 *
 * @author Benjamin Knauer, Henner
 */
public class PlaylistMediaTableModel extends DefaultTableModel {

    private Playlist currentPlaylist;
    private String[] headlines = new String[]{"Künstler", "Titel", "Album",
        "Genre", "Uploaddatum", "Bewertung"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class, String.class,
        String.class, String.class, Date.class, Double.class};

    private Medium[] data;

    @Override
    public int getColumnCount() {
        return headlines.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headlines[columnIndex];
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        } else {
            return data.length;
        }
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (data != null && row >= 0 && row < data.length) {
            Medium m = data[row];
            switch (col) {
                case -1:
                    return m;
                case 0:
                    return m.getArtist().getPseudonym();
                case 1:
                    return m.getTitle();
                case 2:
                    if (m.getAlbum() != null) {
                        return m.getAlbum().getTitle();
                    } else {
                        return "";
                    }
                case 3:
                    if (m.getGenre() != null) {
                        return m.getGenre().getName();
                    } else {
                        return "";
                    }
                case 4:
                    return m.getUploadDate();
                case 5:
                    // Round to 2 digits
                    return Math.round(m.getAvgRating() * 100) / 100;
            }
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * Sets the data of this model.
     *
     * @param playlist The data to display
     */
    public void setData(Playlist playlist) {
        this.currentPlaylist = playlist;
        if (playlist == null) {
            this.data = null;
        } else {
            this.data = new Medium[playlist.getList().size()];
            playlist.getList().toArray(this.data);
        }
        fireTableDataChanged();
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

}
