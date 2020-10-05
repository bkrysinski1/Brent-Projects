package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	
//Money Tests for feed money
	@Test
	public void feed_money_test_1_dollar() {
		Money testObj = new Money();
		String actualResult = testObj.feedMoney(1);
		Assert.assertEquals("$1.00", actualResult);
	}
	@Test
	public void feed_money_test_2_dollars() {
		Money testObj = new Money();
		String actualResult = testObj.feedMoney(2);
		Assert.assertEquals("$2.00", actualResult);
	}
	@Test
	public void feed_money_test_5_dollars() {
		Money testObj = new Money();
		String actualResult = testObj.feedMoney(5);
		Assert.assertEquals("$5.00", actualResult);
	}
	@Test
	public void feed_money_test_10_dollars() {
		Money testObj = new Money();
		String actualResult = testObj.feedMoney(10);
		Assert.assertEquals("$10.00", actualResult);
	}
	
	
//Money Tests for invalid input	for feedMoney
	@Test
	public void feed_money_test_fail() {
		Money testObj = new Money();
		String actualResult = testObj.feedMoney(4);
		Assert.assertEquals("", actualResult);
	}
	@Test
	public void feed_money_test_fail2() {
		Money testObj = new Money();
		String actualResult = testObj.feedMoney(20);
		Assert.assertEquals("", actualResult);
	}
	
	
//Money Tests for get change
	@Test
	public void get_change_test() {
		Money testObj = new Money();
		String expectedResults = ("3 quarter(s) 1 dime(s) 0 nickel(s)");
		int input = 85;
		String actualResults = testObj.getChange(input);
		Assert.assertEquals(expectedResults, actualResults); 
	}
	@Test
	public void get_change_test2() {
		Money testObj = new Money();
		String expectedResults = ("3 quarter(s) 0 dime(s) 1 nickel(s)");
		int input = 80;
		String actualResults = testObj.getChange(input);
		Assert.assertEquals(expectedResults, actualResults); 
	}
	@Test
	public void get_change_test3() {
		Money testObj = new Money();
		String expectedResults = ("3 quarter(s) 1 dime(s) 1 nickel(s)");
		int input = 90;
		String actualResults = testObj.getChange(input);
		Assert.assertEquals(expectedResults, actualResults); 
	}
	@Test
	public void get_change_test4() {
		Money testObj = new Money();
		String expectedResults = ("3 quarter(s) 2 dime(s) 0 nickel(s)");
		int input = 95;
		String actualResults = testObj.getChange(input);
		Assert.assertEquals(expectedResults, actualResults); 
	}
	@Test
	public void get_change_test5() {
		Money testObj = new Money();
		String expectedResults = ("3 quarter(s) 0 dime(s) 0 nickel(s)");
		int input = 75;
		String actualResults = testObj.getChange(input);
		Assert.assertEquals(expectedResults, actualResults); 
	}
}
