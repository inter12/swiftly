package config.spring.test.Repository;

import com.dianping.swiftly.core.entity.JobEntity;
import com.dianping.swiftly.core.Repository.dao.MySqlJobRepository;
import com.dianping.swiftly.utils.component.PrintHelper;
import config.spring.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-18
 *  Time: 上午11:46
 * 
 * </pre>
 */
public class JobRepositoryTest extends AbstractTest {

    @Autowired
    private MySqlJobRepository jobRepository;

    @Test
    public void loadAllEntitys() {

        List<JobEntity> jobEntities = jobRepository.loadAllEntities();
        PrintHelper.print(jobEntities);

    }

}
