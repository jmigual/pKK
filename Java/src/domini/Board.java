package domini;

public class Board {

    public Cell getCell(int i, int j) {
        return _boardInfo[i][j];
    }

    public void setCell(int i, int j, Cell C){
        _boardInfo[i][j] = C;
    }

    public abstract boolean hasSoltuion();
    public abstract int numSolotions();
    public abstract Board getSoltution();
    public abstract void Solve();
    public Region[] getRegions(){
        return _regions;
    }
    public Column[] getColumns(){
        return _columns;
    }
    public Row[] getRows(){
        return _rows;
    }
    private Cell[][] _boardInfo;
    private Region[] _regions;
    private Column[] _columns;
    private Row[] _rows;
}
