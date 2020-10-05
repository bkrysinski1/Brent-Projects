package com.techelevator.tenmo.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferNotFoundException;
import com.techelevator.tenmo.model.User;

@Service
public class UserSqlDAO implements UserDAO {

    private static final double STARTING_BALANCE = 1000;
    private JdbcTemplate jdbcTemplate;

    public UserSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        return jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        for (User user : this.findAll()) {
            if( user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {
        boolean userCreated = false;
        boolean accountCreated = false;

        // create user
        String insertUser = "insert into users (username,password_hash) values(?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String id_column = "user_id";
        userCreated = jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(insertUser, new String[]{id_column});
                    ps.setString(1, username);
                    ps.setString(2,password_hash);
                    return ps;
                }
                , keyHolder) == 1;
        int newUserId = (int) keyHolder.getKeys().get(id_column);

        // create account
        String insertAccount = "insert into accounts (user_id,balance) values(?,?)";
        accountCreated = jdbcTemplate.update(insertAccount,newUserId,STARTING_BALANCE) == 1;

        return userCreated && accountCreated;
    }
    
    public double getBalance(int userId) {
    	double ourBalance = 0;
    	String sqlGetBalance = "SELECT balance FROM accounts WHERE user_id = ?";
    	SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBalance, userId);
    	while(result.next()) {
    	ourBalance = result.getDouble("balance");
    	}
    	return ourBalance;
    }
    
    public boolean sendTransfer(Transfer newTransfer) {
		boolean transferInserted = false;
		boolean fundsTaken = false;
		boolean fundsReceived = false;
    	String sqlLogTransfer = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
    							+ "VALUES (?, ?, ?, ?, ?)";
		transferInserted = jdbcTemplate.update(sqlLogTransfer, newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(), newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount()) == 1;
		String sqlDeduct = "UPDATE accounts SET balance = (balance - ?) WHERE account_id = ?";
		fundsTaken = jdbcTemplate.update(sqlDeduct, newTransfer.getAmount(), newTransfer.getAccountFrom()) == 1;
		String sqlAdd = "UPDATE accounts SET balance = (balance + ?) WHERE account_id = ?";
		fundsReceived = jdbcTemplate.update(sqlAdd, newTransfer.getAmount(), newTransfer.getAccountTo()) == 1;
		
		return transferInserted && fundsTaken && fundsReceived;
	}
    
    public void approveRequest(Transfer t) throws TransferNotFoundException {
		boolean transferUpdated = false;
		boolean fundsTaken = false;
		boolean fundsReceived = false;
    	String sqlLogTransfer = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ?";
		transferUpdated = jdbcTemplate.update(sqlLogTransfer, t.getTransferStatusId(), t.getTransferId()) == 1;
		String sqlDeduct = "UPDATE accounts SET balance = (balance - ?) WHERE account_id = ?";
		fundsTaken = jdbcTemplate.update(sqlDeduct, t.getAmount(), t.getAccountFrom()) == 1;
		String sqlAdd = "UPDATE accounts SET balance = (balance + ?) WHERE account_id = ?";
		fundsReceived = jdbcTemplate.update(sqlAdd, t.getAmount(), t.getAccountTo()) == 1;
		
		if( !transferUpdated || !fundsTaken || !fundsReceived) {
				throw new TransferNotFoundException();
			}
		
	}
    
    public void declineRequest(Transfer t) throws TransferNotFoundException {
    	String sqlDecline = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ?";
		int numAffected = jdbcTemplate.update(sqlDecline, t.getTransferStatusId(), t.getTransferId());
		if (numAffected == 0) {
			throw new TransferNotFoundException();
		}
    }
    
    
    
    
    public boolean makeRequest(Transfer newTransfer) {
		boolean requestInserted = false;
    	String sqlLogTransfer = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
    							+ "VALUES (?, ?, ?, ?, ?)";
		requestInserted = jdbcTemplate.update(sqlLogTransfer, newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(), newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount()) == 1;

		return requestInserted;
	}
    public List<Transfer> seePendingRequests(int userId)  {
    	List<Transfer> myPendings = new ArrayList<>(); 

    	String sqlFindPending = "SELECT transfer_id, transfer_status_id, transfer_type_id, account_to, account_from, amount " +
    							"FROM transfers WHERE transfer_status_id = 1 AND (account_from = ? OR account_to = ?)";
    	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindPending, userId, userId);
    	while(results.next()) {
    		Transfer transfer = mapRowToTransfer(results);
    		myPendings.add(transfer);
    	}
    		return myPendings;
    	
    }
    
    
    
    public List<Transfer> getTransfersById(int userId)  {
    	List<Transfer> ourListOfTransfers = new ArrayList<>(); 

    	String sqlTransfers = "SELECT transfer_id, transfer_status_id, transfer_type_id, account_to, account_from, amount "
    						+ "FROM transfers WHERE (transfer_status_id = 2 OR transfer_status_id = 1) AND (account_from = ? OR account_to = ?)";
    	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlTransfers, userId, userId);
    	while(results.next()) {
    		Transfer transfer = mapRowToTransfer(results);
    		ourListOfTransfers.add(transfer);
    	}
    		return ourListOfTransfers;
    	
    }
    
    public Transfer getTransferDetailsById(int transferId, int userId) throws TransferNotFoundException{
    	Transfer transfer = new Transfer();
    	String sqlTransfers = "SELECT transfer_id, transfer_status_id, transfer_type_id, account_to, account_from, amount "
    						+ "FROM transfers WHERE transfer_id = ? "
    						+ "AND (account_from = ? OR account_to = ?)";
    	SqlRowSet result = jdbcTemplate.queryForRowSet(sqlTransfers, transferId, userId, userId);
    	
    	
    	if(result.next()) {
    		transfer = mapRowToTransfer(result);
    	}
    	else {
    		throw new TransferNotFoundException();
    	}
    	

    	
    	return transfer;
    }
    
    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("ROLE_USER");
        return user;
    }
    
    private Transfer mapRowToTransfer(SqlRowSet rs) {
    	Transfer t = new Transfer();
    	t.setAccountFrom(rs.getInt("account_from"));
    	t.setAccountTo(rs.getInt("account_to"));
    	t.setAmount(rs.getDouble("amount"));
    	t.setTransferTypeId(rs.getInt("transfer_type_id"));
    	t.setTransferId(rs.getInt("transfer_id"));
    	t.setTransferStatusId(rs.getInt("transfer_status_id"));
    	return t;
    }
    
}
