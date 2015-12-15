package presentacio.Stats;

import dades.KKDB;
import dades.Table;
import domini.KKBoard;
import domini.stats.KKStats;
import domini.stats.Playable;
import exceptions.PlayerExistsException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import presentacio.MainWindow;

import java.io.IOException;

/**
 * Created by Esteve on 14/12/2015.
 */
public class StatsBoardController extends AnchorPane {

    private KKStats mStats;

    private AnchorPane rootLayout;

    private boolean result = false;

    @FXML
    private TableView tablefm;
    @FXML
    private ComboBox combofm;

    private Table<KKBoard> boards;

    public StatsBoardController(MainWindow main) {
        mStats = main.getKKStats();
        boards = main.getTaulers();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats_Board.fxml"));
        loader.setRoot(this);
        loader.setController(this);


        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showCombo();
        tablefm.setVisible(false);
    }
    private void showCombo(){
        ObservableList<String> options= FXCollections.observableArrayList();;
        for(int i=0; i<boards.size(); ++i){
            options.add(boards.get(i).get_name());
        }

        combofm.setItems(options);
        combofm.setOnAction(event->{
            change();
        });

    }

    private void change(){
        createDefault();
    }

    private void createDefault() {
        // rank column
        TableColumn<InfoRanking, Integer> rankColumn = new TableColumn<>("Position");
        rankColumn.setMinWidth(50);
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        // Name column
        TableColumn<InfoRanking, String> nameColumn = new TableColumn<>("Player");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Score column
        TableColumn<InfoRanking, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(50);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        //Select game

        KKBoard Selected = null;
        for (KKBoard board : boards) {
            if(board.get_name() == combofm.getSelectionModel().getSelectedItem().toString()) Selected = board;

        }

        tablefm.setItems(getStubItems(Selected));
        tablefm.getColumns().clear();
        tablefm.getColumns().addAll(rankColumn, nameColumn, scoreColumn);

        tablefm.setVisible(true);

    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    public boolean getResult() {
        return result;
    }

    public ObservableList<InfoRanking> getStubItems(Playable game) {
        ObservableList<InfoRanking> info = FXCollections.observableArrayList();
        for(int i=0; i<mStats.recordsGame(game).getSize(); ++i){
            info.add(new InfoRanking(i,mStats.recordsGame(game).getPlayer(i).getName(),
                    mStats.recordsGame(game).getValue(i)));
        }
        return info;
    }
}
