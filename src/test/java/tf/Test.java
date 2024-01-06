package tf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tf.entity.SourceData;
import tf.server.SourceDataService;

import java.util.List;

@SpringBootTest
public class Test {

    @Autowired
    public SourceDataService sourceDataService;
    @org.junit.jupiter.api.Test
    public void test(){
       // List<SourceData> data = sourceDataService.queryTop10();

        System.out.println("test");

    }
}
