package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User extends ActiveRecord {

    private String lastName;
    private String firstName;
    private String login;
    private Gender gender;
    private String password;

    public enum Gender {
        M, F
    }

    public User() {
        _builtFromDB = false;
    }

    public User(String ln, String fn, String lg, Gender gdr, String pwd) {
        this.lastName = ln;
        this.firstName = fn;
        this.login = lg;
        this.gender = gdr;
        this.password = pwd;
        _builtFromDB = false;
    }

    public User(String ln, String fn, String lg, String gdr, String pwd) {
        this.lastName = ln;
        this.firstName = fn;
        this.login = lg;
        if (gdr.equals("M"))
            this.gender = Gender.M;
        if (gdr.equals("F"))
            this.gender = Gender.F;
        this.password = pwd;
        _builtFromDB = false;
    }

    public User(ResultSet result) throws SQLException {
        this.set_id(result.getInt("id"));
        this.firstName = result.getString(2);
        this.lastName = result.getString(3);
        this.login = result.getString(4);
        this.password = result.getString(6);
        this.gender = Gender.valueOf(result.getString("gender"));
        _builtFromDB = true;
    }

    public User(int id) throws IOException, ClassNotFoundException, SQLException {
        build(Integer.toString(id));
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected ResultSet _getByID(String id)
            throws SQLException, ClassNotFoundException, IOException, NumberFormatException {

        String select_query = "SELECT * FROM `db_sr03`.`user` WHERE `id` = ?";
        Connection conn = BDDConector.getInstance();
        PreparedStatement sql = conn.prepareStatement(select_query);
        sql.setInt(1, Integer.parseInt(id));
        return sql.executeQuery();
    }

    @Override
    public void construireModel(ResultSet res) throws SQLException, IOException, ClassNotFoundException {
        this.set_id(res.getInt("id"));
        this.firstName = res.getString(2);
        this.lastName = res.getString(3);
        this.login = res.getString(4);
        this.gender = Gender.valueOf(res.getString("gender"));
        this.password = res.getString(7);
    }

    @Override
    protected void _update() throws SQLException, ClassNotFoundException, IOException {

        String update_query = "UPDATE `db_sr03`.`users` SET "
                + "`fname` = ? , `lname` = ? , `login` = ? , `gender` = ? , `pwd` = ? "
                + "WHERE `id` = ? ;";

        Connection conn = BDDConector.getInstance();
        PreparedStatement sql = conn.prepareStatement(update_query);
        sql.setString(1, firstName);
        sql.setString(2, lastName);
        sql.setString(3, login);
        sql.setString(4, gender.toString());
        sql.setString(5, password);
        sql.setInt(6, get_id());
        sql.executeUpdate();

    }

    @Override
    protected void _insert() throws ClassNotFoundException, IOException, SQLException {

        String insert_query = "INSERT INTO `db_sr03`.`users` (`fname`, `lname`, `login`, `gender`,`pwd`)"
                + "VALUES ( ? , ? , ? , ? , ? );";

        Connection conn = BDDConector.getInstance();
        PreparedStatement sql = conn.prepareStatement(insert_query, Statement.RETURN_GENERATED_KEYS);
        sql.setString(1, firstName);
        sql.setString(2, lastName);
        sql.setString(3, login);
        sql.setString(4, gender.toString());
        sql.setString(5, password);
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
        String delete_query = "DELETE FROM `db_sr03`.`users` WHERE `id` = ? ";
        PreparedStatement sql = conn.prepareStatement(delete_query);
        sql.setInt(1, this.get_id());
        sql.executeQuery();
        
        this._builtFromDB = false;

    }

    public static User FindByloginAndPwd(String login, String pwd)
            throws IOException, ClassNotFoundException, SQLException {
        Connection conn = BDDConector.getInstance();
        String select_query = "SELECT * from `db_sr03`.`users` where `login` = ? and `pwd` = ? ;";
        PreparedStatement sql = null;
        sql = conn.prepareStatement(select_query);
        sql.setString(1, login);
        sql.setString(2, pwd);
        ResultSet res = sql.executeQuery();
        if (res.next()) {
            User u = new User(res);
            return u;

        }
        return null;
    }

    public static ResultSet getAllUsers() throws IOException, ClassNotFoundException, SQLException {
        Connection conn = BDDConector.getInstance();
        String query = "SELECT `login` FROM `db_sr03`.`users`";
        PreparedStatement sql = conn.prepareStatement(query);
        return sql.executeQuery();
    }

    public static User findByLastAndFirstName(String fname, String lname)
            throws IOException, ClassNotFoundException, SQLException {
        Connection conn = BDDConector.getInstance();
        String select_query = "select * from `db_sr03`.`users` where `fname` = ? and `lname` = ? ;";
        PreparedStatement sql = null;
        sql = conn.prepareStatement(select_query);
        sql.setString(1, fname);
        sql.setString(2, lname);
        ResultSet res = sql.executeQuery();
        if (res.next()) {
            User u = new User(res);
            return u;

        }
        return null;
    }

    public static User findByLogin(String login) throws IOException, ClassNotFoundException, SQLException {
        Connection conn = BDDConector.getInstance();
        String select_query = "select * from `db_sr03`.`users` where `login` = ?  ;";
        PreparedStatement sql = null;
        sql = conn.prepareStatement(select_query);
        sql.setString(1, login);
        ResultSet res = sql.executeQuery();
        if (res.next()) {
            User u = new User(res);
            return u;
        }
        return null;
    }

    public static User findById(int id) throws IOException, ClassNotFoundException, SQLException {
        return new User(id);
    }

    public static List<User> findAll() throws IOException, ClassNotFoundException, SQLException {
        List<User> listUser = new ArrayList<User>();

        Connection conn = BDDConector.getInstance();
        Statement sql = conn.createStatement();
        ResultSet res = sql.executeQuery("SELECT * from users");
        
        while (res.next()) {
            User newUser = new User(res);
            listUser.add(newUser);
        }

        return listUser;
    }

}
