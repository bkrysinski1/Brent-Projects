package com.techelevator.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.Brewery;
import com.techelevator.model.User;

@Service
public class BrewerySqlDAO implements BreweryDAO {

    private JdbcTemplate jdbcTemplate;

    public BrewerySqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByBreweryName(String breweryName) {
        return jdbcTemplate.queryForObject("select brewery_id from breweries where brewery_name = ?", int.class, breweryName);
    }

	@Override
	public Brewery getBreweryById(Long breweryId) {
		String sql = "SELECT * FROM breweries WHERE brewery_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, breweryId);
		if(results.next()) {
			return mapRowToBrewery(results);
		} else {
			throw new RuntimeException("BreweryId "+breweryId+" was not found.");
		}
	}

    @Override
    public List<Brewery> findAll() {
        List<Brewery> breweries = new ArrayList<>();
        String sql = "SELECT * FROM breweries;";
        
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Brewery brewery = mapRowToBrewery(results);
            breweries.add(brewery);
        }
        return breweries;
    }

    @Override
    public Brewery findByBreweryName(String breweryName) {
        for (Brewery brewery : this.findAll()) {
            if( brewery.getBreweryName().toLowerCase().equals(breweryName.toLowerCase())) {
                return brewery;
            }
        }
        throw new UsernameNotFoundException("Brewery " + breweryName + " was not found.");
    }

    @Override
    public Brewery update(Brewery brewery, Long id) {
    	String sql = "Update breweries Set "
					+ "brewery_id=?,"
			    	+ "brewery_name=?,"
			    	+ "contact_info=?,"
			    	+ "open_time=?,"
			    	+ "close_time=?,"
			    	+ "address=?,"
			    	+ "history=?,"
			    	+ "isopen=?"
			    	+ "Where brewery_id=?;";
    	jdbcTemplate.update(sql, 
    			brewery.getId(),
    			brewery.getBreweryName(),
    			brewery.getContactInfo(),
    			brewery.getOpenTime(),
    			brewery.getCloseTime(),
    			brewery.getAddress(),
    			brewery.getHistory(),
    			brewery.isOpen(),
    			id);	
    	return brewery;
    }
    
    public Brewery updateBrewer(Long breweryId, Long brewerId) {
    	String sql = "UPDATE brewers SET brewery_id = ?, user_id = ? WHERE brewery_id = ?;";
    	jdbcTemplate.update(sql,breweryId,brewerId,breweryId);
		return getBreweryById(breweryId);
    }

    public Brewery create(Brewery newBrewery) {
        String sqlCreateBrewery = "INSERT INTO breweries (brewery_id, brewery_name, contact_info, open_time, close_time, address, history) " +
        						  "VALUES (?, ?, ?, ?, ?, ?, ?)";
        newBrewery.setId(getNextBreweryId());
        jdbcTemplate.update(sqlCreateBrewery, newBrewery.getId(), newBrewery.getBreweryName(), newBrewery.getContactInfo(), newBrewery.getOpenTime(), newBrewery.getCloseTime(), newBrewery.getAddress(), newBrewery.getHistory());
        String sqlBrewer = "INSERT INTO brewers (brewery_id) VALUES (?);";
        jdbcTemplate.update(sqlBrewer,newBrewery.getId());
        return newBrewery;
    }
    
	@Override
	public void delete(Long id) {
		String deleteBrewer = "DELETE FROM brewers WHERE brewery_id = ?;";
		jdbcTemplate.update(deleteBrewer, id);
        String deleteReview = "DELETE FROM reviews WHERE beer_id IN (SELECT beer_id FROM beer WHERE brewery_id = ?);";
        jdbcTemplate.update(deleteReview, id);
		String deleteBeers = "DELETE FROM beer WHERE brewery_id = ?;";
		jdbcTemplate.update(deleteBeers, id);
		String deleteBrewery = "DELETE FROM breweries WHERE brewery_id = ?;";
		jdbcTemplate.update(deleteBrewery, id);
	}

    private Brewery mapRowToBrewery(SqlRowSet rs) {
        Brewery brewery = new Brewery();
        brewery.setId(rs.getLong("brewery_id"));
        brewery.setBreweryName(rs.getString("brewery_name"));
        brewery.setContactInfo(rs.getString("contact_info"));
        brewery.setOpenTime(rs.getString("open_time"));
        brewery.setCloseTime(rs.getString("close_time"));
        brewery.setAddress(rs.getString("address"));
        brewery.setHistory(rs.getString("history"));
        brewery.setOpen(rs.getBoolean("isOpen"));
        return brewery;
    }
    
	private Long getNextBreweryId() {
		SqlRowSet nextId = jdbcTemplate.queryForRowSet("SELECT nextval ('seq_brewery_id')");
		if (nextId.next()) {
			return nextId.getLong(1);
		} else {
			throw new RuntimeException("Error unable to get next id");
		}
	}

}
