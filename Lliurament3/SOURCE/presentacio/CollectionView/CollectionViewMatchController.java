package presentacio.CollectionView;

import dades.Table;
import domini.Basic.Match;
import domini.BoardCreator.CpuBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinterNoSelect;
import presentacio.MainWindow;
import presentacio.Match.MatchController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Joan on 14/12/2015.
 */
public class CollectionViewMatchController extends AnchorPane implements Controller {

    protected Table<KKBoard> mBoards;


    protected Table<Match> mMatch;

    protected HashMap<String, CheckBox> mPlayers;

    protected ArrayList<RadioButton> mSelMatch;

    protected KKPrinterNoSelect mPrinter;

    protected MainWindow mMain;

    @FXML
    protected VBox leftArea;

    @FXML
    protected VBox rightArea;

    @FXML
    protected StackPane kkPane;

    public CollectionViewMatchController(MainWindow main) {
        mBoards = main.getBoards();
        mMatch = main.getmMatch();
        mPlayers = new HashMap<>();
        mSelMatch = new ArrayList<>();
        mMain = main;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("CollectionView.fxml"));
        loader.setController(this);

        SplitPane second;
        try {
            second = loader.load();
            this.getChildren().add(second);

            AnchorPane.setBottomAnchor(second, .0);
            AnchorPane.setTopAnchor(second, .0);
            AnchorPane.setLeftAnchor(second, .0);
            AnchorPane.setRightAnchor(second, .0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        stubCreaTaulers();
        loadPlayers();
        createBoardsPane();
        applyFilters();

        String username = main.getUsername();
        for (Map.Entry<String, CheckBox> e : mPlayers.entrySet()) {
            e.getValue().setSelected(e.getKey().equals(username));
            e.getValue().setDisable(true);
        }
        applyFilters();
    }

    @Override
    public AnchorPane getRootLayout() {
        return this;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setScene(Scene scene) {

    }

    private void stubCreaTaulers() {
        CpuBoardCreator creator = new CpuBoardCreator(2, mBoards);

        ArrayList<String> names = new ArrayList<>();
        names.add("pere");
        names.add("joan");
        names.add("anna");
        names.add("admin");
        names.add("joan2");
        names.add("pere");
        names.add("joan3");
        names.add("anna3");
        names.add("admin3");
        names.add("joan23");
        names.add("pere4");
        names.add("joan5");
        names.add("anna5");
        names.add("admi5n");
        names.add("joan25");
        names.add("pere6");
        names.add("joan6");
        names.add("anna6");
        names.add("admin6");
        names.add("joan26");
        names.add("pere7");
        names.add("joan7");
        names.add("anna7");
        names.add("admin7");
        names.add("joan27");


        if (mBoards.size() < 25) {
            for (int i = 0; i < 25; ++i) {
                try {
                    creator.createBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                creator.saveBoard("auto" + Integer.toString(i), names.get(i % names.size()));
            }
        }
    }

    protected void loadPlayers() {
        HashSet<String> player;
        player = mBoards.stream().map(KKBoard::getCreator).collect(Collectors.toCollection(HashSet::new));

        for (String s : player) {
            HBox box = new HBox();
            CheckBox check = new CheckBox();
            check.setSelected(true);

            box.getChildren().addAll(check, new Label(s));

            leftArea.getChildren().add(box);
            VBox.setMargin(box, new Insets(5.0));

            mPlayers.put(s, check);
        }
    }

    public void applyFilters() {
        for (RadioButton but : mSelMatch) {
            Match match = (Match) but.getUserData();
            boolean view = mPlayers.get(match.getPlayer().getUserName()).isSelected();
            but.setVisible(view);
            but.setManaged(view);
        }
    }

    protected void createBoardsPane() {

        ToggleGroup toggle = new ToggleGroup();
        for (Match match : mMatch) {
            if(! match.finished()) {
                RadioButton radio = new RadioButton();
                radio.setUserData(match);
                radio.setToggleGroup(toggle);
                radio.setText(match.getPlayer().getUserName() +
                        " (" + match.getPlayer().getName() + ") del Tauler" + match.getBoard().getName() +
                        "  Score " + match.getScore());

                radio.setOnAction(event -> {
                    if (mPrinter == null) mPrinter = new KKPrinterNoSelect(((Match) radio.getUserData()).getBoard(), kkPane);
                    else {
                        mPrinter.setBoard(((Match) radio.getUserData()).getBoard());
                        mPrinter.updateRegions();
                    }
                });

                rightArea.getChildren().add(radio);
                mSelMatch.add(radio);
            }
        }
    }

    @FXML
    public void dialogAccept() {
        Match sel = null;
        for (RadioButton r : mSelMatch) {
            if (r.isSelected()) sel = (Match) r.getUserData();
        }
        MatchController mc = new MatchController(sel, mMain);
        mMain.getMainController().getContSwitch().switchController(mc);
    }

    @FXML
    public void dialogReject() {
        mMain.getMainController().dialogCancelled();
    }
}
