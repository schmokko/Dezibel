package de.dezibel.gui;

import de.dezibel.data.Medium;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * Shows the information of media in the searchpanel.
 * @author Richard, Tobias
 */
public class RecommendationsTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Künstler", "Titel"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class, String.class};

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
     * @param data The data to display
     */
    public void setData(LinkedList<Medium> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new Medium[data.size()];
            data.toArray(this.data);
        }
        fireTableDataChanged();
    }
    
}
