package model;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unq.groupl.app.model.TransactionType;

public class TransactionTypeTest {

	@Test
	public void testValuesMustReturnAnArrayOfTwoTransactionTypeAddFundAndGetFund() {
		TransactionType[] transactionTypes = {TransactionType.ADDFUND, TransactionType.GETFUND};
		assertArrayEquals(transactionTypes, TransactionType.values());
	}
	
	@Test
	public void testValueOfAddFundMustReturnTransactionTypeAddFund() {
		assertEquals(TransactionType.ADDFUND, TransactionType.valueOf("ADDFUND"));
	}
	
	@Test
	public void testValueOfGetFundMustReturnTransactionTypeGetFund() {
		assertEquals(TransactionType.GETFUND, TransactionType.valueOf("GETFUND"));
	}
	
}
