package com.krachkouski.krachkouski;

import com.krachkouski.krachkouski.database.Database;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MainController {

    @FXML
    private TextField wordText;

    @FXML
    private TextArea definitionText;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Word> wordsListView;



    @FXML
    protected void clearClick() {
        wordText.clear();
        definitionText.clear();
        searchField.clear();
        wordText.requestFocus();
    }



    @FXML
    protected void searchFieldEdit() {
        //При вводе изменяем текст в верхний регистр
        searchField.setText(searchField.getText().toUpperCase());
        //Ставим картеку в конец текста
        searchField.positionCaret(searchField.getLength());
        //Заполняем ListView найденными элементами
        wordsListView.setItems(Database.selectTableTerminology(searchField.getText()));
    }



    @FXML
    protected void listClick() {
        Word word = wordsListView.getSelectionModel().getSelectedItem();
        if (word != null) {
            wordText.setText(word.getWord());
            definitionText.setText(word.getDefinition());
            System.out.println(word.getId());
        }
    }



    @FXML
    protected void onInsertButtonClick() {
        Database.insertTableTerminology(wordText.getText(), definitionText.getText());
        ObservableList<Word> words = Database.selectTableTerminology("");
        int count = 0;
        for(Word word : words) {
            if (wordText.getText().equalsIgnoreCase(word.getWord()))
            {
                break;
            }
            count++;
        }

        wordsListView.setItems(words);



        wordsListView.getSelectionModel().select(count);
        wordsListView.getFocusModel().focus(count);
        wordsListView.scrollTo(count);


        clearClick();
    }



    @FXML
    protected void onDeleteButtonClick() {
        Database.deleteTableTerminology(wordsListView.getSelectionModel().getSelectedItem().getId());
        wordsListView.setItems(Database.selectTableTerminology(""));

        clearClick();
    }



    @FXML
    protected void onUpdateButtonClick() {
        Word word = wordsListView.getSelectionModel().getSelectedItem();
        Database.updateTableTerminology(word.getId(), wordText.getText(), definitionText.getText());
        wordsListView.setItems(Database.selectTableTerminology(""));
    }




    public void initialize() {

        //Переопределяем ListView для того чтобы отображать объекты
        wordsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Word item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getWord() == null) {
                    setText(null);
                } else {
                    setText(item.getWord());
                }
            }
        });


        //Добавляем слушатель к ListView
        wordsListView.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> listClick());





        //Заполняем ListView данными из базы данных
        wordsListView.setItems(Database.selectTableTerminology(""));
    }



}