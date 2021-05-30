package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Chat extends ActiveRecord {

	private String title;
	private String owner;
	private String description;
	private String date_creation;
	private String date_peremption;

	public Chat(String title, String owner, String description, String date_creation, String date_peremption) {
		this.title = title;
		this.owner = owner;
		this.description = description;
		this.date_creation = date_creation;
		this.date_peremption = date_peremption;
		this._builtFromDB = false;
	}

	public Chat(String title, String owner, String description, String date_peremption) {
		this.title = title;
		this.owner = owner;
		this.description = description;
		this.date_creation = null;
		this.date_peremption = date_peremption;
		this._builtFromDB = false;
	}

	public Chat(ResultSet res) throws SQLException {
		this.set_id(res.getInt("id"));
		this.title = res.getString(2);
		this.owner = res.getString(3);
		this.description = res.getString(4);
		this.date_creation = res.getString(5);
		this.date_peremption = res.getString(6);
		this._builtFromDB = true;
	}

	@Override
	protected void construireModel(ResultSet res) throws SQLException, IOException, ClassNotFoundException {
		this.set_id(res.getInt("id"));
		this.title = res.getString(2);
		this.owner = res.getString(3);
		this.description = res.getString(4);
		this.date_creation = res.getString(5);
		this.date_peremption = res.getString(6);
	}

	@Override
	protected ResultSet _getByID(String id) throws ClassNotFoundException, IOException, SQLException {

		String select_query = "SELECT * FROM `db_sr03`.`chat` WHERE `id` = ?";
		Connection conn = BDDConector.getInstance();
		PreparedStatement sql = conn.prepareStatement(select_query);
		sql.setInt(1, Integer.parseInt(id));
		return sql.executeQuery();
	}

	@Override
	protected void _update() throws SQLException, ClassNotFoundException, IOException {
		
		Connection conn = BDDConector.getInstance();	
		
		
		String update_query = "UPDATE `db_sr03`.`chat` SET "
				+ "`title` = ? , `owner` = ? , `description` = ? , `date_peremption` = ? " + "WHERE `id` = ? ;";

		PreparedStatement sql = conn.prepareStatement(update_query);
		sql.setString(1, title);
		sql.setString(2, owner);
		sql.setString(3, description);
		sql.setString(4, date_peremption);
		sql.setInt(5, this.get_id());
		sql.executeUpdate();

	}

	@Override
	protected void _insert() throws ClassNotFoundException, IOException, SQLException {

		String insert_query = "INSERT INTO `db_sr03`.`chat` (`title`, `owner`, `description`, `date_peremption`)"
				+ "VALUES ( ? , ? , ? , ? );";

		Connection conn = BDDConector.getInstance();
		PreparedStatement sql = conn.prepareStatement(insert_query, Statement.RETURN_GENERATED_KEYS);
		sql.setString(1, title);
		sql.setString(2, owner);
		sql.setString(3, description);
		sql.setString(4, date_peremption);
		sql.executeUpdate();

		ResultSet res = sql.getGeneratedKeys();
		if (res.next()) {
			int key = res.getInt(1);
			this.set_id(key);
		}

	}

	@Override
	protected void _delete() throws ClassNotFoundException, IOException, SQLException {

		Connection conn = BDDConector.getInstance();
		
		//Suppression des invités
		String delete_query = "DELETE FROM `db_sr03`.`participant_chat` WHERE `chat` = ? ";
		PreparedStatement sql = conn.prepareStatement(delete_query);
		sql.setString(1, this.title);
		sql.executeUpdate();
		
		//Suppression du chat
		delete_query = "DELETE FROM `db_sr03`.`chat` WHERE `id` = ? ";
		sql = conn.prepareStatement(delete_query);
		sql.setInt(1, this.get_id());
		sql.executeUpdate();
		
		this._builtFromDB = false;

	}

	public void delete() throws ClassNotFoundException, IOException, SQLException {
		this._delete();
	}

	public void addParticipant(String login) throws SQLException, ClassNotFoundException, IOException {

		String insert_query = "INSERT INTO `db_sr03`.`participant_chat` VALUES (? , ?);";
		Connection conn = BDDConector.getInstance();
		PreparedStatement sql = conn.prepareStatement(insert_query);
		sql.setString(1, login);
		sql.setString(2, title);
		sql.executeUpdate();
	}

	static public ArrayList<Chat> getAllChatOwnedByLogin(String login)
			throws ClassNotFoundException, IOException, SQLException {
		ArrayList<Chat> listeChat = new ArrayList<Chat>();
		String select_query = "SELECT * FROM `db_sr03`.`chat` " + "WHERE owner = ? "
				+ "AND `date_peremption` > CURDATE();";
		Connection conn = BDDConector.getInstance();
		PreparedStatement sql = conn.prepareStatement(select_query);
		sql.setString(1, login);
		ResultSet res = sql.executeQuery();
		while (res.next()) {
			listeChat.add(new Chat(res));
		}
		return listeChat;
	}

	static public ArrayList<Chat> getAllChatWhereLoginInvited(String login)
			throws ClassNotFoundException, IOException, SQLException {

		ArrayList<Chat> listeChat = new ArrayList<Chat>();
		String select_query = "SELECT DISTINCT `chat`.* FROM `chat`, `participant_chat` " + "WHERE `chat`.`owner` != ? "
				+ "AND `participant_chat`.`login` = ?" + "AND `chat`.`date_peremption` > CURDATE();";
		Connection conn = BDDConector.getInstance();
		PreparedStatement sql = conn.prepareStatement(select_query);
		sql.setString(1, login);
		sql.setString(2, login);
		ResultSet res = sql.executeQuery();
		while (res.next()) {
			listeChat.add(new Chat(res));
		}
		return listeChat;
	}

	static public Chat findChatByTitle(String title) throws ClassNotFoundException, IOException, SQLException {

		String select_query = "SELECT * FROM `db_sr03`.`chat` WHERE `title` = ? ;";
		Connection conn = BDDConector.getInstance();
		PreparedStatement sql = conn.prepareStatement(select_query);
		sql.setString(1, title);
		ResultSet res = sql.executeQuery();

		if (res.next())
			return new Chat(res);

		return null;
	}
	
	public ResultSet getAllInvitedMembers () throws ClassNotFoundException, IOException, SQLException {

		String select_query = "SELECT `login` FROM `db_sr03`.`participant_chat` WHERE `chat` = '"+title+"' ;";
		Connection conn = BDDConector.getInstance();
		PreparedStatement sql = conn.prepareStatement(select_query);
		return sql.executeQuery();
		
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}

	public String getDate_peremption() {
		return date_peremption;
	}

	public void setDate_peremption(String date_peremption) {
		this.date_peremption = date_peremption;
	}

}
