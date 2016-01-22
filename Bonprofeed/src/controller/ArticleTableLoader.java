package controller;

import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import model.Article;
import model.ArticleTable;

public class ArticleTableLoader {

	private ArticleTable articleTable;
	private ObservableList<Article> articles;
	private WindowLoader windowLoader;
	
	
	public ArticleTableLoader(ArticleTable articleTable,ObservableList<Article> articles,WindowLoader windowLoader) {
		this.articleTable= articleTable;
		this.articles=articles;
		this.windowLoader=windowLoader;
	}
	
	public void generateArticleTable(){
		ObservableList<Article> articles = FXCollections.observableArrayList(this.articles);

		// Initialize the columns.
		articleTable.getColumnTitle().setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		articleTable.getColumnAuthor().setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		articleTable.getColumnDate().setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
		articleTable.getColumnVisto().setCellValueFactory(cellData -> cellData.getValue().getReadenProperty());
		
		articleTable.getColumnDate().setCellFactory(column -> {
			return new TableCell<Article, Date>() {
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item.toString());
						
					}
				}
			};
		});
		
		articleTable.getColumnVisto().setCellFactory(column -> {
			return new TableCell<Article, Integer>() {
				@Override
				protected void updateItem(Integer item, boolean empty) {
					super.updateItem(item, empty);
					
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						if(item == 0){
							setText("No Visto");
							setStyle("-fx-background-color: red; -fx-text-fill: white;");
						}else{
							setText("Visto");
							setStyle("-fx-background-color: green; -fx-text-fill: white;");
						}
						
						
					}
				}
			};
		});
		
		
		articleTable.getArticlesTable().setRowFactory( tv -> {
		    TableRow<Article> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Article rowArticle = row.getItem();
		            
		            windowLoader.loadArticle(rowArticle,row);
		            
		        }
		    });
		    return row ;
		});
		
		articleTable.getArticlesTable().setItems(articles);
		
	}

}
