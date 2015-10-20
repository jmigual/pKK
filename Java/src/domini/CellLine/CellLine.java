package domini.CellLine;

import domini.Cell;
import domini.ItemPossibilities;

/**
 * Created by Inigo on 19/10/2015.
 */
public class CellLine extends ItemPossibilities {
    protected Cell[] cells;

    /* public CellLine(){
         int size = 9;
         cells = new Cell[size];
         possibilities = new boolean[size];
         for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
     }*/
    public CellLine(int size) {
        super(size);
        cells = new Cell[size];
        possibilities = new boolean[size];
        for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
    }

    public Cell getCell(int pos) {
        return cells[pos];
    }

    public void setCell(int pos, Cell cell) {
        cells[pos] = cell;
    }


    public void calculatePossibilities() {
        for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
        for (int i = 0; i < cells.length; i++) {
            int pos = cells[i].getValue() - 1;
            if (pos != -1) possibilities[pos] = false;
        }
    }

    public boolean isCorrect() {
        boolean[] appeared = new boolean[possibilities.length];
        for (int i = 0; i < appeared.length; i++) appeared[i] = false;
        for (int i = 0; i < cells.length; i++) {
            int pos = cells[i].getValue() - 1;
            if (pos != -1) {
                if (appeared[pos]) return false;
                else appeared[pos] = true;
            }
        }
        return true;
    }
}
