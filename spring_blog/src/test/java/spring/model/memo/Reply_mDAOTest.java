package spring.model.memo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import spring.model.bbs.ReplyDTO;

public class Reply_mDAOTest {
    private static BeanFactory beans;
    private static Reply_mDAO rmdao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Resource resource = new ClassPathResource("blog.xml");
		beans = new XmlBeanFactory(resource);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		rmdao = (Reply_mDAO)beans.getBean("rmdao");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test @Ignore
	public void testRcount() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testCreate() {
		Reply_mDTO dto = new Reply_mDTO();
		dto.setMemono(966);
		dto.setContent("123");
		dto.setId("123");
		assertTrue(rmdao.create(dto));
	}

	@Test @Ignore
	public void testRead() {
     
   assertNotNull(rmdao.read(1));        
		
	}

	@Test @Ignore
	public void testUpdate() {
		Reply_mDTO dto = new Reply_mDTO();
		dto.setContent("댓글2로변경");
		dto.setRnum(2);
		assertTrue(rmdao.update(dto));
	}

	@Test @Ignore
	public void testList() {
		Map map = new HashMap();
		map.put("memono", 2);
		map.put("eno",5);
		map.put("sno",1);
		List<Reply_mDTO> list = rmdao.list(map);
	
	}

	@Test @Ignore
	public void testTotal() {
	    int memono = 966; // 글번호 
	    
	    assertEquals(rmdao.total(memono), 2);
	}

	@Test @Ignore
	public void testDelete() {
	  int memono = 2;
	  assertTrue(rmdao.delete(memono));
	}

	@Test 
	public void testBdelete() throws Exception {
		int memono = 966;
		assertEquals(rmdao.bdelete(memono),1);
	
	}

}
