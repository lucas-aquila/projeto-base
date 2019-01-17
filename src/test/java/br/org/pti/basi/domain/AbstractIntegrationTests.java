package br.org.pti.basi.domain;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractIntegrationTests {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    /**
     * @throws IOException 
     *
     */
    @Before
    public void beforeTest() throws IOException { 
    	
    	File file = new File(getClass().getClassLoader().getResource("dataset/bootstrap.sql").getFile());
    	String script = FileUtils.readFileToString(file);

    	jdbcTemplate.execute(script);
        Locale.setDefault(new Locale("pt", "BR"));
    }
}
