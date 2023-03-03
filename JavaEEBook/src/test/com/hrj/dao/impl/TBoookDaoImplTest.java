package test.com.hrj.dao.impl;

import com.hrj.bean.TBook;
import com.hrj.dao.TBookDao;
import com.hrj.dao.impl.TBoookDaoImpl;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.math.BigDecimal;
import java.util.List;

/**
 * TBoookDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 3, 2022</pre>
 */
public class TBoookDaoImplTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: save(TBook tBook)
     */
    @Test
    public void testSave() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateById(TBook tBook)
     */
    @Test
    public void testUpdateById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteById(Integer id)
     */
    @Test
    public void testDeleteById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: findById(Integer id)
     */
    @Test
    public void testFindById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: page(Integer pageNumber)
     */
    @Test
    public void testPage() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: pageRecord()
     */
    @Test
    public void testPageRecord() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryForPageTotalCount()
     */
    @Test
    public void testQueryForPageTotalCount() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryForPageItems(int begin, int pageSize)
     */
    @Test
    public void testQueryForPageItemsForBeginPageSize() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryForPageTotalCount(String name, String author, BigDecimal min, BigDecimal max)
     */
    @Test
    public void testQueryForPageTotalCountForNameAuthorMinMax() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryForPageItems(int begin, int pageSize, String name, String author, BigDecimal min, BigDecimal max)
     */
    @Test
    public void testQueryForPageItemsForBeginPageSizeNameAuthorMinMax() throws Exception {
        TBookDao bookDao = new TBoookDaoImpl();
        List<TBook> tBooks = bookDao.queryForPageItems(0, 5, null, null, null, new BigDecimal(80));
        for (TBook tBook : tBooks) {
            System.out.println("tBook = " + tBook);
        }
    }

    /**
     * Method: queryForPageTotalCount(BigDecimal min, BigDecimal max)
     */
    @Test
    public void testQueryForPageTotalCountForMinMax() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryForPageItems(int begin, int pageSize, BigDecimal min, BigDecimal max)
     */
    @Test
    public void testQueryForPageItemsForBeginPageSizeMinMax() throws Exception {
//TODO: Test goes here... 
    }


} 
