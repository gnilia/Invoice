package com.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dao.AuthorityDao;
import com.dao.DaoJdbcTemplate;
import com.model.Authority;
@Repository("authorityDao")
public class AuthorityDaoImpl extends DaoJdbcTemplate implements AuthorityDao {

	@Override
	public void addCon(Authority authority) {
		StringBuffer sb = new StringBuffer("insert into t_com_user_con(CON_ID,COM_ID,USER_ID) VALUES(")
		.append(authority.getConId())
		.append(",")
		.append(authority.getComId())
		.append(",")
		.append(authority.getEmpId())
		.append(")");
		getJdbcTempalte().execute(sb.toString());
	}

	@Override
	public void removeCon(Integer id,Integer comId) {
		String sql = "update  t_com_user_con set ABLED = 0 where ID = ? and COM_ID = ?";
		getJdbcTempalte().update(sql,new Object[]{id,comId});
	}

	@Override
	public void updateCon(Authority authority) {
		String sql = "update t_com_user_con set COM_ID = ? ,CON_ID=?,USER_ID=? WHERE ID = ?";
		getJdbcTempalte().update(sql, new Object[]{
				authority.getComId(),
				authority.getConId(),
				authority.getEmpId()});
	}

	@Override
	public Authority findCon(Authority authority) {
		String sql ="select * from t_com_user_con where COM_ID = ? and CON_ID = ? and USER_ID = ? and ABLED = 1";
		List<Authority> authorityList = getJdbcTempalte().query(sql, new Object[]{
				authority.getComId(), 
				authority.getConId(),
				authority.getEmpId()},new RowMapper<Authority>(){

					@Override
					public Authority mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Authority authority = new Authority();
						authority.setComId(rs.getInt("COM_ID"));
						authority.setConId(rs.getInt("CON_ID"));
						authority.setEmpId(rs.getInt("USER_ID"));
						authority.setId(rs.getInt("ID"));
						authority.setAbled(rs.getBoolean("ABLED"));
						return authority;
					}});
		
		if(null!=authorityList&&authorityList.size()>0){
			return authorityList.get(0);
		}
		return null;
	}

	@Override
	public void addInv(Authority authority) {
		StringBuffer sb = new StringBuffer("insert into t_com_user_inv(INV_ID,COM_ID,USER_ID) VALUES(")
		.append(authority.getInvId())
		.append(",")
		.append(authority.getComId())
		.append(",")
		.append(authority.getEmpId())
		.append(")");
		getJdbcTempalte().execute(sb.toString());
	}

	@Override
	public void removeInv(Integer id,Integer comId) {
		String sql = "update  t_com_user_inv set ABLED = 0 where ID = ? and COM_ID = ?";
		getJdbcTempalte().update(sql,new Object[]{id,comId});
	
	}

	@Override
	public void updateInv(Authority authority) {

		String sql = "update t_com_user_inv set com_ID = ? ,inv_ID=?,USER_ID=? WHERE ID = ?";
		getJdbcTempalte().update(sql, new Object[]{
				authority.getInvId(),
				authority.getConId(),
				authority.getEmpId(),
				authority.getId()});
	}

	@Override
	public Authority findInv(Authority authority) {
		String sql ="select * from t_com_user_inv where com_ID = ? and INV_ID = ? and USER_ID = ? and ABLED = 1";
		List<Authority> authorityList = getJdbcTempalte().query(sql, new Object[]{
				authority.getComId(), 
				authority.getInvId(),
				authority.getEmpId()},new RowMapper<Authority>(){

					@Override
					public Authority mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Authority authority = new Authority();
						authority.setComId(rs.getInt("COM_ID"));
						authority.setConId(rs.getInt("INV_ID"));
						authority.setEmpId(rs.getInt("USER_ID"));
						authority.setId(rs.getInt("ID"));
						authority.setAbled(rs.getBoolean("ABLED"));
						return authority;
					}});
		
		if(null!=authorityList&&authorityList.size()>0){
			return authorityList.get(0);
		}
		return null;
	}

	@Override
	public List<Integer> findConByEmpId(Integer empId) {
		String sql = "select ID from t_com_user_con where USER_ID = ? and ABLED = 1";
		
		return getJdbcTempalte().query(sql, new Object[]{empId},new RowMapper<Integer>(){

			@Override
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				Integer in = new Integer(rs.getInt("ID"));
				return in;
			}
		});
	}

	@Override
	public List<Integer> findInvByEmpId(Integer empId) {
		
		String sql = "select ID from t_com_emp_inv where EMP_ID = ?  and ABLED = 1";
		
		return getJdbcTempalte().query(sql, new Object[]{empId},new RowMapper<Integer>(){

			@Override
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				Integer in = new Integer(rs.getInt("ID"));
				return in;
			}
		});
	}

	@Override
	public List<Authority> findComIdByInvId(Integer invId) {
		
		String sql ="select COM_ID,EMP_ID form t_com_emp_inv where INV_ID = ? AND ABLED = 1 ";
		return getJdbcTempalte().query(sql, new Object[]{invId},new RowMapper<Authority>(){

			@Override
			public Authority mapRow(ResultSet rs, int arg1) throws SQLException {
				Authority in = new Authority();
				in.setComId(rs.getInt("COM_ID"));
				in.setEmpId(rs.getInt("EMP_ID"));
				return in;
			}
		});
	}
}
