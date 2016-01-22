package model;

import java.util.Date;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ArticleTable {

	private TableView<Article> articlesTable;
	private TableColumn<Article, Integer> columnVisto;
	private TableColumn<Article, String> columnTitle;
	private TableColumn<Article, String> columnAuthor;
	private TableColumn<Article, Date> columnDate;
	
	public ArticleTable(TableView<Article> articlesList,TableColumn<Article, Integer> columnVisto,TableColumn<Article, String> columnTitle,TableColumn<Article, String> columnAuthor,TableColumn<Article, Date> columnDate) {
		this.articlesTable=articlesList;
		this.columnVisto=columnVisto;
		this.columnTitle=columnTitle;
		this.columnAuthor=columnAuthor;
		this.columnDate=columnDate;
	}

	/**
	 * @return the articlesTable
	 */
	public TableView<Article> getArticlesTable() {
		return articlesTable;
	}

	/**
	 * @return the columnVisto
	 */
	public TableColumn<Article, Integer> getColumnVisto() {
		return columnVisto;
	}

	/**
	 * @return the columnTitle
	 */
	public TableColumn<Article, String> getColumnTitle() {
		return columnTitle;
	}

	/**
	 * @return the columnAuthor
	 */
	public TableColumn<Article, String> getColumnAuthor() {
		return columnAuthor;
	}

	/**
	 * @return the columnDate
	 */
	public TableColumn<Article, Date> getColumnDate() {
		return columnDate;
	}
	
	

	
}
