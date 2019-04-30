package model;

import static org.junit.Assert.*;
import org.junit.Test;

import ar.edu.unq.groupl.app.model.CreditSituationType;

public class CreditSituationTypeTest {
	
	@Test
	public void testValuesMustReturnAnArrayOfTwoCreditSituationTypeNormalAndRisk() {
		CreditSituationType[] creditSitutationTypes = {CreditSituationType.NORMAL, CreditSituationType.RISK};
		assertArrayEquals(creditSitutationTypes, CreditSituationType.values());
	}
	
	@Test
	public void testValueOfRiskMustReturnCreditSituationTypeRisk() {
		assertEquals(CreditSituationType.RISK, CreditSituationType.valueOf("RISK"));
	}
	
	@Test
	public void testValueOfNormalMustReturnCreditSituationTypeNormal() {
		assertEquals(CreditSituationType.NORMAL, CreditSituationType.valueOf("NORMAL"));
	}

}
