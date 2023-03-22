package com.ssa.hrmsCustomer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssa.hrmsCustomer.dto.model.ProjectDTO;
import com.ssa.hrmsCustomer.dto.mysqlentities.Country;

@SpringBootTest
public class HrmsCustomerTest {

	
	@Test
	public void testStream(){
		Country c1 = new Country();
		c1.setId(1);
		c1.setCountryName("india");
		Country c2 = new Country();
		c2.setId(1);
		c2.setCountryName("india");
		List<Integer> l1 = Arrays.asList(23,56,67,89);
		int sum = l1.stream().reduce((a,b)->a+b).get();
		assertNotSame(c1,c2);
		assertNotNull(c1);
	}

}
