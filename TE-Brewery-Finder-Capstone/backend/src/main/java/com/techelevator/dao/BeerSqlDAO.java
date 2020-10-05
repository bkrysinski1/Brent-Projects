package com.techelevator.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.Beer;
import com.techelevator.model.User;

@Component
public class BeerSqlDAO implements BeerDAO {
	
	private JdbcTemplate jdbcTemplate;

    public BeerSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    

    @Override
    public List<Beer> findAll() {
        List<Beer> beers = new ArrayList<>();
        String sql = "SELECT * FROM beer";
    
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Beer beer = mapRowToBeer(results);
            beers.add(beer);
        }
        return beers;
    }

	@Override
	public Beer getBeerById(long beerId) {
		String sql = "SELECT * FROM beer WHERE beer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, beerId);
		if(results.next()) {
			return mapRowToBeer(results);
		} else {
			throw new RuntimeException("beerId "+beerId+" was not found.");
		}
	}
	
	 @Override
	    public Beer findByBeerName(String beerName) { //throws UsernameNotFoundException {
	        for (Beer beer : this.findAll()) {
	            if( beer.getBeerName().toLowerCase().equals(beerName.toLowerCase())) {
	                return beer;
	            }
	        }
	        throw new UsernameNotFoundException("Beer " + beerName + " was not found.");
	    }
	
	@Override
    public int findIdByBeerName(String beerName) {
        return jdbcTemplate.queryForObject("select beer_id from beer where beername = ?", int.class, beerName);
    }

    @Override
    public Beer create(Beer newBeer) {
        String sqlCreateBeer = "INSERT INTO beer (beer_id, brewery_id, beer_name, description, url, abv, beer_type) " +
        						"VALUES (?, ?, ?, ?, ?, ?, ?)";
        newBeer.setId(getNextBeerId());
        jdbcTemplate.update(sqlCreateBeer, newBeer.getId(), newBeer.getBreweryId(), newBeer.getBeerName(), newBeer.getDescription(), newBeer.getUrl(), newBeer.getAbv(), newBeer.getBeerType());
        return newBeer;
    }
    
    @Override
	public Beer update(Beer beer, Long id) {
		String sql = "Update beer Set "
				+ "beer_name=?,"
				+ "description=?,"
				+ "url=?,"
				+ "abv=?,"
				+ "beer_type=?"
				+ "Where beer_id=?";
		jdbcTemplate.update(sql, 
				beer.getBeerName(),
				beer.getDescription(),
				beer.getUrl(),
				beer.getAbv(),
				beer.getBeerType(),
				id);	
		return beer;
	}
    
    @Override
    public void delete(long id) {
        String deleteReview = "DELETE FROM reviews WHERE beer_id = ?;";
        jdbcTemplate.update(deleteReview, id);
    	String deleteBeer = "DELETE FROM beer WHERE beer_id = ?";
    	jdbcTemplate.update(deleteBeer, id);
    }

    private Beer mapRowToBeer(SqlRowSet rs) {
        Beer beer = new Beer();
        beer.setId(rs.getLong("beer_id"));
        beer.setBreweryId(rs.getLong("brewery_id"));
        beer.setBeerName(rs.getString("beer_name"));
        beer.setDescription(rs.getString("description"));
        beer.setUrl(rs.getString("url"));
        beer.setAbv(rs.getString("abv"));
        beer.setBeerType(rs.getString("beer_type"));
        return beer;
    }
    
    private Long getNextBeerId() {
    	SqlRowSet nextId = jdbcTemplate.queryForRowSet("SELECT nextval ('seq_beer_id')");
    	if (nextId.next()) {
    		return nextId.getLong(1);
    	} else {
    		throw new RuntimeException("Error unable to get next id");
    	}
    }

	@Override
	public void save(Beer newBeer) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Beer> getBeerByBreweryId(long breweryId) {
		List<Beer> beers = new ArrayList<>();
		String sql = "SELECT * FROM beer WHERE brewery_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, breweryId);
		while (results.next()) {
			Beer beer = mapRowToBeer(results);
			beers.add(beer);
		}
		return beers;
	}
}
