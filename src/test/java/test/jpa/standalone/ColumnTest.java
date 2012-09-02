package test.jpa.standalone;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import test.jpa.entity.NumberEntity;

public class ColumnTest extends AbstractJPATest {
	
	@Test
	public void testGeneratedValue() {
		NumberEntity num1 = new NumberEntity();
		NumberEntity num2 = new NumberEntity();
		NumberEntity num3 = new NumberEntity();

		tx.begin();
		em.persist(num1);
		em.persist(num2);
		em.persist(num3);
		tx.commit();
		
		// 主キー以外では@GeneratedValueは機能しない
		assertThat(num1.getNum(), is(0L));
		assertThat(num2.getNum(), is(0L));
		assertThat(num3.getNum(), is(0L));
	}
}
