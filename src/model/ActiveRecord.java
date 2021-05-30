package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ActiveRecord {

	private int _id;

	protected boolean _builtFromDB = false;

	protected abstract void _update() throws SQLException, ClassNotFoundException, IOException;

	protected abstract void _insert() throws ClassNotFoundException, IOException, SQLException;

	protected abstract void _delete() throws ClassNotFoundException, IOException, SQLException;

	protected abstract ResultSet _getByID(String id)
			throws SQLException, ClassNotFoundException, IOException, NumberFormatException;

	protected abstract void construireModel(ResultSet res) throws SQLException, IOException, ClassNotFoundException;

	public void build(String id) throws SQLException, IOException, ClassNotFoundException {

		ResultSet res = _getByID(id);

		if (res.next()) {

			construireModel(res);
			_builtFromDB = true;

		}
	}

	public void save() throws SQLException, IOException, ClassNotFoundException {

		if (_builtFromDB)
			_update();
		else
			_insert();

	}

	public int get_id() {
		return _id;
	}

	protected void set_id(int _id) {
		this._id = _id;
	}

	public void remove() throws IOException, ClassNotFoundException, SQLException {
		if (_builtFromDB) {
			this._delete();

		} else {
			System.out.println("L'objet est non persistant !");
		}
	}

}
