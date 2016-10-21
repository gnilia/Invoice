package com.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dao.CompanyDao;
import com.dao.DaoJdbcTemplate;
import com.model.Company;
@Repository("companyDao")
public class CompanyDaoImpl extends DaoJdbcTemplate implements CompanyDao{

	@Override
	public void add(Company company) {
		
		StringBuffer sql = new StringBuffer("INSERT INTO t_company(")
			//公司名称
			.append("COMPANY_NAME,")
			//密码
			.append("PWORD,")
			//邮箱
			.append("EMAIL,")
			//公司代码
			.append("COMPANY_CODE,")
			//电话
			.append("TELLPHONE_NUMBER)")
			.append(" VALUES('")
			//单位
			.append(company.getCompanyName())
			.append("','")
			//密码
			.append(company.getPassword())
			.append("','")
			//邮箱
			.append(company.getCompanyEmail())
			.append("','")
			//公司代码
			.append(company.getCompanyCode())
			.append("','")
			//电话
			.append(company.getTellphoneNumber())
			.append("')");
		//插入
		getJdbcTempalte().execute(sql.toString());
	}

	@Override
	public void remove(String companyName) {
		String sql ="update t_company set ABLED = 0 WHERE COMPANY_NAME = ? ";
		
		getJdbcTempalte().update(sql, new Object[]{companyName});
	}

	@Override
	public void update(Company company) {
		StringBuffer sql = new StringBuffer("update t_company set ")
				.append("COMPANY_CODE = ? ,")
				.append("COMPANY_NAME = ? ,")
				.append("PWORD = ? ,")
				.append("EMAIL = ? ,")
				.append("TELLPHONE_NUMBER = ? ");
		
		getJdbcTempalte().update(sql.toString(), new Object[]{company.getCompanyCode(),
				company.getCompanyName(),
				company.getPassword(),
				company.getCompanyEmail(),
				company.getTellphoneNumber()});
	}

	@Override
	public Company find(String companyCode) {
		
		String sql = "SELECT * FROM t_company where COMPANY_CODE = ? ";
		List<Company> companyList = (List<Company>)getJdbcTempalte().query(sql, new Object[]{companyCode}, new RowMapper<Company>(){

			@Override
			public Company mapRow(ResultSet rs, int arg1) throws SQLException {
				
				Company company = new Company();
				//id
				company.setId(rs.getInt("ID"));
				company.setCompanyCode(rs.getString("COMPANY_CODE"));
				company.setCompanyName(rs.getString("COMPANY_NAME"));
				company.setPassword(rs.getString("PWORD"));
				company.setCompanyEmail(rs.getString("EMAIL"));
				company.setTellphoneNumber(rs.getString("TELLPHONE_NUMBER"));
				company.setAbled(rs.getBoolean("ABLED"));
				
				return company;
			}});
		//返回数据件数大于0
		if(companyList.size()>0){
			return companyList.get(0);
		}else{//否则返回null
			return null;
		}
	}

	@Override
	public Company findByOtherInfo(String str) {
		
		String sql = "SELECT * FROM t_company WHERE COMPANY_NAME = ? OR EMAIL = ? OR TELLPHONE_NUMBER = ?";
		
		List<Company> companyList = (List<Company>)getJdbcTempalte().query(sql, new Object[]{str,str,str}, new RowMapper<Company>(){

			@Override
			public Company mapRow(ResultSet rs, int arg1) throws SQLException {
				Company company = new Company();
				company.setId(rs.getInt("ID"));
				company.setCompanyCode(rs.getString("COMPANY_CODE"));
				company.setCompanyName(rs.getString("COMPANY_NAME"));
				company.setPassword(rs.getString("PWORD"));
				company.setCompanyEmail(rs.getString("EMAIL"));
				company.setTellphoneNumber(rs.getString("TELLPHONE_NUMBER"));
				company.setAbled(rs.getBoolean("ABLED"));
				return company;
			}
		});
		//返回数据件数大于0
		if(companyList.size()>0){
			return companyList.get(0);
		}else{//否则返回null
			return null;
		}
	}
}
