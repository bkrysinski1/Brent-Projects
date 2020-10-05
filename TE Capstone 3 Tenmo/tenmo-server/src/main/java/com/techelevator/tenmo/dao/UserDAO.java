package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferNotFoundException;
import com.techelevator.tenmo.model.User;

public interface UserDAO {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
    
    double getBalance(int userId);
    
    boolean sendTransfer(Transfer newTransfer);
    
    public List<Transfer> getTransfersById(int userId);

    Transfer getTransferDetailsById(int transferId, int userId) throws TransferNotFoundException;
    
    public void approveRequest(Transfer newTransfer) throws TransferNotFoundException;
    
    public void declineRequest(Transfer newTransfer) throws TransferNotFoundException;
    
    public boolean makeRequest(Transfer newTransfer);
    
    public List<Transfer> seePendingRequests(int userId);
}
